package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MultiResults extends AppCompatActivity {
    TextView tvres;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_results);
        tvres= findViewById(R.id.textViewResultsMulti);
        btn = findViewById(R.id.buttonRetourMulti);

        String winner = getIntent().getExtras().getString("winner");
        String role = getIntent().getExtras().getString("role");
        if(role.equals(winner)){
            tvres.setText("Victoire");
        }else {
            tvres.setText("Défaite");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiResults.this,Multi.class);
                startActivity(intent);
                MultiResults.this.finish();
            }
        });
    }
}