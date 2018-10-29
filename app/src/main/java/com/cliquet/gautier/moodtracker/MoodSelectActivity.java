package com.cliquet.gautier.moodtracker;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MoodSelectActivity extends AppCompatActivity implements CommentDialog.CommentDialogListener {

    private RelativeLayout mLayout;
    private Drawable[] moodList = new Drawable[5];
    private int moodIndex;
    private String commentPreferences;
    private TextView mDisplayComment;
    private int backgroundColor;
    private SharedPreferences preferences;

    Mood mood = new Mood();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_select);

        preferences = getPreferences(MODE_PRIVATE);

        //if there's no preferences normal mood is display by default
        moodIndex = getPreferences(MODE_PRIVATE).getInt("mood_index", 2);
        commentPreferences = getPreferences(MODE_PRIVATE).getString("comment", "");

        //connecting the views
        final ImageView mDisplayedMood;
        final ImageView mAddComment;

        mDisplayedMood = findViewById(R.id.activity_mood_select_placeholder_image);
        mAddComment = findViewById(R.id.activity_add_comment_image);
        mDisplayComment = findViewById(R.id.activity_mood_select_comment_textview);
        mLayout = findViewById(R.id.activity_mood_select_layout_layout);

        mDisplayComment.setText(commentPreferences);

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
                moodIndex = mood.setMoodIndex(moodIndex);
                mDisplayedMood.setImageDrawable(moodList[moodIndex]);   //display the next mood

                backgroundColor = mood.changeBackgroundColor(moodIndex);
                mLayout.setBackgroundColor(backgroundColor);

                preferences.edit().putInt("mood_index", moodIndex).apply();
            }
        });

        mAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        CommentDialog commentDialog = new CommentDialog();
        commentDialog.show(getSupportFragmentManager(), "comment dialog");
    }

    //display and save(preferences) the written comment
    @Override
    public void getComment(String comment) {
        mDisplayComment.setText(comment);
        preferences.edit().putString("comment", comment).apply();
    }
}

