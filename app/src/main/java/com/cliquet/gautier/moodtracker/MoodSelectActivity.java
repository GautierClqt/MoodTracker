package com.cliquet.gautier.moodtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MoodSelectActivity extends AppCompatActivity implements CommentDialog.CommentDialogListener {

    private int moodIndex;
    protected String moodComment = "";
    private Calendar mDate;
    private boolean mSwipeUp;
    private int[] moodDate = new int[2];

    private RelativeLayout mLayout;

    private Drawable[] moodImagesList = new Drawable[5];
    private SharedPreferences preferences;
    private int backgroundColor;

    Mood mood = new Mood(moodIndex, moodComment, moodDate);
    Utils util = new Utils();
    Gson gson = new Gson();

    private ArrayList<Mood> moodList = new ArrayList<>();
    String jsonMood;
    String jsonDate;

    public ImageView mDisplayedMood;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_select);

        preferences = getPreferences(MODE_PRIVATE);

        //if there's no preferences, normal mood is display by default
        moodIndex = getPreferences(MODE_PRIVATE).getInt("mood_index", 3);
        moodComment = getPreferences(MODE_PRIVATE).getString("comment", "");
        jsonDate = getPreferences(MODE_PRIVATE).getString("date", null);

        if(jsonDate != null) {
            moodDate = gson.fromJson(jsonDate, new TypeToken<int[]>(){}.getType());
        }

        //get json type converted mood to Mood object type
        jsonMood = preferences.getString("Moods", null);
        if(jsonMood != null) {
            moodList = gson.fromJson(jsonMood, new TypeToken<List<Mood>>(){}.getType());
        }

        //compare days of month to know if it's a new day
        boolean mIsANewDay = util.compareDate(moodDate[0], moodDate[1]);
        if (mIsANewDay && jsonDate != null) {
            moodList.add(new Mood(moodIndex, moodComment, moodDate));
            if (moodList.size() > 7) {
                moodList.remove(0);
            }
            jsonMood = gson.toJson(moodList);
            preferences.edit().putString("Moods", jsonMood).apply();

            //getting date and time and extracting day of year and year for further comparison
            mDate = Calendar.getInstance();
            moodDate[0] = mDate.get(Calendar.DAY_OF_YEAR);
            moodDate[1] = mDate.get(Calendar.YEAR);

            //adding date, index, comment to preferences
            preferences.edit().putString("date", jsonDate = gson.toJson(moodDate)).apply();
            preferences.edit().putInt("mood_index", moodIndex = 3).apply();
            preferences.edit().putString("comment", moodComment = "").apply();
        }

        //connecting the views
        final ImageView mAddComment;
        final ImageView mDisplayHistoricActivity;
        final ImageView mSendMessage;

        mDisplayedMood = findViewById(R.id.activity_mood_select_placeholder_image);
        mAddComment = findViewById(R.id.activity_add_comment_image);
        mLayout = findViewById(R.id.activity_mood_select_layout_layout);
        mDisplayHistoricActivity = findViewById(R.id.activity_mood_select_display_historic_image);
        mSendMessage = findViewById(R.id.activity_mood_select_sendmessage_image);

        //all five moods' images are stored in moodImagesList from worst [0] to best [4]
        moodImagesList[0] = getResources().getDrawable(R.drawable.smiley_sad);
        moodImagesList[1] = getResources().getDrawable(R.drawable.smiley_disappointed);
        moodImagesList[2] = getResources().getDrawable(R.drawable.smiley_normal);
        moodImagesList[3] = getResources().getDrawable(R.drawable.smiley_happy);
        moodImagesList[4] = getResources().getDrawable(R.drawable.smiley_super_happy);

        //normal mood and background are set by default when using the app for the first time
        mDisplayedMood.setImageDrawable(moodImagesList[moodIndex]);
        backgroundColor = util.changeBackgroundColor(moodIndex);
        mLayout.setBackgroundColor(backgroundColor);


        //handle the mood up and down swapping
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            float y_up;
            float y_down;
            float y_difference;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //check if user gesture went upward or downward and put moodIndex in the correct position then display the right mood and backgroundcolor
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    y_down = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    y_up = event.getY();

                    y_difference = Math.abs(y_down - y_up);
                    if (y_down > y_up) {
                        mSwipeUp = true;
                    } else if (y_down < y_up) {
                        mSwipeUp = false;
                    }

                    if(y_difference > 100) {
                        if (mSwipeUp && moodIndex < 4) {
                            moodIndex++;
                        } else if (!mSwipeUp && moodIndex > 0) {
                            moodIndex--;
                        }

                        mDisplayedMood.setImageDrawable(moodImagesList[moodIndex]);
                        backgroundColor = util.changeBackgroundColor(moodIndex);
                        mLayout.setBackgroundColor(backgroundColor);

                        //getting date and time and extracting day of year and year for further comparison
                        mDate = Calendar.getInstance();
                        moodDate[0] = mDate.get(Calendar.DAY_OF_YEAR);
                        moodDate[1] = mDate.get(Calendar.YEAR);

                        //adding index and date to preferences
                        preferences.edit().putInt("mood_index", moodIndex).apply();
                        preferences.edit().putString("date", jsonDate = gson.toJson(moodDate)).apply();

                        y_down = 0;
                        y_up = 0;
                    }
                }
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

    public void openDialog() {
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
}

