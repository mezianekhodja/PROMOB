package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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
import java.util.Calendar;


public class Hole_Home extends AppCompatActivity {

    private Button easy,medium, hard,load1,load2,load3;

    //gestion score bdd
    String username = "Undefined";
    private static final String KEY_USER = "user", KEY_SCORE = "score", KEY_DATE="date";
    private int hsg1=0,hsg2=0,hsg3=0,hsp1=0,hsp2=0,hsp3=0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    String currentTime = Calendar.getInstance().getTime().toString();
    private static final String TAG = "Hole_Home";
    private boolean v1=false,v2=false,v3=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole__home);

        easy = (Button) findViewById(R.id.button_ho_easy);
        medium = (Button) findViewById(R.id.button_ho_medium);
        hard = (Button) findViewById(R.id.button_ho_hard);
        load1 = (Button) findViewById(R.id.button_ho_loadHS1);
        load2 = (Button) findViewById(R.id.button_ho_loadHS2);
        load3 = (Button) findViewById(R.id.button_ho_loadHS3);

        //gestion bdd
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

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

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v1){
                    Intent intent = new Intent(Hole_Home.this, Hole.class);
                    intent.putExtra("hsp",hsp1);
                    intent.putExtra("hsg",hsg1);
                    intent.putExtra("level",1);
                    startActivity(intent);
                    Hole_Home.this.finish();
                }
                else {
                    Toast.makeText(Hole_Home.this, "Validez le bouton", Toast.LENGTH_SHORT).show();
                }
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v2){
                    Intent intent = new Intent(Hole_Home.this, Hole.class);
                    intent.putExtra("level",2);
                    intent.putExtra("hsp",hsp2);
                    intent.putExtra("hsg",hsg2);
                    startActivity(intent);
                    Hole_Home.this.finish();
                }
                else {
                    Toast.makeText(Hole_Home.this, "Validez le bouton", Toast.LENGTH_SHORT).show();
                }
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v3){
                    Intent intent = new Intent(Hole_Home.this, Hole.class);
                    intent.putExtra("level",3);
                    intent.putExtra("hsp",hsp3);
                    intent.putExtra("hsg",hsg3);
                    startActivity(intent);
                    Hole_Home.this.finish();
                }
                else {
                    Toast.makeText(Hole_Home.this, "Validez le bouton", Toast.LENGTH_SHORT).show();
                }
            }
        });
        load1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v1=true;
                //loadNote1();
            }
        });
        load2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v2=true;
                //loadNote2();
            }
        });
        load3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v3=true;
                //loadNote3();
            }
        });

    }
    public void loadNote1(){
        db.collection("Hole_level_"+1).document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreglob = documentSnapshot.get(KEY_SCORE).toString();
                            hsg1 =Integer.parseInt(scoreglob);
                        }
                        else{
                            Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Hole_level_"+1).document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreus = documentSnapshot.get(KEY_SCORE).toString();
                            hsp1=Integer.parseInt(scoreus);                        }
                        else{
                            Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
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
    public void loadNote2(){
        db.collection("Hole_level_"+2).document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreglob2 = documentSnapshot.get(KEY_SCORE).toString();
                            hsg2=Integer.parseInt(scoreglob2);
                        }
                        else{
                            Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Hole_level_"+2).document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreus2 = documentSnapshot.get(KEY_SCORE).toString();
                            hsp2=Integer.parseInt(scoreus2);                        }
                        else{
                            Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
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
    public void loadNote3(){
        db.collection("Hole_level_"+3).document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreglob3 = documentSnapshot.get(KEY_SCORE).toString();
                            hsg3=Integer.parseInt(scoreglob3);
                        }
                        else{
                            Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Hole_level_"+3).document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreus3 = documentSnapshot.get(KEY_SCORE).toString();
                            hsp3=Integer.parseInt(scoreus3);                        }
                        else{
                            Toast.makeText(Hole.getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
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