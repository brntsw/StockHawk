package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.graph.GraphStock;
import com.sam_chordas.android.stockhawk.model.Stock;
import com.sam_chordas.android.stockhawk.task.StockTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Bruno on 26/03/2016.
 */
public class GraphStockActivity extends Activity {

    private List<Stock> listStocks;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_graph);

        Bundle bundle = getIntent().getExtras();
        String symbol = bundle.getString(MyStocksActivity.TAG_SYMBOL);
        //(new GraphStock((RelativeLayout) findViewById(R.id.relativeGraph), this)).init();

        if(savedInstanceState == null){
            RelativeLayout relativeGraph = (RelativeLayout) findViewById(R.id.relativeGraph);
            StockTask task = new StockTask(relativeGraph, GraphStockActivity.this);

            try {
                listStocks = task.execute(symbol).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        else{
            listStocks = savedInstanceState.getParcelableArrayList("stocks");

            RelativeLayout relativeGraph = (RelativeLayout) findViewById(R.id.relativeGraph);
            if(listStocks != null && listStocks.size() > 0){
                //Passa como parâmetro
                (new GraphStock(relativeGraph, listStocks, GraphStockActivity.this)).init();
            }
            else{
                Toast.makeText(GraphStockActivity.this, "No results to the selected stock", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelableArrayList("stocks", (ArrayList<? extends Parcelable>) listStocks);

        super.onSaveInstanceState(savedInstanceState);
    }

}
