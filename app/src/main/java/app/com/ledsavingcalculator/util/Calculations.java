package app.com.ledsavingcalculator.util;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;

import com.alamkanak.weekview.WeekViewEvent;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.com.ledsavingcalculator.database.dao.ExistingBulb;
import app.com.ledsavingcalculator.database.dao.ReplacementBulb;

public class Calculations {

    public static final String ON_LOAD_START_TIME = "01/14/2012 7:00:00";
    public static final String ON_LOAD_END_TIME = "01/14/2012 11:00:00";
    public static final String ON_LOAD_EVN_START_TIME = "01/14/2012 17:00:00";
    public static final String ON_LOAD_EVN_END_TIME = "01/14/2012 19:00:00";
    public static final String PEAK_LOAD_START_TIME = "01/14/2012 11:00:00";
    public static final String PEAK_LOAD_END_TIME = "01/14/2012 17:00:00";
    public static final String OFF_LOAD_START_TIME = "01/14/2012 19:00:00";
    public static final String OFF_LOAD_END_TIME = "01/14/2012 24:00:00";
    public static final String OFF_LOAD_START_TIME_1 = "01/14/2012 1:00:00";
    public static final String OFF_LOAD_END_TIME_1 = "01/14/2012 7:00:00";

    float intialLedResult = (float) 0.00;
    float costOfExistingBulbReplacement = (float) 0.00;
    float labourCostSavings = (float) 0.0;
    private double MonthlyElectricityCostOfExistingBulb;


    public float getIntialLedCost(float costOfLabour, float costOfBulb, float noOfExistingBulbs) {
        intialLedResult = (costOfLabour + costOfBulb) * noOfExistingBulbs;
        return (intialLedResult);
    }

