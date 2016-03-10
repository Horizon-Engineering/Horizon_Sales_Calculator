package app.com.ledsavingcalculator.util;


public class PerDayData {

    public TimeSlot sundayData;
    public TimeSlot mondayData;
    public TimeSlot tuesdayData;
    public TimeSlot thursdayData;
    public TimeSlot wednesdayData;
    public TimeSlot fridayData;
    public TimeSlot saturdayData;

    public PerDayData(TimeSlot sundayData, TimeSlot mondayData, TimeSlot tuesdayData, TimeSlot wednesdayData,
                      TimeSlot thursdayData, TimeSlot fridayData, TimeSlot saturdayData){
        this.sundayData = sundayData;
        this.mondayData = mondayData;
        this.tuesdayData = tuesdayData;
        this.wednesdayData = wednesdayData;
        this.thursdayData = thursdayData;
        this.fridayData = fridayData;
        this.saturdayData = saturdayData;
    }
}
