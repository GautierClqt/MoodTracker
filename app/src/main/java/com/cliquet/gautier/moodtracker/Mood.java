package com.cliquet.gautier.moodtracker;

import android.graphics.Color;
import java.util.Date;


public class Mood {

    private int mIndexMood;
    private String mComment;
    private Date mDate;

    public Mood(int mIndexMood, String mComment, Date mDate) {
        this.mIndexMood = mIndexMood;
        this.mComment = mComment;
        this.mDate = mDate;
    }

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

        int mBackgroundColor = 0;

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
}
