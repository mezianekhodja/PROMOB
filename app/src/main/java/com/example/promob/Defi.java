package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


public class Defi extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "Defi";
    private static final String KEY_t12 = "trophy12";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String username="";
    private Button btnstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defi);

        btnstart=findViewById(R.id.buttonStartDefi);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                username=userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Defi.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGame();
            }
        });
    }
    public void loadGame(){
        //validation trophée defi
        if(!username.equals("invite")){
            updateNote();
        }
        //génération nb aleatoire
        int nombreAleatoire1 = (int)(Math.random() * 6);
        int nombreAleatoire2 = (int)(Math.random() * 6);
        int nombreAleatoire3 = (int)(Math.random() * 6);

        switch (nombreAleatoire1){
            case 0: startHole(1);
                break;
            case 1: startHole(2);
                break;
            case 2: startHole(3);
                break;
            case 3: startLabyrinthe(1);
                break;
            case 4: startLabyrinthe(2);
                break;
            default: startLabyrinthe(3);
                break;
        }
        switch (nombreAleatoire2){
            case 0: startCandyCrush(1);
                break;
            case 1: startCandyCrush(2);
                break;
            case 2: startCandyCrush(3);
                break;
            case 3: startNumbers(1);
                break;
            case 4: startNumbers(2);
                break;
            default: startNumbers(3);
                break;
        }
        switch (nombreAleatoire3){
            case 0: startPendu(1);
                break;
            case 1: startPendu(2);
                break;
            case 2: startPendu(3);
                break;
            case 3: startLogoQuizz("Hard");
                break;
            case 4: startLogoQuizz("Medium");
                break;
            default: startLogoQuizz("Easy");
                break;
        }
    }
    private void startLogoQuizz(String difficulte){
        Intent intent = new Intent(Defi.this, LogoQuizz_Activity.class);
        intent.putExtra("extraDifficulty", difficulte);
        intent.putExtra("pathScoreMulti", "notMulti");
        startActivityForResult(intent, 1);
    }
    private void startLabyrinthe(int difficulte){
        Intent intent = new Intent(Defi.this, Labyrinthe.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "notMulti");
        startActivityForResult(intent, 1);
    }
    private void startHole(int difficulte){
        Intent intent = new Intent(Defi.this, Hole.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "notMulti");
        startActivityForResult(intent, 1);
    }
    private void startPendu(int difficulte){
        Intent intent = new Intent(Defi.this, Pendu_Activity.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "notMulti");
        startActivityForResult(intent, 1);
    }
    private void startCandyCrush(int difficulte){
        Intent intent = new Intent(Defi.this, CandyCrush.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "notMulti");
        startActivityForResult(intent, 1);
    }
    private void startNumbers(int difficulte){
        Intent intent = new Intent(Defi.this, Numbers.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "notMulti");
        startActivityForResult(intent, 1);
    }
    public void updateNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_t12,"Solitaire");

        db.collection("Trophy").document(username).update(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Defi.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Defi.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
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
        }
        return super.onOptionsItemSelected(item);
    }
}