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

    //gestion score
    int score = 5000;

    //stylo graphique pour afficher l'image
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //Charger l'image dans une instance de bitmap
    private Bitmap ballBitmap,victoirebitmap,defaitebitmap,scoretableBitmap;

    //Largeur et hauteur de l'image
    private int imageWidth;
    private int imageHeight;

    //position affichage balle
    private int currentX;
    private int currentY;
    //liste de nos rectangles representant le chemin
    private List<Rect> listeChemin = new ArrayList<>();
    //ajustement valeurs en fonction du niveau
    private int centerwidht=520,intervalwidht=100;
    //rectangle arrivée
    private Rect arrivee = new Rect(centerwidht-intervalwidht,10,centerwidht+intervalwidht,50);
    //rectangle départ
    private Rect dep = new Rect(centerwidht-intervalwidht,1270,centerwidht+intervalwidht,1350);

    //booleens représentant la victoire ou la defaite
    private boolean win=false,loose=false;
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
        victoirebitmap = BitmapFactory.decodeResource(getResources(), R.drawable.victoire);
        defaitebitmap =BitmapFactory.decodeResource(getResources(), R.drawable.loose);
        scoretableBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.littlescoretable);
        //On recupere donc son image et sa taille
        this.imageWidth = ballBitmap.getWidth();
        this.imageHeight = ballBitmap.getHeight();

        //w et h sont la taille et hauteur de l'ecran
        currentX =  (w - imageWidth)/2;
        currentY =  1320;

        //création des chemins en fonction des niveaux de difficulté (plusieurs chemins possibles par niveau)
        int level = Labyrinthe.getLevel();
        if (level ==2){
            //chemin niveau moyen
            int random = (int)(Math.random() * 4); //tirage aléatoire parmi nos circuits
            if (random ==1){
                //chemin 1 niveau moyen
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
            }

            else if (random ==2){
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
            }
            else {
                //chemin 3 niveau moyen
                Rect rchemed2A = new Rect(centerwidht-intervalwidht,800,centerwidht+intervalwidht,1350);
                listeChemin.add(rchemed2A);
                Rect rchemed2B = new Rect(centerwidht-intervalwidht,10,centerwidht+intervalwidht,400);
                listeChemin.add(rchemed2B);
                Rect rchemed2C = new Rect(centerwidht-3*intervalwidht,200,centerwidht-intervalwidht,400);
                listeChemin.add(rchemed2C);
                Rect rchemed2D = new Rect(centerwidht-3*intervalwidht,800,centerwidht-intervalwidht,1000);
                listeChemin.add(rchemed2D);
                Rect rchemed2E = new Rect(centerwidht-3*intervalwidht,200,centerwidht-intervalwidht,800);
                listeChemin.add(rchemed2E);
            }
        }
        else if (level ==3){
            //chemins niveau difficile
            int random = (int)(Math.random() * 2); //tirage aléatoire parmi nos circuits
            if (random ==1){
            //chemin niveau difficile 1
            Rect rchemhard1A = new Rect(centerwidht-5*intervalwidht,1150,centerwidht+intervalwidht,1350);
            listeChemin.add(rchemhard1A);
            Rect rchemhard1B = new Rect(centerwidht-5*intervalwidht,800,centerwidht+5*intervalwidht,1000);
            listeChemin.add(rchemhard1B);
            Rect rchemhard1C = new Rect(centerwidht-5*intervalwidht,500,centerwidht+5*intervalwidht,700);
            listeChemin.add(rchemhard1C);
            Rect rchemhard1D = new Rect(centerwidht-5*intervalwidht,250,centerwidht+5*intervalwidht,450);
            listeChemin.add(rchemhard1D);
            Rect rchemhard1E = new Rect(centerwidht-intervalwidht,10,centerwidht+5*intervalwidht,210);
            listeChemin.add(rchemhard1E);
            Rect rchemhard1F = new Rect(centerwidht-5*intervalwidht,800,centerwidht-3*intervalwidht,1350);
            listeChemin.add(rchemhard1F);
            Rect rchemhard1G = new Rect(centerwidht+3*intervalwidht,500,centerwidht+5*intervalwidht,1000);
            listeChemin.add(rchemhard1G);
            Rect rchemhard1H = new Rect(centerwidht-5*intervalwidht,250,centerwidht-3*intervalwidht,700);
            listeChemin.add(rchemhard1H);
            Rect rchemhard1I = new Rect(centerwidht+3*intervalwidht,10,centerwidht+5*intervalwidht,450);
            listeChemin.add(rchemhard1I);
             }
            else{
                //chemin niveau difficile 2
                Rect rchemhard1A = new Rect(centerwidht-intervalwidht,1150,centerwidht+5*intervalwidht,1350);
                listeChemin.add(rchemhard1A);
                Rect rchemhard1B = new Rect(centerwidht-5*intervalwidht,800,centerwidht+5*intervalwidht,1000);
                listeChemin.add(rchemhard1B);
                Rect rchemhard1C = new Rect(centerwidht-5*intervalwidht,500,centerwidht+5*intervalwidht,700);
                listeChemin.add(rchemhard1C);
                Rect rchemhard1D = new Rect(centerwidht-5*intervalwidht,250,centerwidht+5*intervalwidht,450);
                listeChemin.add(rchemhard1D);
                Rect rchemhard1E = new Rect(centerwidht-5*intervalwidht,10,centerwidht+intervalwidht,210);
                listeChemin.add(rchemhard1E);
                Rect rchemhard1F = new Rect(centerwidht+3*intervalwidht,800,centerwidht+5*intervalwidht,1350);
                listeChemin.add(rchemhard1F);
                Rect rchemhard1G = new Rect(centerwidht-5*intervalwidht,500,centerwidht-3*intervalwidht,1000);
                listeChemin.add(rchemhard1G);
                Rect rchemhard1H = new Rect(centerwidht+3*intervalwidht,250,centerwidht+5*intervalwidht,700);
                listeChemin.add(rchemhard1H);
                Rect rchemhard1I = new Rect(centerwidht-5*intervalwidht,10,centerwidht-3*intervalwidht,450);
                listeChemin.add(rchemhard1I);
            }
        }
        else{
            //chemin niveau facile
            Rect rchemeasy1A = new Rect(centerwidht-intervalwidht,10,centerwidht+intervalwidht,1350);
            listeChemin.add(rchemeasy1A);
        }
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

        //affichage score
        canvas.drawBitmap(scoretableBitmap,320,1370,paint);
        paintmodif.setTextSize(60);
        paintmodif.setARGB(255,255,255,255);
        canvas.drawText(String.valueOf(score),460,1500,paintmodif);

        //verification victoire/défaite
        if (!win && !loose){
            //gestion score
            if (score>0){
                score--;
            }

            //vérification timerscore non vide
            else if (score==0){
                loose = true;
            }

            isWin();
            isLoose();
            //affichage chemin
            paintmodif.setARGB(255,255,255,255);
            if (!listeChemin.isEmpty()){
                for(int i = 0 ; i < listeChemin.size(); i++){
                    canvas.drawRect(listeChemin.get(i),paintmodif);
                }
            }

            //affichage arrivée
            paintmodif.setARGB(255,255,0,0);
            canvas.drawRect(arrivee,paintmodif);

            //affichage départ
            paintmodif.setARGB(255,0,255,0);
            canvas.drawRect(dep,paintmodif);

            //affichage bille
            canvas.drawBitmap(ballBitmap, currentX, currentY, paint);
        }
        else if (loose) {
            canvas.drawBitmap(defaitebitmap, 300, 500, paint);
        }
        else {
            canvas.drawBitmap(victoirebitmap, 300, 500, paint);
        }

    }

    @Override
    //Des que le sensor capture une nouvelle info
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];

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

    private void isWin(){
        if((currentX>=arrivee.left)&&(currentX<=arrivee.right)&&(currentY<=arrivee.bottom)
                &&(currentY>=arrivee.top)){
                win= true;
        }
    }
    private void isLoose(){
        boolean trouve=loose;
        if (!listeChemin.isEmpty()){
            for(int i = 0 ; i < listeChemin.size(); i++){
                if((currentX>=listeChemin.get(i).left)&&(currentX<=listeChemin.get(i).right)&&
                        (currentY<=listeChemin.get(i).bottom) &&(currentY>=listeChemin.get(i).top)){
                            trouve=true;
                }
            }
         }
        if (!trouve){
            score=0;
            loose= true;
        }
    }
}
