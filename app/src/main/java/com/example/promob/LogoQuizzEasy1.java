package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LogoQuizzEasy1 extends AppCompatActivity  {
    Button btn_suivant;
    RadioGroup rg;
    RadioButton rb;
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_quizz_easy1);
        btn_suivant = (Button) findViewById(R.id.buttonLQE1svt);
        rg = (RadioGroup) findViewById(R.id.radioLQE1);
        btn_suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoQuizzEasy2();
            }
        });
    }
    public void openLogoQuizzEasy2() {
        int id=rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(id);
        if(rb.getText().toString().equals("Arsenal")){
            score=score+1;
        }
        Intent intent = new Intent(this, LogoQuizzEasy2.class);
        intent.putExtra("score",score);
        startActivity(intent);
        //Toast.makeText(this,String.valueOf(score),Toast.LENGTH_LONG).show();
    }
}
