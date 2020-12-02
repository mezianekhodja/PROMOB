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
    private Button button,btndef,btnstop;
    private String playerName ="",roomName="",message="",role="";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,messageHostRef,messageGuestRef;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_connexion);
        button = findViewById(R.id.buttonPokeMulti);
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
                    role="host";
                }else{
                    role="guest";
                    btndef.setEnabled(false);
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
            }
        });

        btndef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageHostRef.setValue(role+": Start Game !");
                //Intent intent = new Intent(MultiConnexion.this, LogoQuizz_Activity.class);
                //intent.putExtra("extraDifficulty", "Medium");
                //startActivityForResult(intent, 1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": Poked ! ";
                if (role.equals("host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
            }
        });
        messageHostRef=firebaseDatabase.getReference("rooms/"+roomName+"/messageHost");
        messageGuestRef=firebaseDatabase.getReference("rooms/"+roomName+"/messageGuest");
        message=role+": Poked!";
        messageHostRef.setValue(message);
        messageGuestRef.setValue(message);
        addRoomEventListener();
    }

    private void addRoomEventListener(){
        messageHostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(role.equals("host")){
                    if (snapshot.getValue(String.class).contains("guest:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("guest:",""), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (snapshot.getValue(String.class).contains("host:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("host:",""), Toast.LENGTH_SHORT).show();
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
                if(role.equals("host")){
                    if (snapshot.getValue(String.class).contains("guest:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("guest:",""), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (snapshot.getValue(String.class).contains("host:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("host:",""), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageGuestRef.setValue(message);
            }
        });
    }
}