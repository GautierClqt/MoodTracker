package com.cliquet.gautier.moodtracker.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cliquet.gautier.moodtracker.R;
import com.cliquet.gautier.moodtracker.model.Mood;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MoodSelectActivity extends AppCompatActivity implements CommentDialog.CommentDialogListener, View.OnClickListener {

    private int moodIndex;
    private String moodComment = "";
    private int[] moodDate = new int[2];

    private Drawable[] moodImagesList = new Drawable[5];
    private int[] backgroundColorsList = new int[5];

    private SharedPreferences preferences;

    private Mood mood = new Mood(moodIndex, moodComment, moodDate);
    private ArrayList<Mood> moodList = new ArrayList<>();
    private Utils util = new Utils();
    private Gson gson = new Gson();

    private String jsonMood;
    private String jsonDate;

    private boolean mSwipeUp;
    private float yDown;

    private RelativeLayout mLayout;
    private ImageView mAddComment;
    private ImageView mDisplayHistoricActivity;
    private ImageView mSendMessage;
    private  ImageView mDisplayedMood;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_select);

        preferences = getPreferences(MODE_PRIVATE);
        getMoodPreferences();
        checkingForNewDay();
        getDates();
        layoutConnexion();
        initMoodsList();
        initColorsList();
        firstTimeUse();

        //handle the mood up and down swapping
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                event.getAction();
                swipe(event);
                return true;
            }
        });

        //call the comment dialog
        mAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        //call the history activity
        mDisplayHistoricActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historicActivityIntent = new Intent(MoodSelectActivity.this, HistoricDisplayActivity.class);
                historicActivityIntent.putExtra("List_Of_Moods", moodList);
                startActivity(historicActivityIntent);
            }
        });

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moodMessage = util.moodSendMessage(moodIndex);
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
                smsIntent.putExtra("sms_body", "Aujourd'hui je suis\b" + moodMessage);
                startActivity(smsIntent);
            }
        });
    }

    //normal mood and background are set by default when using the app for the first time
    public void firstTimeUse() {
        mDisplayedMood.setImageDrawable(moodImagesList[moodIndex]);
        mLayout.setBackgroundColor(backgroundColorsList[moodIndex]);
    }

    //get the last saved preferences(or default one if first use)
    public void getMoodPreferences() {
        moodIndex = getPreferences(MODE_PRIVATE).getInt("mood_index", 3);
        moodComment = getPreferences(MODE_PRIVATE).getString("comment", "");
        jsonDate = getPreferences(MODE_PRIVATE).getString("date", null);

        if(jsonDate != null) {
            moodDate = gson.fromJson(jsonDate, new TypeToken<int[]>(){}.getType());
        }

        jsonMood = preferences.getString("Moods", null);
        if(jsonMood != null) {
            moodList = gson.fromJson(jsonMood, new TypeToken<List<Mood>>(){}.getType());
        }
    }

    //connecting the views
    private void layoutConnexion() {
        mDisplayedMood = findViewById(R.id.activity_mood_select_placeholder_image);
        mAddComment = findViewById(R.id.activity_add_comment_image);
        mLayout = findViewById(R.id.activity_mood_select_layout_layout);
        mDisplayHistoricActivity = findViewById(R.id.activity_mood_select_display_historic_image);
        mSendMessage = findViewById(R.id.activity_mood_select_sendmessage_image);
    }

    private void checkingForNewDay() {
        //compare days of month to know if it's a new day
        boolean mIsANewDay = util.compareDate(moodDate[0], moodDate[1]);
        if (mIsANewDay && jsonDate != null) {
            moodList.add(new Mood(moodIndex, moodComment, moodDate));
            if (moodList.size() > 7) {
                moodList.remove(0);
            }
            jsonMood = gson.toJson(moodList);
            preferences.edit().putString("Moods", jsonMood).apply();

            //adding date, index, comment to preferences
            getDates();
            preferences.edit().putString("date", jsonDate = gson.toJson(moodDate)).apply();
            preferences.edit().putInt("mood_index", moodIndex = 3).apply();
            preferences.edit().putString("comment", moodComment = "").apply();
        }

    }

    //all five moods' images are stored in moodImagesList from worst [0] to best [4]
    private void initMoodsList() {
        moodImagesList[0] = getResources().getDrawable(R.drawable.smiley_sad);
        moodImagesList[1] = getResources().getDrawable(R.drawable.smiley_disappointed);
        moodImagesList[2] = getResources().getDrawable(R.drawable.smiley_normal);
        moodImagesList[3] = getResources().getDrawable(R.drawable.smiley_happy);
        moodImagesList[4] = getResources().getDrawable(R.drawable.smiley_super_happy);
    }

    //all five background colors are stored in backgroundColorsList in a order corresponding to moodImagesList
    private void initColorsList () {
        backgroundColorsList[0] = getResources().getColor(R.color.faded_red);
        backgroundColorsList[1] = getResources().getColor(R.color.warm_grey);
        backgroundColorsList[2] = getResources().getColor(R.color.cornflower_blue_65);
        backgroundColorsList[3] = getResources().getColor(R.color.light_sage);
        backgroundColorsList[4] = getResources().getColor(R.color.banana_yellow);
    }

    //check if user gesture went upward or downward and put moodIndex in the correct position then display the right mood and backgroundcolor
    private void swipe(MotionEvent event) {
        float yDifference;
        float yUp;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            yDown = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            yUp = event.getY();
            yDifference = Math.abs(yDown - yUp);
            if (yDown > yUp) {
                mSwipeUp = true;
            } else if (yDown < yUp) {
                mSwipeUp = false;
            }

            if(yDifference > 100) {
                if (mSwipeUp && moodIndex < 4) {
                    moodIndex++;
                } else if (!mSwipeUp && moodIndex > 0) {
                    moodIndex--;
                }
                mDisplayedMood.setImageDrawable(moodImagesList[moodIndex]);
                mLayout.setBackgroundColor(backgroundColorsList[moodIndex]);

                getDates();

                //adding index and date to preferences
                preferences.edit().putInt("mood_index", moodIndex).apply();
                preferences.edit().putString("date", jsonDate = gson.toJson(moodDate)).apply();
            }
        }
    }

    //getting date and time and extracting day of year and year for further comparison
    private void getDates() {
        Calendar mDate = Calendar.getInstance();
        moodDate[0] = mDate.get(Calendar.DAY_OF_YEAR);
        moodDate[1] = mDate.get(Calendar.YEAR);
    }

    private void openDialog() {
        CommentDialog commentDialog = new CommentDialog();
        commentDialog.show(getSupportFragmentManager(), "comment dialog");
    }

    //save(preferences) the written comment
    @Override
    public void getComment(String DialogComment) {
        moodComment = DialogComment;
        mood.setComment(moodComment);
        moodComment = mood.getComment();
        preferences.edit().putString("comment", moodComment).apply();
    }

    @Override
    public void onClick(View v) {

    }
}

