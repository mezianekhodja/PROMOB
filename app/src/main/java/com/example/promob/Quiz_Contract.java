package com.example.promob;

import android.provider.BaseColumns;


public final class Quiz_Contract {

    private Quiz_Contract() {}

    public static class QuestionTable implements BaseColumns {
        public static final String TABLE_NAME = "quizz_question";
        public static final String COLONNE_QUESTION = "question";
        public static final String COLONNE_IMAGE = "image";
        public static final String COLONNE_OPTION1 = "option1";
        public static final String COLONNE_OPTION2 = "option2";
        public static final String COLONNE_OPTION3 = "option3";
        public static final String COLONNE_OPTION4 = "option4";
        public static final String COLONNE_NUMERO_REPONSE = "numero_reponse";

    }
}
