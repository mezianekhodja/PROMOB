package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {
    Button btn_logoquizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_logoquizz = (Button) findViewById(R.id.button_logoquizz);
        btn_logoquizz.setOnClickListener(new View.OnClickListener() {
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
