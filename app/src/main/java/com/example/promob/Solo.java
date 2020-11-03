package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Solo extends AppCompatActivity  {
    private Button entrainement,defi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo);

        entrainement = (Button)findViewById(R.id.btn_entrainement);
        defi = (Button)findViewById(R.id.btn_defi);

        entrainement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEntrainement();
            }
        });
        defi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDefi();
            }
        });
    }
    public void openEntrainement() {
        Intent intent = new Intent(this, Entrainement.class);
        startActivity(intent);
    }

    public void openDefi() {
        Intent intent = new Intent(this, Defi.class);
        startActivity(intent);
    }

}
