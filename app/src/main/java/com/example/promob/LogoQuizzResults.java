package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//si jamais on veut finir comme ca
public class LogoQuizzResults extends AppCompatActivity {

    Button btn_suivant;
    TextView textviewa;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_quizz_results);
        score = getIntent().getExtras().getInt("score");

        textviewa = (TextView) findViewById(R.id.textViewresultattest);
        textviewa.setText(Integer.toString(score));

        textviewa = (TextView) findViewById(R.id.textViewcommentairetest);
        if(score<5){
            textviewa.setText("Vous pouvez mieux faire c'est certain !");
        }
        else if(score<8){
            textviewa.setText("Plutôt pas mal! Continuez l'entrainement");
        }
        else{
            textviewa.setText("Vous êtes digne d'entrainer les plus grands! Bravo");
        }

        btn_suivant = (Button) findViewById(R.id.buttonsvtres);
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