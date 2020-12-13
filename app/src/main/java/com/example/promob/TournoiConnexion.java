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

public class TournoiConnexion extends AppCompatActivity {

    private Button btngg,btnrev,btneheh,btnsalut,btndef,btnstop,btnres,btnsend;
    private String playerName ="",roomName="",message="",role="";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,messageHostRef,messageGuest1Ref,messageGuest2Ref,messageGuest3Ref,scoreHostRef,
            scoreGuest1Ref,scoreGuest2Ref,scoreGuest3Ref,nbplayers;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "TournoiConnexion";
    private static final String KEY_t1 = "trophy1";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int scoreHost=-1,scoreGuest1=-1,scoreGuest2=-1,scoreGuest3=-1,numberPlayers;
    private EditText messageToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournoi_connexion);
        btngg = findViewById(R.id.buttonGGMultiTournoi);
        btneheh = findViewById(R.id.buttonEHEHMultiTournoi);
        btnsalut = findViewById(R.id.buttonSALUTMultiTournoi);
        btnrev = findViewById(R.id.buttonREVANCHEMultiTournoi);
        btndef=findViewById(R.id.buttonDefiMultiTournoi);
        btnstop=findViewById(R.id.buttonStopMultiTournoi);
        btnres=findViewById(R.id.buttonRESMultiTournoi);
        btnsend=findViewById(R.id.buttonSENDTournoi);
        messageToSend=findViewById(R.id.editTextSendTournoi);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            roomName=extras.getString("roomName");
            role=extras.getString("role");
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                playerName=userProfile.getUserName();
                btnres.setEnabled(false);
                btndef.setEnabled(false);
                if(!role.equals("Host")){
                    btnstop.setEnabled(false);
                    btnstop.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TournoiConnexion.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TournoiConnexion.this, MultiResults.class);
                intent.putExtra("role", role);
                intent.putExtra("name",playerName);
                if((scoreHost>scoreGuest1)&&(scoreHost>scoreGuest2)&&(scoreHost>scoreGuest3)){
                    intent.putExtra("winner", "Host");
                }
                else if((scoreGuest1>scoreHost)&&(scoreGuest1>scoreGuest2)&&(scoreGuest1>scoreGuest3)){
                    intent.putExtra("winner", "Guest1");
                }
                else if((scoreGuest2>scoreHost)&&(scoreGuest2>scoreGuest1)&&(scoreGuest2>scoreGuest3)){
                    intent.putExtra("winner", "Guest2");
                }
                else{
                    intent.putExtra("winner", "Guest3");
                }
                startActivity(intent);
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

        btngg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = role+": GG ! ";
                if (role.equals("Host")){
                    messageHostRef.setValue(message);
                }
                else if (role.equals("Guest1")){
                    messageGuest1Ref.setValue(message);
                }
                else if (role.equals("Guest2")){
                    messageGuest2Ref.setValue(message);
                }
                else {
                    messageGuest3Ref.setValue(message);
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
                    else if (role.equals("Guest1")){
                        messageGuest1Ref.setValue(message);
                    }
                    else if (role.equals("Guest2")){
                        messageGuest2Ref.setValue(message);
                    }
                    else {
                        messageGuest3Ref.setValue(message);
                    }
                }
                else{
                    Toast.makeText(TournoiConnexion.this, "Message Vide", Toast.LENGTH_SHORT).show();
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
                else if (role.equals("Guest1")){
                    messageGuest1Ref.setValue(message);
                }
                else if (role.equals("Guest2")){
                    messageGuest2Ref.setValue(message);
                }
                else {
                    messageGuest3Ref.setValue(message);
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
                else if (role.equals("Guest1")){
                    messageGuest1Ref.setValue(message);
                }
                else if (role.equals("Guest2")){
                    messageGuest2Ref.setValue(message);
                }
                else {
                    messageGuest3Ref.setValue(message);
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
                else if (role.equals("Guest1")){
                    messageGuest1Ref.setValue(message);
                }
                else if (role.equals("Guest2")){
                    messageGuest2Ref.setValue(message);
                }
                else {
                    messageGuest3Ref.setValue(message);
                }
            }
        });
        nbplayers=firebaseDatabase.getReference("roomsTournoi/"+roomName+"/numberPlayers");
        nbplayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                numberPlayers=Integer.valueOf(value);
                if (numberPlayers==4 && role.equals("Host")){btndef.setEnabled(true);}
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        messageHostRef=firebaseDatabase.getReference("roomsTournoi/"+roomName+"/messageHost");
        messageGuest1Ref=firebaseDatabase.getReference("roomsTournoi/"+roomName+"/messageGuest1");
        messageGuest2Ref=firebaseDatabase.getReference("roomsTournoi/"+roomName+"/messageGuest2");
        messageGuest3Ref=firebaseDatabase.getReference("roomsTournoi/"+roomName+"/messageGuest3");
        scoreHostRef=firebaseDatabase.getReference("roomsTournoi/"+roomName+"/scoreHost");
        scoreGuest1Ref=firebaseDatabase.getReference("roomsTournoi/"+roomName+"/scoreGuest1");
        scoreGuest2Ref=firebaseDatabase.getReference("roomsTournoi/"+roomName+"/scoreGuest2");
        scoreGuest3Ref=firebaseDatabase.getReference("roomsTournoi/"+roomName+"/scoreGuest3");
        message=role+": Connected !";
        if (role.equals("Host")){
            messageHostRef.setValue(message);
        }
        else if (role.equals("Guest1")){
            messageGuest1Ref.setValue(message);
        }
        else if (role.equals("Guest2")){
            messageGuest2Ref.setValue(message);
        }
        else {
            messageGuest3Ref.setValue(message);
        }
        scoreHostRef.setValue("-1");
        scoreGuest1Ref.setValue("-1");
        scoreGuest2Ref.setValue("-1");
        scoreGuest3Ref.setValue("-1");
        addRoomEventListener();
        addScoreEventListener();
    }
    private void addScoreEventListener(){
        scoreHostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreHost = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TournoiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuest1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest1 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TournoiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuest2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest2 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TournoiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        scoreGuest3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreGuest3 = Integer.parseInt(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TournoiConnexion.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRoomEventListener(){
        messageHostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(role.equals("Host")){
                        Toast.makeText(TournoiConnexion.this, snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        if (scoreGuest3>-1 && scoreHost>-1 && scoreGuest2>-1 && scoreGuest1>-1 ){
                            btnres.setEnabled(true);                        }
                }else {
                        Toast.makeText(TournoiConnexion.this, snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        if (snapshot.getValue(String.class).contains("Leave")){
                            close();
                        }
                        else if (scoreGuest3>-1 && scoreHost>-1 && scoreGuest2>-1 && scoreGuest1>-1 ){
                            btnres.setEnabled(true);                        }

                        else if (snapshot.getValue(String.class).contains("StartGame")){
                            updateNote();
                            if (snapshot.getValue(String.class).contains("HoleEasy")){
                                startHole(1);
                            }
                            else if (snapshot.getValue(String.class).contains("HoleMedium")){
                                startHole(2);
                            }
                            else if (snapshot.getValue(String.class).contains("HoleHard")){
                                startHole(3);
                            }
                            else if (snapshot.getValue(String.class).contains("LabyrintheEasy")){
                                startLabyrinthe(1);
                            }
                            else if (snapshot.getValue(String.class).contains("LabyrintheMedium")){
                                startLabyrinthe(2);
                            }
                            else if (snapshot.getValue(String.class).contains("LabyrintheHard")){
                                startLabyrinthe(3);
                            }
                            else if (snapshot.getValue(String.class).contains("CandyCrushEasy")){
                                startCandyCrush(1);
                            }
                            else if (snapshot.getValue(String.class).contains("CandyCrushMedium")){
                                startCandyCrush(2);
                            }
                            else if (snapshot.getValue(String.class).contains("CandyCrushHard")){
                                startCandyCrush(3);
                            }
                            else if (snapshot.getValue(String.class).contains("NumbersEasy")){
                                startNumbers(1);
                            }
                            else if (snapshot.getValue(String.class).contains("NumbersMedium")){
                                startNumbers(2);
                            }
                            else if (snapshot.getValue(String.class).contains("NumbersHard")){
                                startNumbers(3);
                            }
                            else if (snapshot.getValue(String.class).contains("PenduEasy")){
                                startPendu(1);
                            }
                            else if (snapshot.getValue(String.class).contains("PenduMedium")){
                                startPendu(2);
                            }
                            else if (snapshot.getValue(String.class).contains("PenduHard")){
                                startPendu(3);
                            }
                            else if (snapshot.getValue(String.class).contains("LogoQuizzHard")){
                                startLogoQuizz("Hard");
                            }
                            else if (snapshot.getValue(String.class).contains("LogoQuizzMedium")){
                                startLogoQuizz("Medium");
                            }
                            else if (snapshot.getValue(String.class).contains("LogoQuizzEasy")){
                                startLogoQuizz("Easy");
                            }
                            else if (snapshot.getValue(String.class).contains("GoogleTrendsHard")){
                                startGoogleTrends("Hard");
                            }
                            else if (snapshot.getValue(String.class).contains("GoogleTrendsMedium")){
                                startGoogleTrends("Medium");
                            }
                            else{
                                startGoogleTrends("Easy");
                            }
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageHostRef.setValue(message);
            }
        });
        messageGuest1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!role.equals("Guest1")){
                        Toast.makeText(TournoiConnexion.this, snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        if (scoreGuest3>-1 && scoreHost>-1 && scoreGuest2>-1 && scoreGuest1>-1 ){
                            btnres.setEnabled(true);                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageGuest1Ref.setValue(message);
            }
        });
        messageGuest2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!role.equals("Guest2")){
                        Toast.makeText(TournoiConnexion.this, snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        if (scoreGuest3>-1 && scoreHost>-1 && scoreGuest2>-1 && scoreGuest1>-1 ){
                            btnres.setEnabled(true);                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageGuest2Ref.setValue(message);
            }
        });
        messageGuest3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!role.equals("Guest3")){
                        Toast.makeText(TournoiConnexion.this, snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        if (scoreGuest3>-1 && scoreHost>-1 && scoreGuest2>-1 && scoreGuest1>-1 ){
                            btnres.setEnabled(true);                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageGuest3Ref.setValue(message);
            }
        });
    }
    public void close(){
        Intent intent = new Intent(TournoiConnexion.this,TournoiRooms.class);
        startActivity(intent);
        TournoiConnexion.this.finish();
    }


    public void loadGame(){
        updateNote();
        if(role.equals("Host")){
            String aEnvoyer = role;
            //génération nb aleatoire
            int nombreAleatoire1 = (int)(Math.random() * 21);

            switch (nombreAleatoire1){
                case 0: startHole(1);
                    aEnvoyer+=": StartGame HoleEasy ";
                    break;
                case 1: startHole(2);
                    aEnvoyer+=": StartGame HoleMedium ";
                    break;
                case 2: startHole(3);
                    aEnvoyer+=": StartGame HoleHard ";
                    break;
                case 3: startLabyrinthe(1);
                    aEnvoyer+=": StartGame LabyrintheEasy ";
                    break;
                case 4: startLabyrinthe(2);
                    aEnvoyer+=": StartGame LabyrintheMedium ";
                    break;
                case 5: startLabyrinthe(3);
                    aEnvoyer+=": StartGame LabyrintheHard ";
                    break;
                case 6: startCandyCrush(1);
                    aEnvoyer+="StartGame CandyCrushEasy ";
                    break;
                case 7: startCandyCrush(2);
                    aEnvoyer+="StartGame CandyCrushMedium ";
                    break;
                case 8: startCandyCrush(3);
                    aEnvoyer+="StartGame CandyCrushHard ";
                    break;
                case 9: startNumbers(1);
                    aEnvoyer+="StartGame NumbersEasy ";
                    break;
                case 10: startNumbers(2);
                    aEnvoyer+="StartGame NumbersMedium ";
                    break;
                case 11: startNumbers(3);
                    aEnvoyer+="StartGame NumbersHard ";
                    break;
                case 12: startPendu(1);
                    aEnvoyer+="StartGame PenduEasy";
                    break;
                case 13: startPendu(2);
                    aEnvoyer+="StartGame PenduMedium";
                    break;
                case 14: startPendu(3);
                    aEnvoyer+="StartGame PenduHard";
                    break;
                case 15: startLogoQuizz("Hard");
                    aEnvoyer+="StartGame LogoQuizzHard";
                    break;
                case 16: startLogoQuizz("Medium");
                    aEnvoyer+="StartGame LogoQuizzMedium";
                    break;
                case 17: startLogoQuizz("Easy");
                    aEnvoyer+="StartGame LogoQuizzEasy";
                    break;
                case 18: startGoogleTrends("Hard");
                    aEnvoyer+="StartGame GoogleTrendsHard";
                    break;
                case 19: startGoogleTrends("Medium");
                    aEnvoyer+="StartGame GoogleTrendsMedium";
                    break;
                default: startGoogleTrends("Easy");
                    aEnvoyer+="StartGame GoogleTrendsEasy";
                    break;
            }
            messageHostRef.setValue(aEnvoyer);
        }
    }
    private void startLogoQuizz(String difficulte){
        Intent intent = new Intent(TournoiConnexion.this, LogoQuizz_Activity.class);
        intent.putExtra("extraDifficulty", difficulte);
        intent.putExtra("pathScoreMulti", "roomsTournoi/"+roomName+"/score"+role);
        intent.putExtra("pathMessageMulti", "roomsTournoi/"+roomName+"/message"+role);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startGoogleTrends(String difficulte){
        Intent intent = new Intent(TournoiConnexion.this, GoogleTrends_Activity.class);
        intent.putExtra("extraDifficulty", difficulte);
        intent.putExtra("pathScoreMulti", "roomsTournoi/"+roomName+"/score"+role);
        intent.putExtra("pathMessageMulti", "roomsTournoi/"+roomName+"/message"+role);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startLabyrinthe(int difficulte){
        Intent intent = new Intent(TournoiConnexion.this, Labyrinthe.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "roomsTournoi/"+roomName+"/score"+role);
        intent.putExtra("pathMessageMulti", "roomsTournoi/"+roomName+"/message"+role);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startHole(int difficulte){
        Intent intent = new Intent(TournoiConnexion.this, Hole.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "roomsTournoi/"+roomName+"/score"+role);
        intent.putExtra("pathMessageMulti", "roomsTournoi/"+roomName+"/message"+role);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startPendu(int difficulte){
        Intent intent = new Intent(TournoiConnexion.this, Pendu_Activity.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "roomsTournoi/"+roomName+"/score"+role);
        intent.putExtra("pathMessageMulti", "roomsTournoi/"+roomName+"/message"+role);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startCandyCrush(int difficulte){
        Intent intent = new Intent(TournoiConnexion.this, CandyCrush.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "roomsTournoi/"+roomName+"/score"+role);
        intent.putExtra("pathMessageMulti", "roomsTournoi/"+roomName+"/message"+role);
        intent.putExtra("role", role);
        startActivityForResult(intent, 1);
    }
    private void startNumbers(int difficulte){
        Intent intent = new Intent(TournoiConnexion.this, Numbers.class);
        intent.putExtra("level", difficulte);
        intent.putExtra("pathScoreMulti", "roomsTournoi/"+roomName+"/score"+role);
        intent.putExtra("pathMessageMulti", "roomsTournoi/"+roomName+"/message"+role);
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
                        Toast.makeText(TournoiConnexion.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TournoiConnexion.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}