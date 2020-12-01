package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Multi extends AppCompatActivity {
    private ListView listView;
    private Button button;
    List<String> roomList;

    String playerName ="";
    String roomName ="";
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,roomRef,roomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                playerName=userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Multi.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        listView = findViewById(R.id.lvRooms);
        button= findViewById(R.id.buttonCreateRoom);
        roomList = new ArrayList<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomName=playerName;
                roomRef=firebaseDatabase.getReference("rooms/"+roomName+"/player1");
                addRoomEventListener();
                roomRef.setValue(playerName);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roomName=roomList.get(position);
                roomRef=firebaseDatabase.getReference("rooms/"+roomName+"/player2");
                addRoomEventListener();
                roomRef.setValue(playerName);
            }
        });
        addRoomsEventListener();
    }
    private void addRoomEventListener(){
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent = new Intent(Multi.this,MultiConnexion.class);
                intent.putExtra("roomName",roomName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Multi.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addRoomsEventListener(){
        roomsRef=firebaseDatabase.getReference("rooms");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomList.clear();
                Iterable<DataSnapshot> rooms = snapshot.getChildren();
                for(DataSnapshot snapshots: rooms) {
                    roomList.add(snapshots.getKey());
                    ArrayAdapter<String> adapter =new ArrayAdapter<String>(Multi.this, android.R.layout.simple_list_item_1,roomList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}