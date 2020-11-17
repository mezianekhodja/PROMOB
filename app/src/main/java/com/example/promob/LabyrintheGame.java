package com.example.promob;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

//Composant grafique (View)
public class LabyrintheGame extends View implements SensorEventListener {

    //stylo graphique pour afficher l'image
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //Charger l'image dans une instance de bitmap
    private Bitmap ballBitmap;

    //Largeur et hauteur de l'image
    private int imageWidth;
    private int imageHeight;

    //position affichage balle
    private int currentX;
    private int currentY;
    //liste de nos rectangles representant le chemin
    private List<Rect> listeChemin = new ArrayList<>();
    //ajustement valeurs en fonction du niveau
    int centerwidht=520,intervalwidht=100;

    public LabyrintheGame(Context context){
        super(context);
    }

    public LabyrintheGame(Context context, AttributeSet attrSet){
        super(context, attrSet);
    }


    @Override
    //Methode appelé a chaque changement de taille constaté
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //decoder l'image
        ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.balle);
        //On recupere donc son image et sa taille
        this.imageWidth = ballBitmap.getWidth();
        this.imageHeight = ballBitmap.getHeight();

        //w et h sont la taille et hauteur de l'ecran
        currentX =  (w - imageWidth)/2;
        currentY =  (h - imageHeight);

        //création des chemins en fonction des niveaux de difficulté (plusieurs chemins possibles par niveau)
        //chemin niveau facile
        Rect rchemeasy1A = new Rect(centerwidht-intervalwidht,10,centerwidht+intervalwidht,1350);
        //listeChemin.add(rchemeasy1);

        //chemin niveau moyen
        //chemin 1 niveau moyen
        /*
        Rect rchemed1A = new Rect(centerwidht-intervalwidht,1000,centerwidht+intervalwidht,1350);
        listeChemin.add(rchemed1A);
        Rect rchemed1B = new Rect(centerwidht-intervalwidht,10,centerwidht+intervalwidht,700);
        listeChemin.add(rchemed1B);
        Rect rchemed1C = new Rect(centerwidht-intervalwidht,500,centerwidht+3*intervalwidht,700);
        listeChemin.add(rchemed1C);
        Rect rchemed1D = new Rect(centerwidht-intervalwidht,1000,centerwidht+3*intervalwidht,1200);
        listeChemin.add(rchemed1D);
        Rect rchemed1E = new Rect(centerwidht+intervalwidht,500,centerwidht+3*intervalwidht,1200);
        listeChemin.add(rchemed1E);
         */

        //chemin 2 niveau moyen
        Rect rchemed2A = new Rect(centerwidht-intervalwidht,1000,centerwidht+intervalwidht,1350);
        listeChemin.add(rchemed2A);
        Rect rchemed2B = new Rect(centerwidht-intervalwidht,10,centerwidht+intervalwidht,700);
        listeChemin.add(rchemed2B);
        Rect rchemed2C = new Rect(centerwidht-3*intervalwidht,500,centerwidht-intervalwidht,700);
        listeChemin.add(rchemed2C);
        Rect rchemed2D = new Rect(centerwidht-3*intervalwidht,1000,centerwidht-intervalwidht,1200);
        listeChemin.add(rchemed2D);
        Rect rchemed2E = new Rect(centerwidht-3*intervalwidht,500,centerwidht-intervalwidht,1200);
        listeChemin.add(rchemed2E);

        //chemin niveau difficile
    }


    @Override
    //appele a chaque fois que l'ecran devra se re actualiser
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //reglage couleurs arrivees
        Paint paintmodif = new Paint();

        //affichage delimitation background
        paintmodif.setARGB(255,0,0,0);
        canvas.drawRect(0,0,1100,10,paintmodif);

        //affichage chemin
        paintmodif.setARGB(255,255,255,255);
        if (!listeChemin.isEmpty()){
            for(int i = 0 ; i < listeChemin.size(); i++){
                canvas.drawRect(listeChemin.get(i),paintmodif);
            }
        }

        //affichage arrivée
        paintmodif.setARGB(255,255,0,0);
        canvas.drawRect(centerwidht-intervalwidht,10,centerwidht+intervalwidht,50,paintmodif);

        //affichage départ
        paintmodif.setARGB(255,0,255,0);
        canvas.drawRect(centerwidht-intervalwidht,1270,centerwidht+intervalwidht,1320,paintmodif);

        //affichage bille
        canvas.drawBitmap(ballBitmap, currentX, currentY, paint);
    }

    @Override
    //Des que le sensor capture une nouvelle info
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        //float z = sensorEvent.values[2];
        this.moveImage(-x*2,y*2);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    //deplacer la balle
    private void  moveImage(float x, float y){
        currentX += (int) x;
        currentY += (int) y;

        if(currentX<0){
            currentX=0;
        } else if(this.currentX + imageWidth > getWidth()){
            currentX = getWidth() - imageWidth;
        }

        if(currentY<0){
            currentY=0;
        } else if(this.currentY + imageHeight > getHeight()){
            currentY = getHeight() - imageHeight;
        }

        this.invalidate();
    }

    private void genereGrille(){

    }
}
