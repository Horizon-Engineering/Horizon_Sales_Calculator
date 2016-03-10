package app.com.ledsavingcalculator.util;

public class EnergyCost {

    private double onLoadCost;
    private double offLoadCost;
    private double peakLoadCost;

    public EnergyCost(double onLoadCost, double peakLoadCost, double offLoadCost){
        this.offLoadCost = offLoadCost;
        this.onLoadCost = onLoadCost;
        this.peakLoadCost = peakLoadCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnergyCost that = (EnergyCost) o;

        if (Double.compare(that.onLoadCost, onLoadCost) != 0) return false;
        if (Double.compare(that.offLoadCost, offLoadCost) != 0) return false;
        return Double.compare(that.peakLoadCost, peakLoadCost) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(onLoadCost);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(offLoadCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(peakLoadCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "EnergyCost{" +
                "onLoadCost=" + onLoadCost +
                ", offLoadCost=" + offLoadCost +
                ", peakLoadCost=" + peakLoadCost +
                '}';
    }
}
