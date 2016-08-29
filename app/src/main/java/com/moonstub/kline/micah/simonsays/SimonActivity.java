package com.moonstub.kline.micah.simonsays;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Micah on 8/23/2016.
 */
public class SimonActivity extends AppCompatActivity {

    Simon mSimon;
    int mCurrentIndex = 0;

    static long sWaitTime = 2000;

    Button mStartButton;
    Button mResetButton;
    boolean mIsUserTurn = false;
    Button[] mButtonArray;

    int[] baseColor = new int[]{Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN};
    int[] highColor = new int[]{Color.CYAN,Color.MAGENTA,Color.WHITE,Color.GRAY};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_simon_layout);

        mSimon = new Simon(4);

        mButtonArray = new Button[4];
        mButtonArray[0] = (Button)findViewById(R.id.blueButton);
        mButtonArray[1] = (Button)findViewById(R.id.redButton);
        mButtonArray[2] = (Button)findViewById(R.id.yellowButton);
        mButtonArray[3] = (Button)findViewById(R.id.greenButton);

        for(int index = 0; index < mButtonArray.length ;index++){
            Button b = mButtonArray[index];
            b.setBackgroundColor(baseColor[index]);
            b.setTag(index);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mIsUserTurn) {
                        displayUserSelection((int) v.getTag());
                        //ChangeButtonColor((Button) v, Color.WHITE);
                        CheckSimon((int) v.getTag());
                    }
                }
            });


        }

        mStartButton = (Button)findViewById(R.id.startButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
            }
        });

        mResetButton = (Button)findViewById(R.id.resetButton);

    }

    private void CheckSimon(int value) {
        boolean isMatch = mSimon.isMatching(mCurrentIndex,value);

        if(isMatch){
            if(mCurrentIndex < mSimon.getSelectionCount() - 1){
                mCurrentIndex++;
            } else {
                mCurrentIndex = 0;
                mSimon.selectARandomNumber();
                displaySelection();
            }
        }else{
            gameOver();
        }
    }

    private void gameOver() {
        Toast.makeText(this, "GAME OVER MAN", Toast.LENGTH_LONG).show();
        mResetButton.setEnabled(false);
        mStartButton.setEnabled(true);
    }

    private void displayUserSelection(final int value){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                Button b = mButtonArray[value];
                ChangeButtonColor(b, highColor[value]);
                long currentTime = System.currentTimeMillis();

                while(currentTime < time + sWaitTime){
                    currentTime = System.currentTimeMillis();
                    Thread.yield();
                }

                ChangeButtonColor(b, baseColor[value]);
            }
        });

        thread.start();
    }

    private void displaySelection() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for(int index = 0; index < mSimon.getSelectionCount(); index++) {
                    long time = System.currentTimeMillis();
                    int value = mSimon.getNumberAtIndex(index);
                    Button b = mButtonArray[value];
                    ChangeButtonColor(b, highColor[value]);

                    long currentTime = System.currentTimeMillis();
                    while (currentTime < time + sWaitTime) {
                        currentTime = System.currentTimeMillis();
                        Thread.yield();
                    }

                    ChangeButtonColor(b, baseColor[value]);

                }
                mIsUserTurn = true;
            }
        });

        thread.start();

    }

    private void ChangeButtonColor(final Button b, final int color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                b.setBackgroundColor(color);
            }
        });
    }

    public void Start(){
        mStartButton.setEnabled(false);
        mResetButton.setEnabled(true);
        mIsUserTurn = false;
        mSimon.clearSelections();
        mCurrentIndex = 0;
        mSimon.selectARandomNumber();
        mSimon.selectARandomNumber();
        mSimon.selectARandomNumber();
        displaySelection();
    }

    public  void Reset(){
        Start();
    }







}
