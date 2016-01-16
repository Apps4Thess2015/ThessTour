package com.kotrots.thesstour.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kotrots.thesstour.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setTitle(getResources().getString(R.string.menu_help));
    }
}
