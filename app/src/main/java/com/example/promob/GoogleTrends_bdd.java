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
        GoogleTrends_Question qE1 = new GoogleTrends_Question("Patate", "Canette", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE1);
        GoogleTrends_Question qE2 = new GoogleTrends_Question("Rien", "Moi", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE2);
        GoogleTrends_Question qE3 = new GoogleTrends_Question("Lille", "Marseille", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE3);
        GoogleTrends_Question qE4 = new GoogleTrends_Question("Patate", "Canette", 1 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE4);
        GoogleTrends_Question qE5 = new GoogleTrends_Question("Rien", "Moi", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE5);
        GoogleTrends_Question qE6 = new GoogleTrends_Question("Lille", "Marseille", 2 , GoogleTrends_Question.DIFFICULTY_EASY);
        addQuestion(qE6);

        GoogleTrends_Question qM1 = new GoogleTrends_Question("NiveauMedGood", "NiveauMed2", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM1);
        GoogleTrends_Question qM2 = new GoogleTrends_Question("NiveauMed1", "NiveauMedGood", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM2);
        GoogleTrends_Question qM3 = new GoogleTrends_Question("NiveauMed1", "NiveauMedGood", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM3);
        GoogleTrends_Question qM4 = new GoogleTrends_Question("NiveauMedGood", "NiveauMed2", 1 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM4);
        GoogleTrends_Question qM5 = new GoogleTrends_Question("NiveauMed1", "NiveauMedGood", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM5);
        GoogleTrends_Question qM6 = new GoogleTrends_Question("NiveauMed1", "NiveauMedGood", 2 , GoogleTrends_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM6);

        GoogleTrends_Question qH1 = new GoogleTrends_Question("HEHE", "OHOH", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH1);
        GoogleTrends_Question qH2 = new GoogleTrends_Question("RIZ", "PP", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH2);
        GoogleTrends_Question qH3 = new GoogleTrends_Question("test", "toto", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH3);
        GoogleTrends_Question qH4 = new GoogleTrends_Question("HEHE", "OHOH", 1 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH4);
        GoogleTrends_Question qH5 = new GoogleTrends_Question("RIZ", "PP", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH5);
        GoogleTrends_Question qH6 = new GoogleTrends_Question("test", "toto", 2 , GoogleTrends_Question.DIFFICULTY_HARD);
        addQuestion(qH6);


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
