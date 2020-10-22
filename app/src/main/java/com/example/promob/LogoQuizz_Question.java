package com.example.promob;

//Class mere pour les questions
//Pont entre notre bdd et l'application
public class LogoQuizz_Question {

    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";


    //Attributs
    private String question;
    private String image;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int numeroReponse;
    private String difficulty;

    //Constructeur vide
    public LogoQuizz_Question(){}

    //Constructeur
    public LogoQuizz_Question(String image, String option1, String option2, String option3, String option4, int numeroReponse, String difficulty) {
        this.question = "Quel est ce club ?"; //Cette question est fixe
        this.image = image;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.numeroReponse = numeroReponse;
        this.difficulty = difficulty;
    }

    //GETTERS ET SETTERS
    //Changer les valeurs des attributs de la classe
    //Afficher ces valeurs hors de cette classe

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
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
