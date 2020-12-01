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
    private Button button,btndef;
    private String playerName ="",roomName="",message="",role="";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,messageRef;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_connexion);
        button = findViewById(R.id.buttonPokeMulti);
        btndef=findViewById(R.id.buttonDefiMulti);
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        btndef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiConnexion.this, LogoQuizz_Activity.class);
                intent.putExtra("extraDifficulty", "Medium");
                startActivityForResult(intent, 1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": Poked ! ";
                messageRef.setValue(message);
            }
        });
        messageRef=firebaseDatabase.getReference("rooms/"+roomName+"/message");
        message=role+": Poked!";
        messageRef.setValue(message);
        addRoomEventListener();
    }

    private void addRoomEventListener(){
        messageRef.addValueEventListener(new ValueEventListener() {
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
                messageRef.setValue(message);
            }
        });
    }
}