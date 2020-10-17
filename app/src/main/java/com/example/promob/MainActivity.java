package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    private Button connexion,invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connexion = (Button)findViewById(R.id.btn_P1_connexion);
        invite = (Button)findViewById(R.id.btn_P1_invite);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConnexion();
            }
        });
    }
    public void openConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
}
