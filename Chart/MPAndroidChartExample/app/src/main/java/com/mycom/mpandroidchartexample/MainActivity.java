package com.mycom.mpandroidchartexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * https://github.com/PhilJay/MPAndroidChart
 * wiki: https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started
 *
 * http://code.tutsplus.com/tutorials/add-charts-to-your-android-app-using-mpandroidchart--cms-23335
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    BarChart barchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barchart_main);

        barchart = (BarChart) findViewById(R.id.barchart);

        //Entries
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        BarData data = new BarData(labels, dataset);
        barchart.setData(data);

        barchart.setDescription("# of times Alice called Bob");

        barchart.setOnClickListener(this);

        /**
         *
            BarChart barchart = new BarChart(context);
            setContentView(barchart);

            LineChart linechart = new LineChart(Context);
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
            rl.add(linechart);

         */
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show();
        barchart.animateY(5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
