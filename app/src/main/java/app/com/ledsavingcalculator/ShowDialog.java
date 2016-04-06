package app.com.ledsavingcalculator;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ShowDialog extends Activity {

    private static final int TIME_DIALOG_ID = 1112;
    private static final int TIME_DIALOG_ID_END = 1111;
    /*String[] timeArray = new String[]{"1:00 AM", "2:00 AM", "3:00 AM", "4:00 AM", "5:00 AM", "6:00 AM", "7:00 AM", "8:00 AM",
            "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM",
            "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM", "11:00 PM", "12:00 PM" };*/
    private Button submitBtn;
    private  Button cancelBtn;
    private int year;
    private int month;
    private int dayOfTheMonth;
    private int eventStartTime;
    private int eventEndTime;
    private int startAMPM;
    private int endAMPM;
    private  CheckBox sunday;
    private  CheckBox monday;
    private  CheckBox tuesday;
    private  CheckBox wednesday;
    private  CheckBox thursday;
    private  CheckBox friday;
    private  CheckBox saturday;
    public String repeatStatus = "false";
    private TextView startOutput;
    private TextView endOutput;
    private int startHour;
    private int startMinute;
    private  int endHour;
    private int endMinute;
    private boolean singleDayChecked = false;
    HashMap<Integer, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pop_up_layout);

        hashMap = new HashMap<>();

        Bundle bundle = getIntent().getExtras();
        year = bundle.getInt("year");
        month = bundle.getInt("month");

        dayOfTheMonth = bundle.getInt("dayOfTheMonth");
        startAMPM = bundle.getInt("startAMPM");
        eventStartTime = bundle.getInt("startTime");

        hashMap.put(1, "false");
        hashMap.put(2, "false");
        hashMap.put(3, "false");
        hashMap.put(4, "false");
        hashMap.put(5, "false");
        hashMap.put(6, "false");
        hashMap.put(7, "false");

        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");

        final ViewGroup mContainer = (ViewGroup) findViewById(
                android.R.id.content).getRootView();
        SetFont.setAppFont(mContainer, mFont);


       /* final Spinner startTime = (Spinner) findViewById(R.id.startTime);
        ArrayAdapter<String> startTimeArray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeArray);
        startTime.setAdapter(startTimeArray);*/

       /* final Spinner endTime = (Spinner) findViewById(R.id.endTime);
        ArrayAdapter<String> endTimeArray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeArray);
        endTime.setAdapter(endTimeArray)*/
        startOutput = (TextView) findViewById(R.id.startTimeOutput);
        endOutput = (TextView) findViewById(R.id.endTimeOutput);

        /********* display current time on screen Start ********/

        final Calendar c = Calendar.getInstance();
        // Current Hour
      //  hour = c.get(Calendar.HOUR_OF_DAY);
        // Current Minute
       // minute = c.get(Calendar.MINUTE);

        // set current time into output textview
        //updateTime(hour, minute);

        /********* display current time on screen End ********/

        // Add Button Click Listener


        Button startBtn = (Button) findViewById(R.id.startTimeBtn);
        Button endBtn = (Button) findViewById(R.id.endTimeBtn);


        submitBtn =(Button) findViewById(R.id.submit);
        cancelBtn = (Button) findViewById(R.id.cancel);

       sunday = (CheckBox) findViewById(R.id.sunday);
       monday = (CheckBox) findViewById(R.id.monday);
        tuesday = (CheckBox) findViewById(R.id.tuesday);
         wednesday = (CheckBox) findViewById(R.id.wednesday);
         thursday = (CheckBox) findViewById(R.id.thursday);
         friday = (CheckBox) findViewById(R.id.friday);
        saturday = (CheckBox) findViewById(R.id.saturday);
        final CheckBox repeat = (CheckBox) findViewById(R.id.repeat);

        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked && !singleDayChecked) {
                    repeatStatus = "true";
                    sunday.setChecked(true);
                    monday.setChecked(true);
                    tuesday.setChecked(true);
                    wednesday.setChecked(true);
                    thursday.setChecked(true);
                    friday.setChecked(true);
                    saturday.setChecked(true);
                }

                if(!isChecked && singleDayChecked){
                    sunday.setChecked(false);
                    monday.setChecked(false);
                    tuesday.setChecked(false);
                    wednesday.setChecked(false);
                    thursday.setChecked(false);
                    friday.setChecked(false);
                    saturday.setChecked(false);

                }
            }
        });// end of repeat check box listener


        sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    singleDayChecked = true;
                    repeat.setChecked(true);
                    hashMap.put(1, "true");
                } else {
                    singleDayChecked = false;
                    hashMap.put(1, "false");
                    repeat.setChecked(false);
                }
            }
        });


    monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(monday.isChecked()){
                singleDayChecked = true;
                hashMap.put(2, "true");
                repeat.setChecked(true);
            } else {
                singleDayChecked = false;
                hashMap.put(2, "false");
                repeat.setChecked(false);
            }
        }
    });

        tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(tuesday.isChecked()){
                    singleDayChecked = true;
                    hashMap.put(3, "true");
                    repeat.setChecked(true);

                } else {
                    hashMap.put(3, "false");
                    singleDayChecked = false;
                    repeat.setChecked(false);
                }
            }
        });

        wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    singleDayChecked = true;
                    hashMap.put(4, "true");
                    repeat.setChecked(true);

                } else {
                    hashMap.put(4, "false");
                    singleDayChecked = false;
                    repeat.setChecked(false);
                }
            }
        });


        thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    singleDayChecked = true;
                    hashMap.put(5, "true");
                    repeat.setChecked(true);

                } else {
                    hashMap.put(5, "false");
                    singleDayChecked = false;
                    repeat.setChecked(false);
                }
            }
        });

        friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    singleDayChecked = true;
                    hashMap.put(6, "true");
                    repeat.setChecked(true);

                } else {
                    hashMap.put(6, "false");
                    singleDayChecked = false;
                    repeat.setChecked(false);
                }
            }
        });

        saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    singleDayChecked = true;
                    hashMap.put(7, "true");
                    repeat.setChecked(true);

                } else {
                    hashMap.put(7, "false");
                    singleDayChecked = false;
                    repeat.setChecked(false);
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewIntent = new Intent(ShowDialog.this, TimeScheduling.class);
                startActivity(startNewIntent);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateTime()) {

                }
                else if(!(hashMap.containsValue("true"))){
                    Toast.makeText(getBaseContext(), "please select day for which event is scheduled", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent startNewIntent = new Intent(ShowDialog.this, TimeScheduling.class);
                    startNewIntent.putExtra("year", year);
                    startNewIntent.putExtra("month", month);
                    startNewIntent.putExtra("dayOfTheMonth", dayOfTheMonth);
                    startNewIntent.putExtra("startTime", startHour);
                    startNewIntent.putExtra("startMinute", startMinute);
                    startNewIntent.putExtra("startAMPM", startAMPM);
                    startNewIntent.putExtra("endTime", endHour);
                    startNewIntent.putExtra("endMinute", endMinute);
                    startNewIntent.putExtra("endAMPM", endAMPM);
                    startNewIntent.putExtra("map", hashMap);
                    //Log.v("HashMapTest", hashMap.get("sunday"));
                    startNewIntent.putExtra("repeat", repeatStatus);
                    startActivity(startNewIntent);
                }
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID_END);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, startHour, startMinute, false);
            case TIME_DIALOG_ID_END:
                return new TimePickerDialog(this, timeEndPickerListener, endHour, endMinute, false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            startHour   = hourOfDay;
            startMinute = minutes;
            startOutput.setText(updateTime(startHour,startMinute));

        }
    };

    private TimePickerDialog.OnTimeSetListener timeEndPickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            endHour   = hourOfDay;
            endMinute = minutes;
            endOutput.setText(updateTime(endHour,endMinute));
            validateTime();
        }
    };

    private static String utilTime(int value) {

        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        return aTime;
    }

    public boolean validateTime(){
        try {

            String string1 = startHour+":"+startMinute +":"+ "0";
            String string2 = endHour+":"+endMinute +":"+ "0";
            Date startTime =  new SimpleDateFormat("HH:mm:ss").parse(string1);
            Date endTime = new SimpleDateFormat("HH:mm:ss").parse(string2);

            if(startTime.compareTo(endTime)>0 || (startHour == 0 && endHour == 0)){
                Toast.makeText(getBaseContext(), "Please select valid time, end time cannot be smaller than start time",
                        Toast.LENGTH_SHORT).show();
                return  false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  true;
    }

    public void showTimePickerDialog(View view) {
    }
}
