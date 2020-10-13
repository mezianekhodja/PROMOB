package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    private EditText userName, userMail, userPassword;
    private Button regButton;
    private TextView userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpUIViews();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    openPage();
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    openMainPage();
                }
            }
        });
    }
    private void setUpUIViews(){
        userName = (EditText)findViewById(R.id.editTextRegusername);
        userMail = (EditText)findViewById(R.id.editTextRegmail);
        userPassword= (EditText)findViewById(R.id.editTextRegPassword);
        regButton= (Button)findViewById(R.id.buttonReginscription);
        userLogin= (TextView)findViewById(R.id.textViewRegconnexion);
    }
    private Boolean validate(){
        Boolean result = false;
        String name = userName.getText().toString();
        String mail = userMail.getText().toString();
        String password = userPassword.getText().toString();
        if((!name.isEmpty()) &&(!mail.isEmpty())&&(!password.isEmpty())){
            result=true;
        }
        else{
            Toast.makeText(this, "Veuillez tout renseigner",Toast.LENGTH_SHORT);
        }
        return result;
    }
    private void openPage(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    private void openMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}