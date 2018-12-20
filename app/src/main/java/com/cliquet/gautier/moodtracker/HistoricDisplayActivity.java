package com.cliquet.gautier.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class HistoricDisplayActivity extends AppCompatActivity {

    //variables
    private ArrayList<String> mDateList = new ArrayList<>();
    private ArrayList<Integer> mBackgroundColorList = new ArrayList<>();
    private ArrayList<Integer> mTextViewWeightList = new ArrayList<>();
    private ArrayList<String> mCommentList = new ArrayList<>();


    Mood mood = new Mood();
    Utils util = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_display);

        initMoodHistoryList();;
    }

    //get the moods ArrayList to fill up the history RecyclerView
    private void initMoodHistoryList() {
        //Arraylist getting the moodlist from MoodSelectActivity
        ArrayList<Mood> MoodList = (ArrayList<Mood>) getIntent().getSerializableExtra("List_Of_Moods");

        for(int i = 0; i <= MoodList.size()-1; i++) {
            mCommentList.add(MoodList.get(i).getmComment());
            String mMDays = util.timeAgo(MoodList.get(i).getmDate());
            mDateList.add(mMDays);
            int mMBackgroundColor = mood.changeBackgroundColor(MoodList.get(i).getmIndexMood());
            mBackgroundColorList.add(mMBackgroundColor);
            mTextViewWeightList.add(MoodList.get(i).getmIndexMood());
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
