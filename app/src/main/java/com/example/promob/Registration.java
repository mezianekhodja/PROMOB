package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private EditText userName, userMail, userPassword, userPhone;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String name, phone, password, mail;
    private static final String TAG = "Registration";
    private static final String KEY_t1 = "trophy1", KEY_t2 = "trophy2",KEY_t3 = "trophy3",KEY_t4 = "trophy4",
            KEY_t5 = "trophy5",KEY_t6 = "trophy6",KEY_t7 = "trophy7",KEY_t8 = "trophy8",KEY_t9 = "trophy9",
            KEY_t10 = "trophy10",KEY_t11 = "trophy11",KEY_t12 = "trophy12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    String user_email =userMail.getText().toString().trim();
                    String user_password =userPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                                                                                                                    @Override
                                                                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                                        if(task.isSuccessful()){
                                                                                                                            sendEmailVerification();

                                                                                                                        }
                                                                                                                        else {
                                                                                                                            Toast.makeText(Registration.this, "Registration Failed",Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                    }
                                                                                                                });
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
        userPhone = (EditText)findViewById(R.id.editTextPhone);
    }
    private Boolean validate(){
        Boolean result = false;
        name = userName.getText().toString();
        mail = userMail.getText().toString();
        password = userPassword.getText().toString();
        phone = userPhone.getText().toString();
        if((!name.isEmpty()) &&(!mail.isEmpty())&&(!password.isEmpty())&&(!phone.isEmpty())){
            result=true;
        }
        else{
            Toast.makeText(this, "Veuillez tout renseigner",Toast.LENGTH_SHORT);
        }
        return result;
    }
    private void openPage(){
        Intent intent = new Intent(this, Entrainement.class);
        startActivity(intent);
        Registration.this.finish();
    }
    private void openMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Registration.this.finish();
    }
    private void openCoPage(){
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
        Registration.this.finish();
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserdata();
                        Toast.makeText(Registration.this, "Successfully register",Toast.LENGTH_SHORT).show();
                        saveNote();
                        firebaseAuth.signOut();
                        openCoPage();
                    }
                    else{
                        Toast.makeText(Registration.this, "Failed registration",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserdata(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(name, mail, phone);
        myRef.setValue(userProfile);
    }
    public void saveNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_t1,"false");
        note.put(KEY_t2,"Home Sweet Home");
        note.put(KEY_t3,"false");
        note.put(KEY_t4,"false");
        note.put(KEY_t5,"false");
        note.put(KEY_t6,"false");
        note.put(KEY_t7,"false");
        note.put(KEY_t8,"false");
        note.put(KEY_t9,"false");
        note.put(KEY_t10,"false");
        note.put(KEY_t11,"false");
        note.put(KEY_t12,"false");
            db.collection("Trophy").document(name).set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Registration.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, "Fail", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
    }
}