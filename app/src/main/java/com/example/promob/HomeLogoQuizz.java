package com.example.promob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeLogoQuizz extends AppCompatActivity {
    Button btn_logoquizz;
    private FirebaseAuth firebaseAuth;
    private static final int REQUEST_CODE_QUIZ =1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE= "keyHighscore";
    private TextView textViewHighscoreEasy;
    private TextView textViewHighscoreMedium;
    private TextView textViewHighscoreHard;

    private int highscore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_logo_quizz);

        textViewHighscoreEasy = findViewById(R.id.LogoQuizzHighscoreEasy);
        textViewHighscoreMedium = findViewById(R.id.LogoQuizzHighscoreMedium);
        textViewHighscoreHard = findViewById(R.id.LogoQuizzHighscoreHard);

        loadHighscore();



        btn_logoquizz = (Button) findViewById(R.id.button_logoquizz_easy);
        btn_logoquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoQuizzEasy1();
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void openLogoQuizzEasy1() {
        Intent intent = new Intent(this, Quiz_Activity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ); //Avoir un resultat d'une autre activité
    }
    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
    }
    private void Logout() {
        firebaseAuth.signOut();
        openActivityConnexion();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void openProfil() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        HomeLogoQuizz.this.finish();
    }

    public void openAcceuil(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        HomeLogoQuizz.this.finish();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.profileMenu:{
                openProfil();
                break;
            }
            case R.id.acceuilMenu:{
                openAcceuil();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_QUIZ){
            if(resultCode==RESULT_OK){
                int score = data.getIntExtra(Quiz_Activity.EXTRA_SCORE, 0);
                if(score > highscore){
                    upadteHighscore(score);
                }
            }

        }
    }

    private void loadHighscore(){
        textViewHighscoreEasy.setText("Highscore: " + highscore);
    }

    private void upadteHighscore(int newHighscore) {
        highscore = newHighscore;
        textViewHighscoreEasy.setText("Highscore: " + highscore);
    }
}
