package com.cliquet.gautier.moodtracker;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    //private Calendar calendar = Calendar.getInstance();
    private int newDayOfYear;
    private int newYear;
    private Calendar newDate = Calendar.getInstance();
    private int oldDayOfYear;
    private int oldYear;
    private boolean isANewDay;

    public boolean compareDate(Calendar oldDate) {
        newDayOfYear = newDate.get(Calendar.DAY_OF_YEAR);
        newYear = newDate.get(Calendar.YEAR);
        oldDayOfYear = oldDate.get(Calendar.DAY_OF_YEAR);
        oldYear = oldDate.get(Calendar.YEAR);

        if(oldDayOfYear == newDayOfYear || oldYear == newYear) {
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