    public TimeSlot getHoursOfTime(String timeStart, String timeEnd) {

        String dateStart = "01/14/2012 " + timeStart.trim();
        String dateStop = "01/14/2012 " + timeEnd.trim();

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        try {
            Date eventStartTime = format.parse(dateStart);
            Date eventEndTime = format.parse(dateStop);
            Date onLoadStartTime = format.parse(ON_LOAD_START_TIME);
            Date onLoadEndTime = format.parse(ON_LOAD_END_TIME);
            Date peakloadStartTime = format.parse(PEAK_LOAD_START_TIME);
            Date onLoadStartTime1 = format.parse(ON_LOAD_EVN_START_TIME);
            Date peakloadEndTime = format.parse(PEAK_LOAD_END_TIME);
            Date onLoadEndTime1 = format.parse(ON_LOAD_EVN_END_TIME);
            Date offloadStartTime = format.parse(OFF_LOAD_START_TIME);
            Date offloadEndTime = format.parse(OFF_LOAD_END_TIME);
            Date offloadEndTime1 = format.parse(OFF_LOAD_END_TIME_1);
            Date offloadStartTime1 = format.parse(OFF_LOAD_START_TIME_1);

            Hours onload = new Hours(0L, 0L);
            Hours peakload = new Hours(0L, 0L);
            Hours onload1 = new Hours(0L, 0L);
            Hours offload = new Hours(0L, 0L);
            Hours offload1 = new Hours(0L, 0L);

            if (eventStartTime.getTime() >= onLoadStartTime.getTime()
                    && eventEndTime.getTime() <= onLoadEndTime.getTime()) {
                return new TimeSlot(getTimeDifference(eventStartTime, eventEndTime), peakload, offload);
            } else if (eventEndTime.getTime() >= onLoadEndTime.getTime()
                    && eventStartTime.getTime() <= onLoadStartTime.getTime()) {
                onload = getTimeDifference(onLoadStartTime, onLoadEndTime);
            } else if (eventStartTime.getTime() >= onLoadStartTime.getTime()
                    && eventEndTime.getTime() >= onLoadEndTime.getTime()) {
                onload = getTimeDifference(eventStartTime, onLoadEndTime);
            } else if (eventStartTime.getTime() <= onLoadStartTime.getTime()) {
                if (eventEndTime.getTime() <= onLoadEndTime.getTime()) {
                    onload = getTimeDifference(onLoadStartTime, eventEndTime);
                } else {
                    onload = getTimeDifference(onLoadStartTime, onLoadEndTime);
                }
            }

            if (eventStartTime.getTime() >= onLoadStartTime1.getTime()
                    && eventEndTime.getTime() <= onLoadEndTime1.getTime()) {
                return new TimeSlot(getTimeDifference(eventStartTime, eventEndTime), peakload, offload);
            } else if (eventEndTime.getTime() >= onLoadEndTime1.getTime()
                    && eventStartTime.getTime() <= onLoadStartTime1.getTime()) {
                onload1 = getTimeDifference(onLoadStartTime1, onLoadEndTime1);
            } else if (eventStartTime.getTime() >= onLoadStartTime1.getTime()
                    && eventEndTime.getTime() >= onLoadEndTime1.getTime()) {
                onload1 = getTimeDifference(eventStartTime, onLoadEndTime1);
            } else if (eventStartTime.getTime() <= onLoadStartTime1.getTime()) {
                if (eventEndTime.getTime() <= onLoadEndTime1.getTime()) {
                    onload1 = getTimeDifference(onLoadStartTime1, eventEndTime);
                } else {
                    onload1 = getTimeDifference(onLoadStartTime1, onLoadEndTime1);
                }
            }

            onload = calculateOffLoad(onload, onload1);



            if (eventStartTime.getTime() >= peakloadStartTime.getTime()
                    && eventEndTime.getTime() <= peakloadEndTime.getTime()) {
                return new TimeSlot(offload, getTimeDifference(eventStartTime, eventEndTime), offload);
            } else if (eventEndTime.getTime() >= peakloadStartTime.getTime()) {
                if (eventStartTime.getTime() >= peakloadStartTime.getTime()
                        && eventEndTime.getTime() >= offloadStartTime.getTime()) {
                    peakload = getTimeDifference(eventStartTime, peakloadEndTime);
                } else if (eventEndTime.getTime() <= peakloadEndTime.getTime()) {
                    peakload = getTimeDifference(peakloadStartTime, eventEndTime);
                } else {
                    peakload = getTimeDifference(peakloadStartTime, peakloadEndTime);
                }
            }

            if ((eventStartTime.getTime() >= offloadStartTime.getTime()
                    && eventEndTime.getTime() <= offloadEndTime.getTime())
                    ||
                    (eventStartTime.getTime() >= offloadStartTime1.getTime()
                            && eventEndTime.getTime() <= offloadEndTime1.getTime())) {
                return new TimeSlot(offload, peakload, getTimeDifference(eventStartTime, eventEndTime));
            } else if (eventEndTime.getTime() >= offloadStartTime.getTime()) {
                if (eventStartTime.getTime() >= offloadStartTime.getTime()) {
                    offload = getTimeDifference(eventStartTime, eventEndTime);
                } else {
                    offload = getTimeDifference(offloadStartTime, eventEndTime);
                }
            }
            if (eventStartTime.getTime() >= offloadStartTime1.getTime()
                    && (eventEndTime.getTime() >= onLoadStartTime.getTime())) {
                if (eventEndTime.getTime() >= onLoadStartTime.getTime()) {
                    offload1 = getTimeDifference(eventStartTime, offloadEndTime1);
                } else {
                    offload1 = getTimeDifference(offloadStartTime1, eventEndTime);
                }
            }

            offload = calculateOffLoad(offload, offload1);

            return new TimeSlot(onload, peakload, offload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Throw runtime exception
        return null;
    }

    private Hours calculateOffLoad(Hours offload, Hours offload1) {
        long totalOffLoadHours = offload.getNumberOfHours() + offload1.getNumberOfHours();
        long totalOffLoadMinutes = offload.getNumberOfMins() + offload1.getNumberOfMins();

        if (totalOffLoadMinutes > 60) {
            totalOffLoadHours += 1;
            totalOffLoadMinutes -= 60;
        }
        return new Hours(totalOffLoadHours, totalOffLoadMinutes);
    }

    private Hours getTimeDifference(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;

        //If start event is not in the give slot then you will get -1
        if (diffHours < 0) {
            diffHours = 0;
        }
        if (diffMinutes < 0) {
            diffMinutes = 0;
        }
        return new Hours(diffHours, diffMinutes);
    }

    public float getLabourCostSaving(float intialLedCost, float existingBulbReplacementCost) {
        labourCostSavings = existingBulbReplacementCost - intialLedCost;
        return labourCostSavings;
    }

/*
    public double getWeeklyElectricityCostOfExistingBulb(int onLoadCount, int offloadCount, int offloadCount1, float existingWattOfBulb, Double offLoadPrice, Double onLoadPrice, Double onLoadPrice1, int noOfBulbs) {
        double totalOnloadPrice = ((existingWattOfBulb * onLoadCount) / 1000) * (onLoadPrice / 100);
        double totalPeakloadPrice = ((existingWattOfBulb * offloadCount) / 1000) * (offLoadPrice / 100);
        double totalOffloadPrice = ((existingWattOfBulb * offloadCount1) / 1000) * (onLoadPrice1 / 100);

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        //totalOnloadPrice = Double.valueOf(twoDForm.format(totalOnloadPrice));
        //totalPeakloadPrice = Double.valueOf(twoDForm.format(totalPeakloadPrice));
        //totalOffloadPrice = Double.valueOf(twoDForm.format(totalOffloadPrice));

        double totalEnergy = totalOnloadPrice + totalPeakloadPrice + totalOffloadPrice;
        MonthlyElectricityCostOfExistingBulb = totalEnergy * 20 * noOfBulbs;
        MonthlyElectricityCostOfExistingBulb = Double.valueOf(twoDForm.format(MonthlyElectricityCostOfExistingBulb));
        return MonthlyElectricityCostOfExistingBulb;
    }
*/

    public Double getTotalEnergySaving(int ledBulbLifespan, double existingBulbReplacementCost, PerDayData totalHours, Double monthlyEnergySaving) {

       Hours sundayToalHours = addThreeToObj(totalHours.sundayData.getOfLoad(), totalHours.sundayData.getPeakLoad(),
               totalHours.sundayData.getOnLoad());
      double sunday = getMonthlyCostForExisting(ledBulbLifespan, existingBulbReplacementCost, sundayToalHours.getNumberOfHours(),
              sundayToalHours.getNumberOfMins(), monthlyEnergySaving);

       // long sundayToalHours = totalHours.sundayData.getOfLoad().getNumberOfHours() +
             //   totalHours.sundayData.getOnLoad().getNumberOfHours()+ totalHours.sundayData.getPeakLoad().getNumberOfHours();
      //  long sundayTotalMin = addThreeToObj(totalHours.sundayData.getOfLoad().getNumberOfMins() ,
             //   totalHours.sundayData.getOnLoad().getNumberOfMins(), totalHours.sundayData.getPeakLoad().getNumberOfMins();

        //double sunday = getMonthlyCostForExisting(ledBulbLifespan, existingBulbReplacementCost, sundayToalHours, monthlyEnergySaving);

        Hours mondayToalHours = addThreeToObj(totalHours.mondayData.getOfLoad(), totalHours.mondayData.getPeakLoad(),
                totalHours.mondayData.getOnLoad());
        double monday = getMonthlyCostForExisting(ledBulbLifespan, existingBulbReplacementCost, mondayToalHours.getNumberOfHours(),
                mondayToalHours.getNumberOfMins(), monthlyEnergySaving);

        Hours tuesdayToalHours = addThreeToObj(totalHours.tuesdayData.getOfLoad(), totalHours.tuesdayData.getPeakLoad(),
                totalHours.tuesdayData.getOnLoad());
        double tuesday = getMonthlyCostForExisting(ledBulbLifespan, existingBulbReplacementCost, tuesdayToalHours.getNumberOfHours(), tuesdayToalHours.getNumberOfMins(), monthlyEnergySaving);

        Hours wednesdayToalHours = addThreeToObj(totalHours.wednesdayData.getOfLoad(), totalHours.wednesdayData.getPeakLoad(),
                totalHours.wednesdayData.getOnLoad());
        double wednesday = getMonthlyCostForExisting(ledBulbLifespan, existingBulbReplacementCost, wednesdayToalHours.getNumberOfHours(), wednesdayToalHours.getNumberOfMins(), monthlyEnergySaving);

        Hours thursdayToalHours = addThreeToObj(totalHours.thursdayData.getOfLoad(), totalHours.thursdayData.getPeakLoad(),
                totalHours.thursdayData.getOnLoad());
        double thursday = getMonthlyCostForExisting(ledBulbLifespan, existingBulbReplacementCost, thursdayToalHours.getNumberOfHours(),
                thursdayToalHours.getNumberOfMins(), monthlyEnergySaving);

        Hours fridayToalHours = addThreeToObj(totalHours.fridayData.getOfLoad(), totalHours.fridayData.getPeakLoad(),
                totalHours.fridayData.getOnLoad());
        double friday = getMonthlyCostForExisting(ledBulbLifespan, existingBulbReplacementCost, fridayToalHours.getNumberOfHours(), fridayToalHours.getNumberOfMins(), monthlyEnergySaving);

        Hours saturdayToalHours = addThreeToObj(totalHours.saturdayData.getOfLoad(), totalHours.saturdayData.getPeakLoad(),
                totalHours.saturdayData.getOnLoad());
        double saturday = getMonthlyCostForExisting(ledBulbLifespan, existingBulbReplacementCost, saturdayToalHours.getNumberOfHours(), saturdayToalHours.getNumberOfMins(), monthlyEnergySaving);

        return sunday+monday+wednesday+thursday+friday+saturday+tuesday;
    }

    private Hours addThreeToObj(Hours ofLoad, Hours peakLoad, Hours onLoad) {
        long hour =ofLoad.getNumberOfHours() + peakLoad.getNumberOfHours() +  onLoad.getNumberOfHours();
        long min = ofLoad.getNumberOfMins() + peakLoad.getNumberOfMins() + onLoad.getNumberOfMins();

        if(min > 60){
            Hours newHours = recursiveMinCheck(min);
            hour = hour + newHours.getNumberOfHours();
            min =  min + newHours.getNumberOfMins();
        }
        return  new Hours(hour, min);
    }

    private Hours recursiveMinCheck(long min) {
       long newMin = min-60;
        long hour =+ 1;
        if(newMin > 60){
             recursiveMinCheck(newMin);
        }
        return new Hours(hour, newMin);
    }

    @NonNull
    private Double getMonthlyCostForExisting(int ledBulbLifespan, double existingBulbReplacementCost, float totalHours, float totalMin, Double monthlyEnergySaving) {
        totalHours = (float) (totalHours + (totalMin/60.0));
        float lifeSpanIntoYears = (float) (ledBulbLifespan / (totalHours  * 20.0 * 12.0));
        double costSavingPerYear = existingBulbReplacementCost / lifeSpanIntoYears;
        Double costSavingPerMonth = costSavingPerYear / 12;
        Double totalEnergySaving = costSavingPerMonth + monthlyEnergySaving;
        return totalEnergySaving;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public PerDayData getTotalHoursPerDay(List<WeekViewEvent> events) throws ParseException {

        //If day do not have the event then time slot will have zero values
        TimeSlot sundayTotalHours = new TimeSlot();
        TimeSlot mondayTotalHours = new TimeSlot();
        TimeSlot tuesdayTotalHours = new TimeSlot();
        TimeSlot thursdayTotalHours = new TimeSlot();
        TimeSlot fridayTotalHours = new TimeSlot();
        TimeSlot saturdayTotalHours = new TimeSlot();
        TimeSlot wednesdayTotalHours = new TimeSlot();

        for (WeekViewEvent weekView : events) {

            int startHour = weekView.getStartTime().get(Calendar.HOUR_OF_DAY);
            int startMinute = weekView.getStartTime().get(Calendar.MINUTE);
            int weekDay = weekView.getStartTime().get(Calendar.DAY_OF_WEEK);
            String startTime = startHour + ":" + startMinute + ":" + "00";

            int endHour = weekView.getEndTime().get(Calendar.HOUR_OF_DAY);
            int endMinute = weekView.getEndTime().get(Calendar.MINUTE);

            String endTime = " " + endHour + ":" + endMinute + ":" + "00";
            weekView.getStartTime();

            if (weekDay == 1) {
                if (sundayTotalHours != null) {
                    TimeSlot sundayTotalHours1 = getHoursOfTime(startTime, endTime);
                    sundayTotalHours = addToObj(sundayTotalHours1, sundayTotalHours);
                   // new PerDayData(sundayTotalHours, null, null, null, null, null, null);
                } else {
                    sundayTotalHours = getHoursOfTime(startTime, endTime);
                  //  new PerDayData(sundayTotalHours, null, null, null, null, null, null);
                }
            }
            if (weekDay == 2) {

                if (mondayTotalHours != null) {
                    TimeSlot mondayTotalHours1 = getHoursOfTime(startTime, endTime);
                    mondayTotalHours = addToObj(mondayTotalHours1, mondayTotalHours);
                   // new PerDayData(null, mondayTotalHours, null, null, null, null, null);
                } else {
                    mondayTotalHours = getHoursOfTime(startTime, endTime);
                   // new PerDayData(null, mondayTotalHours, null, null, null, null, null);
                }
            }
            if (weekDay == 3) {
                if (tuesdayTotalHours != null) {
                    TimeSlot tuesdayTotalHours1 = getHoursOfTime(startTime, endTime);
                    tuesdayTotalHours = addToObj(tuesdayTotalHours1, tuesdayTotalHours);
                   // new PerDayData(null, tuesdayTotalHours, null, null, null, null, null);
                } else {
                    tuesdayTotalHours = getHoursOfTime(startTime, endTime);
                  //  new PerDayData(null, null, tuesdayTotalHours, null, null, null, null);
                }
            }

            if (weekDay == 4) {
                if (wednesdayTotalHours != null) {
                    TimeSlot wednesdayTotalHours1 = getHoursOfTime(startTime, endTime);
                    wednesdayTotalHours = addToObj(wednesdayTotalHours1, wednesdayTotalHours);
                    //new PerDayData(null, null, null, wednesdayTotalHours, null, null, null);
                } else {
                    wednesdayTotalHours = getHoursOfTime(startTime, endTime);
                    //new PerDayData(null, null, null, wednesdayTotalHours, null, null, null);
                }
            }
            if (weekDay == 5) {
                if (thursdayTotalHours != null) {
                    TimeSlot thursdayTotalHours1 = getHoursOfTime(startTime, endTime);
                    thursdayTotalHours = addToObj(thursdayTotalHours1, thursdayTotalHours);
                    //new PerDayData(null, null, null, null, thursdayTotalHours, null, null);
                } else {
                    thursdayTotalHours = getHoursOfTime(startTime, endTime);
                   // new PerDayData(null, null, null, null, thursdayTotalHours, null, null);
                }
            }
            if (weekDay == 6) {
                if (fridayTotalHours != null) {
                    TimeSlot fridayTotalHours1 = getHoursOfTime(startTime, endTime);
                    fridayTotalHours = addToObj(fridayTotalHours1, fridayTotalHours);
                   // new PerDayData(null, null, null, null, null, fridayTotalHours, null);
                } else {
                    fridayTotalHours = getHoursOfTime(startTime, endTime);
                   // new PerDayData(null, null, null, null, null, fridayTotalHours, null);
                }
            }
            if (weekDay == 7) {
                if (saturdayTotalHours != null) {
                    TimeSlot saturdayTotalHours1 = getHoursOfTime(startTime, endTime);
                    saturdayTotalHours = addToObj(saturdayTotalHours1, saturdayTotalHours);
                    //new PerDayData(null, null, null, null, null, null, saturdayTotalHours);
                } else {
                    saturdayTotalHours = getHoursOfTime(startTime, endTime);
                   // new PerDayData(null, null, null, null, null, null, saturdayTotalHours);
                }
            }


            //SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            //Date date1 = format.parse(startTime);
            //Date date2 = format.parse(endTime);
            }

        return new PerDayData(sundayTotalHours, mondayTotalHours, tuesdayTotalHours, wednesdayTotalHours, thursdayTotalHours, fridayTotalHours, saturdayTotalHours);
    }

    private TimeSlot addToObj(TimeSlot mondayTotalHours1, TimeSlot mondayTotalHours) {

        long onload = mondayTotalHours.getOnLoad().getNumberOfHours() + mondayTotalHours1.getOnLoad().getNumberOfHours();
        long onloadmin = mondayTotalHours.getOnLoad().getNumberOfMins() + mondayTotalHours1.getOnLoad().getNumberOfMins();

        long ofload = mondayTotalHours.getOfLoad().getNumberOfHours() + mondayTotalHours1.getOfLoad().getNumberOfHours();
        long ofloadmin = mondayTotalHours.getOfLoad().getNumberOfMins() + mondayTotalHours1.getOfLoad().getNumberOfMins();

        long peakload = mondayTotalHours.getPeakLoad().getNumberOfHours() + mondayTotalHours1.getPeakLoad().getNumberOfHours();
        long peakloadmin = mondayTotalHours.getPeakLoad().getNumberOfMins() + mondayTotalHours1.getPeakLoad().getNumberOfMins();

        Hours onloadHours = new Hours(onload, onloadmin);
        Hours ofloadHours = new Hours(ofload, ofloadmin);
        Hours peakloadHours = new Hours(peakload, peakloadmin);

        return new TimeSlot(onloadHours, peakloadHours, ofloadHours);
    }

    public float getWeeklyElectricityCostOfExistingBulb(TimeSlot dayData, int wattageOfBulb,
                                                         double onloadCost, double peakloadCost, double offloadCost, int noOfFixtures, int noOfBulbsPerFixture) {
        float onloadMinToHour =0.0f;
        float ofloadMinToHour =0.0f;
        float peakloadHour = 0.0f;
        float peakloadMinToHour= 0.0f;
        DecimalFormat f = new DecimalFormat("##.00");

        if(dayData.getOnLoad().getNumberOfMins()!= 0){
          onloadMinToHour = (float) ((dayData.getOnLoad().getNumberOfMins())/60.0);
            onloadMinToHour = Float.parseFloat(f.format(onloadMinToHour));

        }
        if(dayData.getOfLoad().getNumberOfMins()!= 0){
            ofloadMinToHour = (float) (dayData.getOfLoad().getNumberOfMins()/60.0);
            ofloadMinToHour = Float.parseFloat(f.format(ofloadMinToHour));
        }

        if(dayData.getPeakLoad().getNumberOfMins()!= 0){
            peakloadMinToHour = (float) ((dayData.getPeakLoad().getNumberOfMins()) / 60.0);
            peakloadMinToHour = Float.parseFloat(f.format(peakloadMinToHour));
        }

        float ofloadHour = dayData.getOfLoad().getNumberOfHours() + ofloadMinToHour;
        float onloadHour = dayData.getOnLoad().getNumberOfHours() + onloadMinToHour;
        peakloadHour = dayData.getPeakLoad().getNumberOfHours() + peakloadMinToHour;

        float totalOnloadPrice =(float) ((wattageOfBulb * onloadHour / 1000) * (onloadCost / 100));
        float totalPeakloadPrice = (float)((wattageOfBulb * peakloadHour / 1000) * (peakloadCost / 100));
        float totalOffloadPrice = (float)((wattageOfBulb * ofloadHour / 1000) * (offloadCost / 100));

        DecimalFormat twoDForm = new DecimalFormat();
        twoDForm.setMaximumFractionDigits(2);
        //totalOnloadPrice = Double.valueOf(twoDForm.format(totalOnloadPrice));
        //totalPeakloadPrice = Double.valueOf(twoDForm.format(totalPeakloadPrice));
        //totalOffloadPrice = Double.valueOf(twoDForm.format(totalOffloadPrice));

        Float totalEnergy = totalOnloadPrice + totalPeakloadPrice + totalOffloadPrice;
        MonthlyElectricityCostOfExistingBulb = totalEnergy * noOfBulbsPerFixture  * noOfFixtures;
        //MonthlyElectricityCostOfExistingBulb = Float.parseFloat(twoDForm.format(MonthlyElectricityCostOfExistingBulb));
        return  Float.parseFloat(twoDForm.format(MonthlyElectricityCostOfExistingBulb));
    }

    public Double getMonthlyCostForExistingBulb(PerDayData totalHoursPerDay, ExistingBulb lastRow, double onloadCost, double peakloadCost, double offloadCost) {
        return getMonthlyCostForExisting(totalHoursPerDay, lastRow, onloadCost, peakloadCost, offloadCost);
    }

    @NonNull
    private Double getMonthlyCostForExisting(PerDayData totalHoursPerDay, ExistingBulb lastRow, double onloadCost, double peakloadCost, double offloadCost) {
       //double CostOfExistingBulbForSunday = 0.0;

       double CostOfExistingBulbForSunday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.sundayData, lastRow.getWattageOfBulb(), offloadCost,
              offloadCost, offloadCost, lastRow.getNoOfFixtures(), lastRow.getNoOfBulbsPerFixture());
        double CostOfExistingBulbForMonday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.mondayData, lastRow.getWattageOfBulb(), onloadCost,
                peakloadCost, offloadCost, lastRow.getNoOfFixtures(), lastRow.getNoOfBulbsPerFixture());
        double CostOfExistingBulbForTuesday= getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.tuesdayData, lastRow.getWattageOfBulb(), onloadCost,
                peakloadCost, offloadCost, lastRow.getNoOfFixtures(), lastRow.getNoOfBulbsPerFixture());
        double CostOfExistingBulbForThursday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.thursdayData, lastRow.getWattageOfBulb(), onloadCost,
                peakloadCost, offloadCost, lastRow.getNoOfFixtures(), lastRow.getNoOfBulbsPerFixture());
        double CostOfExistingBulbForFriday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.fridayData, lastRow.getWattageOfBulb(), onloadCost,
                peakloadCost, offloadCost, lastRow.getNoOfFixtures(), lastRow.getNoOfBulbsPerFixture());
        double CostOfExistingBulbForWednesDay = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.wednesdayData, lastRow.getWattageOfBulb(), onloadCost,
                peakloadCost, offloadCost, lastRow.getNoOfFixtures(), lastRow.getNoOfBulbsPerFixture());
       // double CostOfExistingBulbForSaturday = 0.0;
        double CostOfExistingBulbForSaturday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.saturdayData, lastRow.getWattageOfBulb(), offloadCost,
               offloadCost, offloadCost, lastRow.getNoOfFixtures(), lastRow.getNoOfBulbsPerFixture());

        double weekDevidedByMonth = (double)52/12;

        double weeklyCost =  ((CostOfExistingBulbForSunday + CostOfExistingBulbForMonday + CostOfExistingBulbForTuesday + CostOfExistingBulbForThursday + CostOfExistingBulbForFriday +  CostOfExistingBulbForWednesDay + CostOfExistingBulbForSaturday) * weekDevidedByMonth);

        DecimalFormat f = new DecimalFormat("##.00");
        weeklyCost = Double.parseDouble(f.format(weeklyCost));

         return weeklyCost;
    }


    public float getMonthlyCostForReplacementBulb(PerDayData totalHoursPerDay, ReplacementBulb replacementBulbRecord, double onloadCost, double peakloadCost, double offloadCost, int noOfFixtures, int noOfBulbsPerFixture) {
     //float CostOfExistingBulbForSunday= 0.0f;

       float CostOfExistingBulbForSunday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.sundayData, replacementBulbRecord.getWattageOfReplacementBulb(), offloadCost,
              offloadCost, offloadCost, noOfFixtures, noOfBulbsPerFixture);
        float CostOfExistingBulbForMonday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.mondayData, replacementBulbRecord.getWattageOfReplacementBulb(), onloadCost,
                peakloadCost, offloadCost, noOfFixtures, noOfBulbsPerFixture);
        float CostOfExistingBulbForTuesday= getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.tuesdayData, replacementBulbRecord.getWattageOfReplacementBulb(), onloadCost,
                peakloadCost, offloadCost, noOfFixtures, noOfBulbsPerFixture);
        float CostOfExistingBulbForThursday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.thursdayData, replacementBulbRecord.getWattageOfReplacementBulb(), onloadCost,
                peakloadCost, offloadCost, noOfFixtures, noOfBulbsPerFixture);
        float CostOfExistingBulbForFriday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.fridayData, replacementBulbRecord.getWattageOfReplacementBulb(), onloadCost,
                peakloadCost, offloadCost, noOfFixtures, noOfBulbsPerFixture);
        float CostOfExistingBulbForWednesDay = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.wednesdayData, replacementBulbRecord.getWattageOfReplacementBulb(), onloadCost,
                peakloadCost, offloadCost, noOfFixtures,noOfBulbsPerFixture);
       float CostOfExistingBulbForSaturday = getWeeklyElectricityCostOfExistingBulb(totalHoursPerDay.saturdayData, replacementBulbRecord.getWattageOfReplacementBulb(), offloadCost,
               offloadCost, offloadCost, noOfFixtures, noOfBulbsPerFixture);

        //float CostOfExistingBulbForSaturday = 0.0f;

        float divideWeekByMonths = (float)(52.0/12.0);

        float weeklyCost =  (CostOfExistingBulbForSunday + CostOfExistingBulbForMonday  + CostOfExistingBulbForTuesday  + CostOfExistingBulbForThursday +
                CostOfExistingBulbForFriday  +  CostOfExistingBulbForWednesDay  + CostOfExistingBulbForSaturday ) * divideWeekByMonths  ;

        DecimalFormat f = new DecimalFormat("##.00");
        weeklyCost = Float.parseFloat(f.format(weeklyCost));

        return weeklyCost;
    }

    public float getCostOfExistingBulbReplacement(ExistingBulb lastRow, int lifeSpan, float labourCostPerBulb) {

        DecimalFormat f = new DecimalFormat("##.00");
        costOfExistingBulbReplacement = (lifeSpan / lastRow.getLifeSpan()) * lastRow.getNoOfFixtures() * lastRow.getNoOfBulbsPerFixture() * (lastRow.getCostOfBulb() + labourCostPerBulb);
        costOfExistingBulbReplacement = Float.parseFloat(f.format(costOfExistingBulbReplacement));
        return costOfExistingBulbReplacement;
    }

    public double getWeeklyActiveHour(PerDayData totalHoursPerDay) {
        Hours sundayTotalHours = addThreeToObj(totalHoursPerDay.sundayData.getOfLoad(), totalHoursPerDay.sundayData.getPeakLoad(),
                totalHoursPerDay.sundayData.getOnLoad());
        Hours mondayTotalHours = addThreeToObj(totalHoursPerDay.mondayData.getOfLoad(), totalHoursPerDay.mondayData.getPeakLoad(),
                totalHoursPerDay.mondayData.getOnLoad());
        Hours tuesdayTotalHours = addThreeToObj(totalHoursPerDay.tuesdayData.getOfLoad(), totalHoursPerDay.tuesdayData.getPeakLoad(),
                totalHoursPerDay.tuesdayData.getOnLoad());
        Hours wednesDayTotalHours = addThreeToObj(totalHoursPerDay.wednesdayData.getOfLoad(), totalHoursPerDay.wednesdayData.getPeakLoad(),
                totalHoursPerDay.wednesdayData.getOnLoad());
        Hours thursdayTotalHours = addThreeToObj(totalHoursPerDay.thursdayData.getOfLoad(), totalHoursPerDay.thursdayData.getPeakLoad(),
                totalHoursPerDay.thursdayData.getOnLoad());
        Hours fridayTotalHours = addThreeToObj(totalHoursPerDay.fridayData.getOfLoad(), totalHoursPerDay.fridayData.getPeakLoad(),
                totalHoursPerDay.fridayData.getOnLoad());
        Hours saturdayTotalHours = addThreeToObj(totalHoursPerDay.saturdayData.getOfLoad(), totalHoursPerDay.saturdayData.getPeakLoad(),
                totalHoursPerDay.saturdayData.getOnLoad());

        double hours  = sundayTotalHours.getNumberOfHours()+mondayTotalHours.getNumberOfHours()+tuesdayTotalHours.getNumberOfHours()+wednesDayTotalHours.getNumberOfHours()+
               thursdayTotalHours.getNumberOfHours()+ fridayTotalHours.getNumberOfHours() + saturdayTotalHours.getNumberOfHours();

        long min = sundayTotalHours.getNumberOfHours() + mondayTotalHours.getNumberOfMins() + tuesdayTotalHours.getNumberOfMins()+
                        wednesDayTotalHours.getNumberOfMins() + thursdayTotalHours.getNumberOfMins() + fridayTotalHours.getNumberOfMins() +
                        saturdayTotalHours.getNumberOfMins();

        Hours minToHours = recursiveMinCheck(min);

        hours = hours + minToHours.getNumberOfHours();
        hours = hours + minToHours.getNumberOfMins()/60.0;
        return  hours;
    }
}
