package app.com.ledsavingcalculator;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OperationalDays extends Activity{
    Firebase mRef;

   List<String> provinceArray;
    private Spinner regions;
    private String selectedRegion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operational_days_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String doneScheduling = bundle.getString("doneScheduling");
        }

        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");

        final ViewGroup mContainer = (ViewGroup) findViewById(
                android.R.id.content).getRootView();
        SetFont.setAppFont(mContainer, mFont);


        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://boiling-fire-3227.firebaseio.com/Canada/Ontario");

        regions = (Spinner) findViewById(R.id.regions);
        Button operationaDays = (Button) findViewById(R.id.operationaDays);
        Button addAnotherType = (Button) findViewById(R.id.addAnotherTypeBtn);
        Button nextBtn = (Button) findViewById(R.id.nextBtn);
        addAnotherType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startNewActivity = new Intent(getBaseContext(), ExistingBulbActivity.class);
                startActivity(startNewActivity);
            }
        });

      operationaDays.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent startNewActivityIntent = new Intent(getBaseContext(), TimeScheduling.class);
              startActivity(startNewActivityIntent);
          }
      });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                provinceArray = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    provinceArray.add(0, postSnapshot.child("/").getKey());
                }
                updateRegion();
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
    }

    private void updateRegion() {

        MyArrayAdapter dataAdapter = new MyArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_single_choice, provinceArray);
        // attaching data adapter to spinner
        regions.setAdapter(dataAdapter);

        regions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRegion = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
