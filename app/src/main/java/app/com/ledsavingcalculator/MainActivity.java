package app.com.ledsavingcalculator;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DataBaseHelper;
import database.dao.ExistingBulb;

public class MainActivity extends AppCompatActivity {

    private Spinner typeOfLigth;
    private EditText numberOfFixture;
    private EditText numberOfBulbPerFixture;
    private Spinner selectLifeSpanType;
    private EditText costOfExistingBulb;
    private EditText ExistingBulbWattageLable;
    private String selectedTypeofExistingBulb;
    private EditText enteredTypeOfBulb;
    private TextView enterTypeOfBulbLable;
    private String selectedTypeOfLifeSpan;
    private EditText lifespanHour;
    private View hourUnit;
    private View monthUnit;
    private EditText ExistingBulbWattage;
    private Button nextBtn;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        typeOfLigth = (Spinner)  findViewById(R.id.existingBulbType);
        numberOfFixture = (EditText)  findViewById(R.id.numberOfFixture);
        numberOfBulbPerFixture = (EditText)  findViewById(R.id.numberOfBulbPerFixture);
        selectLifeSpanType = (Spinner)  findViewById(R.id.selectLifeSpanType);
        costOfExistingBulb = (EditText)  findViewById(R.id.costOfExistingBulb);
        ExistingBulbWattage = (EditText) findViewById(R.id.ExistingBulbWattage);
        enteredTypeOfBulb = (EditText) findViewById(R.id.enteredTypeOfBulb);
        enterTypeOfBulbLable = (TextView) findViewById(R.id.enterTypeOfBulbLable);
        lifespanHour = (EditText) findViewById(R.id.lifespanHour);
        hourUnit =  findViewById(R.id.hourUnit);
        monthUnit =  findViewById(R.id.monthUnit);
        nextBtn =(Button) findViewById(R.id.nextBtn);
        Spinner spinner = (Spinner) findViewById(R.id.existingBulbType);

        /*Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Lato-BoldItalic.ttf");
        enterTypeOfBulbLable.setTypeface(face);



        dataBaseHelper = new DataBaseHelper(getBaseContext());*/

        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-BoldItalic.ttf");
        final ViewGroup mContainer = (ViewGroup) findViewById(
                android.R.id.content).getRootView();
        SetFont.setAppFont(mContainer, mFont);

        // String[] bulbItems = new String[]{"Incandescent", "Halogen", "CFL", "Fluroscent tube", "HID (metal halide)", "high pressure sodium", "Other"};
        List<String> categories = new ArrayList<>();
        categories.add("Incandescent");
        categories.add("Halogen");
        categories.add("CFL");
        categories.add("Fluroscent tube");
        categories.add("HID (metal halide)");
        categories.add("high pressure sodium");
        categories.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        String[] selectLifeSpan = new String[]{"Hours","Months"};
        final ArrayAdapter<String> adapter_lifespan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, selectLifeSpan);
        adapter_lifespan.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        selectLifeSpanType.setAdapter(adapter_lifespan);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTypeofExistingBulb = parent.getItemAtPosition(position).toString();

                if (selectedTypeofExistingBulb.equals("Other")) {
                    enteredTypeOfBulb.setVisibility(View.VISIBLE);
                    enterTypeOfBulbLable.setVisibility(View.VISIBLE);
                } else {
                    enteredTypeOfBulb.setVisibility(View.INVISIBLE);
                    enterTypeOfBulbLable.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectLifeSpanType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTypeOfLifeSpan = parent.getItemAtPosition(position).toString();

                if (selectedTypeOfLifeSpan.equals("Hours")) {
                    lifespanHour.setVisibility(View.VISIBLE);
                    hourUnit.setVisibility(View.VISIBLE);
                    monthUnit.setVisibility(View.INVISIBLE);
                } else {
                    hourUnit.setVisibility(View.INVISIBLE);
                    monthUnit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int numberOfCurrentFixture = Integer.parseInt( numberOfFixture.getText().toString() );
                int numberOfBulbPerFixture1 = Integer.parseInt(numberOfBulbPerFixture.getText().toString());
                int costOfExistingBulb1 = Integer.parseInt( costOfExistingBulb.getText().toString());
                int ExistingBulbWattage1 = Integer.parseInt( ExistingBulbWattage.getText().toString());
                int lifespanHour1 = Integer.parseInt( lifespanHour.getText().toString());

                ExistingBulb existingBulbData = new ExistingBulb(selectedTypeofExistingBulb, numberOfCurrentFixture,
                        lifespanHour1,costOfExistingBulb1,numberOfBulbPerFixture1, ExistingBulbWattage1);
                try {
                    Dao<ExistingBulb, Integer> existingBulbDao = dataBaseHelper.getExistingBulbDao();
                    existingBulbDao.create(existingBulbData);
                  //  int existingBulb = existingBulbDao.queryForId(6).getId();
                    // System.out.println("TEST ------------------- "+existingBulb.toString());

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent startNewActivityIntent = new Intent(MainActivity.this, ReplacementBulbActivity.class);
                startActivity(startNewActivityIntent);


            }
        });

    }
}
