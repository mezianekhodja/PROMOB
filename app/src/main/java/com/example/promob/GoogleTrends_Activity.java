package com.example.promob;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class GoogleTrends_Activity extends AppCompatActivity {
    private static final long COUNTDOWN_IN_MILLIS = 15000;

    //GESTION SCORE BDD
    private static final String KEY_USER = "user", KEY_SCORE = "score", KEY_DATE = "date";
    public static final String EXTRA_SCORE = "extraScore";
    String username = "Undefined";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    String currentTime = Calendar.getInstance().getTime().toString();
    private static final String TAG = "GoogleTrends_Activity";
    private int highscore_global = 0, highscore_user = 0;

    //textView
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewTimer;
    private TextView textViewDifficulty;
    private TextView result;

    //RadioButton/Group
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;

    //Reste
    private Button valider;
    private ColorStateList textColorDefaultRb;//Couleur RadioBouton
    private ColorStateList textColorDefaultCb;//Couleur Timer

    private CountDownTimer counterDownTimer;
    private long timeLeftInMillis;

    private int questionCounter;
    private int questionCountTotal;
    private int score;
    private boolean answered;
    public GoogleTrends_Question Q;
    private List<GoogleTrends_Question> questionList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googletrends_activity);
        valider = findViewById(R.id.validergt);
        result = findViewById(R.id.resultgt);
        textViewScore = findViewById(R.id.scoregt);
        textViewQuestionCount = findViewById(R.id.questiongt);
        textViewTimer = findViewById(R.id.timergt);
        textViewDifficulty = findViewById(R.id.difficultégt);
        rbGroup = findViewById(R.id.radio_groupegt);
        rb1 = findViewById(R.id.bouton1gt);
        rb2 = findViewById(R.id.bouton2gt);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCb = textViewTimer.getTextColors();

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(GoogleTrends_Home.EXTRA_DIFFICULTY);
        textViewDifficulty.setText("difficulty: " + difficulty);

        //GESTION SCORE BDD
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                username = userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GoogleTrends_Activity.this, error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        GoogleTrends_bdd dbHelper = new GoogleTrends_bdd(this);
        questionList = dbHelper.getQuestion(difficulty);
        questionCountTotal = 5; //Seulement 5 questions
        Collections.shuffle(questionList);
        showNextQuestion();

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(GoogleTrends_Activity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void checkAnswer() {
        answered = true;
        counterDownTimer.cancel();
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNb = rbGroup.indexOfChild(rbSelected) + 1;
        if (answerNb == Q.getNumeroReponse()) {
            score++;
            textViewScore.setText("Score: " + score);
        }
        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);

        switch (Q.getNumeroReponse()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                result.setText("Answer A is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                result.setText("Answer B is correct");
                break;
        }

        if (questionCounter < questionCountTotal) {
            valider.setText(("Next"));
        } else {
            valider.setText(("Finish"));
        }
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        //GESTION SCORE BDD
        if (questionCounter == 2 && !username.equals("invite")) {
            loadNote();
        }
        if (questionCounter < questionCountTotal) {
            Q = questionList.get((questionCounter));
            rb1.setText(Q.getOption1());
            rb2.setText(Q.getOption2());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishQuizz();
        }
    }

    private void startCountDown() {
        counterDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountdownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewTimer.setText(timeFormatted);
        if (timeLeftInMillis < 5000) {
            textViewTimer.setTextColor(Color.RED);
        } else {
            textViewTimer.setTextColor(textColorDefaultCb);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (counterDownTimer != null) {
            counterDownTimer.cancel();
        }
    }

    private void finishQuizz() {
        if (!username.equals("invite")) {
            saveNote();
        }
        String multipath = getIntent().getStringExtra("pathScoreMulti");
        if (!multipath.equals("notMulti")) {
            firebaseDatabase.getReference(multipath).setValue(String.valueOf(score));
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    //GESTION SCORE BDD
    public void loadNote() {
        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(LogoQuizz_Home.EXTRA_DIFFICULTY);

        db.collection("GoogleTrends_level_" + difficulty).document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String scoreglob = documentSnapshot.get(KEY_SCORE).toString();
                            highscore_global = Integer.parseInt(scoreglob);
                        } else {
                            Toast.makeText(GoogleTrends_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GoogleTrends_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("GoogleTrends_level_" + difficulty).document("highscore_" + username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String scoreus = documentSnapshot.get(KEY_SCORE).toString();
                            highscore_user = Integer.parseInt(scoreus);
                        } else {
                            Toast.makeText(GoogleTrends_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GoogleTrends_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void saveNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_USER, username);
        note.put(KEY_SCORE, score);
        note.put(KEY_DATE, currentTime);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(LogoQuizz_Home.EXTRA_DIFFICULTY);
        if (score > highscore_global) {
            db.collection("GoogleTrends_level_" + difficulty).document("highscore_global").set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(GoogleTrends_Activity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GoogleTrends_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });

        }
        if (score > highscore_user) {
            db.collection("GoogleTrends_level_" + difficulty).document("highscore_" + username).set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(GoogleTrends_Activity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GoogleTrends_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
        }
    }
}


