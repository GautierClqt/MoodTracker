package com.cliquet.gautier.moodtracker;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MoodSelectActivity extends AppCompatActivity {

    //must be erased: Using EditText to display some variables(for test only)
    private TextView mTestText;

    private ImageView selectedMood[] = new ImageView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_select);

        mTestText = findViewById(R.id.activity_mood_testText_text);

        selectedMood[4] = findViewById(R.id.activity_mood_select_superHappy_image);
        selectedMood[3] = findViewById(R.id.activity_mood_select_happy_image);
        selectedMood[2] = findViewById(R.id.activity_mood_select_normal_image);
        selectedMood[1] = findViewById(R.id.activity_mood_select_disappointed_image);
        selectedMood[0] = findViewById(R.id.activity_mood_select_sad_image);

        selectedMood[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final Drawable color = selectedMood[2].getBackground();
                //ColorDrawable imageColor = (ColorDrawable) selectedMood[2].getBackground();
                //int colorId = imageColor.getColor();

                selectedMood[2].setBackgroundColor(0xFFAA5599);
                //mTestText.setText(String.valueOf(colorId));
            }
        });
    }
}
