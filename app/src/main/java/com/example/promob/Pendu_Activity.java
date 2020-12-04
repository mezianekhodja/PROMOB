package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Pendu_Activity extends AppCompatActivity {

    private static final long COUNTDOWN_IN_MILLIS = 60000;
    private CountDownTimer counterDownTimer;
    private long timeLeftInMillis;
    private static final String KEY_t8 = "trophy8";
    private LinearLayout container;
    private Button btn_send;
    private TextView lettres_tapees;
    private ImageView image;
    private EditText et_letter;
    private String word;
    private int found;
    private int error;
    private int move=0;
    private List<Character> listOfLetters = new ArrayList<Character>();
    private boolean win;
    private TextView textViewTimer;
    private List<String> listOfWords1 = new ArrayList<String>();
    private List<String> listOfWords2 = new ArrayList<String>();
    private List<String> listOfWords3 = new ArrayList<String>();

    //GESTION BDD
    private FirebaseAuth firebaseAuth;
    String username = "Undefined";
    private static final String KEY_USER = "user", KEY_ERROR = "error", KEY_DATE="date";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseDatabase firebaseDatabase;
    String currentTime = Calendar.getInstance().getTime().toString();
    int highscore_global = 7;
    int highscore_user = 7;
    private static final String TAG = "Pendu_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendu);
        firebaseAuth = FirebaseAuth.getInstance();

        container = (LinearLayout) findViewById(R.id.word_container);
        btn_send = (Button) findViewById(R.id.button_sendpendu);
        lettres_tapees = (TextView) findViewById(R.id.tev_lettres_tapees);
        image = (ImageView) findViewById(R.id.iv_pendu);
        et_letter = (EditText)findViewById(R.id.etletter);
        setListOfWords();
        initGame();

        //GESTION BDD
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                username=userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError e) {
                Toast.makeText(Pendu_Activity.this, e.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letterFromInput = et_letter.getText().toString().toUpperCase();
                et_letter.setText("");

                if (letterFromInput.length()>0){
                    if (!letterAlreadyUsed(letterFromInput.charAt(0),listOfLetters)){
                            move++;
                            if(move==1){
                                if(!username.equals("invite")){
                                    loadNote();
                                }
                            }
                            listOfLetters.add(letterFromInput.charAt(0));
                            checkLetterContained(letterFromInput,word);
                    }
                    if (found == word.length()){
                        win= true;
                        createDialog();
                    }
                    if(!word.contains(letterFromInput)){
                        error++;
                    }
                    setImage();
                    if (error==6){
                        win= false;
                        createDialog();
                     }
                    showAllLetters(listOfLetters);
                }
            }
        });

        /*rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterDownTimer.cancel();
                initGame();
            }
        });*/
    }

    public void initGame(){
        word = generateWord().toUpperCase();
        win = false;
        error = 0;
        found = 0;
        lettres_tapees.setText("");
        image.setBackgroundResource(R.drawable.pendu1);
        listOfLetters = new ArrayList<>();
        textViewTimer = (TextView) findViewById(R.id.timer);
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();

        container.removeAllViews();
        for(int i=0;i<word.length();i++){
            TextView oneletter = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            container.addView(oneletter);
            }
        }
        public boolean letterAlreadyUsed(char a, List<Character> list){
            for(int i = 0; i<list.size();i++){
                if(list.get(i)==a){
                    Toast.makeText(this, "Lettre déja tapée",Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            return false;
        }
        public void checkLetterContained(String letter, String word){
            for(int i = 0; i<word.length();i++){
                if(letter.equals(String.valueOf(word.charAt(i)))){
                    found++;
                    TextView tv = (TextView) container.getChildAt(i);
                    tv.setText(String.valueOf(word.charAt(i)));
                }
            }
        }

        public void showAllLetters (List<Character> listOfLetters){
            String chaine ="";
            for (int i=0;i<listOfLetters.size();i++){
                    chaine += listOfLetters.get(i) +"\n";
            }
            if (!chaine.equals("")){
                lettres_tapees.setText(chaine);
            }
        }

        public void setImage (){
            switch(error){
                case 1 :
                    image.setBackgroundResource(R.drawable.pendu2);
                    break;
                case 2 :
                    image.setBackgroundResource(R.drawable.pendu3);
                    break;
                case 3 :
                    image.setBackgroundResource(R.drawable.pendu4);
                    break;
                case 4 :
                    image.setBackgroundResource(R.drawable.pendu5);
                    break;
                case 5 :
                    image.setBackgroundResource(R.drawable.pendu6);
                    break;
                case 6 :
                    updateNote();
                    image.setBackgroundResource(R.drawable.pendu7);
                    break;
            }
        }

        public void createDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Vous avez gagné");
            counterDownTimer.cancel();
            if(!win){
                builder.setTitle("Vous avez perdu");
                builder.setMessage("Le mot à trouver était : "+word);
            }
            else{
                if(!username.equals("invite")){
                    saveNote();
                }
            }
            builder.create().show();
            String multipath = getIntent().getStringExtra("pathScoreMulti");
            if (!multipath.equals("notMulti")){
                firebaseDatabase.getReference(multipath).setValue(String.valueOf(6-error));
            }
            finish();
        }

        public String generateWord() {
        int level = getIntent().getExtras().getInt("level");

        if (level == 1){
            int random = (int) Math.floor(Math.random()*listOfWords1.size());
            return listOfWords1.get(random).trim();
        }
        else if (level == 2){
                int random = (int) Math.floor(Math.random()*listOfWords2.size());
                return listOfWords2.get(random).trim();
        }
        else{
            int random = (int) Math.floor(Math.random()*listOfWords3.size());
            return listOfWords3.get(random).trim();
            }
        }

        public void setListOfWords(){
            //EASY
            listOfWords1.add("ingenieur");
            listOfWords1.add("signal");
            listOfWords1.add("donnee");
            listOfWords1.add("reseau");
            listOfWords1.add("internet");
            listOfWords1.add("hacker");
            listOfWords1.add("ordinateur");
            listOfWords1.add("android");
            listOfWords1.add("iot");
            listOfWords1.add("ip");
            listOfWords1.add("mail");
            listOfWords1.add("bug");
            listOfWords1.add("souris");
            listOfWords1.add("clavier");
            listOfWords1.add("apple");
            listOfWords1.add("bit");
            listOfWords1.add("menu");
            listOfWords1.add("page");
            listOfWords1.add("pixel");
            listOfWords1.add("url");
            listOfWords1.add("chat");
            listOfWords1.add("dossier");
            listOfWords1.add("gif");
            listOfWords1.add("html");
            listOfWords1.add("css");
            listOfWords1.add("java");
            listOfWords1.add("jpeg");
            listOfWords1.add("wifi");
            listOfWords1.add("url");

            //MEDIUM
            listOfWords2.add("logiciel");
            listOfWords2.add("cookie");
            listOfWords2.add("canal");
            listOfWords2.add("spectre");
            listOfWords2.add("informatique");
            listOfWords2.add("electronique");
            listOfWords2.add("modulation");
            listOfWords2.add("communication");
            listOfWords2.add("developpeur");
            listOfWords2.add("developpement");
            listOfWords2.add("informaticien");
            listOfWords2.add("methodologie");
            listOfWords2.add("agile");
            listOfWords2.add("documentation");
            listOfWords2.add("processeur");
            listOfWords2.add("routeur");
            listOfWords2.add("smartphone");
            listOfWords2.add("filtre");
            listOfWords2.add("diagramme");
            listOfWords2.add("instruction");
            listOfWords2.add("embarque");
            listOfWords2.add("commande");
            listOfWords2.add("activite");
            listOfWords2.add("telecommunication");
            listOfWords2.add("systeme");
            listOfWords2.add("octet");
            listOfWords2.add("antivirus");
            listOfWords2.add("bluetooth");
            listOfWords2.add("firewall");
            listOfWords2.add("fai");
            listOfWords2.add("ftp");
            listOfWords2.add("navigateur");
            listOfWords2.add("protocole");
            listOfWords2.add("routeur");
            listOfWords2.add("serveur");

            //HARD
            listOfWords3.add("troyan");
            listOfWords3.add("domotique");
            listOfWords3.add("demodulation");
            listOfWords3.add("abaque");
            listOfWords3.add("microcontroleur");
            listOfWords3.add("cybersecurite");
            listOfWords3.add("cyberphysique");
            listOfWords3.add("cryptographie");
            listOfWords3.add("architecture");
            listOfWords3.add("hexadecimal");
            listOfWords3.add("nyquist");
            listOfWords3.add("webmaster");
            listOfWords3.add("connectivite");
            listOfWords3.add("ascii");
            listOfWords3.add("defragmentation");
            listOfWords3.add("spyware");
        }
    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        Pendu_Activity.this.finish();
    }
    private void Logout() {
        firebaseAuth.signOut();
        openActivityConnexion();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public void openProfil() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        Pendu_Activity.this.finish();
    }

    public void openAcceuil(){
        Intent intent = new Intent(this, Entrainement.class);
        startActivity(intent);
        Pendu_Activity.this.finish();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.profileMenu:{
                openProfil();
                break;
            }
            case R.id.acceuilMenu:{
                openAcceuil();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                      PARTIE TIMER

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    private void startCountDown() {
        counterDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis= 0;
                error=6;
                updateCountdownText();
                createDialog();
            }
        }.start();
    }

    private void updateCountdownText(){
        int minutes = (int) (timeLeftInMillis/1000)/60;
        int seconds = (int) (timeLeftInMillis/1000)%60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewTimer.setText(timeFormatted);
        if(timeLeftInMillis<10000){
            textViewTimer.setTextColor(Color.RED);
        }
        else{
            textViewTimer.setTextColor(Color.BLACK);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(counterDownTimer!=null){
            counterDownTimer.cancel();
        }
    }
    public void loadNote(){
        int level = getIntent().getExtras().getInt("level");
        db.collection("Pendu_level_"+level).document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreglob = documentSnapshot.get(KEY_ERROR).toString();
                            highscore_global=Integer.parseInt(scoreglob);
                        }
                        else{
                            Toast.makeText(Pendu_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Pendu_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("Pendu_level_"+level).document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreus = documentSnapshot.get(KEY_ERROR).toString();
                            highscore_user=Integer.parseInt(scoreus);                        }
                        else{
                            Toast.makeText(Pendu_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Pendu_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void saveNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_USER,username);
        note.put(KEY_ERROR,error);
        note.put(KEY_DATE,currentTime);

        int level = getIntent().getExtras().getInt("level");
        if (error<highscore_global){
            db.collection("Pendu_level_"+level).document("highscore_global").set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Pendu_Activity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Pendu_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });

        }
        if (error<highscore_user){
            db.collection("Pendu_level_"+level).document("highscore_"+username).set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Pendu_Activity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Pendu_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
        }
    }
    public void updateNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_t8,"Louis XVI");

        db.collection("Trophy").document(username).update(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Pendu_Activity.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Pendu_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}