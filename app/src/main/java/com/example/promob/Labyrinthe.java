package com.example.promob;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;



public class Labyrinthe extends AppCompatActivity {

    private static int niv,hsp,hsg;
    private static String multipath;
    private LabyrintheGame view;
    private SensorManager mgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinthe);
        niv= getIntent().getExtras().getInt("level");
        hsp= getIntent().getExtras().getInt("hsp");
        hsg= getIntent().getExtras().getInt("hsg");
        multipath = getIntent().getStringExtra("pathScoreMulti");
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
    protected static int getLevel(){
        return niv;
    }
    protected static String getMultiPath(){
        return multipath;
    }
    protected static int getHsp(){
        return hsp;
    }
    protected static int getHsg(){
        return hsg;
    }
    protected static Context getContext(){
        return getContext();
    }
}
