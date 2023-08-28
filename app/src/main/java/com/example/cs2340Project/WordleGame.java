package com.example.cs2340Project;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class WordleGame extends AppCompatActivity implements View.OnClickListener, LivesSelectable {
    private EditText[][] letterGrid;
    private Button enter;
    private Button del;
    private int currentRow;
    private String answer;
    private WordleGameFunctionality wordle;
    private Player player;

    private WordleLetters wordleLetters = new WordleLetters();


    int[] buttonIds = {
            R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE,
            R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI, R.id.buttonJ,
            R.id.buttonK, R.id.buttonL, R.id.buttonM, R.id.buttonN, R.id.buttonO,
            R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS, R.id.buttonT,
            R.id.buttonU, R.id.buttonV, R.id.buttonW, R.id.buttonX, R.id.buttonY, R.id.buttonZ
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordle_game);

        TextView wordleHeading = findViewById(R.id.wordleHeading);
        wordleHeading.setEnabled(false);

        currentRow = 0;
        wordle = new WordleGameFunctionality(getResources().getStringArray(R.array.word_list));
        wordle.selectNewWord();
        answer = wordle.getSolution();
        player = Player.getInstance();
        selectLives();

        Button toHome = findViewById(R.id.toMainActivity);
        toHome.setOnClickListener(v -> finish());

        Button restart = findViewById(R.id.wd_restart_button);
        restart.setOnClickListener(v -> {
            Intent intent = new Intent(WordleGame.this, WordleInitialScreen.class);
            startActivity(intent);
            finish();
        });

        letterGrid = new EditText[5][5];
        letterGrid[0][0] = findViewById(R.id.editText1);
        letterGrid[0][1] = findViewById(R.id.editText2);
        letterGrid[0][2] = findViewById(R.id.editText3);
        letterGrid[0][3] = findViewById(R.id.editText4);
        letterGrid[0][4] = findViewById(R.id.editText5);
        letterGrid[1][0] = findViewById(R.id.editText6);
        letterGrid[1][1] = findViewById(R.id.editText7);
        letterGrid[1][2] = findViewById(R.id.editText8);
        letterGrid[1][3] = findViewById(R.id.editText9);
        letterGrid[1][4] = findViewById(R.id.editText10);
        letterGrid[2][0] = findViewById(R.id.editText11);
        letterGrid[2][1] = findViewById(R.id.editText12);
        letterGrid[2][2] = findViewById(R.id.editText13);
        letterGrid[2][3] = findViewById(R.id.editText14);
        letterGrid[2][4] = findViewById(R.id.editText15);
        letterGrid[3][0] = findViewById(R.id.editText16);
        letterGrid[3][1] = findViewById(R.id.editText17);
        letterGrid[3][2] = findViewById(R.id.editText18);
        letterGrid[3][3] = findViewById(R.id.editText19);
        letterGrid[3][4] = findViewById(R.id.editText20);
        letterGrid[4][0] = findViewById(R.id.editText21);
        letterGrid[4][1] = findViewById(R.id.editText22);
        letterGrid[4][2] = findViewById(R.id.editText23);
        letterGrid[4][3] = findViewById(R.id.editText24);
        letterGrid[4][4] = findViewById(R.id.editText25);


        for (int i = 0; i < buttonIds.length; i++) {
            Button button = findViewById(buttonIds[i]);
            button.setOnClickListener(this);
        }

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                EditText disable = letterGrid[row][col];
                disable.setFocusable(false);
                disable.setFocusableInTouchMode(false);
            }
        }

        del = findViewById(R.id.delete);
        del.setOnClickListener(this);

        enter = findViewById(R.id.enter);
        enter.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.delete) {
            Button del = (Button) v;
            String delText = del.getText().toString();
            if (delText.equals("Delete")) {
                for (int col = 4; col >= 0; col--) {
                    EditText letter = letterGrid[currentRow][col];
                    if (!letter.getText().toString().isEmpty()) {
                        letter.setText("");
                        letter.requestFocus();
                        break;
                    }
                }
            } else {
                for (int col = 0; col < 5; col++) {
                    EditText letter = letterGrid[currentRow][col];
                    if (letter.getText().toString().isEmpty()) {
                        letter.setText(delText);
                        if (col + 1 < 5) {
                            letterGrid[currentRow][col + 1].requestFocus();
                        }
                        break;
                    }
                }
            }
        }  else if (v.getId() == R.id.enter) {
            if (currentRow < 5 && checkRow(currentRow)) {
                String playerGuess = getRowCharacters(currentRow);
                // Store as lowercase
                playerGuess = playerGuess.toLowerCase();
                char[] guess = playerGuess.toCharArray();
                char[] solution = answer.toCharArray();
                int count = 0;
                // Check if string playerGuess is found in guessList
                int valid = wordle.checkGuessValid(playerGuess);
                if (valid != -1) {
                    for (int i = 0; i < guess.length; i++) {
                        if (guess[i] == solution[i]) {
                            wordleLetters.updateAccuracy(guess[i], 1);
                            letterGrid[currentRow][count].setBackgroundResource(R.color.bright_green);
                        } else if (answer.contains(String.valueOf(guess[i]))) {
                            wordleLetters.updateAccuracy(guess[i], 0);
                            letterGrid[currentRow][count].setBackgroundResource(R.color.purple);
                        } else {
                            letterGrid[currentRow][count].setBackgroundResource(R.color.gray);
                        }
                        count++;
                    }
                    setColors(guess);


                    boolean checkSolution = true;
                    for (int i = 0; i < guess.length; i++) {
                        if (guess[i] != solution[i]) {
                            checkSolution = false;
                            if(currentRow == 4) {
                                Toast.makeText(this, "You are out of tries, the correct answer was " + answer, Toast.LENGTH_SHORT).show();
                                    player.setPlayerLives((player.getPlayerLives())-1);
                                    Intent intent = new Intent(WordleGame.this, WordleGame.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            break;
                        }

                    // Show toast message if the guess is correct
                    if (checkSolution) {
                        Toast.makeText(this, "Congratulations! You got the right answer!", Toast.LENGTH_SHORT).show();
                    }

                    // Keep this code in the if statement
                    // Determines whether or not it should go to the next row
                    currentRow++;
                    if (currentRow < 5) {
                        letterGrid[currentRow][0].requestFocus();
                    }
                } else {
                    shakeAnimation();
                }

            }
        } else if (v.getId() == R.id.wd_restart_button) {
            recreate();
        } else {
            keyboard(v);
        }

    }

    public void shakeAnimation() {
        TextView tv = findViewById(R.id.wordleHeading);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        tv.startAnimation(animation);
    }

    private boolean checkRow(int row) {
        for (int col = 0; col < 5; col++) {
            if (letterGrid[row][col].getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String getRowCharacters(int row) {
        StringBuilder rowCharacters = new StringBuilder();
        for (int col = 0; col < 5; col++) {
            rowCharacters.append(letterGrid[row][col].getText().toString());
        }
        return rowCharacters.toString();
    }

    private void keyboard(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        if (!buttonText.isEmpty()) {
            for (int col = 0; col < 5; col++) {
                EditText editText = letterGrid[currentRow][col];
                if (editText.getText().toString().isEmpty()) {
                    editText.setText(buttonText);
                    if (col + 1 < 5) {
                        letterGrid[currentRow][col + 1].requestFocus();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void selectLives() {
        if(player.getPlayerLives() == 0){
            Intent intent = new Intent(WordleGame.this, GameOverScreen.class);
            intent.putExtra("Game","WD");
            startActivity(intent);
            finish();
        }
        TextView name = findViewById(R.id.playerDataName);
        name.setText(player.getPlayerName());
        //update the sprite image
        ImageView sprite = findViewById(R.id.sprite);
        player.setSpriteImage(sprite);
        ImageView i1 = findViewById(R.id.life1);
        ImageView i2 = findViewById(R.id.life2);
        ImageView i3 = findViewById(R.id.life3);
        player.setLives(i1,i2,i3);
    }

    public void setColors(char[] guess){
        //go through the guess and for each of the indexes of the int array set the color

        for(int i = 0; i < guess.length; i++ ) {
            int letterIndex = (int) guess[i] - (int) 'a';
            Button b = findViewById(buttonIds[letterIndex]);
            int accuracy = wordleLetters.getLetterAccuracy(letterIndex);
            if(accuracy == 0) {
                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.purple)));
            }
            else if (accuracy ==1 ){
                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.bright_green)));
            }
            else if (accuracy == -1 ){
                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray)));
            }

        }

    }

}
