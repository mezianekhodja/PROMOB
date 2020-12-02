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


public class Solo_Multi extends AppCompatActivity  {
    private Button solo,multi;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String username ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_ou_multi);

        solo = (Button)findViewById(R.id.btn_solo);
        multi = (Button)findViewById(R.id.btn_multijoueur);

        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSolo();
            }
        });
        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMulti();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                username=userProfile.getUserName();
                if (username.equals("invite")){
                    multi.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Solo_Multi.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openSolo() {
        Intent intent = new Intent(this, Solo.class);
        startActivity(intent);
        Solo_Multi.this.finish();
    }

    public void openMulti() {
        Intent intent = new Intent(this, Multi.class);
        startActivity(intent);
        Solo_Multi.this.finish();
    }

}
