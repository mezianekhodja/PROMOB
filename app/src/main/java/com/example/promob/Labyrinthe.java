package com.example.promob;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Labyrinthe extends AppCompatActivity {

    private static final long COUNTDOWN_IN_MILLIS = 15000;
    private static int niv;
    private LabyrintheGame view;
    private SensorManager mgr;
    private CountDownTimer counterDownTimer;
    private long timeLeftInMillis;
    private TextView textViewTimer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinthe);
        textViewTimer = findViewById(R.id.timer);
        niv= getIntent().getExtras().getInt("level");;

        view =  (LabyrintheGame) findViewById(R.id.view2);
        //Initilisation du sensor
        mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();
    }

    @Override
    //des que la fenetre passe en avant plan
    protected void onResume() {
        super.onResume();
        //enregistrer un listener (view) sur le type de sensor mgr
        mgr.registerListener(view, mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), mgr.SENSOR_DELAY_GAME);
    }

    @Override
    //des que je quitte le premier plan
    protected void onPause() {
        super.onPause();
        //Arreter d'enregistrer le listener de l'accelerometre car fenetre plus en premier plan
        mgr.unregisterListener(view);
    }
    protected static int getLevel(){
        return niv;
    }

    private void startCountDown() {
        counterDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis= 0;
                updateCountdownText();
            }
        }.start();
    }

    private void updateCountdownText(){
        int minutes = (int) (timeLeftInMillis/1000)/60;
        int seconds = (int) (timeLeftInMillis/1000)%60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewTimer.setText(timeFormatted);
        if(timeLeftInMillis<5000){
            textViewTimer.setTextColor(Color.RED);
        }
        else{
            textViewTimer.setTextColor(Color.BLACK);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(counterDownTimer!=null){
            counterDownTimer.cancel();
        }
    }

}
