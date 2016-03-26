package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.graph.GraphStock;

/**
 * Created by Bruno on 26/03/2016.
 */
public class GraphStockActivity extends Activity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_graph);

        Bundle bundle = getIntent().getExtras();
        String symbol = bundle.getString(MyStocksActivity.TAG_SYMBOL);

        (new GraphStock((RelativeLayout) findViewById(R.id.relativeGraph), this)).init();

        if(savedInstanceState == null){
            /** TODO Executa o AsyncTask enviando o symbol como parâmetro e, ao final, recebe o resultado e substitui o
             * TODO  fragment carregando (gif) pelo fragment que contêm o Gráfico (GraphStock)
             **/
        }
    }

}
