package com.rahi.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//This activity is for exiting the app from home activity

public class ExitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        finish();
    }
}