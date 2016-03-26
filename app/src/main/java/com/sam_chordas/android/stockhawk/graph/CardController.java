package com.sam_chordas.android.stockhawk.graph;

import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.sam_chordas.android.stockhawk.R;

/**
 * Created by Bruno on 26/03/2016.
 */
public class CardController {


    private final ImageButton mPlayBtn;
    private final ImageButton mUpdateBtn;


    private final Runnable showAction = new Runnable() {
        @Override
        public void run() {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    show(unlockAction);
                }
            }, 500);
        }
    };

    private final Runnable unlockAction =  new Runnable() {
        @Override
        public void run() {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    unlock();
                }
            }, 500);
        }
    };


    protected boolean firstStage;


    protected CardController(RelativeLayout relativeToolbar){
        super();

        RelativeLayout toolbar = (RelativeLayout) relativeToolbar.findViewById(R.id.chart_toolbar);
        mPlayBtn = (ImageButton) toolbar.findViewById(R.id.play);
        mUpdateBtn = (ImageButton) toolbar.findViewById(R.id.update);

        mPlayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss(showAction);
            }
        });

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                update();
            }
        });
    }


    public void init(){
        show(unlockAction);
    }


    protected void show(Runnable action){
        lock();
        firstStage = false;
    }

    protected void update(){
        lock();
        firstStage = !firstStage;
    }

    protected void dismiss(Runnable action){
        lock();
    }


    private void lock(){
        mPlayBtn.setEnabled(false);
        mUpdateBtn.setEnabled(false);
    }

    private void unlock(){
        mPlayBtn.setEnabled(true);
        mUpdateBtn.setEnabled(true);
    }
}
