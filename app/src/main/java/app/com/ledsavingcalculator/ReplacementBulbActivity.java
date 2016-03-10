package app.com.ledsavingcalculator;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import app.com.ledsavingcalculator.database.DataBaseHelper;
import app.com.ledsavingcalculator.database.dao.ReplacementBulb;

public class ReplacementBulbActivity extends Activity {

    View backBtnId;
    EditText lifeSpan;
    EditText costOfReplacementBulb;
    EditText wattageOfReplacementbulb;
    EditText replacmentType;
    Button bulbButtonId;
    View nextBtn;
    private String typeOfReplacementBulb;
    RadioGroup radioGroupId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replacement_page);

        final DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());

        backBtnId = findViewById(R.id.backBtn);
        lifeSpan = (EditText) findViewById(R.id.replacementLifeSpan);
        //lifeSpan.setText("50000");
        costOfReplacementBulb = (EditText) findViewById(R.id.costOfReplacement);
       // costOfReplacementBulb.setText("6");
        wattageOfReplacementbulb = (EditText) findViewById(R.id.wattageOfReplacementbulb);
       // wattageOfReplacementbulb.setText("100");
        replacmentType = (EditText) findViewById(R.id.replacmentType);

        nextBtn = findViewById(R.id.nextBtn);

        //eplacmentType.setText("LED");
        replacmentType.setFocusable(false);
        replacmentType.setClickable(true);

        //set font for activity
        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");
        final ViewGroup mContainer = (ViewGroup) findViewById(
                android.R.id.content).getRootView();
        SetFont.setAppFont(mContainer, mFont);


        //back button listener
        backBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivityIntent = new Intent(ReplacementBulbActivity.this, ExistingBulbActivity.class);
                startActivity(startNewActivityIntent);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wattageOfReplacementbulb.getText().toString().equals("")) {
                    wattageOfReplacementbulb.setError("Wattage of bulb is required");
                } else if (radioGroupId.getCheckedRadioButtonId()<=0) {
                        bulbButtonId.setError("Select Item");
                } else if (costOfReplacementBulb.getText().toString().trim().equals("")) {
                    costOfReplacementBulb.setError("Cost Of bulb is required");
                } else if (lifeSpan.getText().toString().trim().equals("")) {
                    lifeSpan.setError("Wattage of bulb is required");
                } else {
                    int wattageOfBulb = Integer.parseInt(wattageOfReplacementbulb.getText().toString());
                    int replacementLifespan = Integer.parseInt(lifeSpan.getText().toString());
                    float costOfReplacementPerBulb = Float.parseFloat(costOfReplacementBulb.getText().toString());
                    String replacmentType1 = String.valueOf(replacmentType.getText().toString());

                    ReplacementBulb replacementBulb = new ReplacementBulb(typeOfReplacementBulb,
                    replacmentType1, wattageOfBulb, replacementLifespan, costOfReplacementPerBulb);
                    try {
                        Dao<ReplacementBulb, Integer> replacementTypeDao = dataBaseHelper.getReplacementBulbDao();
                        replacementTypeDao.create(replacementBulb);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Intent startNewActivityIntent = new Intent(ReplacementBulbActivity.this, OperationalDays.class);
                    startActivity(startNewActivityIntent);
                }
            }
        });
        radioButtonListener();
    }

    public void radioButtonListener() {
        radioGroupId = (RadioGroup) findViewById(R.id.radioGroupId);
        final Button fixtureButtonId = (Button) findViewById(R.id.fixture);
         bulbButtonId = (Button) findViewById(R.id.bulb1);

        bulbButtonId.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroupId.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton selectBtn = (RadioButton) findViewById(selectedId);
                typeOfReplacementBulb = selectBtn.getText().toString();

                // Toast.makeText(ReplacementBulbActivity.this,
                //selectBtn.getText(), Toast.LENGTH_SHORT).show();
                }
        });

        fixtureButtonId.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroupId.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton selectBtn = (RadioButton) findViewById(selectedId);
                typeOfReplacementBulb = selectBtn.getText().toString();
            }
        });


    }

}





