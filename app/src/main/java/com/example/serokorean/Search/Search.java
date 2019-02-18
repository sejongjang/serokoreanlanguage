package com.example.serokorean.Search;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.serokorean.R;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#c0000000"));
        getWindow().setNavigationBarColor(Color.parseColor("#c0000000"));
        setContentView(R.layout.activity_search);


    }
}
