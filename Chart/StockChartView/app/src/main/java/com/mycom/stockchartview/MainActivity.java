package com.mycom.stockchartview;

import org.stockchart.StockChartView;
import org.stockchart.core.Area;
import org.stockchart.core.Axis.Side;
import org.stockchart.series.Series;
import org.stockchart.series.Series.IPointAdapter;
import org.stockchart.series.points.AbstractPoint;
import org.stockchart.series.points.Point1;
import org.stockchart.series.points.StockPoint;
import org.stockchart.series.renderers.CandlestickStockRenderer;
import org.stockchart.series.renderers.HistogramRenderer;
import org.stockchart.utils.StockDataGenerator;
import org.stockchart.utils.StockDataGenerator.Point;

import android.app.Activity;
import android.os.Bundle;

/**
 * http://stockchartview.org/
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StockDataGenerator gen = new StockDataGenerator();
        final Point[] points = gen.getNext(100);

        StockChartView chart = (StockChartView) this.findViewById(R.id.stockChartView1);

        Area a1 = chart.addArea();
        Area a2 = chart.addArea();

        Series price = chart.addSeries(a1);
        price.setRenderer(new CandlestickStockRenderer());
        price.setName("price");
        price.setYAxisSide(Side.RIGHT);

        price.setPointAdapter(new IPointAdapter()
        {
            private final StockPoint fPoint = new StockPoint();

            @Override
            public int getPointCount()
            {
                return points.length;
            }

            @Override
            public AbstractPoint getPointAt(int i)
            {
                Point p = points[i];
                fPoint.setValues(p.o, p.h, p.l, p.c);
                return fPoint;
            }
        });

        Series volume = chart.addSeries(a2);
        volume.setRenderer(new HistogramRenderer());
        volume.setName("volume");
        volume.setYAxisSide(Side.RIGHT);

        /*
        volume.setPointAdapter(new IPointAdapter()
        {
            private final Point1 fPoint = new Point1();

            @Override
            public int getPointCount()
            {
                return points.length;
            }

            @Override
            public AbstractPoint getPointAt(int i)
            {
                Point p = points[i];

                fPoint.setValues(p.v);
                return fPoint;
            }
        });
        */
        chart.invalidate();

    }
}