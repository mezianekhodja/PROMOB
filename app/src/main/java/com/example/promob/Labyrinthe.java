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
    int carreBlanc = R.drawable.nb2048_0;
    int carreNoir = R.drawable.carrenoir;
    int widthBlock, widthScreen, heightScreen, numberBlocks=10;
    ArrayList<ImageView> carres = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinthe);

        DisplayMetrics displayMetrics =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthScreen = displayMetrics.widthPixels;
        heightScreen = displayMetrics.heightPixels;
        widthBlock = widthScreen / numberBlocks;
        createBoard();
        generationChemin();
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

    private void createBoard() {
        GridLayout gridLayout = findViewById(R.id.boardLB);
        gridLayout.setRowCount(numberBlocks);
        gridLayout.setColumnCount(numberBlocks);
        gridLayout.getLayoutParams().width = widthScreen;
        gridLayout.getLayoutParams().height = heightScreen;
        for (int i = 0; i< numberBlocks*numberBlocks;i++){
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(widthBlock, widthBlock));
            imageView.setMaxHeight(widthBlock);
            imageView.setMaxWidth(widthBlock);
            imageView.setImageResource(carreNoir);
            imageView.setTag(carreNoir);
            carres.add(imageView);
            gridLayout.addView(imageView);
        }
    }
    private void chgmtColor(int i) {
       if(carres.get(i).getTag().equals(carreNoir)){
            carres.get(i).setTag(carreBlanc);
            carres.get(i).setImageResource(carreBlanc);
        }
       else{
           carres.get(i).setTag(carreNoir);
           carres.get(i).setImageResource(carreNoir);
     }
    }

    private void generationChemin() {
       chgmtColor(3);
       chgmtColor(13);
       chgmtColor(23);
       chgmtColor(33);
       chgmtColor(34);
       chgmtColor(35);
       chgmtColor(36);
       chgmtColor(46);
       chgmtColor(56);
        chgmtColor(66);
        chgmtColor(76);
        chgmtColor(86);
        chgmtColor(96);
    }

}
