package com.example.promob;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class Quiz_Activity extends AppCompatActivity {
    //textView
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewTimer;
    private ImageView ImageView;

    //RadioButton/Group
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;

    //Reste
    private Button buttonConfirmeNext;
    private ColorStateList textColorDefaultRb;
    private int questionCounter;
    private int questionCountTotal;
    private QuizQuestion currentQuestion;
    private int score;
    private boolean answered;
    private List<QuizQuestion> questionList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ImageView = findViewById(R.id.logo);
        textViewQuestion = findViewById(R.id.question);
        textViewScore = findViewById(R.id.score);
        textViewQuestionCount = findViewById(R.id.nombre_question);
        textViewTimer =findViewById(R.id.timer);
        rbGroup = findViewById(R.id.radio_groupe);
        rb1 = findViewById(R.id.radio_bouton);
        rb2 = findViewById(R.id.radio_bouton2);
        rb3 = findViewById(R.id.radio_bouton3);
        rb4 = findViewById(R.id.radio_bouton4);

        buttonConfirmeNext = findViewById(R.id.confirmer);

        textColorDefaultRb = rb1.getTextColors();

        Quiz_bdd dbHelper = new Quiz_bdd(this);
        questionList = dbHelper.getAllQuestion();
        questionCountTotal = 5;
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
                        Toast.makeText(Quiz_Activity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    showNextQuestion();
                }
            }
        });

    }

    private void showNextQuestion(){
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

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
        }
        else{
            finishQuizz();
        }
    }

    private void checkAnswer(){
        answered = true;
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

    private void finishQuizz(){
        Intent intent = new Intent(this, LogoQuizzResults.class);
        intent.putExtra("score",score);
        startActivity(intent);
    }
}

