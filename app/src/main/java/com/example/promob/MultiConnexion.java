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
    private Button btngg,btnrev,btneheh,btnsalut,btndef,btnstop;
    private String playerName ="",roomName="",message="",role="";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,messageHostRef,messageGuestRef;
    private FirebaseAuth firebaseAuth;


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
        message=role+": Connected !";
        messageHostRef.setValue(message);
        messageGuestRef.setValue(message);
        addRoomEventListener();
    }

    private void addRoomEventListener(){
        messageHostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(role.equals("Host")){
                    if (snapshot.getValue(String.class).contains("Guest:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Guest:",""), Toast.LENGTH_SHORT).show();
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
                    }
                }else {
                    if (snapshot.getValue(String.class).contains("Host:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Host:",""), Toast.LENGTH_SHORT).show();
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
        startActivityForResult(intent, 1);
    }
}