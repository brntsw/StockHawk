package com.sam_chordas.android.stockhawk.bus_events;

/**
 * Created by Bruno on 25/03/2016.
 */
public class SymbolEvent {
    public enum STATE {SUCCESS, FAILURE}

    public final STATE state;

    public SymbolEvent(STATE state) {
        this.state = state;
    }
}
