package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BandeSon extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button run1,run2,run3,run4,run5,run6,run7,stop1,stop2,stop3,stop4,stop5,stop6,stop7;
    MediaPlayer play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bande_son);
        firebaseAuth = FirebaseAuth.getInstance();
        run1 = findViewById(R.id.buttonRunMusique1);
        run1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusique1();
            }
        });
        run2 = findViewById(R.id.buttonRunMusique2);
        run2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusique2();
            }
        });
        run3 = findViewById(R.id.buttonRunMusique3);
        run3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusique3();
            }
        });
        run4 = findViewById(R.id.buttonRunMusique4);
        run4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusique4();
            }
        });
        run5 = findViewById(R.id.buttonRunMusique5);
        run5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusique5();
            }
        });
        run6 = findViewById(R.id.buttonRunMusique6);
        run6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusique6();
            }
        });
        run7 = findViewById(R.id.buttonRunMusique7);
        run7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusique7();
            }
        });
        stop1 = findViewById(R.id.buttonStopMusique1);
        stop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });
        stop2 = findViewById(R.id.buttonStopMusique2);
        stop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });
        stop3 = findViewById(R.id.buttonStopMusique3);
        stop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });
        stop4 = findViewById(R.id.buttonStopMusique4);
        stop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });
        stop5 = findViewById(R.id.buttonStopMusique5);
        stop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });
        stop6 = findViewById(R.id.buttonStopMusique6);
        stop6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });
        stop7 = findViewById(R.id.buttonStopMusique7);
        stop7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });

    }
    public void stopMusic(){
        play.stop();
        play = null;
        Toast.makeText(this, "Musique arrêtée", Toast.LENGTH_SHORT).show();
    }
    public void startMusique1(){
        if(play == null){
            play = MediaPlayer.create(this, R.raw.musique1);
            Toast.makeText(this, "Musique lancée", Toast.LENGTH_SHORT).show();
        }
        play.start();
    }
    public void startMusique2(){
        if(play == null){
            play = MediaPlayer.create(this, R.raw.musique2);
            Toast.makeText(this, "Musique lancée", Toast.LENGTH_SHORT).show();
        }
        play.start();
    }public void startMusique3(){
        if(play == null){
            play = MediaPlayer.create(this, R.raw.musique3);
            Toast.makeText(this, "Musique lancée", Toast.LENGTH_SHORT).show();
        }
        play.start();
    }public void startMusique4(){
        if(play == null){
            play = MediaPlayer.create(this, R.raw.musique4);
            Toast.makeText(this, "Musique lancée", Toast.LENGTH_SHORT).show();
        }
        play.start();
    }public void startMusique5(){
        if(play == null){
            play = MediaPlayer.create(this, R.raw.musique5);
            Toast.makeText(this, "Musique lancée", Toast.LENGTH_SHORT).show();
        }
        play.start();
    }public void startMusique6(){
        if(play == null){
            play = MediaPlayer.create(this, R.raw.musique6);
            Toast.makeText(this, "Musique lancée", Toast.LENGTH_SHORT).show();
        }
        play.start();
    }public void startMusique7(){
        if(play == null){
            play = MediaPlayer.create(this, R.raw.musique7);
            Toast.makeText(this, "Musique lancée", Toast.LENGTH_SHORT).show();
        }
        play.start();
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
    public void openBandeSon(){
        finish();
        Intent intent = new Intent(this, BandeSon.class);
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
            case R.id.bandesonMenu:{
                openBandeSon();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}