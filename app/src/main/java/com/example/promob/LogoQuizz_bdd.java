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
       //EASY
        LogoQuizz_Question qE1 = new LogoQuizz_Question("logoquizz_marseille", "Marseille", "Monaco", "Montpellier", "Lyon", 1 , LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE1);
        LogoQuizz_Question qE2 = new LogoQuizz_Question("logoquizz_marseille", "Manchester United", "Monaco", "Marseille", "Paris", 3 , LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE2);
        LogoQuizz_Question qE3 = new LogoQuizz_Question("logoquizz_marseille", "Lille", "Marseille", "Montpellier", "Metz", 2 , LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE3);
        LogoQuizz_Question qE4 = new LogoQuizz_Question("logoquizz_lyon","Lyon", "Marseille", "Lille", "Lorient", 1, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE4);
        LogoQuizz_Question qE5 = new LogoQuizz_Question("logoquizz_lyon","Lille", "Marseille", "Lyon", "Lorient", 3, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE5);
        LogoQuizz_Question qE6 = new LogoQuizz_Question("logoquizz_lyon","Liverpool", "Monaco", "Lille", "Lyon", 4, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE6);
        LogoQuizz_Question qE7 = new LogoQuizz_Question("logoquizz_liverpool","Liverpool", "Leeds", "Chelsea", "Leicester", 1, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE7);
        LogoQuizz_Question qE8 = new LogoQuizz_Question("logoquizz_liverpool","Manchester United", "Leeds", "Liverpool", "Leicester", 3, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE8);
        LogoQuizz_Question qE9 = new LogoQuizz_Question("logoquizz_liverpool","Manchester City", "Liverpool", "Aston Villa", "Leicester", 2, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE9);
        LogoQuizz_Question qE10 = new LogoQuizz_Question("logoquizz_atletico","Atletico", "Bilbao", "Barcelone", "Benfica", 1, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE10);
        LogoQuizz_Question qE11 = new LogoQuizz_Question("logoquizz_atletico","Real", "Betis", "Atletico", "Porto", 3, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE11);
        LogoQuizz_Question qE12 = new LogoQuizz_Question("logoquizz_atletico","Real", "Seville", "Barcelone", "Atletico", 4, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE12);
        LogoQuizz_Question qE13 = new LogoQuizz_Question("logoquizz_mancity","Manchester City", "Manchester United", "Liverpool", "Chelsea", 1, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE13);
        LogoQuizz_Question qE14 = new LogoQuizz_Question("logoquizz_mancity","Manchester United", "Manchester City", "Arsenal", "Chelsea", 2, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE14);
        LogoQuizz_Question qE15 = new LogoQuizz_Question("logoquizz_mancity","Tottenham", "Manchester United", "Liverpool", "Manchester City", 4, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE15);
        LogoQuizz_Question qE16 = new LogoQuizz_Question("logoquizz_manunited","Tottenham", "Manchester United", "Liverpool", "Manchester City", 2, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE16);
        LogoQuizz_Question qE17 = new LogoQuizz_Question("logoquizz_manunited","Tottenham", "Bayern", "Manchester United", "Manchester City", 3, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE17);
        LogoQuizz_Question qE18 = new LogoQuizz_Question("logoquizz_manunited","Tottenham", "Leicester", "Liverpool", "Manchester United", 4, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE18);
        LogoQuizz_Question qE19 = new LogoQuizz_Question("logoquizz_paris","Paris", "Marseille", "Real", "Lyon", 1, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE19);
        LogoQuizz_Question qE20 = new LogoQuizz_Question("logoquizz_paris","Saint-Étienne", "Paris", "Chelsea", "Lyon", 2, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE20);
        LogoQuizz_Question qE21 = new LogoQuizz_Question("logoquizz_paris","Lyon", "Marseille", "Paris", "Juventus", 3, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE21);
        LogoQuizz_Question qE22 = new LogoQuizz_Question("logoquizz_juventus","Milan", "Inter", "Paris", "Juventus", 4, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE22);
        LogoQuizz_Question qE23 = new LogoQuizz_Question("logoquizz_juventus","Juventus", "Inter", "Paris", "Naples", 1, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE23);
        LogoQuizz_Question qE24 = new LogoQuizz_Question("logoquizz_juventus","Milan", "Inter", "Juventus", "Jablonec", 3, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE24);
        LogoQuizz_Question qE25 = new LogoQuizz_Question("logoquizz_chelsea","Chelsea", "Liverpool", "Arsenal", "Cardiff", 1, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE25);
        LogoQuizz_Question qE26 = new LogoQuizz_Question("logoquizz_chelsea","Paris", "Liverpool", "Chelsea", "Cardiff", 3, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE26);
        LogoQuizz_Question qE27 = new LogoQuizz_Question("logoquizz_chelsea","Manchester City", "Liverpool", "Arsenal", "Chelsea", 4, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE27);
        LogoQuizz_Question qE28 = new LogoQuizz_Question("logoquizz_dortmund","Dortmund", "Hanovre", "Bayern", "Chelsea", 1, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE28);
        LogoQuizz_Question qE29 = new LogoQuizz_Question("logoquizz_dortmund","Francfort", "Dortmund", "Bayern", "Chelsea", 2, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE29);
        LogoQuizz_Question qE30 = new LogoQuizz_Question("logoquizz_dortmund","Real", "Hanovre", "Bayern", "Dortmund", 4, LogoQuizz_Question.DIFFICULTY_EASY);
        addQuestion(qE30);

        //MEDIUM
        LogoQuizz_Question qM1 = new LogoQuizz_Question("logoquizz_rennes","Brest", "Rennes", "Lorient", "Nantes", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM1);
        LogoQuizz_Question qM2 = new LogoQuizz_Question("logoquizz_rennes","Rennes", "Guinguamp", "Lorient", "Reims", 1 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM2);
        LogoQuizz_Question qM3 = new LogoQuizz_Question("logoquizz_rennes","Lorient", "Bordeaux", "Nantes", "Rennes", 4 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM3);
        LogoQuizz_Question qM4 = new LogoQuizz_Question("logoquizz_nantes","Nancy", "Rennes", "Nantes", "Nimes", 3 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM4);
        LogoQuizz_Question qM5 = new LogoQuizz_Question("logoquizz_nantes","Nantes", "Nuremberg", "Bordeaux", "Nimes", 1 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM5);
        LogoQuizz_Question qM6 = new LogoQuizz_Question("logoquizz_nantes","Nancy", "Montpellier", "Niort", "Nantes", 4 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM6);
        LogoQuizz_Question qM7 = new LogoQuizz_Question("logoquizz_lorient","Guingamp", "Lorient", "Toulouse", "Lille", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM7);
        LogoQuizz_Question qM8 = new LogoQuizz_Question("logoquizz_lorient","Guingamp", "Rennes", "Brest", "Lorient", 4 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM8);
        LogoQuizz_Question qM9 = new LogoQuizz_Question("logoquizz_lorient","Lorient", "Nantes", "Lens", "Lille", 1 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM9);
        LogoQuizz_Question qM10 = new LogoQuizz_Question("logoquizz_sociedad","Sociedad", "Girona", "Betis", "Boavista", 1 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM10);
        LogoQuizz_Question qM11 = new LogoQuizz_Question("logoquizz_sociedad","Saragosse", "Girona", "Brescia", "Sociedad", 4 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM11);
        LogoQuizz_Question qM12 = new LogoQuizz_Question("logoquizz_sociedad","Saragosse", "Empoli", "Sociedad", "Boavista", 3 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM12);
        LogoQuizz_Question qM13 = new LogoQuizz_Question("logoquizz_moscou","Moscou", "Saint-Petersbourg", "Volgograd", "Kiev", 1 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM13);
        LogoQuizz_Question qM14 = new LogoQuizz_Question("logoquizz_moscou","Cologne", "Saint-Petersbourg", "Moscou", "Kiev", 3 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM14);
        LogoQuizz_Question qM15 = new LogoQuizz_Question("logoquizz_moscou","Spartak", "Saint-Petersbourg", "Volgograd", "Moscou", 4 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM15);
        LogoQuizz_Question qM16 = new LogoQuizz_Question("logoquizz_montpellier","Monaco", "Montpellier", "Marseille", "Toulouse", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM16);
        LogoQuizz_Question qM17 = new LogoQuizz_Question("logoquizz_montpellier","Monaco", "Nimes", "Montpellier", "Toulouse", 3 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM17);
        LogoQuizz_Question qM18 = new LogoQuizz_Question("logoquizz_montpellier","Monaco", "Saint-Étienne", "Marseille", "Montpellier", 4 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM18);
        LogoQuizz_Question qM19 = new LogoQuizz_Question("logoquizz_betis","Betis", "Seville", "Braga", "Benevento", 1 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM19);
        LogoQuizz_Question qM20 = new LogoQuizz_Question("logoquizz_betis","Grenade", "Seville", "Betis", "Benevento", 3 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM20);
        LogoQuizz_Question qM21 = new LogoQuizz_Question("logoquizz_betis","Ibiza", "Bologne", "Braga", "Betis", 4 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM21);
        LogoQuizz_Question qM22 = new LogoQuizz_Question("logoquizz_villareal","Villareal", "Vigo", "Valladolid", "Betis", 1 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM22);
        LogoQuizz_Question qM23 = new LogoQuizz_Question("logoquizz_villareal","Valence", "Vigo", "Valladolid", "Villareal", 4 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM23);
        LogoQuizz_Question qM24 = new LogoQuizz_Question("logoquizz_villareal","Valence", "Villareal", "Valladolid", "Grenade", 2 ,LogoQuizz_Question.DIFFICULTY_MEDIUM);
        addQuestion(qM24);


        //HARD
        LogoQuizz_Question qH1 = new LogoQuizz_Question("logoquizz_ludogorets","Ludogorets", "Linzer", "Sofia", "Iekaterinbourg", 1 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH1);
        LogoQuizz_Question qH2 = new LogoQuizz_Question("logoquizz_ludogorets","Saint-Pétersbourg", "Linzer", "Ludogorets", "Lviv", 3 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH2);
        LogoQuizz_Question qH3 = new LogoQuizz_Question("logoquizz_ludogorets","Lviv", "Ludogorets", "Sofia", "Saint-Pétersbourg", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH3);
        LogoQuizz_Question qH4 = new LogoQuizz_Question("logoquizz_feyenoord","Francfort", "Feyenoord", "Famalicao", "Ferencvaros", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH4);
        LogoQuizz_Question qH5 = new LogoQuizz_Question("logoquizz_feyenoord","Ferencvaros", "Floria", "Famalicao", "Feyenoord", 4 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH5);
        LogoQuizz_Question qH6 = new LogoQuizz_Question("logoquizz_feyenoord","Francfort", "Famalicao", "Feyenoord", "Fulham", 3 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH6);
        LogoQuizz_Question qH7 = new LogoQuizz_Question("logoquizz_dundalk","Aberdeen", "Hamilton", "Cardiff", "Dundalk", 4 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH7);
        LogoQuizz_Question qH8 = new LogoQuizz_Question("logoquizz_dundalk","Dundalk", "Sheffield", "Cardiff", "Burnley", 1 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH8);
        LogoQuizz_Question qH9 = new LogoQuizz_Question("logoquizz_dundalk","Aberdeen", "Dundalk", "Brighton", "Leeds", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH9);
        LogoQuizz_Question qH10 = new LogoQuizz_Question("logoquizz_cluj","Clinceni", "Cluj", "Cracovia", "Lech", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH10);
        LogoQuizz_Question qH11 = new LogoQuizz_Question("logoquizz_cluj","Clinceni", "Cologne", "Cluj", "Jablonec", 3 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH11);
        LogoQuizz_Question qH12 = new LogoQuizz_Question("logoquizz_cluj","Clinceni", "Cologne", "Cracovia", "Cluj", 4 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH12);
        LogoQuizz_Question qH13 = new LogoQuizz_Question("logoquizz_hanovre","Schalke", "Wolverhampton", "Wolfsburg", "Hanovre", 4 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH13);
        LogoQuizz_Question qH14 = new LogoQuizz_Question("logoquizz_hanovre","Schalke", "Hanovre", "Hoffenheim", "Fribourg", 2 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH14);
        LogoQuizz_Question qH15 = new LogoQuizz_Question("logoquizz_hanovre","Hanovre", "Werder", "Wolfsburg", "Stuttgart", 1 ,LogoQuizz_Question.DIFFICULTY_HARD);
        addQuestion(qH15);
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
