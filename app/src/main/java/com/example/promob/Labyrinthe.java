package com.example.promob;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Labyrinthe extends AppCompatActivity {

    private LabyrintheGame view;
    private SensorManager mgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinthe);

        view =  (LabyrintheGame) findViewById(R.id.view2);
        //Initilisation du sensor
        mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
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
}
