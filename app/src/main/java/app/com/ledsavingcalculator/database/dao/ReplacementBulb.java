package app.com.ledsavingcalculator.database.dao;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "replacementBulb")
public class ReplacementBulb {

    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    private String typeOfReplacement;
    @DatabaseField
    private String replacementLight;
    @DatabaseField
    private int wattageOfReplacementBulb;
    @DatabaseField
    private int lifeSpan;
    @DatabaseField
    private float costOfReplacementbulb;



    public ReplacementBulb(String typeOfReplacement, String replacementLight, int wattageOfReplacementBulb, int lifeSpan, float costOfReplacementbulb){
        this.typeOfReplacement = typeOfReplacement;
        this.replacementLight = replacementLight;
        this.wattageOfReplacementBulb = wattageOfReplacementBulb;
        this.lifeSpan = lifeSpan;
        this.costOfReplacementbulb = costOfReplacementbulb;
    }


    //empty constructor
    public ReplacementBulb (){ }

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

    public float getCostOfReplacementbulb() {
        return costOfReplacementbulb;
    }

    public void setCostOfReplacementbulb(int costOfReplacementbulb) {
        this.costOfReplacementbulb = costOfReplacementbulb;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReplacementBulb that = (ReplacementBulb) o;

        if (id != that.id) return false;
        if (wattageOfReplacementBulb != that.wattageOfReplacementBulb) return false;
        if (lifeSpan != that.lifeSpan) return false;
        if (Float.compare(that.costOfReplacementbulb, costOfReplacementbulb) != 0) return false;
        if (typeOfReplacement != null ? !typeOfReplacement.equals(that.typeOfReplacement) : that.typeOfReplacement != null)
            return false;
        return !(replacementLight != null ? !replacementLight.equals(that.replacementLight) : that.replacementLight != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (typeOfReplacement != null ? typeOfReplacement.hashCode() : 0);
        result = 31 * result + (replacementLight != null ? replacementLight.hashCode() : 0);
        result = 31 * result + wattageOfReplacementBulb;
        result = 31 * result + lifeSpan;
        result = 31 * result + (costOfReplacementbulb != +0.0f ? Float.floatToIntBits(costOfReplacementbulb) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReplacementBulb{" +
                "id=" + id +
                ", typeOfReplacement='" + typeOfReplacement + '\'' +
                ", replacementLight='" + replacementLight + '\'' +
                ", wattageOfReplacementBulb=" + wattageOfReplacementBulb +
                ", lifeSpan=" + lifeSpan +
                ", costOfReplacementbulb=" + costOfReplacementbulb +
                '}';
    }
}
