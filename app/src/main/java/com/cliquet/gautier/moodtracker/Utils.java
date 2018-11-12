package com.cliquet.gautier.moodtracker;

import java.util.Calendar;

public class Utils {

    private Calendar date = Calendar.getInstance();
    private int newDay;
    private boolean isANewDay;

    public boolean compareDate(int currentDay) {
        newDay = date.get(Calendar.DAY_OF_MONTH);

        if(currentDay == newDay) {
            isANewDay = false;
        }
        else {
            isANewDay = true;
        }
        return isANewDay;
    }
}
