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
import java.text.DecimalFormat;
import java.util.List;

import app.com.ledsavingcalculator.database.DataBaseHelper;
import app.com.ledsavingcalculator.database.dao.Results;

public class CostSavingGraph extends Activity {
    GraphView graphView;
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cost_saving_graph);

        final Typeface mFont = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");

        final ViewGroup mContainer = (ViewGroup) findViewById(
                android.R.id.content).getRootView();
        SetFont.setAppFont(mContainer, mFont);

        double monthlyCostSavingsForSummer = 0.0;
        double monthlyCostSavingsForWinter = 0.0;
        double mothlyCostForLEDForSummer = 0.0;
        double mothlyCostForLEDForWinter = 0.0;

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());

        Button nextBtn = (Button) findViewById(R.id.nextBtn);
        Button backBtn = (Button) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getBaseContext(), OperationalDays.class);
                startActivity(startNewActivity);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getBaseContext(), EnergySavigGraph.class);
                startActivity(startNewActivity);
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                View screenView = rootView.getRootView();
                screenView.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache(), 0, 0, graphView.getWidth(), graphView.getHeight());
                writedata(bitmap, "cost.png");
            }
        });

        try {
            Dao<Results, Integer> resultDao = dataBaseHelper.getResultDao();
            List<Results> resultses = resultDao.queryForAll();

            for (Results results : resultses) {
                monthlyCostSavingsForSummer += results.getMonthlyElectricityOfExistingBulbForSummer();
                monthlyCostSavingsForWinter += results.getMonthlyCostForExistingBulbsForWinter();

                mothlyCostForLEDForSummer += results.getMonthlyElecticityCostForLEDForSummer();
                mothlyCostForLEDForWinter += results.getMonthlyElecticityCostForLEDForWinter();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        graphView = (GraphView) findViewById(R.id.graph);
        TextView existingCost = (TextView) findViewById(R.id.existingCost);
        TextView replacement = (TextView) findViewById(R.id.replacementCost);



        double totalSavingsForExistings = (monthlyCostSavingsForSummer + monthlyCostSavingsForWinter)/2.0;
        double totalSavingsForLED = (mothlyCostForLEDForSummer + mothlyCostForLEDForWinter)/2.0;

        DecimalFormat f = new DecimalFormat("##.00");
        totalSavingsForExistings = Double.parseDouble(f.format(totalSavingsForExistings));
        totalSavingsForLED = Double.parseDouble(f.format(totalSavingsForLED));

        replacement.setText(""+totalSavingsForExistings);
        existingCost.setText(""+totalSavingsForLED);




        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan", "Feb", "Mar", "Apr","May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, monthlyCostSavingsForWinter),
                new DataPoint(1, monthlyCostSavingsForWinter),
                new DataPoint(2, monthlyCostSavingsForWinter),
                new DataPoint(3, monthlyCostSavingsForWinter),
                new DataPoint(4, monthlyCostSavingsForSummer),
                new DataPoint(5, monthlyCostSavingsForSummer),
                new DataPoint(6, monthlyCostSavingsForSummer),
                new DataPoint(7, monthlyCostSavingsForSummer),
                new DataPoint(8, monthlyCostSavingsForSummer),
                new DataPoint(9, monthlyCostSavingsForWinter),
                new DataPoint(10,monthlyCostSavingsForWinter),
                new DataPoint(11,monthlyCostSavingsForWinter)
        });

        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, mothlyCostForLEDForWinter),
                new DataPoint(1, mothlyCostForLEDForWinter),
                new DataPoint(2, mothlyCostForLEDForWinter),
                new DataPoint(3, mothlyCostForLEDForWinter),
                new DataPoint(4, mothlyCostForLEDForSummer),
                new DataPoint(5, mothlyCostForLEDForSummer),
                new DataPoint(6, mothlyCostForLEDForSummer),
                new DataPoint(7, mothlyCostForLEDForSummer),
                new DataPoint(8, mothlyCostForLEDForSummer),
                new DataPoint(9, mothlyCostForLEDForWinter),
                new DataPoint(10,mothlyCostForLEDForWinter),
                new DataPoint(11,mothlyCostForLEDForWinter)
        });

        series.setColor(Color.RED);
        series.setTitle("Existing");

        series1.setColor(Color.BLUE);
        series1.setTitle("Replacement");

        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graphView.setTitle("Cost Saving Graph");
        graphView.setTitleColor(Color.BLACK);
        graphView.setBackgroundColor(Color.WHITE);

        graphView.addSeries(series1);
        graphView.addSeries(series);
    }


    public void writedata(Bitmap bitmap, String filename) {
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

    }

}
