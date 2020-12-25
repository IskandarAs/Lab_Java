package com.rumato.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Lab1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);

        setTitle(getString(R.string.welcome_lab1, getClass().getSimpleName()));
    }
}
