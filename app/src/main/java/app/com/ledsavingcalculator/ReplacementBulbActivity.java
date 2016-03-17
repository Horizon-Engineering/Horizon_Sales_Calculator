package app.com.ledsavingcalculator;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import app.com.ledsavingcalculator.database.DataBaseHelper;
import app.com.ledsavingcalculator.database.dao.ReplacementBulb;
import app.com.ledsavingcalculator.util.Calculations;

public class ReplacementBulbActivity extends Activity {

    View backBtnId;
    EditText lifeSpan;
    EditText costOfReplacementBulb;
    EditText wattageOfReplacementbulb;
    Spinner replacmentType;
    Button bulbButtonId;
    View nextBtn;
    private String typeOfReplacementBulb;
    RadioGroup radioGroupId;
    Firebase mRef;
    private ArrayList<String> lightArray;
    private String selectedBulbType;
    TextView enterTypeOfBulbLable;
    TextView typeOfBulb;

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replacement_page);

        final DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());

        backBtnId = findViewById(R.id.backBtn);
        lifeSpan = (EditText) findViewById(R.id.replacementLifeSpan);
        //lifeSpan.setText("50000");
        costOfReplacementBulb = (EditText) findViewById(R.id.costOfReplacement);
        //costOfReplacementBulb.setText("6");
        wattageOfReplacementbulb = (EditText) findViewById(R.id.wattageOfReplacementbulb);
        //wattageOfReplacementbulb.setText("100");
        replacmentType = (Spinner) findViewById(R.id.replacmentType);
        enterTypeOfBulbLable = (TextView) findViewById(R.id.typeOfBulbLabel);
        typeOfBulb = (TextView) findViewById(R.id.typeOfBulb);

        nextBtn = findViewById(R.id.nextBtn);

        //replacmentType.setText("LED");
        replacmentType.setFocusable(false);
        replacmentType.setClickable(true);

        setupUI(findViewById(R.id.parent));

        //set font for activity
        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");
        final ViewGroup mContainer = (ViewGroup) findViewById(
                android.R.id.content).getRootView();
        SetFont.setAppFont(mContainer, mFont);

        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://crackling-fire-1725.firebaseio.com/Light-type");

        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lightArray = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    lightArray.add(0, postSnapshot.getValue().toString());
                }
                updateTypeOfBulbs();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getBaseContext(), CostSavingGraph.class);
                startActivity(startNewActivity);
            }
        });

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
                    //String replacmentType1 = String.valueOf(replacmentType.getText().toString());

                    if(selectedBulbType.equals("Other")){
                        selectedBulbType = typeOfBulb.getText().toString();
                    }

                    ReplacementBulb replacementBulb = new ReplacementBulb(typeOfReplacementBulb,
                            selectedBulbType, wattageOfBulb, replacementLifespan, costOfReplacementPerBulb);
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


    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Calculations.hideSoftKeyboard(ReplacementBulbActivity.this);
                    return false;
                }

            });
        }
    }

    private void updateTypeOfBulbs() {

        MyArrayAdapter dataAdapter = new MyArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_single_choice,
                lightArray);
        // attaching data adapter to spinner
        replacmentType.setAdapter(dataAdapter);

        replacmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBulbType = parent.getItemAtPosition(position).toString();

                if (selectedBulbType.equals("Other")) {
                    typeOfBulb.setVisibility(View.VISIBLE);
                    enterTypeOfBulbLable.setVisibility(View.VISIBLE);
                } else {
                    typeOfBulb.setVisibility(View.INVISIBLE);
                    enterTypeOfBulbLable.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}





