package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private Button button,join,rmv;
    List<String> roomList;
    private int numberPlayers;
    String playerName ="";
    String roomName ="undefined";
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "Multi";
    private DatabaseReference databaseReference,roomRef,roomsRef,numbplayer;

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

        join=findViewById(R.id.buttonJoinRoomMulti);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomName.equals("undefined")){
                    Toast.makeText(Multi.this, "Sélectionner un adversaire", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (numberPlayers==1){
                        roomRef=firebaseDatabase.getReference("rooms/"+roomName+"/player2");
                        addRoomEventListener();
                        roomRef.setValue(playerName);
                        numbplayer.setValue(String.valueOf(1+numberPlayers));
                    }
                    else{
                        Toast.makeText(Multi.this, "Plus aucune place restante", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        listView = findViewById(R.id.lvRooms);
        button= findViewById(R.id.buttonCreateRoom);
        roomList = new ArrayList<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomName=playerName;
                numbplayer=firebaseDatabase.getReference("rooms/"+roomName+"/numberPlayers");
                roomRef=firebaseDatabase.getReference("rooms/"+roomName+"/player1");
                addRoomEventListener();
                roomRef.setValue(playerName);
                numbplayer.setValue("1");
            }
        });
        rmv =findViewById(R.id.buttonRemoveRoomMulti);
        rmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomRef=firebaseDatabase.getReference("rooms/"+playerName);
                finish();
                roomRef.setValue(null);
                Toast.makeText(Multi.this, "Deleted room : "+playerName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Multi.this, Multi.class);
                startActivity(intent);
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roomName=roomList.get(position);
                numberPlayers=0;
                numbplayer=firebaseDatabase.getReference("rooms/"+roomName+"/numberPlayers");

                numbplayer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        if (!(value==null)){
                        numberPlayers=Integer.valueOf(value);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void openProfil() {
        finish();
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void openAcceuil(){
        finish();
        Intent intent = new Intent(this, Entrainement.class);
        startActivity(intent);
    }
    public void openClassement(){
        finish();
        Intent intent = new Intent(this, Classement.class);
        startActivity(intent);
    }
    public void openTrophy(){
        finish();
        Intent intent = new Intent(this, Trophy.class);
        startActivity(intent);
    }
    public void openCopyright(){
        finish();
        Intent intent = new Intent(this, Copyright.class);
        startActivity(intent);
    }
    public void openActivityConnexion() {
        finish();
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
    }
    private void Logout() {
        firebaseAuth.signOut();
        openActivityConnexion();
    }
    public void openSoloMulti(){
        finish();
        Intent intent = new Intent(this, Solo_Multi.class);
        startActivity(intent);
    }
    public void openBandeSon(){
        finish();
        Intent intent = new Intent(this, BandeSon.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.profileMenu:{
                openProfil();
                break;
            }
            case R.id.acceuilMenu:{
                openAcceuil();
                break;
            }
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.classementMenu:{
                openClassement();
                break;
            }
            case R.id.trophyMenu:{
                openTrophy();
                break;
            }
            case R.id.copyrightMenu:{
                openCopyright();
                break;
            }
            case R.id.solomultiMenu:{
                openSoloMulti();
                break;
            }
            case R.id.bandesonMenu:{
                openBandeSon();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}