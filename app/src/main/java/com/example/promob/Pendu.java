package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Pendu extends AppCompatActivity {

    private LinearLayout container;
    private Button btn_send;
    private TextView lettres_tapees;
    private ImageView image;
    private EditText et_letter;
    private String word;
    private int found;
    private int error;
    private List<Character> listOfLetters = new ArrayList<Character>();
    private boolean win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendu);

        container = (LinearLayout) findViewById(R.id.word_container);
        btn_send = (Button) findViewById(R.id.button_sendpendu);
        lettres_tapees = (TextView) findViewById(R.id.tev_lettres_tapees);
        image = (ImageView) findViewById(R.id.iv_pendu);
        et_letter = (EditText)findViewById(R.id.etletter);

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
                }
            }
        });
    }

    public void initGame(){
        word = "INGENIEUR";
        win = false;
        error = 0;
        found = 0;
        lettres_tapees.setText("");
        image.setBackgroundResource(R.drawable.pendu1);

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
}