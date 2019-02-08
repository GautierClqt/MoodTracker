package com.cliquet.gautier.moodtracker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cliquet.gautier.moodtracker.R;
import com.cliquet.gautier.moodtracker.model.Mood;

import java.util.ArrayList;

public class HistoricDisplayActivity extends AppCompatActivity {

    //variables
    private ArrayList<String> mDateList = new ArrayList<>();
    private ArrayList<Integer> mBackgroundColorList = new ArrayList<>();
    private ArrayList<Integer> mTextViewWeightList = new ArrayList<>();
    private ArrayList<String> mCommentList = new ArrayList<>();

    int mBackgroundColor;

    Utils util = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_display);

        initMoodHistoryList();
    }

    //get the moods ArrayList to fill up the history RecyclerView
    private void initMoodHistoryList() {
        //Arraylist getting the moodlist from MoodSelectActivity
        ArrayList<Mood> moodList = (ArrayList<Mood>) getIntent().getSerializableExtra("List_Of_Moods");

        for(int i = 0; i <= moodList.size()-1; i++) {
            mCommentList.add(moodList.get(i).getComment());
            String mMDays = util.timeAgo(moodList.get(i).getDate());
            mDateList.add(mMDays);

            int moodIndex = moodList.get(i).getIndexMood();

            switch(moodIndex) {
                case 0: mBackgroundColor = getResources().getColor(R.color.faded_red);
                    break;
                case 1: mBackgroundColor = getResources().getColor(R.color.warm_grey);
                    break;
                case 2: mBackgroundColor = getResources().getColor(R.color.cornflower_blue_65);
                    break;
                case 3: mBackgroundColor = getResources().getColor(R.color.light_sage);
                    break;
                case 4: mBackgroundColor = getResources().getColor(R.color.banana_yellow);
                    break;
            }
            mBackgroundColorList.add(mBackgroundColor);
            mTextViewWeightList.add(moodList.get(i).getIndexMood());
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_historic_recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mDateList, mBackgroundColorList, mTextViewWeightList, mCommentList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
