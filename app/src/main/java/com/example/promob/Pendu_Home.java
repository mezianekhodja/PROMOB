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

public class Pendu_Home extends AppCompatActivity {
    private Button easy,medium, hard;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendu_home);

        firebaseAuth = FirebaseAuth.getInstance();

        easy = (Button) findViewById(R.id.button_logopendu_easy);
        medium = (Button) findViewById(R.id.button_logopendu_medium);
        hard = (Button) findViewById(R.id.button_logopendu_hard);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pendu_Home.this, Pendu_Activity.class);
                intent.putExtra("level",1);
                intent.putExtra("pathScoreMulti", "notMulti");
                startActivityForResult(intent,1);
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pendu_Home.this, Pendu_Activity.class);
                intent.putExtra("level",2);
                intent.putExtra("pathScoreMulti", "notMulti");
                startActivityForResult(intent,1);
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pendu_Home.this, Pendu_Activity.class);
                intent.putExtra("level",3);
                intent.putExtra("pathScoreMulti", "notMulti");
                startActivityForResult(intent,1);
            }
        });
    }

    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        Pendu_Home.this.finish();
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
        Pendu_Home.this.finish();
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