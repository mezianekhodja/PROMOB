package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class CandyCrush_Home extends AppCompatActivity {

    private Button easy,medium, hard;
    private FirebaseAuth firebaseAuth;
    private TextView textViewHighscoreEasy,textViewHighscoreMedium, textViewHighscoreHard;

    private int highscore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candy_crush__home);

        firebaseAuth = FirebaseAuth.getInstance();

        easy = (Button) findViewById(R.id.button_cc_easy);
        medium = (Button) findViewById(R.id.button_cc_medium);
        hard = (Button) findViewById(R.id.button_cc_hard);

        textViewHighscoreEasy = (TextView) findViewById(R.id.tvcchighscoreeasy);
        textViewHighscoreMedium = (TextView) findViewById(R.id.tvcchighscoremedium);
        textViewHighscoreHard = (TextView) findViewById(R.id.tvcchighscorehard);

        loadHighscore();


        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CandyCrush_Home.this, CandyCrush.class);
                intent.putExtra("level",1);
                startActivity(intent);
                CandyCrush_Home.this.finish();
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CandyCrush_Home.this, CandyCrush.class);
                intent.putExtra("level",2);
                startActivity(intent);
                CandyCrush_Home.this.finish();
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CandyCrush_Home.this, CandyCrush.class);
                intent.putExtra("level",3);
                startActivity(intent);
                CandyCrush_Home.this.finish();
            }
        });
    }

    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        CandyCrush_Home.this.finish();
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
        CandyCrush_Home.this.finish();
    }

    public void openAcceuil(){
        finish();
        Intent intent = new Intent(this, Entrainement.class);
        startActivity(intent);
    }
    public void openClassement(){
        finish();
        Intent intent = new Intent(this, Classement.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.profileMenu:{
                openProfil();
                break;
            }
            case R.id.acceuilMenu:{
                openAcceuil();
                break;
            }
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.classementMenu:{
                openClassement();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadHighscore(){
        textViewHighscoreEasy.setText("Highscore = " + highscore);
    }

    private void upadteHighscore(int newHighscore) {
        highscore = newHighscore;
        textViewHighscoreEasy.setText("Highscore = " + highscore);

    }
}