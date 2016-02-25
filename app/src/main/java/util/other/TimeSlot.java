package util.other;


public class TimeSlot {

    private int onLoad;
    private int ofLoad;
    private  int peakLoad;


    public TimeSlot(int ofLoad, int onLoad, int peakLoad) {
        this.onLoad = onLoad;
        this.ofLoad = ofLoad;
        this.peakLoad = peakLoad;

    }

    public int getOnLoad() {
        return onLoad;
    }

    public void setOnLoad(int onLoad) {
        this.onLoad = onLoad;
    }

    public int getOfLoad() {
        return ofLoad;
    }

    public void setOfLoad(int ofLoad) {
        this.ofLoad = ofLoad;
    }

    public int getPeakLoad() {
        return peakLoad;
    }

    public void setPeakLoad(int peakLoad) {
        this.peakLoad = peakLoad;
    }
}
