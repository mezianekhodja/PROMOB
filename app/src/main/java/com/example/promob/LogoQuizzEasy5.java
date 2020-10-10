package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LogoQuizzEasy5 extends AppCompatActivity {

    Button btn_suivant;
    RadioGroup rg;
    RadioButton rb;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_quizz_easy5);
        btn_suivant = (Button) findViewById(R.id.buttonLQE5svt);
        rg = (RadioGroup) findViewById(R.id.radioLQE5);
        btn_suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoQuizzEasy6();
            }
        });
    }
    public void openLogoQuizzEasy6() {
        int id=rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(id);
        score = getIntent().getExtras().getInt("score");
        if(rb.getText().toString().equals("Real")){
            score=score+1;
        }
        Intent intent = new Intent(this, LogoQuizzEasy6.class);
        intent.putExtra("score",score);
        startActivity(intent);
        //Toast.makeText(this,String.valueOf(score),Toast.LENGTH_LONG).show();
    }
}