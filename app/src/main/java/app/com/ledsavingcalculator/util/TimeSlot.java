package app.com.ledsavingcalculator.util;


public class TimeSlot {

    private Hours onLoad;
    private Hours ofLoad;
    private Hours peakLoad;

    public TimeSlot(Hours onload, Hours peakload, Hours offload) {
        this.onLoad = onload;
        this.peakLoad = peakload;
        this.ofLoad = offload;
    }

    //Empty hours/no event time solt
    public TimeSlot() {
        this.onLoad = new Hours(0, 0);
        this.peakLoad = new Hours(0, 0);
        this.ofLoad = new Hours(0, 0);
    }

    public Hours getOnLoad() {
        return onLoad;
    }

    public Hours getOfLoad() {
        return ofLoad;
    }

    public Hours getPeakLoad() {
        return peakLoad;
    }
}
