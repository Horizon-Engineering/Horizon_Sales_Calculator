package app.com.ledsavingcalculator.util;


import org.junit.Test;

import java.text.ParseException;

import app.com.ledsavingcalculator.database.dao.ReplacementBulb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CalculationsTest {

    @Test
    public  void testGetHourTime() throws ParseException {
        Calculations calculations = new Calculations();
        TimeSlot numberOfHours = calculations.getHoursOfTime(" 06:30:00", " 23:00:00");
        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(6L));
        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(0L));
        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(6L));
        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(0L));
        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(4L));
        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(30L));

        numberOfHours = calculations.getHoursOfTime(" 11:00:00", " 16:59:00");
        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(0L));
        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(0L));
        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(5L));
        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(59L));
        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(0L));
        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(0L));

//        numberOfHours = calculations.getHoursOfTime(" 17:00:00", " 23:59:00");
//        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(0L));
//        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(0L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(0L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(0L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(6L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(59L));
//
//        numberOfHours = calculations.getHoursOfTime(" 1:00:00", " 6:59:00");
//        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(0L));
//        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(0L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(0L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(0L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(5L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(59L));
//
//        numberOfHours = calculations.getHoursOfTime(" 7:00:00", " 11:30:00");
//        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(3L));
//        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(59L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(0L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(30L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(0L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(0L));
//
//        numberOfHours = calculations.getHoursOfTime(" 14:00:00", " 20:30:00");
//        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(0L));
//        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(0L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(2L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(59L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(3L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(30L));
//
//        numberOfHours = calculations.getHoursOfTime(" 8:00:00", " 20:30:00");
//        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(2L));
//        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(59L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(5L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(59L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(3L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(30L));
//
//        numberOfHours = calculations.getHoursOfTime(" 15:00:00", " 22:00:00");
//        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(0L));
//        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(0L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(1L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(59L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(5L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(0L));
//
//        numberOfHours = calculations.getHoursOfTime(" 1:30:00", " 14:30:00");
//        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(3L));
//        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(59L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(3L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(30L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(5L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(29L));
//
//        numberOfHours = calculations.getHoursOfTime(" 1:0:00", " 23:59:00");
//        assertThat(numberOfHours.getOnLoad().getNumberOfHours(), is(3L));
//        assertThat(numberOfHours.getOnLoad().getNumberOfMins(), is(59L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfHours(), is(5L));
//        assertThat(numberOfHours.getPeakLoad().getNumberOfMins(), is(59L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfHours(), is(12L));
//        assertThat(numberOfHours.getOfLoad().getNumberOfMins(), is(58L));
    }

