package com.example.vaibhavchopda.gymmy24v11;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

public class GraphActivity extends AppCompatActivity {

    Button insertButton;
    EditText xInput, yInput;
    GraphView graphView;

    BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[0]);
    MySQLHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        insertButton = (Button) findViewById(R.id.insertButton);
        xInput = (EditText) findViewById(R.id.InputTextX);
        yInput = (EditText) findViewById(R.id.InputTextY);
        graphView = (GraphView) findViewById(R.id.graph);

        myHelper = new MySQLHelper(this);
        sqLiteDatabase = myHelper.getWritableDatabase();

        // Stlying for the graph, including legends, view windows and scalability/scrolling

        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setTextSize(30);
        graphView.getLegendRenderer().setFixedPosition(50, 100);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setMaxY(10);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxX(31);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setXAxisBoundsManual(true);

        graphView.getGridLabelRenderer().setVerticalAxisTitle("Hours Trained");
        graphView.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("December");
        //series.setAnimated(true);
        series.setTitle("Daily Gym Attendance");
        graphView.getGridLabelRenderer().setNumHorizontalLabels(9);

        //Displays a toast on touch that tells the user how many hours they have trained for
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Double y = dataPoint.getY();
                Double x = dataPoint.getX();
                int yVal = y.intValue();
                int xVal = x.intValue();
                Toast.makeText(getApplicationContext(), "You trained for " + yVal + " hours on the " + xVal + " of December!", Toast.LENGTH_SHORT).show();
            }
        });

        // Sets a colour for each bar of the graph depending on its value

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 7), 100);
            }
        });


        execInsert();
        graphView.addSeries(series);
    }


    // Inserts data points from the user inputs
    private void execInsert() {

        // initial data points for graph passed to sqlite

        myHelper.insertData(1, 1);
        myHelper.insertData(2, 2);
        myHelper.insertData(3, 3);
        myHelper.insertData(4, 4);
        myHelper.insertData(5, 4);
        myHelper.insertData(6, 5);
        myHelper.insertData(7, 6);
        series.resetData(getDataPoint());

        insertButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int xVal = Integer.parseInt(String.valueOf(xInput.getText()));
                int yVal = Integer.parseInt(String.valueOf(yInput.getText()));
                if(xVal < 32 ) {
                    myHelper.insertData(xVal, yVal);
                    series.resetData(getDataPoint());

                }else {
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    // Method for iterating through data inputted into the sqlite database
    private DataPoint[] getDataPoint() {
        String[] columns = {"xValues","yValues"};
        //Cursor cursor = sqLiteDatabase.query("GraphTable", columns, null, null, null, null, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT distinct xValues,yValues FROM GraphTable order by xValues asc",new String[]{});

        DataPoint[] dp = new DataPoint[cursor.getCount()];

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getInt(0), cursor.getInt(1));
        }
        return dp;


    }

}