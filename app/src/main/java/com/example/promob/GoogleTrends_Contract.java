package com.example.promob;

import android.provider.BaseColumns;

//Class pour stocker des constantes que l'on va utiliser pour les opérations sql

public final class GoogleTrends_Contract {

    private GoogleTrends_Contract() {}

    public static class QuestionGoogleTrends implements BaseColumns {

        //Si on veut changer le noms d'un colomne on peut le faire directement ici
        //Public car on veut que ces valeurs existent or de cette classe
        //Static car on veut pouvoir les creer sans instance de Question Table

        public static final String TABLE_NAME = "GoogleTrends";
        public static final String COLONNE_OPTION1 = "option1";
        public static final String COLONNE_OPTION2 = "option2";
        public static final String COLONNE_NUMERO_REPONSE = "numero_reponse";
        public static final String COLONNE_DIFFICULTY = "difficulté";

    }
}
