package app.com.ledsavingcalculator;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
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
import java.util.Arrays;
import java.util.List;

import app.com.ledsavingcalculator.database.DataBaseHelper;
import app.com.ledsavingcalculator.database.dao.ExistingBulb;
import app.com.ledsavingcalculator.util.Calculations;

public class ExistingBulbActivity extends AppCompatActivity {

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
    private MyArrayAdapter mySpinnerArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeOfLigth = (Spinner)  findViewById(R.id.existingBulbType);
        numberOfFixture = (EditText)  findViewById(R.id.numberOfFixture);
       //numberOfFixture.setText("280");
        numberOfBulbPerFixture = (EditText)  findViewById(R.id.numberOfBulbPerFixture);
      //numberOfBulbPerFixture.setText("6");
        selectLifeSpanType = (Spinner)  findViewById(R.id.selectLifeSpanType);
        costOfExistingBulb = (EditText)  findViewById(R.id.costOfExistingBulb);
     // costOfExistingBulb.setText("3");
        ExistingBulbWattage = (EditText) findViewById(R.id.ExistingBulbWattage);
      // ExistingBulbWattage.setText("54");
        enteredTypeOfBulb = (EditText) findViewById(R.id.enteredTypeOfBulb);
        enterTypeOfBulbLable = (TextView) findViewById(R.id.enterTypeOfBulbLable);
        lifespanHour = (EditText) findViewById(R.id.lifespanHour);
      // lifespanHour.setText("25000");
        hourUnit =  findViewById(R.id.hourUnit);
        monthUnit =  findViewById(R.id.monthUnit);
        nextBtn =(Button) findViewById(R.id.nextBtn);
        Spinner spinner = (Spinner) findViewById(R.id.existingBulbType);

        setupUI(findViewById(R.id.parent));

        dataBaseHelper = new DataBaseHelper(getBaseContext());

        final Typeface mFont = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

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
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        MyArrayAdapter dataAdapter = new MyArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_single_choice, categories);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        String[] selectLifeSpan = new String[]{"Hours","Months"};
        final ArrayAdapter<String> adapter_lifespan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, selectLifeSpan);
       // adapter_lifespan.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //selectLifeSpanType.setAdapter(adapter_lifespan);

       // mySpinnerArrayAdapter = new MyArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);
       // mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        MyArrayAdapter adapter = new MyArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_single_choice, Arrays.asList(selectLifeSpan));
        selectLifeSpanType.setAdapter(adapter);


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

                if( numberOfFixture.getText().toString().equals("")) {
                    numberOfFixture.setError("Number of Fixture is required");
                }
                else if(numberOfBulbPerFixture.getText().toString().trim().equals("")){
                    numberOfBulbPerFixture.setError("Number of bulb is required");
                }
                else if(lifespanHour.getText().toString().trim().equals("")){
                    lifespanHour.setError("Life Span is required");
                }
                else if(ExistingBulbWattage.getText().toString().trim().equals("")){
                    ExistingBulbWattage.setError("Wattage of bulb is required");
                }
                else if(costOfExistingBulb.getText().toString().trim().equals("")){
                    costOfExistingBulb.setError("Cost of bulb is required");
                }
                else {

                    int numberOfCurrentFixture = Integer.parseInt( numberOfFixture.getText().toString() );
                    int numberOfBulbPerFixture1 = Integer.parseInt(numberOfBulbPerFixture.getText().toString());
                    float costOfExistingBulb1 = Float.parseFloat(costOfExistingBulb.getText().toString());
                    int ExistingBulbWattage1 = Integer.parseInt( ExistingBulbWattage.getText().toString());
                    int lifespanHour1 = Integer.parseInt( lifespanHour.getText().toString());

                    if(selectedTypeOfLifeSpan.equals("Months")){
                        lifespanHour1 = lifespanHour1 * 30 * 24;
                    }

                    ExistingBulb existingBulbData = new ExistingBulb(selectedTypeofExistingBulb, numberOfCurrentFixture,
                            lifespanHour1, costOfExistingBulb1, numberOfBulbPerFixture1, ExistingBulbWattage1);

               // ExistingBulb existingBulb = new ExistingBulb("CFL", 54, 25000, 202,6,54);
                    try {
                        Dao<ExistingBulb, Integer> existingBulbDao = ExistingBulbActivity.this.dataBaseHelper.getExistingBulbDao();
                        existingBulbDao.create(existingBulbData);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Intent startNewActivityIntent = new Intent(ExistingBulbActivity.this, ReplacementBulbActivity.class);
                    startActivity(startNewActivityIntent);
               }
            }

       });

    }

    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Calculations.hideSoftKeyboard(ExistingBulbActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
