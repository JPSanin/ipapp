package com.example.ipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private TextView pingsTxt;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        pingsTxt= findViewById(R.id.pingsTxt);
        btnBack= findViewById(R.id.btnBack);
        String result= getIntent().getExtras().getString("result");
        pingsTxt.setText(result);

        btnBack.setOnClickListener((view)->{
            finish();
        });
    }
}