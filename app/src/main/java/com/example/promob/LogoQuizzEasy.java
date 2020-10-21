package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;

public class LogoQuizzEasy extends AppCompatActivity {

    Button btn_suivant;
    RadioGroup rg;
    RadioButton rb;
    int score=0;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_quizz_easy);
        btn_suivant = (Button) findViewById(R.id.buttonLQEsvt);
        rg = (RadioGroup) findViewById(R.id.radioLQE);
        btn_suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoQuizzResults();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void openLogoQuizzResults() {
        int id=rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(id);
        if(rb.getText().toString().equals("Arsenal")){
            score=score+1;
        }
        Intent intent = new Intent(this, LogoQuizzResults.class);
        intent.putExtra("score",score);
        startActivity(intent);
        LogoQuizzEasy.this.finish();
    }
    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        LogoQuizzEasy.this.finish();
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
        LogoQuizzEasy.this.finish();
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