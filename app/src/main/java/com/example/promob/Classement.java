package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
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

public class Classement extends AppCompatActivity {
    private TextView textViewG,textViewL;
    String username = "Undefined";

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Classement";
    private static final String KEY_USER = "user", KEY_SCORE = "score",KEY_ERROR = "error", KEY_DATE="date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classement);
        textViewG = findViewById(R.id.textViewResultsG);
        textViewL = findViewById(R.id.textViewResultsL);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                username=userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Classement.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        if(!username.equals("invite")){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioButtonLQR:
                if (checked)
                    loadNoteLQE();
                    loadNoteLQM();
                    loadNoteLQH();
                break;
            case R.id.radioButtonPR:
                if (checked)
                    loadNoteP1();
                    loadNoteP2();
                    loadNoteP3();
                break;
            case R.id.radioButtonCCR:
                if (checked)
                    loadNoteCC1();
                    loadNoteCC2();
                    loadNoteCC3();
                    break;
            case R.id.radioButtonNR:
                if (checked)
                    loadNoteN1();
                    loadNoteN2();
                    loadNoteN3();
                break;
            case R.id.radioButtonLB:
                if (checked)
                    loadNoteLB1();
                    loadNoteLB2();
                    loadNoteLB3();
                break;
            case R.id.radioButtonB2:
                if (checked)
                    textViewG.setText("bientot 2");
                    textViewL.setText("bientot 2");
                break;
        }
        }
        else {
            textViewG.setText("Pas de résultats globaux disponibles en mode invité");
            textViewL.setText("Pas de résultats locaux disponibles en mode invité");
        }
    }
    public void loadNoteCC1(){
            db.collection("CandyCrush_level_1").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText("GLOBAL : Level 1 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("CandyCrush_level_1").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText("LOCAL : Level 1 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        }
    public void loadNoteCC2(){
        db.collection("CandyCrush_level_2").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level 2 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("CandyCrush_level_2").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level 2 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNoteCC3(){
        db.collection("CandyCrush_level_3").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level 3 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("CandyCrush_level_3").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level 3 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void loadNoteLB1(){
        db.collection("Labyrinthe_level_1").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText("GLOBAL : Level 1 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Labyrinthe_level_1").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText("LOCAL : Level 1 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNoteLB2(){
        db.collection("Labyrinthe_level_2").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level 2 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Labyrinthe_level_2").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level 2 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNoteLB3(){
        db.collection("Labyrinthe_level_3").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level 3 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Labyrinthe_level_3").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level 3 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }



    public void loadNoteN1(){
        db.collection("Numbers_level_1").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText("GLOBAL : Level 1 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Numbers_level_1").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText("LOCAL : Level 1 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNoteN2(){
        db.collection("Numbers_level_2").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level 2 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Numbers_level_2").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level 2 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNoteN3(){
        db.collection("Numbers_level_3").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level 3 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Numbers_level_3").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level 3 : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }



    public void loadNoteP1(){
        db.collection("Pendu_level_1").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String error= documentSnapshot.get(KEY_ERROR).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText("GLOBAL : Level 1 : user : "+user+", erreurs : "+error+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Pendu_level_1").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String error = documentSnapshot.get(KEY_ERROR).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText("LOCAL : Level 1 : user : "+user+", erreurs : "+error+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNoteP2(){
        db.collection("Pendu_level_2").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String error = documentSnapshot.get(KEY_ERROR).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level 2 : user : "+user+", erreurs : "+error+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Pendu_level_2").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String error = documentSnapshot.get(KEY_ERROR).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level 2 : user : "+user+", erreurs : "+error+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNoteP3(){
        db.collection("Pendu_level_3").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String error = documentSnapshot.get(KEY_ERROR).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level 3 : user : "+user+", erreurs : "+error+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Pendu_level_3").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String error = documentSnapshot.get(KEY_ERROR).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level 3 : user : "+user+", erreurs : "+error+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }



    public void loadNoteLQE(){
        db.collection("LogoQuizz_level_Easy").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText("GLOBAL : Level Easy : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("LogoQuizz_level_Easy").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText("LOCAL : Level Easy : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNoteLQM(){
        db.collection("LogoQuizz_level_Medium").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level Medium : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("LogoQuizz_level_Medium").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level Medium : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void loadNoteLQH(){
        db.collection("LogoQuizz_level_Hard").document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewG.setText(textViewG.getText() + " \n Level Hard : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("LogoQuizz_level_Hard").document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user = documentSnapshot.getString(KEY_USER);
                            String score = documentSnapshot.get(KEY_SCORE).toString();
                            String date = documentSnapshot.getString(KEY_DATE);

                            textViewL.setText(textViewL.getText() + " \n Level Hard : user : "+user+", score : "+score+", date : "+date);
                        }
                        else{
                            Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Classement.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}