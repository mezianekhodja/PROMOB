package com.example.promob;

public class GoogleTrends_Question {
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";


    private String option1;
    private String option2;

    private int numeroReponse;
    private String difficulty;

    //Constructeur vide
    public GoogleTrends_Question(){};

    //Constructeur
    public GoogleTrends_Question(String option1, String option2, int numeroReponse, String difficulty) {
        this.option1 = option1;
        this.option2 = option2;
        this.numeroReponse = numeroReponse;
        this.difficulty = difficulty;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumeroReponse() {
        return numeroReponse;
    }

    public void setNumeroReponse(int numeroReponse) {
        this.numeroReponse = numeroReponse;
    }

    public static String[] getAllDifficultyLevels(){
        return new String[]{
                DIFFICULTY_EASY,
                DIFFICULTY_MEDIUM,
                DIFFICULTY_HARD
        };
    }

}

