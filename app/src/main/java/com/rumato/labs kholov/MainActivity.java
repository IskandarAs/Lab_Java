package com.rumato.kslab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rumato.lab1.Lab1Activity;
import com.rumato.lab2.Lab2Activity;
import com.rumato.lab3.Activities.Lab3Activity;
import com.rumato.lab4.Lab4Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bLab1 = findViewById(R.id.b_lab1);
        Button bLab2 = findViewById(R.id.b_lab2);
        Button bLab3 = findViewById(R.id.b_lab3);
        Button bLab4 = findViewById(R.id.b_lab4);

        bLab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lab1Activity.class);
                startActivity(intent);
            }
        });

        bLab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lab2Activity.class);
                startActivity(intent);
            }
        });

        bLab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lab3Activity.class);
                startActivity(intent);
            }
        });

        bLab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lab4Activity.class);
                startActivity(intent);
            }
        });
    }
}
