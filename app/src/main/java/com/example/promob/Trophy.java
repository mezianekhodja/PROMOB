package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
            KEY_t5 = "trophy5",KEY_t6 = "trophy6",KEY_t7 = "trophy7",KEY_t8 = "trophy8";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button load;
    private static final String TAG = "Trophy";
    private ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;

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
        tv1=findViewById(R.id.tvTrophy1);
        tv2=findViewById(R.id.tvTrophy2);
        tv3=findViewById(R.id.tvTrophy3);
        tv4=findViewById(R.id.tvTrophy4);
        tv5=findViewById(R.id.tvTrophy5);
        tv6=findViewById(R.id.tvTrophy6);
        tv7=findViewById(R.id.tvTrophy7);
        tv8=findViewById(R.id.tvTrophy8);

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
                                        iv1.setImageResource(R.drawable.trophy);
                                        tv1.setText(documentSnapshot.getString(KEY_t1));
                                    }
                                    if (!documentSnapshot.getString(KEY_t2).equals("false")) {
                                        iv2.setImageResource(R.drawable.logovm);
                                        tv2.setText(documentSnapshot.getString(KEY_t2));
                                    }
                                    if (!documentSnapshot.getString(KEY_t3).equals("false")) {
                                        iv3.setImageResource(R.drawable.trophy);
                                        tv3.setText(documentSnapshot.getString(KEY_t3));
                                    }
                                    if (!documentSnapshot.getString(KEY_t4).equals("false")) {
                                        iv4.setImageResource(R.drawable.trophy);
                                        tv4.setText(documentSnapshot.getString(KEY_t4));
                                    }
                                    if (!documentSnapshot.getString(KEY_t5).equals("false")) {
                                        iv5.setImageResource(R.drawable.trophy);
                                        tv5.setText(documentSnapshot.getString(KEY_t5));
                                    }
                                    if (!documentSnapshot.getString(KEY_t6).equals("false")) {
                                        iv6.setImageResource(R.drawable.trophy);
                                        tv6.setText(documentSnapshot.getString(KEY_t6));
                                    }
                                    if (!documentSnapshot.getString(KEY_t7).equals("false")) {
                                        iv7.setImageResource(R.drawable.trophy);
                                        tv7.setText(documentSnapshot.getString(KEY_t7));
                                    }
                                    if (!documentSnapshot.getString(KEY_t8).equals("false")) {
                                        iv8.setImageResource(R.drawable.trophy);
                                        tv8.setText(documentSnapshot.getString(KEY_t8));
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
}