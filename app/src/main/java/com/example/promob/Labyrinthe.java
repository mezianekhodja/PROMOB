package com.example.promob;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Labyrinthe extends AppCompatActivity {

    private static int niv,hsp,hsg,score = 5000;
    private static String multipath;
    private LabyrintheGame view;
    private SensorManager mgr;
    private static boolean termine=false;
    private FirebaseDatabase firebaseDatabase;
    private Button btnfin;
    private String username ="";
    private static final String TAG = "Labyrinthe";
    private static final String KEY_t11 = "trophy11";
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinthe);
        btnfin=findViewById(R.id.buttonFINISHLAB);
        niv= getIntent().getExtras().getInt("level");
        hsp= getIntent().getExtras().getInt("hsp");
        hsg= getIntent().getExtras().getInt("hsg");
        multipath = getIntent().getStringExtra("pathScoreMulti");
        firebaseDatabase = FirebaseDatabase.getInstance();

        view =  (LabyrintheGame) findViewById(R.id.view2);
        //Initilisation du sensor
        mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        btnfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termine){
                    if (score>=4500){
                        updateNote();
                    }
                    if (!multipath.equals("notMulti")){
                        firebaseDatabase.getReference(multipath).setValue(String.valueOf(score));
                    }
                    score=5000;
                    finish();
                }
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                username=userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Labyrinthe.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
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
    protected static int getScore(){
        return score;
    }
    protected static void setScore(int sc){
         score=sc;
    }
    protected static void setTermine(boolean tr){
        termine=tr;
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
    public void updateNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_t11,"Flash");

        db.collection("Trophy").document(username).update(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Labyrinthe.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Labyrinthe.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}