/*    @Test
    public  void getTotalHoursPerDayTest() throws ParseException {
        Calculations calculations = new Calculations();

        List<WeekViewEvent> events;
        events = new ArrayList<>();
        TimeScheduling timeScheduling = new TimeScheduling();
        Calendar startTime = Calendar.getInstance();
        int event_color_01= 1929;
        int eventStartTime = 2016;

        // events= timeScheduling.addEvent(2016, 7, 1,45,0,2,30,0, startTime, 3,event_color_01);
        startTime.set(Calendar.HOUR_OF_DAY, eventStartTime);
        int startMinute = 39;
        int month = 3;
        int newYear = 2016;
        int dayOfTheMonth = 1;
        int startAMPM = 0;
        int eventEndTime= 2;
        int endMinute= 30;
        int endAMPM =0;

        startTime.set(Calendar.MINUTE, startMinute);
        startTime.set(Calendar.MONTH, month);
        startTime.set(Calendar.YEAR, newYear);
        startTime.set(Calendar.DAY_OF_MONTH, dayOfTheMonth);
        startTime.add(Calendar.AM_PM, startAMPM);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, eventEndTime);
        endTime.set(Calendar.MINUTE, endMinute);
        endTime.set(Calendar.MONTH, month);
        endTime.set(Calendar.DAY_OF_MONTH, dayOfTheMonth);
        endTime.add(Calendar.AM_PM, endAMPM);
        WeekViewEvent event = new WeekViewEvent(10, timeScheduling.getEventTitle(startTime), startTime, endTime);
        //event.setColor(getResources().getColor(event_color_01));
        events.add(event);
        PerDayData perDayData;
        perDayData = calculations.getTotalHoursPerDay(events);
        assertThat(perDayData.saturdayData.getOfLoad().getNumberOfHours(), is(3L));
    }*/

    @Test
    public void getInitialLEDTest(){
        Calculations calculations = new Calculations();
        float intialLedCost = calculations.getIntialLedCost(40, 202, 1680);
        assertThat(intialLedCost, is(406560f));
    }

    @Test
    public  void getMonthlyCostTest(){
        Calculations cal = new Calculations();

        Hours onload = new Hours(6,00);
        Hours peak = new Hours(6, 0);
        Hours offload = new Hours(4, 30);

        TimeSlot sunday = new TimeSlot(onload, peak, offload);
        TimeSlot monday = new TimeSlot(onload, peak, offload);
        TimeSlot tuesday = new TimeSlot(onload, peak, offload);
        TimeSlot thursday = new TimeSlot(onload, peak, offload);
        TimeSlot friday = new TimeSlot(onload, peak, offload);
        TimeSlot sat = new TimeSlot(onload, peak, offload);
        TimeSlot wed = new TimeSlot(onload, peak, offload);

        ReplacementBulb replacementBulb = new ReplacementBulb("Led", "Led", 54, 50000, 280);


        PerDayData perDy = new PerDayData(sunday, monday, tuesday,wed, thursday, friday, sat);
        float monthlyCostForExistingBulb = cal.getMonthlyCostForReplacementBulb(perDy, replacementBulb, 12.8, 17.5, 8.3, 280, 6);

        assertThat(monthlyCostForExistingBulb, is(741.04f));
    }

    @Test
    public void getMonthlyElectricityForLedTest(){
        Calculations calculations = new Calculations();

        Hours onload = new Hours(6,0);
        Hours peak = new Hours(6, 0);
        Hours offload = new Hours(4, 30);

        TimeSlot sunday = new TimeSlot(onload, peak, offload);
        TimeSlot monday = new TimeSlot(onload, peak, offload);
        TimeSlot tuesday = new TimeSlot(onload, peak, offload);
        TimeSlot thursday = new TimeSlot(onload, peak, offload);
        TimeSlot friday = new TimeSlot(onload, peak, offload);
        TimeSlot sat = new TimeSlot(onload, peak, offload);
        TimeSlot wed = new TimeSlot(onload, peak, offload);

        ReplacementBulb replacementBulb = new ReplacementBulb("Fixture","LED", 100, 50000, 280);
        PerDayData perDy = new PerDayData(sunday, monday, tuesday,wed, thursday, friday, sat);

        float monthlyCostForReplacementBulb = calculations.getMonthlyCostForReplacementBulb(perDy, replacementBulb, 1, 1, 1, 280, 1);
        assertThat(monthlyCostForReplacementBulb, is(228.63f));
    }

   /* @Test
    public void getTotalEnergySavings(){


        Hours onload = new Hours(3,0);
        Hours peak = new Hours(4, 0);
        Hours offload = new Hours(6, 0);

        TimeSlot sunday = new TimeSlot(onload, peak, offload);
        TimeSlot monday = new TimeSlot(onload, peak, offload);
        TimeSlot tuesday = new TimeSlot(onload, peak, offload);
        TimeSlot thursday = new TimeSlot(onload, peak, offload);
        TimeSlot friday = new TimeSlot(onload, peak, offload);
        TimeSlot sat = new TimeSlot(onload, peak, offload);
        TimeSlot wed = new TimeSlot(onload, peak, offload);


//        PerDayData perDy = new PerDayData(sunday, monday, tuesday,wed, thursday, friday, sat);
//        Calculations calculations = new Calculations();
//        Double totalEnergySaving = calculations.getTotalEnergySaving(50000, 3888.0, perDy, 515.0);
//        assertThat(totalEnergySaving, is(45d));
    }

    @Test
    public void getExistingBulbReplacementCost(){
        Calculations calculations = new Calculations();

        ExistingBulb existingBulb = new ExistingBulb("CFL", 54, 25000, 3,6,54);
        float costOfExistingBulbReplacement = calculations.getCostOfExistingBulbReplacement(existingBulb, 50000, 3);
        assertThat(costOfExistingBulbReplacement, is(3888.0f));
    }*/
}

