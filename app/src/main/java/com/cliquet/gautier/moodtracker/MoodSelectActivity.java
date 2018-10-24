package com.cliquet.gautier.moodtracker;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MoodSelectActivity extends AppCompatActivity {

    public RelativeLayout mLayout;
    public Drawable[] moodList = new Drawable[5];
    public int moodIndex;
    public SharedPreferences preferences = getPreferences(MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_select);

        //preferences.edit().putInt("mood_index", moodIndex).apply(); // <- MUST BE ERASSED - use to set preferences right
        moodIndex = getPreferences(MODE_PRIVATE).getInt("mood_index", 2);

        //connecting the views
        final ImageView mDisplayedMood;
        mDisplayedMood = findViewById(R.id.activity_mood_select_placeholder_image);

        mLayout = findViewById(R.id.activity_mood_select_layout_layout);

        //all five moods' images are stored in moodList
        moodList[0] = getResources().getDrawable(R.drawable.smiley_sad);
        moodList[1] = getResources().getDrawable(R.drawable.smiley_disappointed);
        moodList[2] = getResources().getDrawable(R.drawable.smiley_normal);
        moodList[3] = getResources().getDrawable(R.drawable.smiley_happy);
        moodList[4] = getResources().getDrawable(R.drawable.smiley_super_happy);

        //normal mood and background are set by default
        mDisplayedMood.setImageDrawable(moodList[moodIndex]);
        moodIndex = modifyBackgroundColor(moodIndex);
        //mLayout.setBackgroundColor(0xa5468ad9);

        //at each click on the ImageView moods and the layout background colors are cycle through(worst to best)
        mDisplayedMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moodIndex++;
                mDisplayedMood.setImageDrawable(moodList[moodIndex]);   //display the next mood
                moodIndex = modifyBackgroundColor(moodIndex);
                savingPreferences(moodIndex);
            }
        });
    }

    private int modifyBackgroundColor(int currentMood) {
        //display the corresponding background color
        switch (currentMood) {
            case 0: mLayout.setBackgroundColor(0xffde3c50);
                break;
            case 1: mLayout.setBackgroundColor(0xff9b9b9b);
                break;
            case 2: mLayout.setBackgroundColor(0xa5468ad9);
                break;
            case 3: mLayout.setBackgroundColor(0xffb8e986);
                break;
            case 4: mLayout.setBackgroundColor(0xfff9ec4f);
                currentMood = 0; //will be valued to 0(worst mood) at the next click
                break;
        }
        return currentMood;
    }

    private void savingPreferences(int currentMood) {
        if (currentMood >= 0) {
            preferences.edit().putInt("mood_index", currentMood).apply();
        } else {
            currentMood = 0;
            preferences.edit().putInt("mood_index", currentMood).apply();
        }
    }
}

