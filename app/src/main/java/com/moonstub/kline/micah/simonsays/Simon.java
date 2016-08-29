package com.moonstub.kline.micah.simonsays;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Micah on 8/23/2016.
 */
public class Simon {

    //Randomly selected between 4 buttons
    //Keep track of Previous Selections

    Random mRandom;
    List<Integer> mSelections;
    int mButtonCount;

    public Simon(int buttonCount){
        mButtonCount = buttonCount;
        mRandom = new Random();
        mSelections = new ArrayList<>();
    }

    public void selectARandomNumber(){
        mSelections.add(mRandom.nextInt(mButtonCount));
    }

    public int getNumberAtIndex(int index){
        return mSelections.get(index);
    }

    public int getSelectionCount(){
        return mSelections.size();
    }

    //Should Simon keep track of working index
    public boolean isMatching(int index, int value){
        return (mSelections.get(index) == value) ? true : false;
    }

    public void clearSelections(){
        mSelections.clear();
        mSelections = new ArrayList<>();
    }



}
