package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pendu_Activity extends AppCompatActivity {

    private static final long COUNTDOWN_IN_MILLIS = 60000;
    private CountDownTimer counterDownTimer;
    private long timeLeftInMillis;

    private LinearLayout container;
    private Button btn_send, rejouer;
    private TextView lettres_tapees;
    private ImageView image;
    private EditText et_letter;
    private String word;
    private int found;
    private int error;
    private List<Character> listOfLetters = new ArrayList<Character>();
    private boolean win;
    private TextView textViewTimer;
    private List<String> listOfWords1 = new ArrayList<String>();
    private List<String> listOfWords2 = new ArrayList<String>();
    private List<String> listOfWords3 = new ArrayList<String>();

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendu);
        firebaseAuth = FirebaseAuth.getInstance();

        container = (LinearLayout) findViewById(R.id.word_container);
        btn_send = (Button) findViewById(R.id.button_sendpendu);
        rejouer = (Button) findViewById(R.id.buttonrejouerpendu);
        lettres_tapees = (TextView) findViewById(R.id.tev_lettres_tapees);
        image = (ImageView) findViewById(R.id.iv_pendu);
        et_letter = (EditText)findViewById(R.id.etletter);
        setListOfWords();
        initGame();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letterFromInput = et_letter.getText().toString().toUpperCase();
                et_letter.setText("");

                if (letterFromInput.length()>0){
                    if (!letterAlreadyUsed(letterFromInput.charAt(0),listOfLetters)){
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

        rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               initGame();
            }
        });
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
                    image.setBackgroundResource(R.drawable.pendu7);
                    break;
            }
        }

        public void createDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Vous avez gagné");
            if(!win){
                builder.setTitle("Vous avez perdu");
                builder.setMessage("Le mot à trouver était : "+word);
            }
            builder.create().show();
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
            int random = (int) Math.floor(Math.random()*listOfWords3.size());
            return listOfWords3.get(random).trim();
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
        Intent intent = new Intent(this, Home.class);
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

}