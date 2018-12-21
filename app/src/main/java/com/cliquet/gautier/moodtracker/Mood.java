package com.cliquet.gautier.moodtracker;

import java.io.Serializable;

public class Mood implements Serializable {

    private int mIndexMood;
    private String mComment;
    private int[] mDate;

    Mood(int mIndexMood, String mComment, int[] mDate) {
        this.mIndexMood = mIndexMood;
        this.mComment = mComment;
        this.mDate = mDate;
    }

    int getIndexMood() {
        return mIndexMood;
    }

    String getComment() {
        return mComment;
    }

    void setComment(String mComment) {
        this.mComment = mComment;
    }

    public int[] getDate() {
        return mDate;
    }
}
