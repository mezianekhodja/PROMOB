package com.example.promob;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LogoQuizz_Activity extends AppCompatActivity {


    /*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                      PARTIE DECLARATION VARIABLES

    -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 15000;

    //GESTION SCORE BDD
    private static final String KEY_USER = "user", KEY_SCORE = "score", KEY_DATE="date";
    String username = "Undefined";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    String currentTime = Calendar.getInstance().getTime().toString();
    int highscore_global = 0;
    int highscore_user = 0;
    private static final String TAG = "LogoQuizz_Activity";

    //textView
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewTimer;
    private TextView textViewDifficulty;
    private ImageView ImageView;

    //RadioButton/Group
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;

    //Reste
    private Button buttonConfirmeNext;
    private ColorStateList textColorDefaultRb;//Couleur RadioBouton
    private ColorStateList textColorDefaultCb;//Couleur Timer

    private CountDownTimer counterDownTimer;
    private long timeLeftInMillis;

    private int questionCounter;
    private int questionCountTotal;
    private LogoQuizz_Question currentQuestion;
    private int score;
    private boolean answered;
    private List<LogoQuizz_Question> questionList;



    /*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                      PARTIE ONCREAT()

    -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoquiz);

        ImageView = findViewById(R.id.logo);
        textViewQuestion = findViewById(R.id.question);
        textViewScore = findViewById(R.id.score);
        textViewQuestionCount = findViewById(R.id.nombre_question);
        textViewTimer =findViewById(R.id.timer);
        textViewDifficulty=findViewById(R.id.difficulty);
        rbGroup = findViewById(R.id.radio_groupe);
        rb1 = findViewById(R.id.radio_bouton);
        rb2 = findViewById(R.id.radio_bouton2);
        rb3 = findViewById(R.id.radio_bouton3);
        rb4 = findViewById(R.id.radio_bouton4);
        buttonConfirmeNext = findViewById(R.id.confirmer);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCb = textViewTimer.getTextColors();

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(LogoQuizz_Home.EXTRA_DIFFICULTY);
        textViewDifficulty.setText("difficulty: " + difficulty);

        //GESTION SCORE BDD
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                username=userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LogoQuizz_Activity.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });


        LogoQuizz_bdd dbHelper = new LogoQuizz_bdd(this);
        questionList = dbHelper.getQuestion(difficulty);
        questionCountTotal = 5; //Seulement 5 questions
        Collections.shuffle(questionList);

        showNextQuestion();

        buttonConfirmeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(LogoQuizz_Activity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    showNextQuestion();
                }
            }
        });

    }

    /*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                      PARTIE VERIFICATION QUESTION + PROCHAINE QUESTION

    -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    private void checkAnswer(){
        answered = true;
        counterDownTimer.cancel();
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNb = rbGroup.indexOfChild(rbSelected) + 1;

        if(answerNb == currentQuestion.getNumeroReponse()){
            score++;
            textViewScore.setText("Score: " + score);
        }

        showSolution();
    }

    private void showSolution(){
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        switch (currentQuestion.getNumeroReponse()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer A is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer B is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer C is correct");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer D is correct");
                break;
        }

        if(questionCounter<questionCountTotal){
            buttonConfirmeNext.setText(("Next"));
        }
        else{
            buttonConfirmeNext.setText(("Finish"));
        }
    }

    private void showNextQuestion(){
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        //GESTION SCORE BDD
        if(questionCounter ==2 && !username.equals("invite")){
                loadNote();
        }
        if(questionCounter < questionCountTotal){
            currentQuestion = questionList.get((questionCounter));
            //PASSER L'IMAGE EN INT
            String mDrawableName = currentQuestion.getImage();
            int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
            ImageView.setImageResource(resID);
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmeNext.setText("confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        }
        else{
            finishQuizz();
        }
    }


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                      PARTIE TIMER

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    private void startCountDown() {
        counterDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis= 0;
                updateCountdownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountdownText(){
        int minutes = (int) (timeLeftInMillis/1000)/60;
        int seconds = (int) (timeLeftInMillis/1000)%60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewTimer.setText(timeFormatted);
        if(timeLeftInMillis<5000){
            textViewTimer.setTextColor(Color.RED);
        }
        else{
            textViewTimer.setTextColor(textColorDefaultCb);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(counterDownTimer!=null){
            counterDownTimer.cancel();
        }
    }

    /*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                      PARTIE FIN DE L'ACTIVITE

    -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/



    private void finishQuizz(){
        if(!username.equals("invite")){
            saveNote();
        }
        String multipath = getIntent().getStringExtra("pathScoreMulti");
        if (!multipath.equals("notMulti")){
            firebaseDatabase.getReference(multipath).setValue(String.valueOf(score));
            firebaseDatabase.getReference(getIntent().getStringExtra("pathMessageMulti")).setValue("Score transmis");
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    //GESTION SCORE BDD
    public void loadNote(){
        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(LogoQuizz_Home.EXTRA_DIFFICULTY);

        db.collection("LogoQuizz_level_"+difficulty).document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreglob = documentSnapshot.get(KEY_SCORE).toString();
                            highscore_global=Integer.parseInt(scoreglob);
                        }
                        else{
                            Toast.makeText(LogoQuizz_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LogoQuizz_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("LogoQuizz_level_"+difficulty).document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreus = documentSnapshot.get(KEY_SCORE).toString();
                            highscore_user=Integer.parseInt(scoreus);                        }
                        else{
                            Toast.makeText(LogoQuizz_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LogoQuizz_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void saveNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_USER,username);
        note.put(KEY_SCORE,score);
        note.put(KEY_DATE,currentTime);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(LogoQuizz_Home.EXTRA_DIFFICULTY);

        if (score>highscore_global){
            db.collection("LogoQuizz_level_"+difficulty).document("highscore_global").set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(LogoQuizz_Activity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LogoQuizz_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });

        }
        if (score>highscore_user){
            db.collection("LogoQuizz_level_"+difficulty).document("highscore_"+username).set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(LogoQuizz_Activity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LogoQuizz_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
        }
    }

}

