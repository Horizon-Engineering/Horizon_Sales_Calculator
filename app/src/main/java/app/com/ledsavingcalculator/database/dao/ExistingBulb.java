package app.com.ledsavingcalculator.database.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "existingBulb")
public class ExistingBulb{

    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    private String typeOfLight;
    @DatabaseField
    private int noOfFixtures;
    @DatabaseField
    private int lifeSpan;
    @DatabaseField
    private float costOfBulb;
    @DatabaseField
    private int noOfBulbsPerFixture;
    @DatabaseField
    private int wattageOfBulb;

    public ExistingBulb(String typeOfLight, int noOfFixtures, int lifeSpan, float costOfBulb, int noOfBulbsPerFixture, int wattageOfBulb){
        this.typeOfLight = typeOfLight;
        this.noOfFixtures = noOfFixtures;
        this.lifeSpan = lifeSpan;
        this.costOfBulb = costOfBulb;
        this.noOfBulbsPerFixture = noOfBulbsPerFixture;
        this.wattageOfBulb = wattageOfBulb;
    }

    public ExistingBulb() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfLight() {
        return typeOfLight;
    }

    public void setTypeOfLight(String typeOfLight) {
        this.typeOfLight = typeOfLight;
    }

    public int getNoOfFixtures() {
        return noOfFixtures;
    }

    public void setNoOfFixtures(int noOfFixtures) {
        this.noOfFixtures = noOfFixtures;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public float getCostOfBulb() {
        return costOfBulb;
    }

    public void setCostOfBulb(float costOfBulb) {
        this.costOfBulb = costOfBulb;
    }

    public int getNoOfBulbsPerFixture() {
        return noOfBulbsPerFixture;
    }

    public void setNoOfBulbsPerFixture(int noOfBulbsPerFixture) {
        this.noOfBulbsPerFixture = noOfBulbsPerFixture;
    }

    public int getWattageOfBulb() {
        return wattageOfBulb;
    }

    public void setWattageOfBulb(int wattageOfBulb) {
        this.wattageOfBulb = wattageOfBulb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExistingBulb that = (ExistingBulb) o;

        if (id != that.id) return false;
        if (noOfFixtures != that.noOfFixtures) return false;
        if (lifeSpan != that.lifeSpan) return false;
        if (Float.compare(that.costOfBulb, costOfBulb) != 0) return false;
        if (noOfBulbsPerFixture != that.noOfBulbsPerFixture) return false;
        if (wattageOfBulb != that.wattageOfBulb) return false;
        return !(typeOfLight != null ? !typeOfLight.equals(that.typeOfLight) : that.typeOfLight != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (typeOfLight != null ? typeOfLight.hashCode() : 0);
        result = 31 * result + noOfFixtures;
        result = 31 * result + lifeSpan;
        result = 31 * result + (costOfBulb != +0.0f ? Float.floatToIntBits(costOfBulb) : 0);
        result = 31 * result + noOfBulbsPerFixture;
        result = 31 * result + wattageOfBulb;
        return result;
    }

    @Override
    public String toString() {
        return "ExistingBulb{" +
                "id=" + id +
                ", typeOfLight='" + typeOfLight + '\'' +
                ", noOfFixtures=" + noOfFixtures +
                ", lifeSpan=" + lifeSpan +
                ", costOfBulb=" + costOfBulb +
                ", noOfBulbsPerFixture=" + noOfBulbsPerFixture +
                ", wattageOfBulb=" + wattageOfBulb +
                '}';
    }
}
