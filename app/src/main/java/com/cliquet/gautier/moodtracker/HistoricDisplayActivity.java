package com.cliquet.gautier.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class HistoricDisplayActivity extends AppCompatActivity {

    //variables
    private ArrayList<String> mDays = new ArrayList<>();
    private ArrayList<Integer> mBackgroundColor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_display);

        initLists();
    }

    //TEST: méthode permettant de remplir le listitem pour le recycler view -- DOIT ETRE EFFACE DANS LE PROJET FINAL
    private void initLists() {

        mDays.add("TEST" + 1);
        mBackgroundColor.add(0xFF55116B);

        mDays.add("TEST" + 2);
        mBackgroundColor.add(0xFF55100B);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_historic_recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mDays, mBackgroundColor);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
