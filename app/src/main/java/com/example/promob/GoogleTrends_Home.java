package com.example.promob;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class GoogleTrends_Home extends AppCompatActivity {

    Button btn_GoogleTrends;
    private FirebaseAuth firebaseAuth;
    private static final int REQUEST_CODE_QUIZ =1;
    public static String EXTRA_DIFFICULTY = "extraDifficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googletrends_home);

        TextView Easy = findViewById(R.id.Easy);
        TextView Medium = findViewById(R.id.Medium);
        TextView Hard = findViewById(R.id.Hard);


        btn_GoogleTrends = (Button) findViewById(R.id.button_easy);
        btn_GoogleTrends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleTrendsEasy();
            }
        });

        btn_GoogleTrends = (Button) findViewById(R.id.button_medium);
        btn_GoogleTrends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleTrendsMedium();
            }
        });
        btn_GoogleTrends = (Button) findViewById(R.id.button_hard);
        btn_GoogleTrends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleTrendsHard();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void openGoogleTrendsEasy() {
        //EXTRA_DIFFICULTY = "Easy";
        Intent intent = new Intent(this, GoogleTrends_Activity.class);
        intent.putExtra(EXTRA_DIFFICULTY, "Easy");
        intent.putExtra("pathScoreMulti", "notMulti");
        startActivityForResult(intent, REQUEST_CODE_QUIZ); //Avoir un resultat d'une autre activité
    }

    public void  openGoogleTrendsMedium() {
        Intent intent = new Intent(this, GoogleTrends_Activity.class);
        intent.putExtra(EXTRA_DIFFICULTY, "Medium");
        intent.putExtra("pathScoreMulti", "notMulti");
        startActivityForResult(intent, REQUEST_CODE_QUIZ); //Avoir un resultat d'une autre activité
    }

    public void  openGoogleTrendsHard() {
        Intent intent = new Intent(this, GoogleTrends_Activity.class);
        intent.putExtra(EXTRA_DIFFICULTY, "Hard");
        intent.putExtra("pathScoreMulti", "notMulti");
        startActivityForResult(intent, REQUEST_CODE_QUIZ); //Avoir un resultat d'une autre activité
    }

    public void openActivityConnexion() {
        Intent intent = new Intent(this, Connexion.class);
        startActivity(intent);
    }

    private void Logout() {
        firebaseAuth.signOut();
        openActivityConnexion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void openProfil() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        GoogleTrends_Home.this.finish();
    }

    public void openClassement() {
        finish();
        Intent intent = new Intent(this, Classement.class);
        startActivity(intent);
    }

    public void openCopyright() {
        finish();
        Intent intent = new Intent(this, Copyright.class);
        startActivity(intent);
    }

    public void openAcceuil() {
        finish();
        Intent intent = new Intent(this, Entrainement.class);
        startActivity(intent);
    }

    public void openTrophy() {
        finish();
        Intent intent = new Intent(this, Trophy.class);
        startActivity(intent);
    }

    public void openSoloMulti() {
        finish();
        Intent intent = new Intent(this, Solo_Multi.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                Logout();
            }
            case R.id.profileMenu: {
                openProfil();
            }
            case R.id.acceuilMenu: {
                openAcceuil();
                break;
            }
            case R.id.copyrightMenu: {
                openCopyright();
                break;
            }
            case R.id.trophyMenu: {
                openTrophy();
                break;
            }
            case R.id.classementMenu: {
                openClassement();
                break;
            }
            case R.id.solomultiMenu: {
                openSoloMulti();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
