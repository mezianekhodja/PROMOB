package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Pendu extends AppCompatActivity {

    private LinearLayout container;
    private Button btn_send;
    private TextView lettres_tapees;
    private ImageView image;
    private EditText letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendu);

        container = (LinearLayout) findViewById(R.id.word_container);
        btn_send = (Button) findViewById(R.id.button_sendpendu);
        lettres_tapees = (TextView) findViewById(R.id.tev_lettres_tapees);
        image = (ImageView) findViewById(R.id.iv_pendu);
        letter = (EditText)findViewById(R.id.etletter);
    }
}