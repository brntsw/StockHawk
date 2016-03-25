package com.sam_chordas.android.stockhawk.rest;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Bruno on 25/03/2016.
 */
public class DisplayToast implements Runnable {

    private Context mContext;
    private String mMessage;

    public DisplayToast(Context mContext, String message){
        this.mContext = mContext;
        this.mMessage = message;
    }

    @Override
    public void run() {
        Toast.makeText(mContext, mMessage, Toast.LENGTH_LONG).show();
    }
}
