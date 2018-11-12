package com.cliquet.gautier.moodtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class MoodSelectActivity extends AppCompatActivity implements CommentDialog.CommentDialogListener {

    private int moodIndex;
    private String mComment;
    private Calendar mDate;
    private int mDayOfMonth;
    private boolean mNewDay;

    private RelativeLayout mLayout;
    private TextView mDisplayComment; //NE FAIT PAS PARTI DU PRODUIT FINAL

    private Drawable[] moodList = new Drawable[5];
    private SharedPreferences preferences;
    private int backgroundColor;

    Mood mood = new Mood(moodIndex, mComment, mDate);
    Utils util = new Utils();

    private ArrayList<Mood> MoodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_select);

        preferences = getPreferences(MODE_PRIVATE);

        //compare days of month to know if this is a new day
        mNewDay = util.compareDate(mDayOfMonth);

        //if there's no preferences normal mood is display by default
        moodIndex = getPreferences(MODE_PRIVATE).getInt("mood_index", 2);
        mComment = getPreferences(MODE_PRIVATE).getString("comment", "");

        //connecting the views
        final ImageView mDisplayedMood;
        final ImageView mAddComment;
        final ImageView mDisplayHistoricActivity;

        mDisplayedMood = findViewById(R.id.activity_mood_select_placeholder_image);
        mAddComment = findViewById(R.id.activity_add_comment_image);
        mDisplayComment = findViewById(R.id.activity_mood_select_comment_textview); //NE FAIT PAS PARTI DU PRODUIT FINAL
        mLayout = findViewById(R.id.activity_mood_select_layout_layout);
        mDisplayHistoricActivity = findViewById(R.id.activity_mood_select_display_historic_image);

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

        //at each click on the ImageView moods and the layout background colors are cycle through(worst to best)
        mDisplayedMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to correctly cycle through the moods, especially to go from super happy mood to sad mood
                mood.setmIndexMood(moodIndex);
                moodIndex = mood.getmIndexMood();
                mDisplayedMood.setImageDrawable(moodList[moodIndex]);   //display the right mood

                backgroundColor = mood.changeBackgroundColor(moodIndex);
                mLayout.setBackgroundColor(backgroundColor);

                preferences.edit().putInt("mood_index", moodIndex).apply();

                mDate = mood.getmDate(); //get the current date and time(doesn't take account of winter hours)
                mDayOfMonth = mDate.get(Calendar.DAY_OF_MONTH);
                preferences.edit().putInt("date", mDayOfMonth).apply();
            }
        });

        mAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        mDisplayHistoricActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historicActivityIntent = new Intent(MoodSelectActivity.this, HistoricDisplayActivity.class);
                startActivity(historicActivityIntent);
            }
        });
    }

    public void openDialog() {
        CommentDialog commentDialog = new CommentDialog();
        commentDialog.show(getSupportFragmentManager(), "comment dialog");
    }

    //display and save(preferences) the written comment
    @Override
    public void getComment(String mComment) {
        mDisplayComment.setText(mComment); //NE FAIT PAS PARTI DU PRODUIT FINAL
        mood.setmComment(mComment);
        preferences.edit().putString("comment", mComment).apply();
    }

    //add current preferences to the ArrayList
    public void addPreferencesToList () {

    }
}

