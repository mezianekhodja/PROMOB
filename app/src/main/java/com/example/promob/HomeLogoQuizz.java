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

public class HomeLogoQuizz extends AppCompatActivity {
    Button btn_logoquizz;
    private FirebaseAuth firebaseAuth;

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

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void openLogoQuizzEasy1() {
        Intent intent = new Intent(this, Quizz_easy.class);
        startActivity(intent);
        HomeLogoQuizz.this.finish();
    }
    public void openLogoQuizzMedium1() {
        Intent intent = new Intent(this, LogoQuizzMedium.class);
        startActivity(intent);
        HomeLogoQuizz.this.finish();
    }
    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        HomeLogoQuizz.this.finish();
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
}
