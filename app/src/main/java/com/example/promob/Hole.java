package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Hole extends AppCompatActivity {

    private static int niv,score = 0;
    private HoleGame view;
    private SensorManager mgr;
    private static boolean termine=false;
    private static String multipath;
    private Button btnfin;
    private FirebaseDatabase firebaseDatabase;
    private String username ="";
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "Hole";
    private static final String KEY_t10 = "trophy10";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole);
        niv= getIntent().getExtras().getInt("level");
        multipath = getIntent().getStringExtra("pathScoreMulti");
        btnfin=findViewById(R.id.buttonFINISHHOLE);
        firebaseDatabase = FirebaseDatabase.getInstance();

        view = (HoleGame) findViewById(R.id.viewHOLE);

        //Initilisation du sensor
        mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        btnfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termine){
                    if (score>=450 && !username.equals("invite")){
                        updateNote();
                    }
                    if (!multipath.equals("notMulti")){
                        firebaseDatabase.getReference(multipath).setValue(String.valueOf(score));
                    }
                    score=0;
                    finish();
                }
                else{
                    Toast.makeText(Hole.this, "Activité non finie", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Hole.this, error.getCode(),Toast.LENGTH_SHORT).show();
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
    protected static Context getContext(){
        return getContext();
    }
    public void updateNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_t10,"Stabilité");

        db.collection("Trophy").document(username).update(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Hole.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Hole.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}