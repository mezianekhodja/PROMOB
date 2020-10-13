package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Connexion extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private TextView info;
    private Button login;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        name = (EditText) findViewById(R.id.editTextTextPersonName);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        info = (TextView)findViewById(R.id.info);
        login = (Button)findViewById(R.id.buttonlog);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(),password.getText().toString());
            }
        });
    }

    private void validate(String username, String password){
        if((username.equals("Admin"))&&(password.equals("mdp"))){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
        else{
            counter--;
            info.setText("nombre d'essais restants = "+String.valueOf(counter));
        }
        if(counter == 0){
            login.setEnabled(false);
        }
    }
}