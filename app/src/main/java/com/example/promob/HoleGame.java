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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HoleGame extends View implements SensorEventListener {
    //gestion score
    int score = 0;

    //gestion niveau
    int level;

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

    //définition rectangle de jeu
    Rect rectGame = new Rect(0,0,1100,1500);

    //gestion score bdd
    String username = "Undefined";
    private static final String KEY_USER = "user", KEY_SCORE = "score", KEY_DATE="date";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    String currentTime = Calendar.getInstance().getTime().toString();
    int highscore_global = 0;
    int highscore_user = 0;
    private static final String TAG = "Hole";

    //booleens représentant la victoire ou la defaite
    private boolean win=false,loose=false;
    public HoleGame(Context context){
        super(context);
    }

    public HoleGame(Context context, AttributeSet attrSet){
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

        //récupération records
        highscore_global=Hole.getHsg();
        highscore_user=Hole.getHsp();

        //création des chemins en fonction des niveaux de difficulté (plusieurs chemins possibles par niveau)
        //level = Hole.getLevel();

        }
        //gestion bdd
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
    /*

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                username=userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Hole.getContext(), error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

     */


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
        //canvas.drawText(String.valueOf(score),460,1500,paintmodif);
        canvas.drawText(String.valueOf(score),460,1500,paintmodif);

        //verification victoire/défaite
        if (!loose){

            //test défaite
            testLoose();



            //redéfinition rect
            if ((rectGame.left<rectGame.right) && (rectGame.top<rectGame.bottom)){
                rectGame.top=rectGame.top+1;
                rectGame.bottom=rectGame.bottom-1;
                rectGame.left=rectGame.left+1;
                rectGame.right=rectGame.right-1;

                //incrémentation score
                score++;
            }
            //affichage rect
            paintmodif.setARGB(255,255,255,255);
            canvas.drawRect(rectGame,paintmodif);

            //affichage bille
            canvas.drawBitmap(ballBitmap, currentX, currentY, paint);

        }

        else {
            paintmodif.setTextSize(50);
            canvas.drawText("votre score : "+String.valueOf(score),500,500,paintmodif);
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
    public void testLoose(){
        if ((currentX<rectGame.left)||(currentX>rectGame.right)||(currentY<rectGame.top)
                ||(currentY>rectGame.bottom)){
            loose=true;
        }
    }

    public void saveNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_USER,username);
        note.put(KEY_SCORE,score);
        note.put(KEY_DATE,currentTime);

        if (score>highscore_global){
            db.collection("Hole_level_"+level).document("highscore_global").set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Hole.getContext(), "Sucess", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });

        }
        if (score>highscore_user){
            db.collection("Hole_level_"+level).document("highscore_"+username).set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Hole.getContext(), "Sucess", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
        }

    }
}
