package app.com.ledsavingcalculator.database;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.j256.ormlite.android.apptools.OrmLiteBaseService;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import app.com.ledsavingcalculator.database.dao.ExistingBulb;

public class DBService extends OrmLiteBaseService<DataBaseHelper>{

    public DBService(Context context) {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    public static int insertExistingBulb(ExistingBulb existingBulb) throws SQLException {
//        Dao<ExistingBulb, Integer> existingBulbDao = getHelper().getExistingBulbDao();
//        int id = existingBulbDao.create(existingBulb);
//        return id;
//    }

    public Dao<ExistingBulb, Integer>  getExistingBulbDao() throws SQLException {
        return  getHelper().getExistingBulbDao();
    }
}
