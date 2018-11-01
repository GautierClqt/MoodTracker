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

    //TEST: méthode permettant de remplir les ArrayList -- DOIT ETRE EFFACE DANS LE PROJET FINAL
    private void initLists() {

        mDays.add("premier");
        mBackgroundColor.add(0xFF000000);

        mDays.add("deuxième");
        mBackgroundColor.add(0xFF222222);

        mDays.add("troisième");
        mBackgroundColor.add(0xFF444444);

        mDays.add("quatrième");
        mBackgroundColor.add(0xFF666666);

        mDays.add("cinquième");
        mBackgroundColor.add(0xFF888888);

        mDays.add("sixième");
        mBackgroundColor.add(0xFFAAAAAA);

        mDays.add("septième");
        mBackgroundColor.add(0xFFCCCCCC);

        mDays.add("septième");
        mBackgroundColor.add(0xFFCCAACC);

        mDays.add("septième");
        mBackgroundColor.add(0xFFCCCC99);

        mDays.add("septième");
        mBackgroundColor.add(0xFFDDCCCC);

        mDays.add("septième");
        mBackgroundColor.add(0xFF123456);

        mDays.add("septième");
        mBackgroundColor.add(0xFF654321);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_historic_recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mDays, mBackgroundColor);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
