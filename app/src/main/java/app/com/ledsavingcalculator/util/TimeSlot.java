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
