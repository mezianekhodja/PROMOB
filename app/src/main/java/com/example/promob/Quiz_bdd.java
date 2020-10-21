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

        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " + QuestionTable.TABLE_NAME
                + " ( " + QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLONNE_QUESTION + " TEXT, " +
                QuestionTable.COLONNE_IMAGE + " TEXT, " +
                QuestionTable.COLONNE_OPTION1 + " TEXT, " +
                QuestionTable.COLONNE_OPTION2 + " TEXT, " +
                QuestionTable.COLONNE_OPTION3 + " TEXT, " +
                QuestionTable.COLONNE_OPTION4 + " TEXT, " +
                QuestionTable.COLONNE_NUMERO_REPONSE + " INTEGER" + ")";

        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionTable(){
        QuizQuestion q1 = new QuizQuestion("logo_rennes", "Reims", "Rennes", "Guingamps", "Dijon", 2 );
        addQuestion(q1);
        QuizQuestion q2 = new QuizQuestion("logo_nantes", "Montpellier", "Toulouse", "Nantes", "Strasbourg", 3 );
        addQuestion(q2);
        QuizQuestion q3 = new QuizQuestion("logo_bordeaux", "Bordeaux", "Rennes", "Lyon", "Marseille", 1 );
        addQuestion(q3);
        QuizQuestion q4 = new QuizQuestion("logo_paris","Lyon", "Marseille", "St Etienne", "Paris", 4);
        addQuestion(q4);
        QuizQuestion q5 = new QuizQuestion("logo_reims","Marseille", "Guimgamps", "Rennes", "Reims", 4 );
        addQuestion(q5);
        QuizQuestion q6 = new QuizQuestion("logo_lyon","Lyon", "Paris", "Marseille", "Reims", 1 );
        addQuestion(q6);
        QuizQuestion q7 = new QuizQuestion("logo_guingamps","Rennes", "Guingamps", "Marseille", "Reims", 2 );
        addQuestion(q7);
        QuizQuestion q8 = new QuizQuestion("logo_lorient","Lorient", "Rennes", "Brest", "Nantes", 1 );
        addQuestion(q8);
        QuizQuestion q9 = new QuizQuestion("logo_marseille","Lyon", "Marseille", "Reims", "Rennes", 2 );
        addQuestion(q9);
        QuizQuestion q10 = new QuizQuestion("logo_rennes","Nantes", "Rennes", "Marseille", "Reims", 2 );
        addQuestion(q10);
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
        db.insert(QuestionTable.TABLE_NAME, null, cv);
    }

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
