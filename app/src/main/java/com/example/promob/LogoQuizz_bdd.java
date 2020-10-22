package com.example.promob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.promob.LogoQuizz_Contract.*;

import java.util.ArrayList;
import java.util.List;

public class LogoQuizz_bdd extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="bdd.db";
    private static final int DATABASE_VERSION =3;
    private SQLiteDatabase db;

    public LogoQuizz_bdd(Context context) {
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
                QuestionTable.COLONNE_NUMERO_REPONSE + " INTEGER," +
                QuestionTable.COLONNE_DIFFICULTY + " TEXT" + ")";

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
        LogoQuizz_Question q1 = new LogoQuizz_Question("logo_rennes", "E", "Rennes", "Guingamps", "Dijon", 2 , LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(q1);
        LogoQuizz_Question q2 = new LogoQuizz_Question("logo_nantes", "E", "Toulouse", "Nantes", "Strasbourg", 3 ,LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(q2);
        LogoQuizz_Question q3 = new LogoQuizz_Question("logo_bordeaux", "E", "Rennes", "Lyon", "Marseille", 1,LogoQuizz_Question.DIFFICULTY_EASY );
        addQuestion(q3);
        LogoQuizz_Question q4 = new LogoQuizz_Question("logo_paris","E", "Marseille", "St Etienne", "Paris", 4, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(q4);
        LogoQuizz_Question q5 = new LogoQuizz_Question("logo_reims","E", "Guimgamps", "Rennes", "Reims", 4,LogoQuizz_Question.DIFFICULTY_EASY );
        addQuestion(q5);
        LogoQuizz_Question q6 = new LogoQuizz_Question("logo_lyon","E", "Paris", "Marseille", "Reims", 1,LogoQuizz_Question.DIFFICULTY_EASY );
        addQuestion(q6);
        LogoQuizz_Question q7 = new LogoQuizz_Question("logo_guingamps","E", "Guingamps", "Marseille", "Reims", 2,LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(q7);
        LogoQuizz_Question q8 = new LogoQuizz_Question("logo_lorient","E", "Rennes", "Brest", "Nantes", 1,LogoQuizz_Question.DIFFICULTY_EASY );
        addQuestion(q8);
        LogoQuizz_Question q9 = new LogoQuizz_Question("logo_marseille","E", "Marseille", "Reims", "Rennes", 2,LogoQuizz_Question.DIFFICULTY_EASY );
        addQuestion(q9);
        LogoQuizz_Question q10 = new LogoQuizz_Question("logo_rennes","E", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(q10);
        LogoQuizz_Question q11 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q11);
        LogoQuizz_Question q12 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q12);
        LogoQuizz_Question q13 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q13);
        LogoQuizz_Question q14 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q14);
        LogoQuizz_Question q15 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q15);
        LogoQuizz_Question q16 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q16);
        LogoQuizz_Question q17 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q17);
        LogoQuizz_Question q18 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q18);
        LogoQuizz_Question q19 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q19);
        LogoQuizz_Question q20 = new LogoQuizz_Question("logo_rennes","M", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(q20);
        LogoQuizz_Question q21 = new LogoQuizz_Question("logo_rennes","H", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q21);
        LogoQuizz_Question q22 = new LogoQuizz_Question("logo_rennes","H", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q22);
        LogoQuizz_Question q23 = new LogoQuizz_Question("logo_rennes","H", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q23);
        LogoQuizz_Question q24 = new LogoQuizz_Question("logo_rennes","H", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q24);
        LogoQuizz_Question q25 = new LogoQuizz_Question("logo_rennes","H", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q25);
        LogoQuizz_Question q26 = new LogoQuizz_Question("logo_rennes","H", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q26);
        LogoQuizz_Question q27 = new LogoQuizz_Question("logo_rennes","H", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q27);
        LogoQuizz_Question q28 = new LogoQuizz_Question("logo_rennes","Nantes", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q28);
        LogoQuizz_Question q29 = new LogoQuizz_Question("logo_rennes","Nantes", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q29);
        LogoQuizz_Question q30 = new LogoQuizz_Question("logo_rennes","Nantes", "Rennes", "Marseille", "Reims", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(q30);
    }

    private void addQuestion(LogoQuizz_Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLONNE_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLONNE_IMAGE, question.getImage());
        cv.put(QuestionTable.COLONNE_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLONNE_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLONNE_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLONNE_OPTION4, question.getOption4());
        cv.put(QuestionTable.COLONNE_NUMERO_REPONSE, question.getNumeroReponse());
        cv.put(QuestionTable.COLONNE_DIFFICULTY, question.getDifficulty());

        //Inserer les valeurs dans la bdd
        db.insert(QuestionTable.TABLE_NAME, null, cv);
    }

    //recuperer les données de la table
    public List<LogoQuizz_Question> getAllQuestion(){
        List<LogoQuizz_Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);
        if(c.moveToFirst()){
            do{
                LogoQuizz_Question question = new LogoQuizz_Question();
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

    //recuperer les données de la table
    public List<LogoQuizz_Question> getQuestion(String difficulty){
        List<LogoQuizz_Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME + " WHERE " + QuestionTable.COLONNE_DIFFICULTY + " = ? " , selectionArgs);
        if(c.moveToFirst()){
            do{
                LogoQuizz_Question question = new LogoQuizz_Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLONNE_QUESTION)));
                question.setImage(c.getString(c.getColumnIndex(QuestionTable.COLONNE_IMAGE)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLONNE_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLONNE_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLONNE_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLONNE_OPTION4)));
                question.setNumeroReponse(c.getInt(c.getColumnIndex(QuestionTable.COLONNE_NUMERO_REPONSE)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionTable.COLONNE_DIFFICULTY)));
                questionList.add(question);
            } while(c.moveToNext());
        }
        c.close();
        return questionList;
    }

}
