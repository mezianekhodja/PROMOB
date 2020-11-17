package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Entrainement extends AppCompatActivity {

    Button btn_logoquizz,btn_pendu,btn_cc,btn_nb,btn_laby;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_jeux);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_logoquizz = (Button) findViewById(R.id.button_logoquizz);
        btn_logoquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeLogoQuizz();
            }
        });
        btn_pendu = (Button) findViewById(R.id.buttongamependu);
        btn_pendu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPendu();
            }
        });
        btn_cc = (Button) findViewById(R.id.buttongamecandycrush);
        btn_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCC();
            }
        });
        btn_nb = (Button) findViewById(R.id.buttongame2048);
        btn_nb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNumbers();
            }
        });
        btn_laby = (Button) findViewById(R.id.btn_laby);
        btn_laby.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openLabyrithe();
            }
        });
    }

    public void openHomeLogoQuizz() {
        Intent intent = new Intent(this, LogoQuizz_Home.class);
        startActivity(intent);
        Entrainement.this.finish();
    }
    public void openCC() {
        Intent intent = new Intent(this, CandyCrush_Home.class);
        startActivity(intent);
        Entrainement.this.finish();
    }
    public void openPendu() {
        Intent intent = new Intent(this, Pendu_Home.class);
        startActivity(intent);
        Entrainement.this.finish();
    }
    public void openNumbers() {
        Intent intent = new Intent(this, Numbers_Home.class);
        startActivity(intent);
        Entrainement.this.finish();
    }
    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        Entrainement.this.finish();
    }
    public void openLabyrithe() {
        Intent intent = new Intent(this, LabyrintheHome.class);
        startActivity(intent);
        Entrainement.this.finish();
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
        Entrainement.this.finish();
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
}