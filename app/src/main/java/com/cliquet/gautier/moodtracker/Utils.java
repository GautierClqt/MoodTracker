package com.cliquet.gautier.moodtracker;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private Calendar newDate = Calendar.getInstance();

    public boolean compareDate(Calendar oldDate) {
        //private Calendar calendar = Calendar.getInstance();
        int mNewDayOfYear = newDate.get(Calendar.DAY_OF_YEAR);
        int mNewYear = newDate.get(Calendar.YEAR);
        int mOldDayOfYear = oldDate.get(Calendar.DAY_OF_YEAR);
        int mOldYear = oldDate.get(Calendar.YEAR);

        boolean mIsANewDay;
        if(mOldDayOfYear == mNewDayOfYear || mOldYear == mNewYear) {
            mIsANewDay = false;
        }
        else {
            mIsANewDay = true;
        }
        return mIsANewDay;
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

    public String moodSendMessage(int moodIndex) {
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

    public String timeAgo() {
        long timeInMillis = System.currentTimeMillis();
        Locale LocaleBylanguageTag = Locale.forLanguageTag("fr");
        TimeAgoMessages messages = new TimeAgoMessages.Builder().withLocale(LocaleBylanguageTag).build();
        String timeAgoMessage = TimeAgo.using(timeInMillis, messages);

        return timeAgoMessage;
    }
}
