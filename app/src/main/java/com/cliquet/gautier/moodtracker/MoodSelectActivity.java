package com.cliquet.gautier.moodtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MoodSelectActivity extends AppCompatActivity implements CommentDialog.CommentDialogListener {

    private int moodIndex;
    protected String mComment;
    private Calendar mDate;
    private Date savedDate;
    private int mDayOfMonth;
    private boolean isANewDay;
    private boolean mSwipeUp;

    private RelativeLayout mLayout;
    private TextView mDisplayComment; //NE FAIT PAS PARTI DU PRODUIT FINAL

    private Drawable[] moodList = new Drawable[5];
    private SharedPreferences preferences;
    private int backgroundColor;

    Mood mood = new Mood(moodIndex, mComment, mDate);
    Utils util = new Utils();
    Gson gson = new Gson();

    private ArrayList<Mood> MoodList = new ArrayList<>();
    String jsonMood;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_select);

        preferences = getPreferences(MODE_PRIVATE);

        //if there's no preferences normal mood is display by default
        moodIndex = getPreferences(MODE_PRIVATE).getInt("mood_index", 3);
        mComment = getPreferences(MODE_PRIVATE).getString("comment", "");
        //mDate = mDate.getInstance();

        //get json type converted mood to Mood object type
        jsonMood = preferences.getString("Moods", null);
        if(jsonMood != null) {
            MoodList = gson.fromJson(jsonMood, new TypeToken<List<Mood>>(){}.getType());
        }

        mood.setMoodList(MoodList);

        //compare days of month to know if this is a new day
        mDate = Calendar.getInstance();
        isANewDay = util.compareDate(mDate);

//        if(isANewDay) {
//            if(MoodList.size() < 7) {
//                MoodList.add(new Mood(moodIndex, mComment, mDate));
//            }
//            if(MoodList.size() >= 7) {
//                MoodList.add(new Mood(moodIndex, mComment, mDate));
//                MoodList.remove(0);
//            }
//            jsonMood = gson.toJson(MoodList);
//            preferences.edit().putString("Moods", jsonMood).apply();
//        }

        //connecting the views
        final ImageView mDisplayedMood;
        final ImageView mAddComment;
        final ImageView mDisplayHistoricActivity;
        final Button mAddMood;

        mDisplayedMood = findViewById(R.id.activity_mood_select_placeholder_image);
        mAddComment = findViewById(R.id.activity_add_comment_image);
        mDisplayComment = findViewById(R.id.activity_mood_select_comment_textview); //NE FAIT PAS PARTI DU PRODUIT FINAL
        mLayout = findViewById(R.id.activity_mood_select_layout_layout);
        mDisplayHistoricActivity = findViewById(R.id.activity_mood_select_display_historic_image);
        mAddMood = findViewById(R.id.activity_mood_select_addmood_button); //NE FAIT PAS PARTI DU PRODUIT FINAL

        mDisplayComment.setText(mComment);

        //all five moods' images are stored in moodList
        moodList[0] = getResources().getDrawable(R.drawable.smiley_sad);
        moodList[1] = getResources().getDrawable(R.drawable.smiley_disappointed);
        moodList[2] = getResources().getDrawable(R.drawable.smiley_normal);
        moodList[3] = getResources().getDrawable(R.drawable.smiley_happy);
        moodList[4] = getResources().getDrawable(R.drawable.smiley_super_happy);

        //normal mood and background are set by default
        mDisplayedMood.setImageDrawable(moodList[moodIndex]);
        backgroundColor = mood.changeBackgroundColor(moodIndex);
        mLayout.setBackgroundColor(backgroundColor);

        //mDisplayedMood.setOnTouchListener(new GestureDetector.OnGestureListener(){

        //handle the mood up and down swapping
        mDisplayedMood.setOnTouchListener(new View.OnTouchListener() {
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

                        mDisplayedMood.setImageDrawable(moodList[moodIndex]);
                        backgroundColor = mood.changeBackgroundColor(moodIndex);
                        mLayout.setBackgroundColor(backgroundColor);

                        preferences.edit().putInt("mood_index", moodIndex).apply();

                        mDate = mood.getmDate(); //get the current date and time(doesn't take account of winter time)
                        mDayOfMonth = mDate.get(Calendar.DAY_OF_MONTH);
                        preferences.edit().putInt("date", mDayOfMonth).apply();

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
                historicActivityIntent.putExtra("List_Of_Moods", MoodList);
                startActivity(historicActivityIntent);
            }
        });

        //NE FAIT PAS PARTI DU PRODUIT FINAL -- Bouton simulant l'ajout de mood pour l'historique
        mAddMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MoodList.size() < 7) {
                    MoodList.add(new Mood(moodIndex, mComment, mDate));
                }
                if(MoodList.size() >= 7) {
                    MoodList.add(new Mood(moodIndex, mComment, mDate));
                    MoodList.remove(0);
                }
                jsonMood = gson.toJson(MoodList);
                preferences.edit().putString("Moods", jsonMood).apply();
            }
        });
    }

    public void openDialog() {
        CommentDialog commentDialog = new CommentDialog();
        commentDialog.show(getSupportFragmentManager(), "comment dialog");
    }

    //display and save(preferences) the written comment
    @Override
    public void getComment(String DialogComment) {
        mComment = DialogComment;
        mDisplayComment.setText(mComment); //NE FAIT PAS PARTI DU PRODUIT FINAL
        mood.setmComment(mComment);
        mComment = mood.getmComment();
        preferences.edit().putString("comment", mComment).apply();
    }

    @Override
    protected void OnResume() {
        super.onResume();
    }
}

