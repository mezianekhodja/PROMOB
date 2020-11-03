package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Solo_Multi extends AppCompatActivity  {
    private Button solo,multi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_ou_multi);

        solo = (Button)findViewById(R.id.btn_solo);
        multi = (Button)findViewById(R.id.btn_multijoueur);

        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSolo();
            }
        });
        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMulti();
            }
        });
    }
    public void openSolo() {
        Intent intent = new Intent(this, Solo.class);
        startActivity(intent);
        Solo_Multi.this.finish();
    }

    public void openMulti() {
        Intent intent = new Intent(this, Multi.class);
        startActivity(intent);
        Solo_Multi.this.finish();
    }

}
