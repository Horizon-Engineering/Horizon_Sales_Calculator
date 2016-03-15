package app.com.ledsavingcalculator;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import app.com.ledsavingcalculator.database.DataBaseHelper;
import app.com.ledsavingcalculator.database.dao.Results;

public class EnergySavigGraph extends Activity{
    GraphView graphView;
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.energy_saving_graph);

        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");

        final ViewGroup mContainer = (ViewGroup) findViewById(
                android.R.id.content).getRootView();
        SetFont.setAppFont(mContainer, mFont);

        double monthlyEnergyCostForExisting = 0.0;
        double monthlyEnergyCostReplacement = 0.0;

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());

        Button nextBtn = (Button) findViewById(R.id.nextBtn);
        Button backBtn = (Button) findViewById(R.id.backBtn);
        TextView existingPower = (TextView) findViewById(R.id.existingPower);
        TextView replacementPower = (TextView) findViewById(R.id.replacementPower);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getBaseContext(), CostSavingGraph.class);
                startActivity(startNewActivity);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getBaseContext(), SendEmail.class);
                startActivity(startNewActivity);
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                View screenView = rootView.getRootView();
                screenView.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache(), 0, 0, graphView.getWidth(), graphView.getHeight());
                writedata(bitmap, "save.png");
            }
        });

        try {
            Dao<Results, Integer> resultDao = dataBaseHelper.getResultDao();
            List<Results> resultses = resultDao.queryForAll();

            for (Results results : resultses) {
                monthlyEnergyCostForExisting += results.getMonthlyEnergyCostForExisting();
                monthlyEnergyCostReplacement += results.getMonthlyEnergyCostForReplacementBulb();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        graphView = (GraphView) findViewById(R.id.graph);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan", "Feb", "Mar", "Apr","May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, monthlyEnergyCostForExisting),
                new DataPoint(1, monthlyEnergyCostForExisting),
                new DataPoint(2, monthlyEnergyCostForExisting),
                new DataPoint(3, monthlyEnergyCostForExisting),
                new DataPoint(4, monthlyEnergyCostForExisting),
                new DataPoint(5, monthlyEnergyCostForExisting),
                new DataPoint(6, monthlyEnergyCostForExisting),
                new DataPoint(7, monthlyEnergyCostForExisting),
                new DataPoint(8, monthlyEnergyCostForExisting),
                new DataPoint(9, monthlyEnergyCostForExisting),
                new DataPoint(10,monthlyEnergyCostForExisting),
                new DataPoint(11,monthlyEnergyCostForExisting)
        });

        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, monthlyEnergyCostReplacement),
                new DataPoint(1, monthlyEnergyCostReplacement),
                new DataPoint(2, monthlyEnergyCostReplacement),
                new DataPoint(3, monthlyEnergyCostReplacement),
                new DataPoint(4, monthlyEnergyCostReplacement),
                new DataPoint(5, monthlyEnergyCostReplacement),
                new DataPoint(6, monthlyEnergyCostReplacement),
                new DataPoint(7, monthlyEnergyCostReplacement),
                new DataPoint(8, monthlyEnergyCostReplacement),
                new DataPoint(9, monthlyEnergyCostReplacement),
                new DataPoint(10,monthlyEnergyCostReplacement),
                new DataPoint(11,monthlyEnergyCostReplacement)
        });


        replacementPower.setText(""+ (int) monthlyEnergyCostForExisting);
        existingPower.setText(""+(int) monthlyEnergyCostReplacement);

        series.setColor(Color.RED);
        series.setTitle("Existing");

        series1.setColor(Color.BLUE);
        series1.setTitle("Replacement");

        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphView.getSecondScale().setMinY(0);
        graphView.setTitle("Energy Saving Graph");
        graphView.setTitleColor(Color.BLACK);
        graphView.setBackgroundColor(Color.WHITE);

        graphView.addSeries(series1);
        graphView.addSeries(series);
    }


    public void writedata(Bitmap bitmap, String filename) {
        /*
        String state;
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/PdfTest");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, filename);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
