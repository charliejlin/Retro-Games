package com.example.cs2340Project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WordleInitialScreen extends AppCompatActivity implements SpriteSettable{
    private EditText name;
    private Button add;
    private Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordle_initial_screen);
        name = findViewById(R.id.wordlePLayerName);
        add = findViewById(R.id.startGame);

        Button homeButton = findViewById(R.id.BackButton);
        homeButton.setOnClickListener(v -> finish());

        Button startGame = findViewById(R.id.startGame);
        startGame.setOnClickListener(v -> {
            player = Player.getInstance();
            if (name.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter player name.", Toast.LENGTH_SHORT).show();
            } else {
                player.setPlayerName(name.getText().toString());
                setSprite();
                Intent intent = new Intent(WordleInitialScreen.this, WordleGame.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void setSprite(){
        RadioButton pandaButton = findViewById(R.id.pandaButton);
        RadioButton foxButton = findViewById(R.id.foxButton);
        RadioButton turtleButton = findViewById(R.id.turtleButton);
        ImageView sprite = findViewById(R.id.sprite);
        if(pandaButton.isChecked()){
            player.setPlayerSprite('p');
            //sprite.setImageResource(R.drawable.panda);
        }
        else if(turtleButton.isChecked()){
            player.setPlayerSprite('t');
            //sprite.setImageResource(R.drawable.turtle);
        }
        else {
            player.setPlayerSprite('f');
            //sprite.setImageResource(R.drawable.fox);
        }
    }
}