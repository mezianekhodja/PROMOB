package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class Pendu extends AppCompatActivity {

    private LinearLayout container;
    private Button btn_send,rejouer;
    private TextView lettres_tapees;
    private ImageView image;
    private EditText et_letter;
    private String word;
    private int found;
    private int error;
    private List<Character> listOfLetters = new ArrayList<Character>();
    private boolean win;
    private List<String> listOfWords = new ArrayList<String>();

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
        int random = (int) Math.floor(Math.random()*listOfWords.size());
        return listOfWords.get(random).trim();
         }

        public void setListOfWords(){
            listOfWords.add("ingenieur");
            listOfWords.add("domotique");
            listOfWords.add("informatique");
            listOfWords.add("electronique");
            listOfWords.add("signal");
            listOfWords.add("modulation");
            listOfWords.add("donnee");
            listOfWords.add("communication");
            listOfWords.add("ordinateur");
            listOfWords.add("demodulation");
            listOfWords.add("canal");
            listOfWords.add("spectre");
            listOfWords.add("abaque");
            listOfWords.add("developpeur");
            listOfWords.add("developpement");
            listOfWords.add("informaticien");
            listOfWords.add("methodologie");
            listOfWords.add("agile");
            listOfWords.add("documentation");
            listOfWords.add("processeur");
            listOfWords.add("microcontroleur");
            listOfWords.add("instruction");
            listOfWords.add("embarque");
            listOfWords.add("commande");
            listOfWords.add("reseau");
            listOfWords.add("routeur");
            listOfWords.add("smartphone");
            listOfWords.add("internet");
            listOfWords.add("filtre");
            listOfWords.add("diagramme");
            listOfWords.add("hacker");
            listOfWords.add("cybersecurite");
            listOfWords.add("crypyographie");
            listOfWords.add("activite");
            listOfWords.add("android");
            listOfWords.add("telecommunication");
        }
    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        Pendu.this.finish();
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
        Pendu.this.finish();
    }

    public void openAcceuil(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        Pendu.this.finish();
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
}