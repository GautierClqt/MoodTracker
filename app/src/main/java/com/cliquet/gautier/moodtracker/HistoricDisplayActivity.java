package com.cliquet.gautier.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class HistoricDisplayActivity extends AppCompatActivity {

    //variables
    private ArrayList<String> mDaysList = new ArrayList<>();
    private ArrayList<Integer> mBackgroundColorList = new ArrayList<>();
    private ArrayList<Integer> mTextviewWeightList = new ArrayList<>();
    private ArrayList<String> mCommentList = new ArrayList<>();

    private int mBackgroundColor;
    private int mTextviewWeight;
    private String mDays;
    private boolean isCommentTrue;
    private String mComment;
    Random rand = new Random();
    Mood mood = new Mood();
    Utils util = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_display);

        initLists();
    }

    //TEST: méthode permettant de remplir le listitem pour le recycler view -- DOIT ETRE EFFACE DANS LE PROJET FINAL
    private void initLists() {

        for(int i = 6; i >= 0; i--) {
            isCommentTrue = rand.nextBoolean();
            if(isCommentTrue) {
                mComment = "Bonjour";
            }
            else {
                mComment = "";
            }
            mCommentList.add(mComment);
            mDays = util.historicDayPast(i);
            mDaysList.add(mDays);
            mBackgroundColor = rand.nextInt(5);
            mTextviewWeight = mBackgroundColor;
            mBackgroundColor = mood.changeBackgroundColor(mBackgroundColor);
            mBackgroundColorList.add(mBackgroundColor);
            mTextviewWeightList.add(mTextviewWeight);
        }

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_historic_recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mDaysList, mBackgroundColorList, mTextviewWeightList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
