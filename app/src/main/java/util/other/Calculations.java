package util.other;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calculations {

    float intialLedResult = (float) 0.00;
    float costOfExistingBulbReplacement = (float) 0.00;
    float labourCostSavings = (float) 0.0;
    private double MonthlyElectricityCostOfExistingBulb;



    public float getIntialLedCost(float costOfLabour, float costOfBulb, float noOfExistingBulbs){
        intialLedResult = (costOfLabour + costOfBulb) * noOfExistingBulbs ;
        return (intialLedResult);
    }

    public float getCostOfExistingBulbReplacement(int existingBulbLifeSpan, int selectedLifeTimeOfBulb, int noOfExistingBulb, float priceOfEachLight, float labourCostPerBulb) {
        costOfExistingBulbReplacement = (selectedLifeTimeOfBulb/existingBulbLifeSpan) * noOfExistingBulb * (priceOfEachLight+ labourCostPerBulb);
        return costOfExistingBulbReplacement;

    }

   /* public float getMonthlyElectricityCostOfExistingBulb(float existingWattOfBulb, int numberOfHours, float costOfExistingBulb) {
        monthlyElectricityCostOfExistingBulb = ((existingWattOfBulb * numberOfHours * 30)/1000) * costOfExistingBulb;
        return monthlyElectricityCostOfExistingBulb;
    }*/

    public TimeSlot getNumberOfHours(String startTime, String endTime) throws ParseException {
        String date1 = "12/02/2016";
        String date2 = "12/02/2016";
        String onloadEndTime = "12:00 PM";


       // String onloadStartTime1 = "7:00 AM";
        String onloadStartTime2 = "8:00 AM";
        String onloadStartTime3 = "9:00 AM";
        String onloadStartTime4 = "10:00 AM";
        String onloadStartTime5 = "11:00 AM";
       String onloadStartTime6 = "5:00 PM";
       //String onloadStartTime7 = "6:00 PM";


        String offLoadTime2 = "8:00 PM";
        String offLoadTime3 = "9:00 PM";
        String offLoadTime4 = "10:00 PM";
        String offLoadTime5 = "11:00 PM";
        String offLoadTime6 = "12:00 PM";
        String offLoadTime7 = "1:00 AM";
        String offLoadTime8 = "2:00 AM";
        String offLoadTime9 = "3:00 AM";
        String offLoadTime10 = "4:00 AM";
        String offLoadTime11 = "5:00 AM";
        String offLoadTime12 = "6:00 AM";


        String peakLoadTime1 = "12:00 PM";
        String peakLoadTime2 = "1:00 PM";
        String peakLoadTime3 = "2:00 PM";
        String peakLoadTime4 = "3:00 PM";
        String peakLoadTime5 = "4:00 PM";
        String peakLoadTime6 = "5:00 PM";
        //String peakLoadTime9 = "11:00 AM";



        String format = "dd/MM/yyyy hh:mm a";

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date dateObj1 = sdf.parse(date1 + " " + startTime);
        Date dateObj2 = sdf.parse(date2 + " " + endTime);
        //Date onloadStartDateTIme1 = sdf.parse(date2 + " " + onloadStartTime1);
        Date onloadStartDateTIme2 = sdf.parse(date2 + " " + onloadStartTime2);
        Date onloadStartDateTIme3 = sdf.parse(date2 + " " + onloadStartTime3);
        Date onloadStartDateTIme4 = sdf.parse(date2 + " " + onloadStartTime4);
        Date onloadStartDateTIme5 = sdf.parse(date2 + " " + onloadStartTime5);
        Date onloadStartDateTIme6 = sdf.parse(date2 + " " + onloadStartTime6);
        //Date onloadStartDateTIme7 = sdf.parse(date2 + " " + onloadStartTime7);

      //  Date offLoadStartTime1 = sdf.parse(date2 + " " + onloadStartTime1);


        Date offloadEndDateTIme2 = sdf.parse(date2 + " " + offLoadTime2);
        Date offloadEndDateTIme3 = sdf.parse(date2 + " " + offLoadTime3);
        Date offloadEndDateTIme4 = sdf.parse(date2 + " " + offLoadTime4);
        Date offloadEndDateTIme5 = sdf.parse(date2 + " " + offLoadTime5);
        Date offloadEndDateTIme6 = sdf.parse(date2 + " " + offLoadTime6);
        Date offloadEndDateTIme7 = sdf.parse(date2 + " " + offLoadTime7);
        Date offloadEndDateTIme8 = sdf.parse(date2 + " " + offLoadTime8);
        Date offloadEndDateTIme9 = sdf.parse(date2 + " " + offLoadTime9);
        Date offloadEndDateTIme10 = sdf.parse(date2 + " " + offLoadTime10);
        Date offloadEndDateTIme11 = sdf.parse(date2 + " " + offLoadTime11);
        Date offloadEndDateTIme12 = sdf.parse(date2 + " " + offLoadTime12);



        Date peakEndDateTIme1 = sdf.parse(date2 + " " + peakLoadTime1);
        Date peakEndDateTIme2 = sdf.parse(date2 + " " + peakLoadTime2);
        Date peakEndDateTIme3 = sdf.parse(date2 + " " + peakLoadTime3);
        Date peakEndDateTIme4 = sdf.parse(date2 + " " + peakLoadTime4);
        Date peakEndDateTIme5 = sdf.parse(date2 + " " + peakLoadTime5);

      Date peakEndDateTIme6 = sdf.parse(date2 + " " + peakLoadTime6);
       // Date peakEndDateTIme9 = sdf.parse(date2 + " " + peakLoadTime9);

        System.out.println("Date Start: " + dateObj1);
        System.out.println("Date End: " + dateObj2);

//Date d = new Date(dateObj1.getTime() + 3600000);
        List<Date> onload_array = new ArrayList<>();
        List<Date>  offload_array = new ArrayList<>();
        List<Date>  peakLoad_array = new ArrayList<>();


        peakLoad_array.add(peakEndDateTIme1);
        peakLoad_array.add(peakEndDateTIme2);
        peakLoad_array.add(peakEndDateTIme3);
        peakLoad_array.add(peakEndDateTIme4);
        peakLoad_array.add(peakEndDateTIme5);
        peakLoad_array.add(peakEndDateTIme6);
        //peakLoad_array.add(peakEndDateTIme7);
       // peakLoad_array.add(peakEndDateTIme8);
       // peakLoad_array.add(peakEndDateTIme9);

        //onload_array.add(onloadStartDateTIme1);
        onload_array.add(onloadStartDateTIme2);
        onload_array.add(onloadStartDateTIme3);
        onload_array.add(onloadStartDateTIme4);
        onload_array.add(onloadStartDateTIme5);
        onload_array.add(onloadStartDateTIme5);
        onload_array.add(onloadStartDateTIme6);
        //onload_array.add(onloadStartDateTIme7);

        offload_array.add(offloadEndDateTIme2);
        offload_array.add(offloadEndDateTIme3);
        offload_array.add(offloadEndDateTIme4);
        offload_array.add(offloadEndDateTIme5);
        offload_array.add(offloadEndDateTIme6);
        offload_array.add(offloadEndDateTIme7);
        offload_array.add(offloadEndDateTIme8);
        offload_array.add(offloadEndDateTIme9);
        offload_array.add(offloadEndDateTIme10);
        offload_array.add(offloadEndDateTIme11);
        offload_array.add(offloadEndDateTIme12);


        long dif = dateObj1.getTime();

        int offLoadCount = 0;
        int onloadCount = 0;
        int peakLoadCount = 0;
        while (dif < dateObj2.getTime()) {
            Date slot = new Date(dif);

            for (Date date : onload_array) {

                if (slot.equals(date)) {
                    onloadCount ++;
                }
                //System.out.println(date);
                System.out.println("===>>> Count of onload hours" +  onloadCount);
            }

            for (Date date : offload_array) {

                if (slot.equals(date)) {
                    offLoadCount ++;
                }
                //System.out.println(date);
                System.out.println("===>>> Count of offLoadCount hours" +  offLoadCount);
            }

            for (Date date : peakLoad_array) {

                if (slot.equals(date)) {
                    peakLoadCount ++;
                }
                //System.out.println(date);
                System.out.println("===>>> Count of peakLoadCount hours" +  peakLoadCount);
            }
                dif += 3600000;

        }
        return new TimeSlot(offLoadCount, onloadCount, peakLoadCount) ;
    }

    public float getLabourCostSaving(float intialLedCost, float existingBulbReplacementCost) {
        labourCostSavings = existingBulbReplacementCost - intialLedCost;
        return  labourCostSavings;
    }

    public double getMonthlyElectricityCostOfExistingBulb(int onLoadCount, int offloadCount, int offloadCount1, float existingWattOfBulb, Double offLoadPrice, Double onLoadPrice, Double onLoadPrice1, int noOfBulbs) {
        double totalOnloadPrice = ((existingWattOfBulb * onLoadCount)/1000) * (onLoadPrice/100);
        double totalPeakloadPrice = ((existingWattOfBulb * offloadCount)/1000) * (offLoadPrice/100);
        double totalOffloadPrice = ((existingWattOfBulb * offloadCount1)/1000) * (onLoadPrice1/100);

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        //totalOnloadPrice = Double.valueOf(twoDForm.format(totalOnloadPrice));
        //totalPeakloadPrice = Double.valueOf(twoDForm.format(totalPeakloadPrice));
        //totalOffloadPrice = Double.valueOf(twoDForm.format(totalOffloadPrice));

        double totalEnergy = totalOnloadPrice + totalPeakloadPrice + totalOffloadPrice;
        MonthlyElectricityCostOfExistingBulb = totalEnergy * 20 * noOfBulbs;
        MonthlyElectricityCostOfExistingBulb = Double.valueOf(twoDForm.format(MonthlyElectricityCostOfExistingBulb));
        return  MonthlyElectricityCostOfExistingBulb;
    }

    public Double getTotalEnergySaving(int ledBulbLifespan, float existingBulbReplacementCost, int totalHours, Double monthlyEnergySaving) {
        int lifeSpanIntoYears = ledBulbLifespan/(totalHours * 20 * 12);
        double costSavingPerYear = existingBulbReplacementCost/lifeSpanIntoYears;
        Double costSavingPerMonth = costSavingPerYear/12;
        Double totalEnergySaving = costSavingPerMonth + monthlyEnergySaving;
        return  totalEnergySaving;
    }


}
