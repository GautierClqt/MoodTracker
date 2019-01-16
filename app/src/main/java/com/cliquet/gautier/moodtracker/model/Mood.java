package com.cliquet.gautier.moodtracker.model;

import java.io.Serializable;

public class Mood implements Serializable {

    private int mIndexMood;
    private String mComment;
    private int[] mDate;

    public Mood(int mIndexMood, String mComment, int[] mDate) {
        this.mIndexMood = mIndexMood;
        this.mComment = mComment;
        this.mDate = mDate;
    }

    public int getIndexMood() {
        return mIndexMood;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    public int[] getDate() {
        return mDate;
    }
}
