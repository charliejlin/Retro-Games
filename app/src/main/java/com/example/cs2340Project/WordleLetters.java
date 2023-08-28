package com.example.cs2340Project;

public class WordleLetters {

    private int[] lettersAccuracy = new int[26];

    public WordleLetters(){
        for (int i = 0; i< lettersAccuracy.length; i++){
            lettersAccuracy[i] = -1;
        }
    }

    public void updateAccuracy(char letter, int color){
        //only update if its more accurate
        int letterIndex = (int) letter - (int) 'a';
        if(lettersAccuracy[letterIndex] < color) {
            lettersAccuracy[letterIndex] = color;
        }
    }

    public int getLetterAccuracy(int index){
        return lettersAccuracy[index];
    }
}
