package com.example.promob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.promob.Quiz_Contract.*;

import java.util.ArrayList;
import java.util.List;

public class Quiz_bdd extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="bdd.db";
    private static final int DATABASE_VERSION =2;
    private SQLiteDatabase db;

    public Quiz_bdd(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        //Creation de la table
        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " + QuestionTable.TABLE_NAME
                + " ( " + QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLONNE_QUESTION + " TEXT, " +
                QuestionTable.COLONNE_IMAGE + " TEXT, " +
                QuestionTable.COLONNE_OPTION1 + " TEXT, " +
                QuestionTable.COLONNE_OPTION2 + " TEXT, " +
                QuestionTable.COLONNE_OPTION3 + " TEXT, " +
                QuestionTable.COLONNE_OPTION4 + " TEXT, " +
                QuestionTable.COLONNE_NUMERO_REPONSE + " INTEGER" + ")";

        //Ecxectuer notre String en SQL
        db.execSQL(SQL_CREATE_QUESTION_TABLE);


        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionTable(){
        QuizQuestion q1 = new QuizQuestion("logoquizz_rennes", "Reims", "Rennes", "Guingamp", "Dijon", 2 );
        addQuestion(q1);
        QuizQuestion q2 = new QuizQuestion("logoquizz_nantes", "Montpellier", "Nimes", "Nantes", "Strasbourg", 3 );
        addQuestion(q2);
        QuizQuestion q3 = new QuizQuestion("logoquizz_marseille", "Monaco", "Rennes", "Lyon", "Marseille", 4 );
        addQuestion(q3);
        QuizQuestion q4 = new QuizQuestion("logoquizz_lorient","Lorient", "Brest", "St Etienne", "Guingamp", 1);
        addQuestion(q4);
        QuizQuestion q5 = new QuizQuestion("logoquizz_lyon","Marseille", "Lille", "Lyon", "Paris", 3 );
        addQuestion(q5);
    }

    private void addQuestion(QuizQuestion question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLONNE_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLONNE_IMAGE, question.getImage());
        cv.put(QuestionTable.COLONNE_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLONNE_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLONNE_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLONNE_OPTION4, question.getOption4());
        cv.put(QuestionTable.COLONNE_NUMERO_REPONSE, question.getNumeroReponse());
        //Inserer les valeurs dans la bdd
        db.insert(QuestionTable.TABLE_NAME, null, cv);
    }

    //recuperer les données de la table
    public List<QuizQuestion> getAllQuestion(){
        List<QuizQuestion> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);
        if(c.moveToFirst()){
            do{
                QuizQuestion question = new QuizQuestion();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLONNE_QUESTION)));
                question.setImage(c.getString(c.getColumnIndex(QuestionTable.COLONNE_IMAGE)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLONNE_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLONNE_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLONNE_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLONNE_OPTION4)));
                question.setNumeroReponse(c.getInt(c.getColumnIndex(QuestionTable.COLONNE_NUMERO_REPONSE)));
                questionList.add(question);
            } while(c.moveToNext());
        }
        c.close();
        return questionList;
    }

}
