package app.com.ledsavingcalculator.database;


import android.provider.BaseColumns;

public class ExistingBulbDataStructure {

    public ExistingBulbDataStructure(){}

    public static abstract class CurrentBulbEntry implements BaseColumns{
        public static final String TABLE_NAME = "existingBulb_table";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_TYPEOFBULB = "typeOfBulb";
        public static final String COLUMN_NAME_NOOFFIXTURES = "fixtures";
        public static final String COLUMN_NAME_NOofBULBSPERFIXTURE = "bulbsPerFixture";
        public static final String COLUMN_NAME_WATTAGEOFBULB = "wattage";
        public static final String COLUMN_NAME_COSTOFBULB   = "cost";
    }
}
