package app.example.com.ledsavingcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView dynamicData;
    Firebase mRef;
    ArrayList<String> provinceArray  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        dynamicData = (TextView) findViewById(R.id.textView);
        mRef = new Firebase("https://crackling-fire-1725.firebaseio.com/Canada/Ontario");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Firebase country = mRef.child("Canada/Ontario/");
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                  // String key = String.valueOf(postSnapshot.child("/"));
                    System.out.println(postSnapshot.child("/") + " - " + " ");
                    provinceArray.add(0, postSnapshot.child("/").getKey());
                   // dynamicData.setText(postSnapshot.child("/").getKey());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"10 V ", "20 V ", "30 V ", "40 V ", "50 V "};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Spinner dropdown1 = (Spinner)findViewById(R.id.spinner2);
        String[] bulbItems = new String[]{"Incandescent", "Halogen", "CFL", "Fluroscent tube", "HID (metal halide)", "high pressure sodium"};
        ArrayAdapter<String> adapter_bulb = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bulbItems);
        dropdown1.setAdapter(adapter_bulb);


        Spinner dropdown2 = (Spinner)findViewById(R.id.spinner3);
        String[] timeInterval = new String[]{"9am - 12pm", "2pm - 6pm", "6pm - 9pm"};
        ArrayAdapter<String> adapter_timeInterval = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeInterval);
        dropdown2.setAdapter(adapter_timeInterval);

        Spinner dropdown3 = (Spinner)findViewById(R.id.spinner4);
        ArrayAdapter<String> adapter_region = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinceArray);
        dropdown3.setAdapter(adapter_region);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                System.out.println(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Firebase.setAndroidContext(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
