package com.cliquet.gautier.moodtracker;

import android.graphics.Color;


public class Mood {

    private int mBackgroundColor;
    private String mComment;

    public int setMoodIndex(int mMoodPosition) {
        if (mMoodPosition >= 0 && mMoodPosition < 4) {
            mMoodPosition++;
        }
        else{  //if mMoodPosition = 5(ie. out of array bound) it will be set back to 0
            mMoodPosition = 0;
        }
        return mMoodPosition;
    }

    public int changeBackgroundColor(int mMoodPosition) {
        switch(mMoodPosition) {
            case 0: mBackgroundColor = Color.parseColor("#ffde3c50");
            break;
            case 1: mBackgroundColor = Color.parseColor("#ff9b9b9b");
            break;
            case 2: mBackgroundColor = Color.parseColor("#a5468ad9");
            break;
            case 3: mBackgroundColor = Color.parseColor("#ffb8e986");
            break;
            case 4: mBackgroundColor = Color.parseColor("#fff9ec4f");
            break;
        }
        return mBackgroundColor;
    }

    public String getComment() {
        //
        return mComment;
    }
}
