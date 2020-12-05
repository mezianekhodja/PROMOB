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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Trophy extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String playerName ="";
    private static final String KEY_t1 = "trophy1", KEY_t2 = "trophy2",KEY_t3 = "trophy3",KEY_t4 = "trophy4",
            KEY_t5 = "trophy5",KEY_t6 = "trophy6",KEY_t7 = "trophy7",KEY_t8 = "trophy8",KEY_t9 = "trophy9",
            KEY_t10 = "trophy10",KEY_t11 = "trophy11",KEY_t12 = "trophy12";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button load;
    private static final String TAG = "Trophy";
    private ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8,iv9,iv10,iv11,iv12;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy);

        load = findViewById(R.id.buttonLoadTrophy);
        iv1=findViewById(R.id.ivTrophy1);
        iv2=findViewById(R.id.ivTrophy2);
        iv3=findViewById(R.id.ivTrophy3);
        iv4=findViewById(R.id.ivTrophy4);
        iv5=findViewById(R.id.ivTrophy5);
        iv6=findViewById(R.id.ivTrophy6);
        iv7=findViewById(R.id.ivTrophy7);
        iv8=findViewById(R.id.ivTrophy8);
        iv9=findViewById(R.id.ivTrophy9);
        iv10=findViewById(R.id.ivTrophy10);
        iv11=findViewById(R.id.ivTrophy11);
        iv12=findViewById(R.id.ivTrophy12);

        tv1=findViewById(R.id.tvTrophy1);
        tv2=findViewById(R.id.tvTrophy2);
        tv3=findViewById(R.id.tvTrophy3);
        tv4=findViewById(R.id.tvTrophy4);
        tv5=findViewById(R.id.tvTrophy5);
        tv6=findViewById(R.id.tvTrophy6);
        tv7=findViewById(R.id.tvTrophy7);
        tv8=findViewById(R.id.tvTrophy8);
        tv9=findViewById(R.id.tvTrophy9);
        tv10=findViewById(R.id.tvTrophy10);
        tv11=findViewById(R.id.tvTrophy11);
        tv12=findViewById(R.id.tvTrophy12);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                playerName=userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Trophy.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Trophy").document(playerName).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
                                    if (!documentSnapshot.getString(KEY_t1).equals("false")) {
                                        iv1.setImageResource(R.drawable.trophy_multi);
                                        tv1.setText(documentSnapshot.getString(KEY_t1));
                                    }
                                    if (!documentSnapshot.getString(KEY_t2).equals("false")) {
                                        iv2.setImageResource(R.drawable.logovm);
                                        tv2.setText(documentSnapshot.getString(KEY_t2));
                                    }
                                    if (!documentSnapshot.getString(KEY_t3).equals("false")) {
                                        iv3.setImageResource(R.drawable.trophy_candy);
                                        tv3.setText(documentSnapshot.getString(KEY_t3));
                                    }
                                    if (!documentSnapshot.getString(KEY_t4).equals("false")) {
                                        iv4.setImageResource(R.drawable.trophy_max);
                                        tv4.setText(documentSnapshot.getString(KEY_t4));
                                    }
                                    if (!documentSnapshot.getString(KEY_t5).equals("false")) {
                                        iv5.setImageResource(R.drawable.trophy_jumelles);
                                        tv5.setText(documentSnapshot.getString(KEY_t5));
                                    }
                                    if (!documentSnapshot.getString(KEY_t6).equals("false")) {
                                        iv6.setImageResource(R.drawable.trophy_zero);
                                        tv6.setText(documentSnapshot.getString(KEY_t6));
                                    }
                                    if (!documentSnapshot.getString(KEY_t7).equals("false")) {
                                        iv7.setImageResource(R.drawable.trophy_multiwinner);
                                        tv7.setText(documentSnapshot.getString(KEY_t7));
                                    }
                                    if (!documentSnapshot.getString(KEY_t8).equals("false")) {
                                        iv8.setImageResource(R.drawable.trophy_pendu);
                                        tv8.setText(documentSnapshot.getString(KEY_t8));
                                    }
                                    if (!documentSnapshot.getString(KEY_t9).equals("false")) {
                                        iv9.setImageResource(R.drawable.trophy_2048);
                                        tv9.setText(documentSnapshot.getString(KEY_t9));
                                    }
                                    if (!documentSnapshot.getString(KEY_t10).equals("false")) {
                                        iv10.setImageResource(R.drawable.trophy_hole);
                                        tv10.setText(documentSnapshot.getString(KEY_t10));
                                    }
                                    if (!documentSnapshot.getString(KEY_t11).equals("false")) {
                                        iv11.setImageResource(R.drawable.trophy_lab);
                                        tv11.setText(documentSnapshot.getString(KEY_t11));
                                    }
                                    if (!documentSnapshot.getString(KEY_t12).equals("false")) {
                                        iv12.setImageResource(R.drawable.trophy_defi);
                                        tv12.setText(documentSnapshot.getString(KEY_t12));
                                    }
                                }
                                else{
                                    Toast.makeText(Trophy.this, "Fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Trophy.this, "Fail", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
                Toast.makeText(Trophy.this, playerName, Toast.LENGTH_SHORT).show();
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