package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//si jamais on veut finir comme ca
public class LogoQuizzResults extends AppCompatActivity {

    Button btn_suivant;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_quizz_results);
        score = getIntent().getExtras().getInt("score");
        Toast.makeText(this,String.valueOf(score),Toast.LENGTH_LONG).show();
        btn_suivant = (Button) findViewById(R.id.buttonsvteasyres);
        btn_suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeLogoQuizz();
            }
        });
    }
    public void openHomeLogoQuizz() {
        Intent intent = new Intent(this, HomeLogoQuizz.class);
        startActivity(intent);
    }
}