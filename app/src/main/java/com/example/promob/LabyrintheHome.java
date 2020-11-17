package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LabyrintheHome extends AppCompatActivity {
    private Button easy,medium, hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinthe_home);

        easy = (Button) findViewById(R.id.button_lb_easy);
        medium = (Button) findViewById(R.id.button_lb_medium);
        hard = (Button) findViewById(R.id.button_lb_hard);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabyrintheHome.this, Labyrinthe.class);
                intent.putExtra("level",1);
                startActivity(intent);
                LabyrintheHome.this.finish();
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabyrintheHome.this, Labyrinthe.class);
                intent.putExtra("level",2);
                startActivity(intent);
                LabyrintheHome.this.finish();
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LabyrintheHome.this, Labyrinthe.class);
                intent.putExtra("level",3);
                startActivity(intent);
                LabyrintheHome.this.finish();
            }
        });
    }

}