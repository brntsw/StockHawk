package com.sam_chordas.android.stockhawk.graph;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.CubicEase;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.model.Stock;
import com.sam_chordas.android.stockhawk.rest.Utils;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by Bruno on 26/03/2016.
 */
public class GraphStock extends CardController {


    private final LineChartView mChart;
    private final Context mContext;
    private List<Stock> mListStocks;

    private Tooltip mTip;

    private Runnable mBaseAction;


    public GraphStock(RelativeLayout relativeGraph, List<Stock> listStocks, Context context){
        super(relativeGraph);

        mContext = context;
        mChart = (LineChartView) relativeGraph.findViewById(R.id.chart1);
        mListStocks = listStocks;
    }


    @Override
    public void show(Runnable action) {
        super.show(action);

        int dataSize = mListStocks.size();

        String[] labels = new String[dataSize];
        final float[] values = new float[dataSize];

        for(int i = 0; i < dataSize; i++){
            labels[i] = Utils.convertDateToWeekday(mListStocks.get(i).getDate(), mContext);
            values[i] = mListStocks.get(i).getCloseValue();
        }

        // Data
        LineSet dataset = new LineSet(labels, values);

        // Tooltip
        mTip = new Tooltip(mContext, R.layout.linechart_three_tooltip, R.id.value);

        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(65), (int) Tools.fromDpToPx(25));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            mTip.setPivotX(Tools.fromDpToPx(65) / 2);
            mTip.setPivotY(Tools.fromDpToPx(25));
        }

        mChart.setTooltips(mTip);

        mChart.setAxisColor(Color.WHITE)
                .setLabelsColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);

        mChart.setGrid(ChartView.GridType.VERTICAL, 2, mListStocks.size(), paint);

        dataset.setColor(Color.parseColor("#758cbb"))
                .setDotsRadius(Tools.fromDpToPx(4))
                .setDotsStrokeThickness(Tools.fromDpToPx(3))
                .setDotsStrokeColor(Color.YELLOW)
                .setThickness(10);

        mChart.addData(dataset);

        Animation anim = new Animation()
                .setEasing(new CubicEase()
                );

        StringBuilder descriptionBuilder = new StringBuilder();

        float minValue = Float.MAX_VALUE, maxValue = Float.MIN_VALUE, value;

        for (int i = 0; i < dataSize; ++i) {
            value = dataset.getValue(i);

            minValue = min(minValue, value);
            maxValue = max(maxValue, value);

            descriptionBuilder.append(dataset.getLabel(i))
                    .append(": ")
                    .append(value);
        }

        mChart.setContentDescription(descriptionBuilder.toString());

        int maxRoundUp = Utils.roundToNextTen((int) maxValue, true);
        int minRoundDown = Utils.roundToNextTen((int) minValue, false);

        mChart.setAxisBorderValues(minRoundDown,
                maxRoundUp,
                (maxRoundUp - minRoundDown) / 5);

        mChart.show(anim);
    }
}
