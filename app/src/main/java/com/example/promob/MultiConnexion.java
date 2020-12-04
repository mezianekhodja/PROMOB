package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MultiConnexion extends AppCompatActivity {
    private Button btngg,btnrev,btneheh,btnsalut,btndef,btnstop,btnres;
    private String playerName ="",roomName="",message="",role="";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,messageHostRef,messageGuestRef,scoreHost1Ref,
            scoreGuest1Ref,scoreHost2Ref,scoreGuest2Ref,scoreHost3Ref,scoreGuest3Ref;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MultiConnexion";
    private static final String KEY_t1 = "trophy1";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int scoreHost1=-1,scoreGuest1=-1,scoreHost2=-1,scoreGuest2=-1,scoreHost3=1,scoreGuest3=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_connexion);
        btngg = findViewById(R.id.buttonGGMulti);
        btneheh = findViewById(R.id.buttonEHEHMulti);
        btnsalut = findViewById(R.id.buttonSALUTMulti);
        btnrev = findViewById(R.id.buttonREVANCHEMulti);
        btndef=findViewById(R.id.buttonDefiMulti);
        btnstop=findViewById(R.id.buttonStopMulti);
        btnres=findViewById(R.id.buttonRESMulti);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            roomName=extras.getString("roomName");
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                playerName=userProfile.getUserName();
                btnres.setEnabled(false);
                if (roomName.equals(playerName)){
                    role="Host";
                }else{
                    role="Guest";
                    btndef.setEnabled(false);
                    btnstop.setEnabled(false);
                    btnstop.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        btnres.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(MultiConnexion.this, MultiResults.class);
             intent.putExtra("role", role);
             intent.putExtra("name",playerName);
             int cpt=0;
             if(scoreHost1>scoreGuest1){
                 cpt++;
             }
             if(scoreHost2>scoreGuest2){
                 cpt++;
             }
             if(scoreHost3>scoreGuest3){
                 cpt++;
             }
             if(cpt>1){
                 intent.putExtra("winner", "Host");
             } else{
                 intent.putExtra("winner", "Guest");
             }
             startActivity(intent);
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageHostRef.setValue(role+": Leave");
                close();
            }
        });

        btndef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageHostRef.setValue(role+": Start Game !");
                loadGame();
            }
        });

        btngg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": GG ! ";
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
            }
        });
        btnsalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": Salut ! ";
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
            }
        });
        btneheh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": Eheh ;) ";
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
            }
        });
        btnrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": Revanche ? ";
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
            }
        });
        messageHostRef=firebaseDatabase.getReference("rooms/"+roomName+"/messageHost");
        messageGuestRef=firebaseDatabase.getReference("rooms/"+roomName+"/messageGuest");
        scoreGuest1Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreGuest1");
        scoreHost1Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreHost1");
        scoreGuest2Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreGuest2");
        scoreHost2Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreHost2");
        scoreGuest3Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreGuest3");
        scoreHost3Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreHost3");
        message=role+": Connected !";
        messageHostRef.setValue(message);
        messageGuestRef.setValue(message);
        scoreHost1Ref.setValue("-1");
        scoreGuest1Ref.setValue("-1");
        scoreHost2Ref.setValue("-1");
        scoreGuest2Ref.setValue("-1");
        scoreHost3Ref.setValue("-1");
        scoreGuest3Ref.setValue("-1");
        addRoomEventListener();
        addScoreEventListener();
    }
    private void addScoreEventListener(){
        scoreHost1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreHost1 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreHost2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreHost2 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreHost3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreHost3 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuest1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest1 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuest2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest2 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuest3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest3 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRoomEventListener(){
        messageHostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(role.equals("Host")){
                    if (snapshot.getValue(String.class).contains("Guest:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Guest:",""), Toast.LENGTH_SHORT).show();
                        if (scoreGuest3>-1 && scoreHost3>-1){
                            btnres.setEnabled(true);                        }
                    }
                }else {
                    if (snapshot.getValue(String.class).contains("Host:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Host:",""), Toast.LENGTH_SHORT).show();
                        if (snapshot.getValue(String.class).contains("Leave")){
                            close();
                        }
                        else if (snapshot.getValue(String.class).contains("Start Game")){
                            loadGame();
                        }
                        else if (scoreGuest3>-1 && scoreHost3>-1){
                            btnres.setEnabled(true);                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageHostRef.setValue(message);
            }
        });
        messageGuestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(role.equals("Host")){
                    if (snapshot.getValue(String.class).contains("Guest:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Guest:",""), Toast.LENGTH_SHORT).show();
                        if (scoreGuest3>-1 && scoreHost3>-1){
                            btnres.setEnabled(true);                        }
                    }
                }else {
                    if (snapshot.getValue(String.class).contains("Host:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Host:",""), Toast.LENGTH_SHORT).show();
                        if (scoreGuest3>-1 && scoreHost3>-1){
                            btnres.setEnabled(true);                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageGuestRef.setValue(message);
            }
        });
    }
    public void close(){
        Intent intent = new Intent(MultiConnexion.this,Multi.class);
        startActivity(intent);
        MultiConnexion.this.finish();
    }
    public void loadGame(){
        updateNote();
        startPendu(1,"3");
        //startPendu(2,"3");
        //startPendu(3,"3");
        //startLogoQuizz("Hard","3");
        //startLogoQuizz("Medium","2");
        startCandyCrush(1,"2");
        startNumbers(1,"1");
        //startLogoQuizz("Easy","1");
    }
    private void startLogoQuizz(String difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, LogoQuizz_Activity.class);
        intent.putExtra("extraDifficulty", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startPendu(int difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, Pendu_Activity.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startCandyCrush(int difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, CandyCrush.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startNumbers(int difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, Numbers.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    public void updateNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_t1,"Never Walk Alone");

        db.collection("Trophy").document(playerName).update(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MultiConnexion.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MultiConnexion.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}