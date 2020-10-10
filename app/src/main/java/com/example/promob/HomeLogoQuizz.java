package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeLogoQuizz extends AppCompatActivity {
    Button btn_logoquizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_logo_quizz);

        btn_logoquizz = (Button) findViewById(R.id.button_logoquizz_easy);
        btn_logoquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoQuizzEasy1();
            }
        });

        btn_logoquizz = (Button) findViewById(R.id.button_logoquizz_medium);
        btn_logoquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoQuizzMedium1();
            }
        });
    }

    public void openLogoQuizzEasy1() {
        Intent intent = new Intent(this, LogoQuizzEasy1.class);
        startActivity(intent);
    }
    public void openLogoQuizzMedium1() {
        Intent intent = new Intent(this, LogoQuizzMedium1.class);
        startActivity(intent);
    }
}
