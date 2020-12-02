package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MultiConnexion extends AppCompatActivity {
    private Button btngg,btnrev,btneheh,btnsalut,btndef,btnstop,btnres;
    private String playerName ="",roomName="",message="",role="";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,messageHostRef,messageGuestRef,scoreHostRef,scoreGuestRef;
    private FirebaseAuth firebaseAuth;
    private int scoreHost=-1,scoreGuest=-1;

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
             if(scoreHost>scoreGuest){
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
        scoreGuestRef=firebaseDatabase.getReference("rooms/"+roomName+"/scoreGuest");
        scoreHostRef=firebaseDatabase.getReference("rooms/"+roomName+"/scoreHost");
        message=role+": Connected !";
        messageHostRef.setValue(message);
        messageGuestRef.setValue(message);
        scoreHostRef.setValue("-1");
        scoreGuestRef.setValue("-1");
        addRoomEventListener();
        addScoreEventListener();
    }
    private void addScoreEventListener(){
        scoreHostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreHost = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest = Integer.parseInt(snapshot.getValue(String.class));
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
                        if (scoreGuest>0 && scoreHost>0){
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
                        else if (scoreGuest>0 && scoreHost>0){
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
                        if (scoreGuest>0 && scoreHost>0){
                            btnres.setEnabled(true);                        }
                    }
                }else {
                    if (snapshot.getValue(String.class).contains("Host:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Host:",""), Toast.LENGTH_SHORT).show();
                        if (scoreGuest>0 && scoreHost>0){
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
        Intent intent = new Intent(MultiConnexion.this, LogoQuizz_Activity.class);
        intent.putExtra("extraDifficulty", "Medium");
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
}