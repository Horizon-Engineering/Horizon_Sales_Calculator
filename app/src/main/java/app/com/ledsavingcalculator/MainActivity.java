package app.com.ledsavingcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;

import app.com.ledsavingcalculator.database.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(getBaseContext());
        dataBaseHelper.createTables();
        Intent existingBulb = new Intent(this, ExistingBulbActivity.class);
        Firebase.setAndroidContext(this);
        try{
        Firebase.getDefaultConfig().setPersistenceEnabled(true);}
        catch (Exception e){
            //Toast.makeText(getBaseContext(), "Error in reopening the applications", Toast.LENGTH_SHORT).show();
        }
        startActivity(existingBulb);
    }
}
