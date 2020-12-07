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

public class Multi_Tournoi extends AppCompatActivity {
    private Button defi1V1,tournoi;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi__tournoi);
        tournoi =(Button)findViewById(R.id.btn_tournoi);
        defi1V1 = (Button)findViewById(R.id.btn_multijoueur);

        defi1V1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDefi1V1();
            }
        });
        tournoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTournoiRooms();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void openDefi1V1() {
        Intent intent = new Intent(this, Multi.class);
        startActivity(intent);
        Multi_Tournoi.this.finish();
    }
    public void openTournoiRooms() {
        Intent intent = new Intent(this, TournoiRooms.class);
        startActivity(intent);
        Multi_Tournoi.this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void openProfil() {
        finish();
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
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
    public void openTrophy(){
        finish();
        Intent intent = new Intent(this, Trophy.class);
        startActivity(intent);
    }
    public void openCopyright(){
        finish();
        Intent intent = new Intent(this, Copyright.class);
        startActivity(intent);
    }
    public void openActivityConnexion() {
        finish();
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
    }
    private void Logout() {
        firebaseAuth.signOut();
        openActivityConnexion();
    }
    public void openSoloMulti(){
        finish();
        Intent intent = new Intent(this, Solo_Multi.class);
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
            case R.id.trophyMenu:{
                openTrophy();
                break;
            }
            case R.id.copyrightMenu:{
                openCopyright();
                break;
            }
            case R.id.solomultiMenu:{
                openSoloMulti();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}