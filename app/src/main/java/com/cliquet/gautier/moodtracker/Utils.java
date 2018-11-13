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

    public String historicDayPast(int intDays) {
        String sinceWhen = "";

        switch (intDays) {
            case 0: sinceWhen = "Hier";
                break;
            case 1: sinceWhen = "Avant-hier";
                break;
            case 2: sinceWhen = "Il y a 3 jours";
                break;
            case 3: sinceWhen = "Il y a 4 jours";
                break;
            case 4: sinceWhen = "Il y a 5 jours";
                break;
            case 5: sinceWhen = "Il y a 6 jours";
                break;
            case 6: sinceWhen = "Il y a une semaine";
                break;
        }
        return sinceWhen;
    }
}
