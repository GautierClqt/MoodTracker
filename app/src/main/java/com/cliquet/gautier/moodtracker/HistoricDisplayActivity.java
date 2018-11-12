package com.cliquet.gautier.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class HistoricDisplayActivity extends AppCompatActivity {

    //variables
    private ArrayList<String> mDays = new ArrayList<>();
    private ArrayList<Integer> mBackgroundColorList = new ArrayList<>();
    private ArrayList<String> mWithComment = new ArrayList<>();

    private int mBackgroundColor;
    Random rand = new Random();
    Mood mood = new Mood();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_display);

        initLists();
    }

    //TEST: méthode permettant de remplir le listitem pour le recycler view -- DOIT ETRE EFFACE DANS LE PROJET FINAL
    private void initLists() {

        for(int i = 0; i < 7; i++) {
            mDays.add("Texte" + " " + i);
            mBackgroundColor = rand.nextInt(5);
            mBackgroundColor = mood.changeBackgroundColor(mBackgroundColor);
            mBackgroundColorList.add(mBackgroundColor);
        }

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_historic_recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mDays, mBackgroundColorList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
