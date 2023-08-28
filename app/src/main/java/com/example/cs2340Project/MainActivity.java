package com.example.cs2340Project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        player = Player.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button blackJackScreenButton = findViewById(R.id.blackJackStartScreen);
        blackJackScreenButton.setOnClickListener(v -> {
            player.setCurrentGame('b');
            startActivity(new Intent(MainActivity.this, BlackJackStartScreen.class));
        });

        Button checkersScreenButton = findViewById(R.id.checkersStartScreen);
        checkersScreenButton.setOnClickListener(v -> {
            player.setCurrentGame('c');
            Intent intent = new Intent(MainActivity.this, TicTacToeStartScreen.class);
            startActivity(intent);
        });


        Button wordleStScreen = findViewById(R.id.wordleStartScreen2);
        wordleStScreen.setOnClickListener(v -> {
            player.setCurrentGame('w');
            Intent intent = new Intent(MainActivity.this, WordleStartScreen.class);
            startActivity(intent);
        });
    }
}