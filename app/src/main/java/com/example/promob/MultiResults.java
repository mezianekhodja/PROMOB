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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MultiResults extends AppCompatActivity {
    private TextView tvres;
    private Button btn;
    private ImageView ivicon;
    private static final String KEY_t7 = "trophy7";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MultiResults";
    String name ="";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_results);
        tvres= findViewById(R.id.textViewResultsMulti);
        btn = findViewById(R.id.buttonRetourMulti);
        ivicon=findViewById(R.id.imageViewResultsIconMulti);
        firebaseAuth = FirebaseAuth.getInstance();

        name=getIntent().getExtras().getString("name");
        String winner = getIntent().getExtras().getString("winner");
        String role = getIntent().getExtras().getString("role");
        if(role.equals(winner)){
            tvres.setText("Victoire");
            ivicon.setImageResource(R.drawable.victoire);
            updateNote();
        }else {
            tvres.setText("Défaite");
            ivicon.setImageResource(R.drawable.loose);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiResults.this,Multi.class);
                startActivity(intent);
                MultiResults.this.finish();
            }
        });
    }
    public void updateNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_t7,"WIN");

        db.collection("Trophy").document(name).update(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MultiResults.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MultiResults.this, "Fail", Toast.LENGTH_SHORT).show();
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