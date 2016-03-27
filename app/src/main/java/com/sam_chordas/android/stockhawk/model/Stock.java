package com.sam_chordas.android.stockhawk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bruno on 27/03/2016.
 */
public class Stock implements Parcelable {

    private String stockName;
    private String date;
    private float closeValue;

    public Stock(){}

    public Stock(Parcel in){
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Stock> CREATOR = new Parcelable.Creator<Stock>() {
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        public Stock[] newArray(int size) {

            return new Stock[size];
        }

    };

    public void readFromParcel(Parcel in) {
        stockName = in.readString();
        date = in.readString();
        closeValue = in.readFloat();

    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getCloseValue() {
        return closeValue;
    }

    public void setCloseValue(float closeValue) {
        this.closeValue = closeValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stockName);
        dest.writeString(date);
        dest.writeFloat(closeValue);
    }
}
