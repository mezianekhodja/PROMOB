package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  {
    private Button connexion,invite;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        connexion = (Button)findViewById(R.id.btn_P1_connexion);
        invite = (Button)findViewById(R.id.btn_P1_invite);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConnexion();
            }
        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate("invitevmgames@gmail.com","inviteVMG");
            }
        });
    }
    public void openConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    private void validate(String username, String password){
       FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            firebaseAuth.signOut();
        }
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    openHome();
                }
            }
        });
    }
    private void openHome(){
        Intent intent = new Intent(this, Solo_Multi.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
}
