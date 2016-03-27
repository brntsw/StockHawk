package com.sam_chordas.android.stockhawk.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RelativeLayout;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.graph.GraphStock;
import com.sam_chordas.android.stockhawk.model.Stock;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 26/03/2016.
 */
public class StockTask extends AsyncTask<String, Void, List<Stock>> {
    private ProgressDialog progress;
    private RelativeLayout relativeLayout;
    private Context context;
    private OkHttpClient okHttpClient;

    public StockTask(RelativeLayout relativeLayout, Context context){
        this.relativeLayout = relativeLayout;
        this.context = context;
    }

    protected void onPreExecute(){
        progress = new ProgressDialog(context);
        progress.setMessage(context.getResources().getString(R.string.loading_graph));
        progress.show();
    }

    @Override
    protected List<Stock> doInBackground(String... params) {
        String stock = params[0];

        okHttpClient = new OkHttpClient();
        StringBuilder urlStringBuilder = new StringBuilder();
        List<Stock> listStocks = new ArrayList<>();
        try {
            urlStringBuilder.append("https://query.yahooapis.com/v1/public/yql?q=");
            urlStringBuilder.append(URLEncoder.encode("select * from yahoo.finance.historicaldata where symbol = " +
                    "\"" + stock + "\" ", "UTF-8"));
            urlStringBuilder.append("and startDate = \"2016-03-12\" and endDate = \"2016-03-23\"");
            urlStringBuilder.append("&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");

            String data = fetchData(urlStringBuilder.toString());

            //Checks if there were any problems with the server
            if (data.startsWith("{\"error\":{")) {
                throw new IllegalStateException("A problem occurred with the server");
            }
            else{
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonQuery = jsonObject.getJSONObject("query");
                JSONObject jsonResults = jsonQuery.getJSONObject("results");

                JSONArray jsonArrayQuote = jsonResults.getJSONArray("quote");

                for(int i = 0; i < jsonArrayQuote.length(); i++){
                    Stock objStock = new Stock();

                    JSONObject obj = jsonArrayQuote.getJSONObject(i);
                    objStock.setStockName(obj.getString("Symbol"));
                    objStock.setDate(obj.getString("Date"));
                    objStock.setCloseValue(Float.parseFloat(obj.getString("Close")));

                    listStocks.add(objStock);
                }
            }
        } catch (IOException | JSONException e) {
            Log.e(StockTask.class.getName(), "Error: " + e);
        }

        return listStocks;
    }

    protected void onPostExecute(List<Stock> result){
        progress.dismiss();

        //Tratamento do dado na UI - passar o result como parâmetro para o GraphStock
        (new GraphStock(relativeLayout, result, context)).init();
    }

    private String fetchData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }
}
