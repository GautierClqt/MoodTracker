package com.cliquet.gautier.moodtracker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;

import java.util.Calendar;

class Utils {

    private Calendar newDate = Calendar.getInstance();

    //get the correct color according to the selected mood
    int changeBackgroundColor(int mMoodPosition) {
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

    //determine if this is a new day
    boolean compareDate(int oldDayOfYear, int oldYear) {
        int newDayOfYear = newDate.get(Calendar.DAY_OF_YEAR);
        int newYear = newDate.get(Calendar.YEAR);

        return oldDayOfYear != newDayOfYear || oldYear != newYear;
    }

    //determine the correct String to display in the historic activity
    String timeAgo(int[] oldDate) {
        String sinceWhen = "";

        int oldDayOfYear = oldDate[0];
        int oldYear = oldDate[1];

        int newDayOfYear = newDate.get(Calendar.DAY_OF_YEAR);
        int newYear = newDate.get(Calendar.YEAR);

        int dateDifference = 0;
        if(newYear-oldYear == 0){
            dateDifference = newDayOfYear - oldDayOfYear;
        }
        else if(newYear-oldYear > 0){
            dateDifference = 365 - oldDayOfYear + 365*(newYear-oldYear-1) + newDayOfYear;
        }

        if(dateDifference <= 7) {
            switch (newDayOfYear - oldDayOfYear) {
                case 1:
                    sinceWhen = "Hier";
                    break;
                case 2:
                    sinceWhen = "Avant-hier";
                    break;
                case 3:
                    sinceWhen = "Il y a 3 jours";
                    break;
                case 4:
                    sinceWhen = "Il y a 4 jours";
                    break;
                case 5:
                    sinceWhen = "Il y a 5 jours";
                    break;
                case 6:
                    sinceWhen = "Il y a 6 jours";
                    break;
                case 7:
                    sinceWhen = "Il y a une semaine";
                    break;
            }
        }
        else {
            sinceWhen = "Il y a " + dateDifference + " jours";
        }
        return sinceWhen;
    }

    String moodSendMessage(int moodIndex) {
        String moodMessage = "";

        switch (moodIndex) {
            case 0: moodMessage = "d'humeur exécrable :(";
                break;
            case 1: moodMessage = "de mauvaise humeur :/";
                break;
            case 2: moodMessage = "d'humeur normale :|";
                break;
            case 3: moodMessage = "de bonne humeur :)";
                break;
            case 4: moodMessage = "d'excellente humeur :D";
                break;
        }
        return moodMessage;
    }
}
