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

//si jamais on veut finir comme ca
public class LogoQuizzResults extends AppCompatActivity {

    Button btn_suivant;
    TextView textviewa;
    int score;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoquizz_results);
        score = getIntent().getExtras().getInt("score");

        textviewa = (TextView) findViewById(R.id.textViewresultattest);
        textviewa.setText(Integer.toString(score));

        textviewa = (TextView) findViewById(R.id.textViewcommentairetest);
        if(score<2){
            textviewa.setText("Vous pouvez mieux faire c'est certain !");
        }
        else if(score<5){
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

        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void openHomeLogoQuizz() {
        Intent intent = new Intent(this, LogoQuizz_Home.class);
        startActivity(intent);
        LogoQuizzResults.this.finish();
    }
    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        LogoQuizzResults.this.finish();
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
        LogoQuizzResults.this.finish();
    }

    public void openAcceuil(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        LogoQuizzResults.this.finish();
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
}