package com.example.cs2340Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class TicTacToeStartScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_start_screen);

        Button initButton = findViewById(R.id.initializeButton);
        initButton.setOnClickListener(v -> {
            Intent intent = new Intent(TicTacToeStartScreen.this, TicTacToeInitialScreen.class);
            startActivity(intent);
            finish();
        });
        Button homeButton = findViewById(R.id.t_home_button);
        homeButton.setOnClickListener(v -> finish());
    }
}