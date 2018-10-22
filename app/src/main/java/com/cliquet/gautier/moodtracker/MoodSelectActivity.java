package com.cliquet.gautier.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


public class MoodSelectActivity extends AppCompatActivity {

    ImageView selectedMood[] = new ImageView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_select);

        selectedMood[4] = findViewById(R.id.activity_mood_select_superHappy_image);
        selectedMood[3] = findViewById(R.id.activity_mood_select_happy_image);
        selectedMood[2] = findViewById(R.id.activity_mood_select_normal_image);
        selectedMood[1] = findViewById(R.id.activity_mood_select_disappointed_image);
        selectedMood[0] = findViewById(R.id.activity_mood_select_sad_image);
    }
}
