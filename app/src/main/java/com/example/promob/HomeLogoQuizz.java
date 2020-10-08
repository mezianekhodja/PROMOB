package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeLogoQuizz extends AppCompatActivity {
    Button btn_logoquizz_easy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_logo_quizz);
        btn_logoquizz_easy = (Button) findViewById(R.id.button_logoquizz_easy);
        btn_logoquizz_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoQuizzEasy1();
            }
        });
    }

    public void openLogoQuizzEasy1() {
        Intent intent = new Intent(this, LogoQuizzEasy1.class);
        startActivity(intent);
    }
}
