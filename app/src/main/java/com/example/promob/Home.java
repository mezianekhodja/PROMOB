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

public class Home extends AppCompatActivity {

    Button btn_logoquizz,btn_pendu;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
    }

    public void openHomeLogoQuizz() {
        Intent intent = new Intent(this, HomeLogoQuizz.class);
        startActivity(intent);
        Home.this.finish();
    }
    public void openPendu() {
        Intent intent = new Intent(this, Pendu.class);
        startActivity(intent);
        Home.this.finish();
    }
    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        Home.this.finish();
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
        Home.this.finish();
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