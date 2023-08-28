package com.example.cs2340Project;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.View;

public class Player {

    //singleton design pattern
    private static Player myPlayer;
    private static int playerLives = 3;
    private static char playerSprite = 'f';
    private static int score = 0;
    private static int highScore = 0;
    private static String playerName = "Anon";
    private static String leader = "None";

    private static char currentGame;
    private Player(){}
    public static synchronized Player getInstance(){
        if (myPlayer == null) {
            myPlayer = new Player();
        }
        return myPlayer;
    }
    public int getPlayerLives() {
        return myPlayer.playerLives;
    }
    public void setPlayerLives(int playerLives){
        myPlayer.playerLives = playerLives;
    }

    public void setLives(ImageView imgView1, ImageView imgView2, ImageView imgView3) {
        if (myPlayer.getPlayerLives() == 2) {
            imgView1.setVisibility(View.INVISIBLE);
        }
        else if (myPlayer.getPlayerLives() == 1) {
            imgView1.setVisibility(View.INVISIBLE);
            imgView2.setVisibility(View.INVISIBLE);
        }
        else if (myPlayer.getPlayerLives() == 0){
            imgView1.setVisibility(View.INVISIBLE);
            imgView2.setVisibility(View.INVISIBLE);
            imgView3.setVisibility(View.INVISIBLE);
        }
    }

    public void addLife(ImageView imgView) {
        imgView.setVisibility(View.VISIBLE);
        myPlayer.playerLives++;
    }

    public char getPlayerSprite() {
        return playerSprite;
    }
    public void setPlayerSprite(char playerSprite){
        myPlayer.playerSprite = playerSprite;
    }

    public void setSpriteImage(ImageView sprite){
            if(myPlayer.playerSprite == ('p')){
                sprite.setImageResource(R.drawable.panda);
            }
            else if(myPlayer.playerSprite == ('t')){
                sprite.setImageResource(R.drawable.turtle);
            }
            else {
                sprite.setImageResource(R.drawable.fox);
            }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        myPlayer.score = score;
    }

    public void setHighScore(int score) {
        myPlayer.highScore = score;
    }

    public int getHighScore() { return highScore;}

    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName){
        myPlayer.playerName = playerName;
    }

    public String getLeader() {
        return leader;
    }
    public void setLeader() {
        leader = playerName;
    }

    public void setCurrentGame(char currentGame){
        myPlayer.currentGame = currentGame;
    }
    public char getCurrentGame(){
        return myPlayer.currentGame;
    }
    }
