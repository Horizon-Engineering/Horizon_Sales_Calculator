package app.com.ledsavingcalculator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import app.com.ledsavingcalculator.database.dao.ExistingBulb;
import app.com.ledsavingcalculator.database.dao.ReplacementBulb;
import app.com.ledsavingcalculator.database.dao.Results;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    // If you change the app.com.ledsavingcalculator.database schema, you must increment the app.com.ledsavingcalculator.database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "hesolutions.db";
    private Dao<ExistingBulb, Integer> existingBulbDao;
    private Dao<ReplacementBulb, Integer> replacementBulbDao;
    private Dao<Results, Integer> resultDao;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTableIfNotExists(connectionSource, ExistingBulb.class);
            TableUtils.createTableIfNotExists(connectionSource, ReplacementBulb.class);
            TableUtils.createTableIfNotExists(connectionSource, Results.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //upgrade data
    }

    public Dao<ExistingBulb, Integer> getExistingBulbDao() throws SQLException {

        if (existingBulbDao == null) {
            existingBulbDao = getDao(ExistingBulb.class);
        }
        return existingBulbDao;
    }

    public Dao<ReplacementBulb, Integer> getReplacementBulbDao() throws SQLException {
        if (replacementBulbDao == null) {
            replacementBulbDao = getDao(ReplacementBulb.class);
        }
        return replacementBulbDao;
    }


    public Dao<Results, Integer> getResultDao() throws SQLException {
        if (resultDao == null) {
            resultDao = getDao(Results.class);
        }
        return resultDao;
    }

    private DataBaseHelper getHelper() {
        return null;
    }

    public void createTables() {
        try {
            //First delete all data then create new table
            TableUtils.dropTable(connectionSource, ExistingBulb.class, true);
            TableUtils.dropTable(connectionSource, ReplacementBulb.class, true);
            TableUtils.dropTable(connectionSource, Results.class, true);

            TableUtils.createTable(connectionSource, ExistingBulb.class);
            TableUtils.createTable(connectionSource, ReplacementBulb.class);
            TableUtils.createTable(connectionSource, Results.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

