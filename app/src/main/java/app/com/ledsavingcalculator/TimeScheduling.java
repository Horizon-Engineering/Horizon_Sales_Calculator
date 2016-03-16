package app.com.ledsavingcalculator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.ledsavingcalculator.database.DataBaseHelper;
import app.com.ledsavingcalculator.database.dao.ExistingBulb;
import app.com.ledsavingcalculator.database.dao.ReplacementBulb;
import app.com.ledsavingcalculator.database.dao.Results;
import app.com.ledsavingcalculator.util.Calculations;
import app.com.ledsavingcalculator.util.PerDayData;

public class TimeScheduling extends Activity implements WeekView.EventClickListener, WeekView.EventLongPressListener, MonthLoader.MonthChangeListener {

    private WeekView mWeekView;
    public int startTime1;
    public int endTime1;
    private static List<WeekViewEvent> events;
    public boolean shiftOverlap;
    public String doneScheduling = "false";

    public TimeScheduling() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.schedule_time_for_operationaldays);

        if (events == null) {
            events = new ArrayList<>();
        }

        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);

        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    int year = bundle.getInt("year");
                    int month = bundle.getInt("month");
                    int dayOfTheMonth = bundle.getInt("dayOfTheMonth");
                    int eventStartTime = bundle.getInt("startTime");
                    int startAMPM = bundle.getInt("startAMPM");
                    int eventEndTime = bundle.getInt("endTime");
                    int endAMPM = bundle.getInt("endAMPM");
                    int startMinute = bundle.getInt("startMinute");
                    int endMinute = bundle.getInt("endMinute");
                    String repeat = bundle.getString("repeat");
                    Intent intent = getIntent();
                    HashMap<Integer, String> hashMap = (HashMap<Integer, String>) intent.getSerializableExtra("map");

                    for (WeekViewEvent weekView : events) {
                        int day = weekView.getStartTime().get(Calendar.DAY_OF_MONTH);
                        int existingStartTime = weekView.getStartTime().get(Calendar.HOUR_OF_DAY);
                        int existingEndTime = weekView.getEndTime().get(Calendar.HOUR_OF_DAY);

                        if (day == dayOfTheMonth && (existingStartTime < eventEndTime && existingEndTime > eventStartTime)
                                && (startAMPM == weekView.getStartTime().get(Calendar.AM_PM))) {
                            shiftOverlap = true;
                            // Toast.makeText(getBaseContext(), "Time Shifts are overlapping", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (repeat != null && repeat.equals("true") || hashMap != null) {
                        int count = 1;
                        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {

                            if (entry.getValue().equals("true")) {
                                if (newMonth - 1 == month && !shiftOverlap) {
                                    Calendar startTime = Calendar.getInstance();
                                    startTime.set(Calendar.DAY_OF_WEEK, entry.getKey());
                                    dayOfTheMonth = startTime.get(Calendar.DATE);
                                    int Month = startTime.get(Calendar.MONTH);
                                    addEvent(newYear, dayOfTheMonth, eventStartTime, startMinute, startAMPM, eventEndTime, endMinute,
                                            endAMPM, startTime, Month, R.color.event_color_01);
                                }
                            }
                            count++;
                        }
                        return events;
                    } else if (newMonth - 1 == month && !shiftOverlap) {
                        Calendar startTime = Calendar.getInstance();
                        addEvent(newYear, dayOfTheMonth, eventStartTime, startMinute, startAMPM, eventEndTime, endMinute,
                                endAMPM, startTime, newMonth - 1, R.color.event_color_02);
                    }
                }
                return events;
            }
        });

        mWeekView.setEventLongPressListener(this);

        mWeekView.setEmptyViewLongPressListener(new WeekView.EmptyViewLongPressListener() {
            @Override
            public void onEmptyViewLongPress(Calendar time) {
                int year = time.get(Calendar.YEAR);
                //firstVisibleDay.get(Calendar.DATE);
                int month = time.get(Calendar.MONTH);
                int dayOfTheMonth = time.get(Calendar.DAY_OF_MONTH);
                int startTime = time.get(Calendar.HOUR);
                int startAMPM = time.get(Calendar.AM_PM);
                Intent startNewActivityIntent = new Intent(getApplicationContext(), ShowDialog.class);
                startNewActivityIntent.putExtra("year", year);
                startNewActivityIntent.putExtra("month", month);
                startNewActivityIntent.putExtra("dayOfTheMonth", dayOfTheMonth);
                startNewActivityIntent.putExtra("startTime", startTime);
                startNewActivityIntent.putExtra("startAMPM", startAMPM);
                startActivity(startNewActivityIntent);
            }
        });

        Button clearBtn = (Button) findViewById(R.id.cancelBtn);
        Button doneBtn = (Button) findViewById(R.id.doneBtn);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh = new Intent(getBaseContext(), TimeScheduling.class);
                startActivity(refresh);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());
                try {
                    Dao<Results, Integer> resultDao = dataBaseHelper.getResultDao();
                    resultDao.clearObjectCache();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                events.clear();

            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneScheduling = "true";
                Calculations calculations = new Calculations();
                try {
                    final PerDayData totalHoursPerDay = calculations.getTotalHoursPerDay(events);

                    Firebase mRef;
                    mRef = new Firebase("https://crackling-fire-1725.firebaseio.com/Canada/Ontario/Brampton");

                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println(dataSnapshot);

                            ExistingBulb lastRow = null;
                            ReplacementBulb replacementBulbRecord = null;

                            DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());
                            try {
                                Dao<ExistingBulb, Integer> existingBulbDao = dataBaseHelper.getExistingBulbDao();
                                List<ExistingBulb> existingBulbs = existingBulbDao.queryForAll();
                                lastRow = existingBulbs.get(existingBulbs.size() - 1);


                                Dao<ReplacementBulb, Integer> replacementBulbDao = dataBaseHelper.getReplacementBulbDao();
                                List<ReplacementBulb> replacementBulbs = replacementBulbDao.queryForAll();
                                replacementBulbRecord = replacementBulbs.get(replacementBulbs.size() - 1);

                                //query("select * from (select * from tblmessage order by sortfield ASC limit 10) order by sortfield DESC");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            //summer price K/W
                            double onloadSummerPrice = (double) dataSnapshot.child("/summer/weekdays/onload").getValue();
                            double offloadsummerCost = (double) dataSnapshot.child("/summer/weekdays/offload").getValue();
                            double peakloadSummerCost = (double) dataSnapshot.child("/summer/weekdays/peakload").getValue();


                            double onloadWinterPrice = (double) dataSnapshot.child("/winter/weekdays/onload").getValue();
                            double offloadWinterPrice = (double) dataSnapshot.child("/winter/weekdays/offload").getValue();
                            double peakloadWinterPrice = (double) dataSnapshot.child("/winter/weekdays/peakload").getValue();

                            Calculations calculations1 = new Calculations();

                            int getNoOfBulbsPerFixture = 0;
                            int getNoOfFixtures = 0;
                            if (replacementBulbRecord.getTypeOfReplacement().equals("Fixture")) {
                                getNoOfBulbsPerFixture = 1;
                                getNoOfFixtures = lastRow.getNoOfFixtures();
                            } else {
                                getNoOfBulbsPerFixture = lastRow.getNoOfBulbsPerFixture();
                                getNoOfFixtures = lastRow.getNoOfFixtures();
                            }

                            //Inital LED Installation cost
                            double intialLedCost = calculations1.getIntialLedCost(replacementBulbRecord.getCostOfReplacementbulb(),
                                    0, getNoOfBulbsPerFixture * getNoOfFixtures);

                            //Calculate Total hours perWeek
                            double weeklyActiveHour = calculations1.getWeeklyActiveHour(totalHoursPerDay);

                            //Monthly electricity cost for  existing bulb in both winter and summer
                            double monthlyCostForExistingBulbWinter = calculations1.getMonthlyCostForExistingBulb(totalHoursPerDay, lastRow, onloadWinterPrice,
                                    peakloadWinterPrice, offloadWinterPrice);

                            double monthlyCostForExistingBulbSummer = calculations1.getMonthlyCostForExistingBulb(totalHoursPerDay, lastRow, onloadSummerPrice,
                                    peakloadSummerCost, offloadsummerCost);

                            //Monthly energy cost for existing bulb
                            double monthlyEnergyCostForExisting = calculations1.getMonthlyCostForExistingBulb(totalHoursPerDay, lastRow, 1, 1, 1) * 100.00;


                            //Monthly energy cost for LED Bulb
                            double monthlyEnergyCostForReplacementBulb = calculations1.getMonthlyCostForReplacementBulb(totalHoursPerDay,
                                    replacementBulbRecord, 1, 1, 1, getNoOfFixtures, getNoOfBulbsPerFixture) * 100.00;

                            double energySaving = monthlyEnergyCostForExisting - monthlyEnergyCostForReplacementBulb;

                            //monthly electricity cost for replacement bulb (LED) for both summer and winter
                            double monthlyCostForReplacementBulbForWinter = calculations1.getMonthlyCostForReplacementBulb(totalHoursPerDay, replacementBulbRecord, onloadWinterPrice,
                                    peakloadWinterPrice, offloadWinterPrice, getNoOfFixtures, getNoOfBulbsPerFixture);

                            double monthlyCostForReplacementBulbForSummer = calculations1.getMonthlyCostForReplacementBulb(totalHoursPerDay, replacementBulbRecord, onloadSummerPrice,
                                    peakloadSummerCost, offloadsummerCost, getNoOfFixtures, getNoOfBulbsPerFixture);

                            double costOfExistingBulbReplacement = calculations1.getCostOfExistingBulbReplacement(lastRow, replacementBulbRecord.getLifeSpan(), replacementBulbRecord.getCostOfReplacementbulb());

                            double monthlyCostSavingsForSummer = monthlyCostForExistingBulbSummer - monthlyCostForReplacementBulbForSummer;
                            double monthlyCostSavingsForWinter = monthlyCostForExistingBulbWinter - monthlyCostForReplacementBulbForWinter;
                            Double totalEnergySaving = calculations1.getTotalEnergySaving(replacementBulbRecord.getLifeSpan(), costOfExistingBulbReplacement,
                                    totalHoursPerDay, monthlyCostSavingsForWinter);

                            Results results = new Results();

                            results.setIntialLEDCost(intialLedCost);
                            results.setWeeklyActiveHour(weeklyActiveHour);
                            results.setMonthlyCostForExistingBulbsForWinter(monthlyCostForExistingBulbWinter);
                            results.setMonthlyElectricityOfExistingBulbForSummer(monthlyCostForExistingBulbSummer);
                            results.setMonthlyElecticityCostForLEDForSummer(monthlyCostForReplacementBulbForSummer);

                            results.setMonthlyElecticityCostForLEDForWinter(monthlyCostForReplacementBulbForWinter);
                            results.setCostOfExistingBulbReplacement(costOfExistingBulbReplacement);
                            results.setEnergySavingSummer(energySaving);
                            results.setMonthlyCostSavingForSummer(monthlyCostSavingsForSummer);
                            results.setMonthlyCostSavingForWinter(monthlyCostSavingsForWinter);
                            results.setTotalEnergySaving(totalEnergySaving);
                            results.setMonthlyEnergyCostForExisting(monthlyEnergyCostForExisting);
                            results.setMonthlyEnergyCostForReplacementBulb(monthlyEnergyCostForReplacementBulb);

                            // System.out.println("====>>> result "+results.getId());
                            try {
                                Dao<Results, Integer> resultDao = dataBaseHelper.getResultDao();
                                resultDao.create(results);
                                List<Results> resultsList = resultDao.queryForAll();
                                Results resultsrecord = resultsList.get(resultsList.size() - 1);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                           if(!events.isEmpty()){
                            Intent startNewActivity = new Intent(getBaseContext(), OperationalDays.class);
                            startNewActivity.putExtra("doneScheduling", doneScheduling);
                            startActivity(startNewActivity);
                           }
                            else
                           {
                               Toast.makeText(TimeScheduling.this, "Please select the time", Toast.LENGTH_SHORT).show();
                           }
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }

                    });

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addEvent(int newYear, int dayOfTheMonth, int eventStartTime, int startMinute, int startAMPM,
                         int eventEndTime, int endMinute, int endAMPM, Calendar startTime, int month, int event_color_01) {

        startTime.set(Calendar.HOUR_OF_DAY, eventStartTime);
        startTime.set(Calendar.MINUTE, startMinute);
        startTime.set(Calendar.MONTH, month);
        startTime.set(Calendar.YEAR, newYear);
        startTime.set(Calendar.DAY_OF_MONTH, dayOfTheMonth);
        startTime.add(Calendar.AM_PM, startAMPM);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, eventEndTime);
        endTime.set(Calendar.MINUTE, endMinute);
        endTime.set(Calendar.MONTH, month);
        endTime.set(Calendar.DAY_OF_MONTH, dayOfTheMonth);
        endTime.add(Calendar.AM_PM, endAMPM);
        WeekViewEvent event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(event_color_01));
        events.add(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }


    /**
     * Very important interface, it's the base to load events in the calendar.
     * This method is called three times: once to load the previous month, once to load the next month and once to load the current month.<br/>
     * <strong>That's why you can have three times the same event at the same place if you mess up with the configuration</strong>
     *
     * @param newYear  : year of the events required by the view.
     * @param newMonth : month of the events required by the view <br/><strong>1 based (not like JAVA API) --> January = 1 and December = 12</strong>.
     * @return a list of the events happening <strong>during the specified month</strong>.
     */
    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return null;
    }
}