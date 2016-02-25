package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import database.dao.ExistingBulb;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "hesolutions.db";
    private Dao<ExistingBulb, Integer> existingBulbDao;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(getWritableDatabase(), getConnectionSource());
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, ExistingBulb.class);
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

   /* public List getAllData() throws SQLException {
        Dao<ExistingBulb, Integer> existingBulbDao = getHelper().getExistingBulbDao();
          // query for all of the data objects in the database
         List<ExistingBulb> list = existingBulbDao.queryForAll();
        return  list;
    }*/

    private DataBaseHelper getHelper() {
        return null;
    }
}

