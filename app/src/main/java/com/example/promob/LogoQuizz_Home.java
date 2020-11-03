package com.example.promob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LogoQuizz_Home extends AppCompatActivity {
    Button btn_logoquizz;
    private FirebaseAuth firebaseAuth;
    private static final int REQUEST_CODE_QUIZ =1;
    public static String EXTRA_DIFFICULTY = "extraDifficulty";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE= "keyHighscore";
    private TextView textViewHighscoreEasy,textViewHighscoreMedium, textViewHighscoreHard;

    private int highscore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoquizz_home);

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

        btn_logoquizz = (Button) findViewById(R.id.button_logoquizz_medium);
        btn_logoquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoQuizzMedium1();
            }
        });
        btn_logoquizz = (Button) findViewById(R.id.button_logoquizz_hard);
        btn_logoquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoQuizzHard1();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void openLogoQuizzEasy1() {
        //EXTRA_DIFFICULTY = "Easy";
        Intent intent = new Intent(this, LogoQuizz_Activity.class);
        intent.putExtra(EXTRA_DIFFICULTY, "Easy");
        startActivityForResult(intent, REQUEST_CODE_QUIZ); //Avoir un resultat d'une autre activité
    }
    public void openLogoQuizzMedium1() {
       // EXTRA_DIFFICULTY = "Medium";
        Intent intent = new Intent(this, LogoQuizz_Activity.class);
        intent.putExtra(EXTRA_DIFFICULTY, "Medium");
        startActivityForResult(intent, REQUEST_CODE_QUIZ); //Avoir un resultat d'une autre activité
    }

    public void openLogoQuizzHard1() {
        //EXTRA_DIFFICULTY = "Hard";
        Intent intent = new Intent(this, LogoQuizz_Activity.class);
        intent.putExtra(EXTRA_DIFFICULTY, "Hard");
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
        LogoQuizz_Home.this.finish();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
            }
            case R.id.profileMenu:{
                openProfil();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_QUIZ){
            if(resultCode==RESULT_OK){
                int score = data.getIntExtra(LogoQuizz_Activity.EXTRA_SCORE, 0);
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


