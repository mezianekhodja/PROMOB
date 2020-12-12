package com.example.promob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GoogleTrends_bdd extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="bdd2.db";
    private static final int DATABASE_VERSION =1;
    private SQLiteDatabase db;

    public GoogleTrends_bdd(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        //Creation de la table
        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " + GoogleTrends_Contract.QuestionGoogleTrends.TABLE_NAME
                + " ( " +  GoogleTrends_Contract.QuestionGoogleTrends._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_OPTION1 + " TEXT, " +
                GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_OPTION2 + " TEXT, " +
                GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_NUMERO_REPONSE + " INTEGER," +
                GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_DIFFICULTY + " TEXT" + ")";

        //Ecxectuer notre String en SQL
        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  GoogleTrends_Contract.QuestionGoogleTrends.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionTable(){
        GoogleTrends_Question qE1 = new GoogleTrends_Question("USA", "France", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE1);
        GoogleTrends_Question qE2 = new GoogleTrends_Question("Rugby", "Football", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE2);
        GoogleTrends_Question qE3 = new GoogleTrends_Question("Cinéma", "Théâtre", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE3);
        GoogleTrends_Question qE4 = new GoogleTrends_Question("Morue", "Raclette", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE4);
        GoogleTrends_Question qE5 = new GoogleTrends_Question("Manger", "Respirer", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE5);
        GoogleTrends_Question qE6 = new GoogleTrends_Question("Rap", "Jazz", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE6);
        GoogleTrends_Question qE7 = new GoogleTrends_Question("Facebook", "Instagram", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE7);
        GoogleTrends_Question qE8 = new GoogleTrends_Question("Chine", "Inde", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE8);
        GoogleTrends_Question qE9 = new GoogleTrends_Question("Obama", "Trump", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE9);
        GoogleTrends_Question qE10 = new GoogleTrends_Question("Mbappe", "Pogba", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE10);
        GoogleTrends_Question qE11 = new GoogleTrends_Question("Karaté", "Judo", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE11);
        GoogleTrends_Question qE12 = new GoogleTrends_Question("Vent", "Eau", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE12);
        GoogleTrends_Question qE13 = new GoogleTrends_Question("Velo", "Moto", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE13);
        GoogleTrends_Question qE14 = new GoogleTrends_Question("Milliard", "Million", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE14);
        GoogleTrends_Question qE15 = new GoogleTrends_Question("Alcool", "Drogue", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE15);
        GoogleTrends_Question qE16 = new GoogleTrends_Question("Mère", "Mer", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE16);
        GoogleTrends_Question qE17 = new GoogleTrends_Question("Train", "Voiture", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE17);
        GoogleTrends_Question qE18 = new GoogleTrends_Question("Bus", "Car", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE18);
        GoogleTrends_Question qE19 = new GoogleTrends_Question("Ciseau", "Papier", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE19);
        GoogleTrends_Question qE20 = new GoogleTrends_Question("Salon", "Cuisine", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE20);

        GoogleTrends_Question qM1 = new GoogleTrends_Question("Béarnaise", "Tabasco", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM1);
        GoogleTrends_Question qM2 = new GoogleTrends_Question("Mathématiques", "Géographie", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM2);
        GoogleTrends_Question qM3 = new GoogleTrends_Question("Dictature", "Démocratie", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM3);
        GoogleTrends_Question qM4 = new GoogleTrends_Question("Afrique", "Asie", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM4);
        GoogleTrends_Question qM5 = new GoogleTrends_Question("Chat", "Chien", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM5);
        GoogleTrends_Question qM6 = new GoogleTrends_Question("Streaming", "Download", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM6);
        GoogleTrends_Question qM7 = new GoogleTrends_Question("Deezer", "Spotify", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM7);
        GoogleTrends_Question qM8 = new GoogleTrends_Question("Android", "Apple", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM8);
        GoogleTrends_Question qM9 = new GoogleTrends_Question("Brésil", "Mexique", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM9);
        GoogleTrends_Question qM10 = new GoogleTrends_Question("Cuit", "Cru", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM10);
        GoogleTrends_Question qM11 = new GoogleTrends_Question("Mourir", "Vivre", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM11);
        GoogleTrends_Question qM12 = new GoogleTrends_Question("Papa", "Maman", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM12);
        GoogleTrends_Question qM13 = new GoogleTrends_Question("Singe", "Chèvre", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM13);
        GoogleTrends_Question qM14 = new GoogleTrends_Question("Chimie", "Physique", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM14);
        GoogleTrends_Question qM15 = new GoogleTrends_Question("Monténégro", "Turkménistan", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM15);
        GoogleTrends_Question qM16 = new GoogleTrends_Question("Facebook", "Amazon", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM16);
        GoogleTrends_Question qM17 = new GoogleTrends_Question("Café", "Lait", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM17);
        GoogleTrends_Question qM18 = new GoogleTrends_Question("Ariana Grande", "Post Malone", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM18);
        GoogleTrends_Question qM19 = new GoogleTrends_Question("Argent", "Or", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM19);
        GoogleTrends_Question qM20 = new GoogleTrends_Question("Attaque", "Défense", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM20);

        GoogleTrends_Question qH1 = new GoogleTrends_Question("Espagne", "France", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH1);
        GoogleTrends_Question qH2 = new GoogleTrends_Question("Congo", "Madagascar", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH2);
        GoogleTrends_Question qH3 = new GoogleTrends_Question("Snapchat", "Tiktok", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH3);
        GoogleTrends_Question qH4 = new GoogleTrends_Question("Écureuil", "Méduse", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH4);
        GoogleTrends_Question qH5 = new GoogleTrends_Question("Zèbre", "Martin Pêcheur", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH5);
        GoogleTrends_Question qH6 = new GoogleTrends_Question("Boxe", "MMA", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH6);
        GoogleTrends_Question qH7 = new GoogleTrends_Question("Coronavirus", "Mail", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH7);
        GoogleTrends_Question qH8 = new GoogleTrends_Question("Pele", "Zidane", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH8);
        GoogleTrends_Question qH9 = new GoogleTrends_Question("Kebab", "Tacos", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH9);
        GoogleTrends_Question qH10 = new GoogleTrends_Question("Zoo", "Bowling", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH10);
        GoogleTrends_Question qH11 = new GoogleTrends_Question("Fortnite", "Minecraft", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH11);
        GoogleTrends_Question qH12 = new GoogleTrends_Question("Gauche", "Droite", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH12);
        GoogleTrends_Question qH13 = new GoogleTrends_Question("Gagner", "Perdre", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH13);
        GoogleTrends_Question qH14 = new GoogleTrends_Question("Tennis", "Golf", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH14);
        GoogleTrends_Question qH15 = new GoogleTrends_Question("Walking Dead", "Game of Thrones", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH15);
        GoogleTrends_Question qH16 = new GoogleTrends_Question("Plage", "Mer", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH16);
        GoogleTrends_Question qH17 = new GoogleTrends_Question("Chaussure", "Manteau", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH17);
        GoogleTrends_Question qH18 = new GoogleTrends_Question("Pompier", "Policier", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH18);
        GoogleTrends_Question qH19 = new GoogleTrends_Question("Google", "Facebook", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH19);
        GoogleTrends_Question qH20 = new GoogleTrends_Question("Lézard", "Loutre", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH20);
    }

    private void addQuestion(GoogleTrends_Question question){
        ContentValues cv = new ContentValues();
        cv.put(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_OPTION1, question.getOption1());
        cv.put(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_OPTION2, question.getOption2());
        cv.put(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_NUMERO_REPONSE, question.getNumeroReponse());
        cv.put(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_DIFFICULTY, question.getDifficulty());

        //Inserer les valeurs dans la bdd
        db.insert( GoogleTrends_Contract.QuestionGoogleTrends.TABLE_NAME, null, cv);
    }

    //recuperer les données de la table
    public List<GoogleTrends_Question> getAllQuestion(){
        List<GoogleTrends_Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + GoogleTrends_Contract.QuestionGoogleTrends.TABLE_NAME, null);
        if(c.moveToFirst()){
            do{
                GoogleTrends_Question question = new GoogleTrends_Question();
                question.setOption1(c.getString(c.getColumnIndex(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_OPTION2)));
                question.setNumeroReponse(c.getInt(c.getColumnIndex(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_NUMERO_REPONSE)));
                questionList.add(question);
            } while(c.moveToNext());
        }
        c.close();
        return questionList;
    }

    //recuperer les données de la table
    public List<GoogleTrends_Question> getQuestion(String difficulty){
        List<GoogleTrends_Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + GoogleTrends_Contract.QuestionGoogleTrends.TABLE_NAME + " WHERE " + GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_DIFFICULTY + " = ? " , selectionArgs);
        if(c.moveToFirst()){
            do{
                GoogleTrends_Question question = new GoogleTrends_Question();
                question.setOption1(c.getString(c.getColumnIndex(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_OPTION2)));
                question.setNumeroReponse(c.getInt(c.getColumnIndex(GoogleTrends_Contract.QuestionGoogleTrends.COLONNE_NUMERO_REPONSE)));
                questionList.add(question);
            } while(c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
