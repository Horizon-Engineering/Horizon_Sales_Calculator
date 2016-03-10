package app.com.ledsavingcalculator.database.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "calculation")
public class Results {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private double intialLEDCost;
    @DatabaseField
    private double monthlyElectricityOfExistingBulbForSummer;
    @DatabaseField
    private double monthlyElecticityCostForLEDForSummer;
    @DatabaseField
    private  double energySavingSummer;
    @DatabaseField
    private double monthlyCostSavingForSummer;
    @DatabaseField
    private double monthlyCostSavingForWinter;
    @DatabaseField
    private Double totalEnergySaving;
    @DatabaseField
    private double monthlyCostForExistingBulbsForWinter;
    @DatabaseField
    private  double monthlyElecticityCostForLEDForWinter;
    @DatabaseField
    private Double costOfExistingBulbReplacement;
    @DatabaseField
    private double monthlyEnergyCostForExisting;
    @DatabaseField
    private double monthlyEnergyCostForReplacementBulb;

   //empty constructor required for creating the Dao obj
    public Results(){}

//    public  Results(double intialLEDCost,
//                    double monthlyElectricityOfExistingBulbForSummer,
//                    double monthlyElecticityCostForLEDForWinter,
//                    double energySaving,
//                    double  costOfExistingBulbReplacement,
//                    double monthlyCostSavingForSummer,
//                    double monthlyCostSavingForWinter ,
//                    double totalEnergySaving){
//
//        this.intialLEDCost = intialLEDCost;
//        this.energySavingSummer = energySaving;
//        this.monthlyElectricityOfExistingBulbForSummer = monthlyElectricityOfExistingBulbForSummer;
//        this.monthlyCostSavingForSummer = monthlyCostSavingForSummer;
//        this.monthlyCostSavingForWinter = monthlyCostSavingForWinter;
//        this.totalEnergySaving = totalEnergySaving;
//        this.costOfExistingBulbReplacement = costOfExistingBulbReplacement;
//        this.monthlyCostForExistingBulbsForWinter = monthlyCostSavingForWinter;
//        this.monthlyElecticityCostForLEDForSummer = monthlyElecticityCostForLEDForWinter;
//        this.monthlyElecticityCostForLEDForWinter = monthlyElecticityCostForLEDForWinter;
//    }

    public double getIntialLEDCost() {
        return intialLEDCost;
    }

    public void setIntialLEDCost(double intialLEDCost) {
        this.intialLEDCost = intialLEDCost;
    }

    public double getMonthlyElectricityOfExistingBulbForSummer() {
        return monthlyElectricityOfExistingBulbForSummer;
    }

    public void setMonthlyElectricityOfExistingBulbForSummer(double monthlyElectricityOfExistingBulbForSummer) {
        this.monthlyElectricityOfExistingBulbForSummer = monthlyElectricityOfExistingBulbForSummer;
    }

    public double getMonthlyElecticityCostForLEDForSummer() {
        return monthlyElecticityCostForLEDForSummer;
    }

    public void setMonthlyElecticityCostForLEDForSummer(double monthlyElecticityCostForLEDForSummer) {
        this.monthlyElecticityCostForLEDForSummer = monthlyElecticityCostForLEDForSummer;
    }

    public double getEnergySavingSummer() {
        return energySavingSummer;
    }

    public void setEnergySavingSummer(double energySavingSummer) {
        this.energySavingSummer = energySavingSummer;
    }

    public double getMonthlyCostSavingForSummer() {
        return monthlyCostSavingForSummer;
    }

    public void setMonthlyCostSavingForSummer(double monthlyCostSavingForSummer) {
        this.monthlyCostSavingForSummer = monthlyCostSavingForSummer;
    }

    public Double getTotalEnergySaving() {
        return totalEnergySaving;
    }

