package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MultiConnexion extends AppCompatActivity {
    private Button btngg,btnrev,btneheh,btnsalut,btndef,btnstop,btnres,btnsend;
    private String playerName ="",roomName="",message="",role="";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,messageHostRef,messageGuestRef,scoreHost1Ref,
            scoreGuest1Ref,scoreHost2Ref,scoreGuest2Ref,scoreHost3Ref,scoreGuest3Ref,nbplayers;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MultiConnexion";
    private static final String KEY_t1 = "trophy1";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int scoreHost1=-1,scoreGuest1=-1,scoreHost2=-1,scoreGuest2=-1,scoreHost3=1,scoreGuest3=1,numberPlayers;
    private EditText messageToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_connexion);
        btngg = findViewById(R.id.buttonGGMulti);
        btneheh = findViewById(R.id.buttonEHEHMulti);
        btnsalut = findViewById(R.id.buttonSALUTMulti);
        btnrev = findViewById(R.id.buttonREVANCHEMulti);
        btndef=findViewById(R.id.buttonDefiMulti);
        btnstop=findViewById(R.id.buttonStopMulti);
        btnres=findViewById(R.id.buttonRESMulti);
        btnsend=findViewById(R.id.buttonSENDMulti);
        messageToSend=findViewById(R.id.editTextSendMulti);
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
                btnres.setEnabled(false);
                btndef.setEnabled(false);
                if (roomName.equals(playerName)){
                    role="Host";
                }else{
                    role="Guest";
                    btndef.setEnabled(false);
                    btnstop.setEnabled(false);
                    btnstop.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        btnres.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(MultiConnexion.this, MultiResults.class);
             intent.putExtra("role", role);
             intent.putExtra("name",playerName);
             int cpt=0;
             if(scoreHost1>scoreGuest1){
                 cpt++;
             }
             if(scoreHost2>scoreGuest2){
                 cpt++;
             }
             if(scoreHost3>scoreGuest3){
                 cpt++;
             }
             if(cpt>1){
                 intent.putExtra("winner", "Host");
             } else{
                 intent.putExtra("winner", "Guest");
             }
             startActivity(intent);
             MultiConnexion.this.finish();
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageHostRef.setValue(role+": Leave");
                close();
            }
        });

        btndef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGame();
            }
        });
        nbplayers=firebaseDatabase.getReference("rooms/"+roomName+"/numberPlayers");
        nbplayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (!(value==null)){
                    numberPlayers=Integer.valueOf(value);
                }
                else{
                    numberPlayers=0;
                }
                if (numberPlayers==2 && role.equals("Host")){btndef.setEnabled(true);}
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        btngg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": GG ! ";
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
            }
        });
        btnsalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": Salut ! ";
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
            }
        });
        btneheh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": Eheh ;) ";
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
            }
        });
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage=messageToSend.getText().toString();
                if (!userMessage.isEmpty()){
                message = role+": "+userMessage;
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
                }
                else{
                    Toast.makeText(MultiConnexion.this, "Message Vide", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": Revanche ? ";
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else {
                    messageGuestRef.setValue(message);
                }
            }
        });
        messageHostRef=firebaseDatabase.getReference("rooms/"+roomName+"/messageHost");
        messageGuestRef=firebaseDatabase.getReference("rooms/"+roomName+"/messageGuest");
        scoreGuest1Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreGuest1");
        scoreHost1Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreHost1");
        scoreGuest2Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreGuest2");
        scoreHost2Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreHost2");
        scoreGuest3Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreGuest3");
        scoreHost3Ref=firebaseDatabase.getReference("rooms/"+roomName+"/scoreHost3");
        message=role+": Connected !";
        messageHostRef.setValue(message);
        messageGuestRef.setValue(message);
        scoreHost1Ref.setValue("-1");
        scoreGuest1Ref.setValue("-1");
        scoreHost2Ref.setValue("-1");
        scoreGuest2Ref.setValue("-1");
        scoreHost3Ref.setValue("-1");
        scoreGuest3Ref.setValue("-1");
        addRoomEventListener();
        addScoreEventListener();
    }
    private void addScoreEventListener(){
        scoreHost1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreHost1 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreHost2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreHost2 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreHost3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreHost3 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuest1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest1 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuest2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest2 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuest3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest3 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRoomEventListener(){
        messageHostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(role.equals("Host")){
                    if (snapshot.getValue(String.class).contains("Guest:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Guest:",""), Toast.LENGTH_SHORT).show();
                        if (scoreGuest3>-1 && scoreHost3>-1){
                            btnres.setEnabled(true);                        }
                    }
                }else {
                    if (snapshot.getValue(String.class).contains("Host:")){
                        Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Host:",""), Toast.LENGTH_SHORT).show();
                        if (snapshot.getValue(String.class).contains("Leave")){
                            close();
                        }
                        else if (snapshot.getValue(String.class).contains("StartGame")){
                            updateNote();
                            if (snapshot.getValue(String.class).contains("HoleEasy")){
                                startHole(1,"3");
                            }
                            else if (snapshot.getValue(String.class).contains("HoleMedium")){
                                startHole(2,"3");
                            }
                            else if (snapshot.getValue(String.class).contains("HoleHard")){
                                startHole(3,"3");
                            }
                            else if (snapshot.getValue(String.class).contains("LabyrintheEasy")){
                                startLabyrinthe(1,"3");
                            }
                            else if (snapshot.getValue(String.class).contains("LabyrintheMedium")){
                                startLabyrinthe(2,"3");
                            }
                            else{
                                startLabyrinthe(3,"3");
                            }


                            if (snapshot.getValue(String.class).contains("CandyCrushEasy")){
                                startCandyCrush(1,"2");
                            }
                            else if (snapshot.getValue(String.class).contains("CandyCrushMedium")){
                                startCandyCrush(2,"2");
                            }
                            else if (snapshot.getValue(String.class).contains("CandyCrushHard")){
                                startCandyCrush(3,"2");
                            }
                            else if (snapshot.getValue(String.class).contains("NumbersEasy")){
                                startNumbers(1,"2");
                            }
                            else if (snapshot.getValue(String.class).contains("NumbersMedium")){
                                startNumbers(2,"2");
                            }
                            else{
                                startNumbers(3,"2");
                            }


                            if (snapshot.getValue(String.class).contains("PenduEasy")){
                                startPendu(1,"1");
                            }
                            else if (snapshot.getValue(String.class).contains("PenduMedium")){
                                startPendu(2,"1");
                            }
                            else if (snapshot.getValue(String.class).contains("PenduHard")){
                                startPendu(3,"1");
                            }
                            else if (snapshot.getValue(String.class).contains("LogoQuizzHard")){
                                startLogoQuizz("Hard","1");
                            }
                            else if (snapshot.getValue(String.class).contains("LogoQuizzMedium")){
                                startLogoQuizz("Medium","1");
                            }
                            else if (snapshot.getValue(String.class).contains("LogoQuizzEasy")){
                                startLogoQuizz("Easy","1");
                            }
                            else if (snapshot.getValue(String.class).contains("GoogleTrendsHard")){
                                startGoogleTrends("Hard","1");
                            }
                            else if (snapshot.getValue(String.class).contains("GoogleTrendsMedium")){
                                startGoogleTrends("Medium","1");
                            }
                            else{
                                startGoogleTrends("Easy","1");
                            }
                        }
                        else if (scoreGuest3>-1 && scoreHost3>-1){
                            btnres.setEnabled(true);                        }
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
                if(role.equals("Host")){
                    if (!(snapshot.getValue(String.class)==null)){
                         if (snapshot.getValue(String.class).contains("Guest:")){
                            Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Guest:",""), Toast.LENGTH_SHORT).show();
                            if (scoreGuest3>-1 && scoreHost3>-1){
                                btnres.setEnabled(true);                        }
                            }
                            }else {
                        if (snapshot.getValue(String.class).contains("Host:")){
                            Toast.makeText(MultiConnexion.this, snapshot.getValue(String.class).replace("Host:",""), Toast.LENGTH_SHORT).show();
                            if (scoreGuest3>-1 && scoreHost3>-1){
                                btnres.setEnabled(true);                        }
                    }
                }
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageGuestRef.setValue(message);
            }
        });
    }
    public void close(){
        Intent intent = new Intent(MultiConnexion.this,Multi.class);
        startActivity(intent);
        MultiConnexion.this.finish();
    }
    public void loadGame(){
        updateNote();
        if(role.equals("Host")){
            String aEnvoyer = role;
        //génération nb aleatoire
        int nombreAleatoire1 = (int)(Math.random() * 6);
        int nombreAleatoire2 = (int)(Math.random() * 6);
        int nombreAleatoire3 = (int)(Math.random() * 9);

        switch (nombreAleatoire1){
            case 0: startHole(1,"3");
                aEnvoyer+=": StartGame HoleEasy ";
                break;
            case 1: startHole(2,"3");
                aEnvoyer+=": StartGame HoleMedium ";
                break;
            case 2: startHole(3,"3");
                aEnvoyer+=": StartGame HoleHard ";
                break;
            case 3: startLabyrinthe(1,"3");
                aEnvoyer+=": StartGame LabyrintheEasy ";
                break;
            case 4: startLabyrinthe(2,"3");
                aEnvoyer+=": StartGame LabyrintheMedium ";
                break;
            default: startLabyrinthe(3,"3");
                aEnvoyer+=": StartGame LabyrintheHard ";
                break;
        }
        switch (nombreAleatoire2){
            case 0: startCandyCrush(1,"2");
                aEnvoyer+="CandyCrushEasy ";
                break;
            case 1: startCandyCrush(2,"2");
                aEnvoyer+="CandyCrushMedium ";
                break;
            case 2: startCandyCrush(3,"2");
                aEnvoyer+="CandyCrushHard ";
                break;
            case 3: startNumbers(1,"2");
                aEnvoyer+="NumbersEasy ";
                break;
            case 4: startNumbers(2,"2");
                aEnvoyer+="NumbersMedium ";
                break;
            default: startNumbers(3,"2");
                aEnvoyer+="NumbersHard ";
                break;
        }
        switch (nombreAleatoire3){
            case 0: startPendu(1,"1");
                aEnvoyer+="PenduEasy";
                break;
            case 1: startPendu(2,"1");
                aEnvoyer+="PenduMedium";
                break;
            case 2: startPendu(3,"1");
                aEnvoyer+="PenduHard";
                break;
            case 3: startLogoQuizz("Hard","1");
                aEnvoyer+="LogoQuizzHard";
                break;
            case 4: startLogoQuizz("Medium","1");
                aEnvoyer+="LogoQuizzMedium";
                break;
            case 5: startLogoQuizz("Easy","1");
                aEnvoyer+="LogoQuizzEasy";
                break;
            case 6: startGoogleTrends("Hard","1");
                aEnvoyer+="GoogleTrendsHard";
                break;
            case 7: startGoogleTrends("Medium","1");
                aEnvoyer+="GoogleTrendsMedium";
                break;
            default: startGoogleTrends("Easy","1");
                aEnvoyer+="GoogleTrendsEasy";
                break;
        }
        messageHostRef.setValue(aEnvoyer);
        }
    }
    private void startLogoQuizz(String difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, LogoQuizz_Activity.class);
        intent.putExtra("extraDifficulty", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startGoogleTrends(String difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, GoogleTrends_Activity.class);
        intent.putExtra("extraDifficulty", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startLabyrinthe(int difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, Labyrinthe.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startHole(int difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, Hole.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startPendu(int difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, Pendu_Activity.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startCandyCrush(int difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, CandyCrush.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startNumbers(int difficulte, String number){
        Intent intent = new Intent(MultiConnexion.this, Numbers.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "rooms/"+roomName+"/score"+role+number);
        intent.putExtra("pathMessageMulti", "rooms/"+roomName+"/message"+role+number);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    public void updateNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_t1,"Never Walk Alone");

        db.collection("Trophy").document(playerName).update(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MultiConnexion.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MultiConnexion.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}