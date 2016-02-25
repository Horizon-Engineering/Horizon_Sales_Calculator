package database.dao;

public class ReplacementBulb {
    private String typeOfReplacement;
    private String replacementLight;
    private int wattageOfReplacementBulb;
    private int lifeSpan;
    private int costOfReplacementbulb;

    private ReplacementBulb(String typeOfReplacement, String replacementLight, int wattageOfReplacementBulb, int lifeSpan, int costOfReplacementbulb){
        this.typeOfReplacement = typeOfReplacement;
        this.replacementLight = replacementLight;
        this.wattageOfReplacementBulb = wattageOfReplacementBulb;
        this.lifeSpan = lifeSpan;
        this.costOfReplacementbulb = costOfReplacementbulb;
    }

    public String getTypeOfReplacement() {
        return typeOfReplacement;
    }

    public void setTypeOfReplacement(String typeOfReplacement) {
        this.typeOfReplacement = typeOfReplacement;
    }

    public String getReplacementLight() {
        return replacementLight;
    }

    public void setReplacementLight(String replacementLight) {
        this.replacementLight = replacementLight;
    }

    public int getWattageOfReplacementBulb() {
        return wattageOfReplacementBulb;
    }

    public void setWattageOfReplacementBulb(int wattageOfReplacementBulb) {
        this.wattageOfReplacementBulb = wattageOfReplacementBulb;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public int getCostOfReplacementbulb() {
        return costOfReplacementbulb;
    }

    public void setCostOfReplacementbulb(int costOfReplacementbulb) {
        this.costOfReplacementbulb = costOfReplacementbulb;
    }
}
