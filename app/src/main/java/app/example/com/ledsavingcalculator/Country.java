package app.example.com.ledsavingcalculator;

public class Country {

    private  String country;
    private  String province;
    private  String region;
    private  float cost;
    private  String peakLoad;
    private  String onLoad;
    private  String ofLoad;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getPeakLoad() {
        return peakLoad;
    }

    public void setPeakLoad(String peakLoad) {
        this.peakLoad = peakLoad;
    }

    public String getOnLoad() {
        return onLoad;
    }

    public void setOnLoad(String onLoad) {
        this.onLoad = onLoad;
    }

    public String getOfLoad() {
        return ofLoad;
    }

    public void setOfLoad(String ofLoad) {
        this.ofLoad = ofLoad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country1 = (Country) o;

        if (Float.compare(country1.cost, cost) != 0) return false;
        if (!country.equals(country1.country)) return false;
        if (!province.equals(country1.province)) return false;
        if (!region.equals(country1.region)) return false;
        if (!peakLoad.equals(country1.peakLoad)) return false;
        if (!onLoad.equals(country1.onLoad)) return false;
        return ofLoad.equals(country1.ofLoad);

    }

    @Override
    public int hashCode() {
        int result = country.hashCode();
        result = 31 * result + province.hashCode();
        result = 31 * result + region.hashCode();
        result = 31 * result + (cost != +0.0f ? Float.floatToIntBits(cost) : 0);
        result = 31 * result + peakLoad.hashCode();
        result = 31 * result + onLoad.hashCode();
        result = 31 * result + ofLoad.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", region='" + region + '\'' +
                ", cost=" + cost +
                ", peakLoad='" + peakLoad + '\'' +
                ", onLoad='" + onLoad + '\'' +
                ", ofLoad='" + ofLoad + '\'' +
                '}';
    }
}