    public void setTotalEnergySaving(Double totalEnergySaving) {
        this.totalEnergySaving = totalEnergySaving;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonthlyCostSavingForWinter() {
        return monthlyCostSavingForWinter;
    }

    public void setMonthlyCostSavingForWinter(double monthlyCostSavingForWinter) {
        this.monthlyCostSavingForWinter = monthlyCostSavingForWinter;
    }

    public Double getCostOfExistingBulbReplacement() {
        return costOfExistingBulbReplacement;
    }

    public void setCostOfExistingBulbReplacement(Double costOfExistingBulbReplacement) {
        this.costOfExistingBulbReplacement = costOfExistingBulbReplacement;
    }

    public double getMonthlyCostForExistingBulbsForWinter() {
        return monthlyCostForExistingBulbsForWinter;
    }

    public void setMonthlyCostForExistingBulbsForWinter(double monthlyCostForExistingBulbsForWinter) {
        this.monthlyCostForExistingBulbsForWinter = monthlyCostForExistingBulbsForWinter;
    }

    public double getMonthlyElecticityCostForLEDForWinter() {
        return monthlyElecticityCostForLEDForWinter;
    }

    public void setMonthlyElecticityCostForLEDForWinter(double monthlyElecticityCostForLEDForWinter) {
        this.monthlyElecticityCostForLEDForWinter = monthlyElecticityCostForLEDForWinter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Results results = (Results) o;

        if (id != results.id) return false;
        if (Double.compare(results.intialLEDCost, intialLEDCost) != 0) return false;
        if (Double.compare(results.monthlyElectricityOfExistingBulbForSummer, monthlyElectricityOfExistingBulbForSummer) != 0)
            return false;
        if (Double.compare(results.monthlyElecticityCostForLEDForSummer, monthlyElecticityCostForLEDForSummer) != 0)
            return false;
        if (Double.compare(results.energySavingSummer, energySavingSummer) != 0) return false;
        if (Double.compare(results.monthlyCostSavingForSummer, monthlyCostSavingForSummer) != 0)
            return false;
        if (Double.compare(results.monthlyCostSavingForWinter, monthlyCostSavingForWinter) != 0)
            return false;
        if (Double.compare(results.monthlyCostForExistingBulbsForWinter, monthlyCostForExistingBulbsForWinter) != 0)
            return false;
        if (Double.compare(results.monthlyElecticityCostForLEDForWinter, monthlyElecticityCostForLEDForWinter) != 0)
            return false;
        if (totalEnergySaving != null ? !totalEnergySaving.equals(results.totalEnergySaving) : results.totalEnergySaving != null)
            return false;
        return !(costOfExistingBulbReplacement != null ? !costOfExistingBulbReplacement.equals(results.costOfExistingBulbReplacement) : results.costOfExistingBulbReplacement != null);

    }

    public double getMonthlyEnergyCostForExisting() {
        return monthlyEnergyCostForExisting;
    }

    public void setMonthlyEnergyCostForExisting(double monthlyEnergyCostForExisting) {
        this.monthlyEnergyCostForExisting = monthlyEnergyCostForExisting;
    }

    public double getMonthlyEnergyCostForReplacementBulb() {
        return monthlyEnergyCostForReplacementBulb;
    }

    public void setMonthlyEnergyCostForReplacementBulb(double monthlyEnergyCostForReplacementBulb) {
        this.monthlyEnergyCostForReplacementBulb = monthlyEnergyCostForReplacementBulb;
    }


    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(intialLEDCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(monthlyElectricityOfExistingBulbForSummer);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(monthlyElecticityCostForLEDForSummer);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(energySavingSummer);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(monthlyCostSavingForSummer);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(monthlyCostSavingForWinter);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (totalEnergySaving != null ? totalEnergySaving.hashCode() : 0);
        temp = Double.doubleToLongBits(monthlyCostForExistingBulbsForWinter);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(monthlyElecticityCostForLEDForWinter);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (costOfExistingBulbReplacement != null ? costOfExistingBulbReplacement.hashCode() : 0);
        temp = Double.doubleToLongBits(monthlyEnergyCostForExisting);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(monthlyEnergyCostForReplacementBulb);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Results{" +
                "id=" + id +
                ", intialLEDCost=" + intialLEDCost +
                ", monthlyElectricityOfExistingBulbForSummer=" + monthlyElectricityOfExistingBulbForSummer +
                ", monthlyElecticityCostForLEDForSummer=" + monthlyElecticityCostForLEDForSummer +
                ", energySavingSummer=" + energySavingSummer +
                ", monthlyCostSavingForSummer=" + monthlyCostSavingForSummer +
                ", monthlyCostSavingForWinter=" + monthlyCostSavingForWinter +
                ", totalEnergySaving=" + totalEnergySaving +
                ", monthlyCostForExistingBulbsForWinter=" + monthlyCostForExistingBulbsForWinter +
                ", monthlyElecticityCostForLEDForWinter=" + monthlyElecticityCostForLEDForWinter +
                ", costOfExistingBulbReplacement=" + costOfExistingBulbReplacement +
                ", monthlyEnergyCostForExisting=" + monthlyEnergyCostForExisting +
                ", monthlyEnergyCostForReplacementBulb=" + monthlyEnergyCostForReplacementBulb +
                '}';
    }
}
