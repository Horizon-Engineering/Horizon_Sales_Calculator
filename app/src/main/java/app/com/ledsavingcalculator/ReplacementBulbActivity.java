package app.com.ledsavingcalculator;


import android.app.Activity;
import android.os.Bundle;

import database.dao.ExistingBulb;

public class ReplacementBulbActivity extends Activity{

    ExistingBulb existingBulbData = new ExistingBulb();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replacement_page);

        existingBulbData.getCostOfBulb();
            System.out.println("===>>>"+existingBulbData);
    }


}
