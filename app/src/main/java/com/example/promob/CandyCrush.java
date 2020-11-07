package com.example.promob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandyCrush extends AppCompatActivity {

    private static final String TAG = "CandyCrush";
    int[] candies = {
        R.drawable.candycrush_bluecandy, R.drawable.candycrush_greencandy,R.drawable.candycrush_orangecandy,
            R.drawable.candycrush_purplecandy,R.drawable.candycrush_redcandy,R.drawable.candycrush_yellowcandy,
            R.drawable.bombcc,R.drawable.ccwrappedblue, R.drawable.ccwrappedgreen,R.drawable.ccwrappedorange,
            R.drawable.ccwrappedpurple,R.drawable.ccwrappedred, R.drawable.ccwrappedyellow,R.drawable.ccstripedbluehorizontal,
            R.drawable.ccstripedbluevertical, R.drawable.ccstripedgreenhorizontal,R.drawable.ccstripedgreenvertical,
            R.drawable.ccstripedorangehorizontal, R.drawable.ccstripedorangevertical,R.drawable.ccstripedpurplehorizontal,
            R.drawable.ccstripedpurplevertical, R.drawable.ccstripedredvertical,R.drawable.ccstripedredhorizontal,
            R.drawable.ccstripedyellowhorizontal, R.drawable.ccstripedyellowvertical
    };
    int[] candiesstart = {
            R.drawable.candycrush_bluecandy, R.drawable.candycrush_greencandy,R.drawable.candycrush_orangecandy,
            R.drawable.candycrush_purplecandy,R.drawable.candycrush_redcandy,R.drawable.candycrush_yellowcandy,
    };
    int widthBlock, widthScreen, heightScreen, numberBlocks,candyToBeDragged, candyToBeReplaced, notCandy=R.drawable.ic_launcher_background;
    ArrayList<ImageView> candy = new ArrayList<>();
    Handler mHandler = new Handler();
    int interval = 100;
    TextView scoreRes, moveRes;
    int score = 0;
    int move  = 30;
    String username = "Undefined";
    private static final String KEY_USER = "user", KEY_SCORE = "score", KEY_DATE="date";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    String currentTime = Calendar.getInstance().getTime().toString();
    int highscore_global = 0;
    int highscore_user = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candy_crush);

        int level = getIntent().getExtras().getInt("level");
        if (level == 1){
            numberBlocks=8;
        }
        else if (level == 2){
            numberBlocks=7;
        }
        else{
            numberBlocks=6;
        }

        DisplayMetrics displayMetrics =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthScreen = displayMetrics.widthPixels;
        heightScreen = displayMetrics.heightPixels;
        widthBlock = widthScreen / numberBlocks;
        scoreRes = (TextView) findViewById(R.id.tvscoreCC);
        moveRes = (TextView) findViewById(R.id.tvmoveCC);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                username=userProfile.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CandyCrush.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        createBoard();

        for (final ImageView imageView : candy){

            imageView.setOnTouchListener(new OnSwipeListener(CandyCrush.this){
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged -1;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                    checkBomb();
                    checkStripedHorizontal();
                    checkWrapped();
                    move--;
                    moveRes.setText(String.valueOf(move));
                    if (move==0){
                        createDialog();
                    }
                }

                @Override
                void onSwipeRight(){
                    super.onSwipeRight();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged +1;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                    checkBomb();
                    checkStripedHorizontal();
                    checkWrapped();
                    move--;
                    moveRes.setText(String.valueOf(move));
                    if (move==0){
                        createDialog();
                    }
                }

                @Override
                void onSwipeTop(){
                    super.onSwipeTop();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged -numberBlocks;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                    checkBomb();
                    checkStripedVertical();
                    checkWrapped();
                    move--;
                    moveRes.setText(String.valueOf(move));
                    if (move==0){
                        createDialog();
                    }
                }

                @Override
                void onSwipeBottom(){
                    super.onSwipeBottom();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged +numberBlocks;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                    checkBomb();
                    checkStripedVertical();
                    checkWrapped();
                    move--;
                    moveRes.setText(String.valueOf(move));
                    if (move==0){
                        createDialog();
                    }
                }
            });
        }
        mHandler = new Handler();
        startRepeat();
    }

    public void createDialog() {
        saveNote();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Votre score est de  : "+String.valueOf(score));
        builder.setMessage("Le nombre de mouvements a été atteint");
        builder.create().show();
        finishGame();
    }
    private void finishGame(){
        Intent intent = new Intent(this, CandyCrush_Home.class);
        intent.putExtra("score", score);
        startActivity(intent);
        CandyCrush.this.finish();
    }
    private void createBoard() {
        //loadNote();
        GridLayout gridLayout = findViewById(R.id.boardCC);
        gridLayout.setRowCount(numberBlocks);
        gridLayout.setColumnCount(numberBlocks);
        gridLayout.getLayoutParams().width = widthScreen;
        gridLayout.getLayoutParams().height = heightScreen;
        moveRes.setText(String.valueOf(move));
        for (int i = 0; i< numberBlocks*numberBlocks;i++){
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(widthBlock, widthBlock));
            imageView.setMaxHeight(widthBlock);
            imageView.setMaxWidth(widthBlock);
            int randomCandy = (int) Math.floor(Math.random()*candiesstart.length);
            imageView.setImageResource(candiesstart[randomCandy]);
            imageView.setTag(candiesstart[randomCandy]);
            candy.add(imageView);
            gridLayout.addView(imageView);
        }
    }

    private void checkStripedHorizontal(){
        if((((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedyellowhorizontal)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_yellowcandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedredhorizontal)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_redcandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedgreenhorizontal)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_greencandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedbluehorizontal)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_bluecandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedorangehorizontal)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_orangecandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedpurplehorizontal)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_purplecandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedyellowhorizontal)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_yellowcandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedredhorizontal)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_redcandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedgreenhorizontal)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_greencandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedbluehorizontal)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_bluecandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedorangehorizontal)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_orangecandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedpurplehorizontal)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_purplecandy))){
            removeRow(candy.get(candyToBeDragged).getId());
        }
    }

    private void checkStripedVertical(){
        if((((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedyellowvertical)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_yellowcandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedredvertical)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_redcandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedgreenvertical)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_greencandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedbluevertical)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_bluecandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedorangevertical)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_orangecandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccstripedpurplevertical)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_purplecandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedyellowvertical)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_yellowcandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedredvertical)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_redcandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedgreenvertical)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_greencandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedbluevertical)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_bluecandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedorangevertical)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_orangecandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccstripedpurplevertical)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_purplecandy))){
            removeColumn(candy.get(candyToBeDragged).getId());
        }
    }

    private void checkWrapped(){
        if((((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccwrappedyellow)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_yellowcandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccwrappedred)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_redcandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccwrappedgreen)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_greencandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccwrappedblue)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_bluecandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccwrappedorange)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_orangecandy))||
                (((int) candy.get(candyToBeDragged).getTag() == R.drawable.ccwrappedpurple)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_purplecandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccwrappedyellow)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_yellowcandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccwrappedred)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_redcandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccwrappedgreen)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_greencandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccwrappedblue)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_bluecandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccwrappedorange)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_orangecandy))||
                (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.ccwrappedpurple)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_purplecandy))){
            removeNeighboors(candy.get(candyToBeDragged).getId());
        }
    }
    private void checkBomb(){
        if(((int) candy.get(candyToBeDragged).getTag() == R.drawable.bombcc)||((int) candy.get(candyToBeReplaced).getTag() == R.drawable.bombcc)){
            removeKindCandy((int) candy.get(candyToBeDragged).getTag());
            removeKindCandy((int) candy.get(candyToBeReplaced).getTag());
        }
    }

    private void checkRowForThree(){
        for (int i=1; i<(numberBlocks*numberBlocks)-1;i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            if ((i%numberBlocks!=0)&&((i%numberBlocks!=7))) {
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-1).getTag() == chosedCandy  &&
                        (int) candy.get(x+1).getTag() == chosedCandy) {
                    score = score+3;
                    scoreRes.setText(String.valueOf(score));
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    candy.get(x-1).setImageResource(notCandy);
                    candy.get(x-1).setTag(notCandy);
                    candy.get(x+1).setImageResource(notCandy);
                    candy.get(x+1).setTag(notCandy);
                }
            }
        }
    }
    private void checkRowForFour(){
        for (int i=2; i<(numberBlocks*numberBlocks)-1;i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;

            if ((i%numberBlocks!=0)&&((i%numberBlocks!=(numberBlocks-1)))&&(i%numberBlocks!=1)) {
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-1).getTag() == chosedCandy  &&
                        (int) candy.get(x+1).getTag() == chosedCandy &&
                        (int) candy.get(x-2).getTag() == chosedCandy)
                {
                    score = score+4;
                    scoreRes.setText(String.valueOf(score));

                    if (chosedCandy == R.drawable.candycrush_bluecandy) {
                        candy.get(x).setImageResource( R.drawable.ccstripedbluehorizontal);
                        candy.get(x).setTag(R.drawable.ccstripedbluehorizontal);
                    }
                    else if (chosedCandy == R.drawable.candycrush_greencandy) {
                        candy.get(x).setImageResource( R.drawable.ccstripedgreenhorizontal);
                        candy.get(x).setTag(R.drawable.ccstripedgreenhorizontal);
                    }
                    else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                        candy.get(x).setImageResource( R.drawable.ccstripedyellowhorizontal);
                        candy.get(x).setTag(R.drawable.ccstripedyellowhorizontal);
                    }
                    else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                        candy.get(x).setImageResource( R.drawable.ccstripedorangehorizontal);
                        candy.get(x).setTag(R.drawable.ccstripedorangehorizontal);
                    }
                    else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                        candy.get(x).setImageResource( R.drawable.ccstripedpurplehorizontal);
                        candy.get(x).setTag(R.drawable.ccstripedpurplehorizontal);
                    }
                    else {
                        candy.get(x).setImageResource( R.drawable.ccstripedredhorizontal);
                        candy.get(x).setTag(R.drawable.ccstripedredhorizontal);
                    }
                    candy.get(x-1).setImageResource(notCandy);
                    candy.get(x-1).setTag(notCandy);
                    candy.get(x+1).setImageResource(notCandy);
                    candy.get(x+1).setTag(notCandy);
                    candy.get(x-2).setImageResource(notCandy);
                    candy.get(x-2).setTag(notCandy);
                }
            }
        }
    }

    private void checkForTDown(){
        for (int i=1; i<((numberBlocks*numberBlocks)-(2*numberBlocks+1));i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;

            if ((i%numberBlocks!=0)&&(i%numberBlocks!=(numberBlocks-1))) {
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-1).getTag() == chosedCandy  &&
                        (int) candy.get(x+1).getTag() == chosedCandy &&
                        (int) candy.get(x+numberBlocks).getTag() == chosedCandy&&
                        (int) candy.get(x+2*numberBlocks).getTag() == chosedCandy)
                {
                    score = score+5;
                    scoreRes.setText(String.valueOf(score));

                    if (chosedCandy == R.drawable.candycrush_bluecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedblue);
                        candy.get(x).setTag(R.drawable.ccwrappedblue);
                    }
                    else if (chosedCandy == R.drawable.candycrush_greencandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedgreen);
                        candy.get(x).setTag(R.drawable.ccwrappedgreen);
                    }
                    else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedyellow);
                        candy.get(x).setTag(R.drawable.ccwrappedyellow);
                    }
                    else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedorange);
                        candy.get(x).setTag(R.drawable.ccwrappedorange);
                    }
                    else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedpurple);
                        candy.get(x).setTag(R.drawable.ccwrappedpurple);
                    }
                    else {
                        candy.get(x).setImageResource( R.drawable.ccwrappedred);
                        candy.get(x).setTag(R.drawable.ccwrappedred);
                    }
                    candy.get(x-1).setImageResource(notCandy);
                    candy.get(x-1).setTag(notCandy);
                    candy.get(x+1).setImageResource(notCandy);
                    candy.get(x+1).setTag(notCandy);
                    candy.get(x+numberBlocks).setImageResource(notCandy);
                    candy.get(x+numberBlocks).setTag(notCandy);
                    candy.get(x+2*numberBlocks).setImageResource(notCandy);
                    candy.get(x+2*numberBlocks).setTag(notCandy);
                }
            }
        }
    }
    private void checkForLLeftDown(){
        for (int i=1; i<((numberBlocks*numberBlocks)-(2*numberBlocks+1));i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;

            if ((i%numberBlocks!=0)&&(i%numberBlocks!=(numberBlocks-1))) {
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-1).getTag() == chosedCandy  &&
                        (int) candy.get(x+1).getTag() == chosedCandy &&
                        (int) candy.get(x+numberBlocks-1).getTag() == chosedCandy&&
                        (int) candy.get(x+2*numberBlocks-1).getTag() == chosedCandy)
                {
                    score = score+5;
                    scoreRes.setText(String.valueOf(score));

                    if (chosedCandy == R.drawable.candycrush_bluecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedblue);
                        candy.get(x).setTag(R.drawable.ccwrappedblue);
                    }
                    else if (chosedCandy == R.drawable.candycrush_greencandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedgreen);
                        candy.get(x).setTag(R.drawable.ccwrappedgreen);
                    }
                    else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedyellow);
                        candy.get(x).setTag(R.drawable.ccwrappedyellow);
                    }
                    else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedorange);
                        candy.get(x).setTag(R.drawable.ccwrappedorange);
                    }
                    else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedpurple);
                        candy.get(x).setTag(R.drawable.ccwrappedpurple);
                    }
                    else {
                        candy.get(x).setImageResource( R.drawable.ccwrappedred);
                        candy.get(x).setTag(R.drawable.ccwrappedred);
                    }
                    candy.get(x-1).setImageResource(notCandy);
                    candy.get(x-1).setTag(notCandy);
                    candy.get(x+1).setImageResource(notCandy);
                    candy.get(x+1).setTag(notCandy);
                    candy.get(x+numberBlocks-1).setImageResource(notCandy);
                    candy.get(x+numberBlocks-1).setTag(notCandy);
                    candy.get(x+2*numberBlocks-1).setImageResource(notCandy);
                    candy.get(x+2*numberBlocks-1).setTag(notCandy);
                }
            }
        }
    }
    private void checkForLRightDown(){
        for (int i=1; i<((numberBlocks*numberBlocks)-(2*numberBlocks+1));i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;

            if ((i%numberBlocks!=0)&&(i%numberBlocks!=(numberBlocks-1))) {
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-1).getTag() == chosedCandy  &&
                        (int) candy.get(x+1).getTag() == chosedCandy &&
                        (int) candy.get(x+numberBlocks+1).getTag() == chosedCandy&&
                        (int) candy.get(x+2*numberBlocks+1).getTag() == chosedCandy)
                {
                    score = score+5;
                    scoreRes.setText(String.valueOf(score));

                    if (chosedCandy == R.drawable.candycrush_bluecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedblue);
                        candy.get(x).setTag(R.drawable.ccwrappedblue);
                    }
                    else if (chosedCandy == R.drawable.candycrush_greencandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedgreen);
                        candy.get(x).setTag(R.drawable.ccwrappedgreen);
                    }
                    else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedyellow);
                        candy.get(x).setTag(R.drawable.ccwrappedyellow);
                    }
                    else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedorange);
                        candy.get(x).setTag(R.drawable.ccwrappedorange);
                    }
                    else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedpurple);
                        candy.get(x).setTag(R.drawable.ccwrappedpurple);
                    }
                    else {
                        candy.get(x).setImageResource( R.drawable.ccwrappedred);
                        candy.get(x).setTag(R.drawable.ccwrappedred);
                    }
                    candy.get(x-1).setImageResource(notCandy);
                    candy.get(x-1).setTag(notCandy);
                    candy.get(x+1).setImageResource(notCandy);
                    candy.get(x+1).setTag(notCandy);
                    candy.get(x+numberBlocks+1).setImageResource(notCandy);
                    candy.get(x+numberBlocks+1).setTag(notCandy);
                    candy.get(x+2*numberBlocks+1).setImageResource(notCandy);
                    candy.get(x+2*numberBlocks+1).setTag(notCandy);
                }
            }
        }
    }
    private void checkForTUp(){
        for (int i=(2*numberBlocks+1); i<((numberBlocks*numberBlocks)-1);i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;

            if ((i%numberBlocks!=0)&&(i%numberBlocks!=(numberBlocks-1))) {
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-1).getTag() == chosedCandy  &&
                        (int) candy.get(x+1).getTag() == chosedCandy &&
                        (int) candy.get(x-numberBlocks).getTag() == chosedCandy&&
                        (int) candy.get(x-2*numberBlocks).getTag() == chosedCandy)
                {
                    score = score+5;
                    scoreRes.setText(String.valueOf(score));

                    if (chosedCandy == R.drawable.candycrush_bluecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedblue);
                        candy.get(x).setTag(R.drawable.ccwrappedblue);
                    }
                    else if (chosedCandy == R.drawable.candycrush_greencandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedgreen);
                        candy.get(x).setTag(R.drawable.ccwrappedgreen);
                    }
                    else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedyellow);
                        candy.get(x).setTag(R.drawable.ccwrappedyellow);
                    }
                    else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedorange);
                        candy.get(x).setTag(R.drawable.ccwrappedorange);
                    }
                    else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedpurple);
                        candy.get(x).setTag(R.drawable.ccwrappedpurple);
                    }
                    else {
                        candy.get(x).setImageResource( R.drawable.ccwrappedred);
                        candy.get(x).setTag(R.drawable.ccwrappedred);
                    }
                    candy.get(x-1).setImageResource(notCandy);
                    candy.get(x-1).setTag(notCandy);
                    candy.get(x+1).setImageResource(notCandy);
                    candy.get(x+1).setTag(notCandy);
                    candy.get(x-numberBlocks).setImageResource(notCandy);
                    candy.get(x-numberBlocks).setTag(notCandy);
                    candy.get(x-2*numberBlocks).setImageResource(notCandy);
                    candy.get(x-2*numberBlocks).setTag(notCandy);
                }
            }
        }
    }
    private void checkForLUpLeft(){
        for (int i=(2*numberBlocks+1); i<((numberBlocks*numberBlocks)-1);i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;

            if ((i%numberBlocks!=0)&&(i%numberBlocks!=(numberBlocks-1))) {
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-1).getTag() == chosedCandy  &&
                        (int) candy.get(x+1).getTag() == chosedCandy &&
                        (int) candy.get(x-numberBlocks-1).getTag() == chosedCandy&&
                        (int) candy.get(x-2*numberBlocks-1).getTag() == chosedCandy)
                {
                    score = score+5;
                    scoreRes.setText(String.valueOf(score));

                    if (chosedCandy == R.drawable.candycrush_bluecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedblue);
                        candy.get(x).setTag(R.drawable.ccwrappedblue);
                    }
                    else if (chosedCandy == R.drawable.candycrush_greencandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedgreen);
                        candy.get(x).setTag(R.drawable.ccwrappedgreen);
                    }
                    else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedyellow);
                        candy.get(x).setTag(R.drawable.ccwrappedyellow);
                    }
                    else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedorange);
                        candy.get(x).setTag(R.drawable.ccwrappedorange);
                    }
                    else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedpurple);
                        candy.get(x).setTag(R.drawable.ccwrappedpurple);
                    }
                    else {
                        candy.get(x).setImageResource( R.drawable.ccwrappedred);
                        candy.get(x).setTag(R.drawable.ccwrappedred);
                    }
                    candy.get(x-1).setImageResource(notCandy);
                    candy.get(x-1).setTag(notCandy);
                    candy.get(x+1).setImageResource(notCandy);
                    candy.get(x+1).setTag(notCandy);
                    candy.get(x-numberBlocks-1).setImageResource(notCandy);
                    candy.get(x-numberBlocks-1).setTag(notCandy);
                    candy.get(x-2*numberBlocks-1).setImageResource(notCandy);
                    candy.get(x-2*numberBlocks-1).setTag(notCandy);
                }
            }
        }
    }
    private void checkForLUpRight(){
        for (int i=(2*numberBlocks+1); i<((numberBlocks*numberBlocks)-1);i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;

            if ((i%numberBlocks!=0)&&(i%numberBlocks!=(numberBlocks-1))) {
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-1).getTag() == chosedCandy  &&
                        (int) candy.get(x+1).getTag() == chosedCandy &&
                        (int) candy.get(x-numberBlocks+1).getTag() == chosedCandy&&
                        (int) candy.get(x-2*numberBlocks+1).getTag() == chosedCandy)
                {
                    score = score+5;
                    scoreRes.setText(String.valueOf(score));

                    if (chosedCandy == R.drawable.candycrush_bluecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedblue);
                        candy.get(x).setTag(R.drawable.ccwrappedblue);
                    }
                    else if (chosedCandy == R.drawable.candycrush_greencandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedgreen);
                        candy.get(x).setTag(R.drawable.ccwrappedgreen);
                    }
                    else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedyellow);
                        candy.get(x).setTag(R.drawable.ccwrappedyellow);
                    }
                    else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedorange);
                        candy.get(x).setTag(R.drawable.ccwrappedorange);
                    }
                    else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                        candy.get(x).setImageResource( R.drawable.ccwrappedpurple);
                        candy.get(x).setTag(R.drawable.ccwrappedpurple);
                    }
                    else {
                        candy.get(x).setImageResource( R.drawable.ccwrappedred);
                        candy.get(x).setTag(R.drawable.ccwrappedred);
                    }
                    candy.get(x-1).setImageResource(notCandy);
                    candy.get(x-1).setTag(notCandy);
                    candy.get(x+1).setImageResource(notCandy);
                    candy.get(x+1).setTag(notCandy);
                    candy.get(x-numberBlocks+1).setImageResource(notCandy);
                    candy.get(x-numberBlocks+1).setTag(notCandy);
                    candy.get(x-2*numberBlocks+1).setImageResource(notCandy);
                    candy.get(x-2*numberBlocks+1).setTag(notCandy);
                }
            }
        }
    }
    private void checkRowForFive(){
        for (int i=2; i<(numberBlocks*numberBlocks)-2;i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;

            if ((i%numberBlocks!=0)&&((i%numberBlocks!=(numberBlocks-1)))&&(i%numberBlocks!=1)&&(i%numberBlocks!=(numberBlocks-2))) {
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-1).getTag() == chosedCandy  &&
                        (int) candy.get(x+1).getTag() == chosedCandy &&
                        (int) candy.get(x+2).getTag() == chosedCandy &&
                        (int) candy.get(x-2).getTag() == chosedCandy)
                {
                    score = score+5;
                    scoreRes.setText(String.valueOf(score));
                    candy.get(x).setImageResource( R.drawable.bombcc);
                    candy.get(x).setTag(R.drawable.bombcc);
                    candy.get(x-1).setImageResource(notCandy);
                    candy.get(x-1).setTag(notCandy);
                    candy.get(x+1).setImageResource(notCandy);
                    candy.get(x+1).setTag(notCandy);
                    candy.get(x-2).setImageResource(notCandy);
                    candy.get(x-2).setTag(notCandy);
                    candy.get(x+2).setImageResource(notCandy);
                    candy.get(x+2).setTag(notCandy);
                }
            }
        }
    }
    private void checkColumnForFour(){
        for (int i=2*numberBlocks; i<((numberBlocks*numberBlocks)-numberBlocks);i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            int x=i;

            if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                    (int) candy.get(x-numberBlocks).getTag() == chosedCandy  &&
                    (int) candy.get(x-2*numberBlocks).getTag() == chosedCandy  &&
                    (int) candy.get(x+numberBlocks).getTag() == chosedCandy) {
                score = score+4;
                scoreRes.setText(String.valueOf(score));

                if (chosedCandy == R.drawable.candycrush_bluecandy) {
                    candy.get(x).setImageResource( R.drawable.ccstripedbluevertical);
                    candy.get(x).setTag(R.drawable.ccstripedbluevertical);
                }
                else if (chosedCandy == R.drawable.candycrush_greencandy) {
                    candy.get(x).setImageResource( R.drawable.ccstripedgreenvertical);
                    candy.get(x).setTag(R.drawable.ccstripedgreenvertical);
                }
                else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                    candy.get(x).setImageResource( R.drawable.ccstripedyellowvertical);
                    candy.get(x).setTag(R.drawable.ccstripedyellowvertical);
                }
                else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                    candy.get(x).setImageResource( R.drawable.ccstripedorangevertical);
                    candy.get(x).setTag(R.drawable.ccstripedorangevertical);
                }
                else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                    candy.get(x).setImageResource( R.drawable.ccstripedpurplevertical);
                    candy.get(x).setTag(R.drawable.ccstripedpurplevertical);
                }
                else {
                    candy.get(x).setImageResource( R.drawable.ccstripedredvertical);
                    candy.get(x).setTag(R.drawable.ccstripedredvertical);
                }
                candy.get(x-numberBlocks).setImageResource(notCandy);
                candy.get(x-numberBlocks).setTag(notCandy);
                candy.get(x+numberBlocks).setImageResource(notCandy);
                candy.get(x+numberBlocks).setTag(notCandy);
                candy.get(x-2*numberBlocks).setImageResource(notCandy);
                candy.get(x-2*numberBlocks).setTag(notCandy);
            }
        }
    }
    private void checkColumnForFive(){
        for (int i=2*numberBlocks; i<((numberBlocks*numberBlocks)-2*numberBlocks);i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            int x=i;

            if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                    (int) candy.get(x-numberBlocks).getTag() == chosedCandy  &&
                    (int) candy.get(x-2*numberBlocks).getTag() == chosedCandy  &&
                    (int) candy.get(x+2*numberBlocks).getTag() == chosedCandy  &&
                    (int) candy.get(x+numberBlocks).getTag() == chosedCandy) {
                score = score+5;
                scoreRes.setText(String.valueOf(score));
                candy.get(x).setImageResource( R.drawable.bombcc);
                candy.get(x).setTag(R.drawable.bombcc);
                candy.get(x-numberBlocks).setImageResource(notCandy);
                candy.get(x-numberBlocks).setTag(notCandy);
                candy.get(x+numberBlocks).setImageResource(notCandy);
                candy.get(x+numberBlocks).setTag(notCandy);
                candy.get(x-2*numberBlocks).setImageResource(notCandy);
                candy.get(x-2*numberBlocks).setTag(notCandy);
                candy.get(x+2*numberBlocks).setImageResource(notCandy);
                candy.get(x+2*numberBlocks).setTag(notCandy);
            }
        }
    }

    private void checkColumnForThree(){
        for (int i=numberBlocks; i<((numberBlocks*numberBlocks)-numberBlocks);i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
                int x=i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x-numberBlocks).getTag() == chosedCandy  &&
                        (int) candy.get(x+numberBlocks).getTag() == chosedCandy) {
                    score = score+3;
                    scoreRes.setText(String.valueOf(score));
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    candy.get(x-numberBlocks).setImageResource(notCandy);
                    candy.get(x-numberBlocks).setTag(notCandy);
                    candy.get(x+numberBlocks).setImageResource(notCandy);
                    candy.get(x+numberBlocks).setTag(notCandy);
                }
        }
    }

    private void candyInterchange(int a, int b){
        int background = (int) candy.get(b).getTag();
        int background1 = (int) candy.get(a).getTag();
        candy.get(a).setImageResource(background);
        candy.get(b).setImageResource(background1);
        candy.get(a).setTag(background);
        candy.get(b).setTag(background1);
    }
    private void removeKindCandy(int color){
        for (int i = 0; i< numberBlocks*numberBlocks;i++){
            if  ((int) candy.get(i).getTag() == color){
                candy.get(i).setImageResource(notCandy);
                candy.get(i).setTag(notCandy);
                score++;
            }
        }
        moveDownCandies();
    }

    private void removeRow(int id){
        int alpha = (int) id/numberBlocks;
        for (int i = (alpha*numberBlocks); i< ((alpha+1)*numberBlocks);i++){
                candy.get(i).setImageResource(notCandy);
                candy.get(i).setTag(notCandy);
                score++;
        }
        moveDownCandies();
    }

    private void removeNeighboors(int id){
        candy.get(id).setImageResource(notCandy);
        candy.get(id).setTag(notCandy);
        if(id<numberBlocks){
            if (id%numberBlocks==0){
                candy.get(id+1).setImageResource(notCandy);
                candy.get(id+1).setTag(notCandy);
                candy.get(id+numberBlocks).setImageResource(notCandy);
                candy.get(id+numberBlocks).setTag(notCandy);
                candy.get(id+numberBlocks+1).setImageResource(notCandy);
                candy.get(id+numberBlocks+1).setTag(notCandy);
            }
            else if (id%numberBlocks==(numberBlocks-1)){
                candy.get(id-1).setImageResource(notCandy);
                candy.get(id-1).setTag(notCandy);
                candy.get(id+numberBlocks).setImageResource(notCandy);
                candy.get(id+numberBlocks).setTag(notCandy);
                candy.get(id+numberBlocks-1).setImageResource(notCandy);
                candy.get(id+numberBlocks-1).setTag(notCandy);
            }
            else {
                candy.get(id-1).setImageResource(notCandy);
                candy.get(id-1).setTag(notCandy);
                candy.get(id+1).setImageResource(notCandy);
                candy.get(id+1).setTag(notCandy);
                candy.get(id+numberBlocks).setImageResource(notCandy);
                candy.get(id+numberBlocks).setTag(notCandy);
                candy.get(id+numberBlocks-1).setImageResource(notCandy);
                candy.get(id+numberBlocks-1).setTag(notCandy);
                candy.get(id+numberBlocks+1).setImageResource(notCandy);
                candy.get(id+numberBlocks+1).setTag(notCandy);
            }
        }
        else if (id>=(numberBlocks*(numberBlocks-1))){
            if (id%numberBlocks==0){
                candy.get(id+1).setImageResource(notCandy);
                candy.get(id+1).setTag(notCandy);
                candy.get(id-numberBlocks).setImageResource(notCandy);
                candy.get(id-numberBlocks).setTag(notCandy);
                candy.get(id-numberBlocks+1).setImageResource(notCandy);
                candy.get(id-numberBlocks+1).setTag(notCandy);
            }
            else if (id%numberBlocks==(numberBlocks-1)){
                candy.get(id-1).setImageResource(notCandy);
                candy.get(id-1).setTag(notCandy);
                candy.get(id-numberBlocks).setImageResource(notCandy);
                candy.get(id-numberBlocks).setTag(notCandy);
                candy.get(id-numberBlocks-1).setImageResource(notCandy);
                candy.get(id-numberBlocks-1).setTag(notCandy);
            }
            else {
                candy.get(id-1).setImageResource(notCandy);
                candy.get(id-1).setTag(notCandy);
                candy.get(id+1).setImageResource(notCandy);
                candy.get(id+1).setTag(notCandy);
                candy.get(id-numberBlocks).setImageResource(notCandy);
                candy.get(id-numberBlocks).setTag(notCandy);
                candy.get(id-numberBlocks-1).setImageResource(notCandy);
                candy.get(id-numberBlocks-1).setTag(notCandy);
                candy.get(id-numberBlocks+1).setImageResource(notCandy);
                candy.get(id-numberBlocks+1).setTag(notCandy);
            }
        }
        else if (id%numberBlocks==0){
            candy.get(id+1).setImageResource(notCandy);
            candy.get(id+1).setTag(notCandy);
            candy.get(id-numberBlocks).setImageResource(notCandy);
            candy.get(id-numberBlocks).setTag(notCandy);
            candy.get(id+numberBlocks).setImageResource(notCandy);
            candy.get(id+numberBlocks).setTag(notCandy);
            candy.get(id-numberBlocks+1).setImageResource(notCandy);
            candy.get(id-numberBlocks+1).setTag(notCandy);
            candy.get(id+numberBlocks+1).setImageResource(notCandy);
            candy.get(id+numberBlocks+1).setTag(notCandy);
        }
        else if(id%numberBlocks==numberBlocks-1){
            candy.get(id-1).setImageResource(notCandy);
            candy.get(id-1).setTag(notCandy);
            candy.get(id-numberBlocks).setImageResource(notCandy);
            candy.get(id-numberBlocks).setTag(notCandy);
            candy.get(id+numberBlocks).setImageResource(notCandy);
            candy.get(id+numberBlocks).setTag(notCandy);
            candy.get(id-numberBlocks-1).setImageResource(notCandy);
            candy.get(id-numberBlocks-1).setTag(notCandy);
            candy.get(id+numberBlocks-1).setImageResource(notCandy);
            candy.get(id+numberBlocks-1).setTag(notCandy);
        }
        else {
            candy.get(id-1).setImageResource(notCandy);
            candy.get(id-1).setTag(notCandy);
            candy.get(id+1).setImageResource(notCandy);
            candy.get(id+1).setTag(notCandy);
            candy.get(id-numberBlocks).setImageResource(notCandy);
            candy.get(id-numberBlocks).setTag(notCandy);
            candy.get(id+numberBlocks).setImageResource(notCandy);
            candy.get(id+numberBlocks).setTag(notCandy);
            candy.get(id-numberBlocks-1).setImageResource(notCandy);
            candy.get(id-numberBlocks-1).setTag(notCandy);
            candy.get(id+numberBlocks-1).setImageResource(notCandy);
            candy.get(id+numberBlocks-1).setTag(notCandy);
            candy.get(id-numberBlocks+1).setImageResource(notCandy);
            candy.get(id-numberBlocks+1).setTag(notCandy);
            candy.get(id+numberBlocks+1).setImageResource(notCandy);
            candy.get(id+numberBlocks+1).setTag(notCandy);
        }
        moveDownCandies();
    }

    private void removeColumn(int id){
        for (int i = (id%numberBlocks); i< (numberBlocks*numberBlocks);i+=numberBlocks){
                candy.get(i).setImageResource(notCandy);
                candy.get(i).setTag(notCandy);
                score++;
        }
        moveDownCandies();
    }

    private void moveDownCandies(){
        for(int a=0;a<numberBlocks;a++){
            for(int i=((numberBlocks*numberBlocks)-1);i>(numberBlocks-1);i--){
                if ((int)candy.get(i).getTag() == notCandy){
                    candyInterchange(i,(i-numberBlocks));
                }
            }
        }
        for(int i=0;i<(numberBlocks*numberBlocks);i++){
            if ((int)candy.get(i).getTag()==notCandy){
                int randomColor = (int) Math.floor(Math.random()*candiesstart.length);
                candy.get(i).setImageResource(candiesstart[randomColor]);
                candy.get(i).setTag(candiesstart[randomColor]);
            }
        }
    }

    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try{
                //bomb
                checkRowForFive();
                checkColumnForFive();
                //striped
                checkRowForFour();
                checkColumnForFour();
                //wrapped
                checkForTDown();
                checkForTUp();
                checkForLLeftDown();
                checkForLRightDown();
                checkForLUpLeft();
                checkForLUpRight();
                //basics
                checkRowForThree();
                checkColumnForThree();
                //move
                moveDownCandies();
            }
            finally{
                mHandler.postDelayed(repeatChecker,interval);
            }
        }

    };
        void startRepeat(){
            repeatChecker.run();
        }

    public void loadNote(){
        int level = getIntent().getExtras().getInt("level");
        db.collection("CandyCrush_level_"+level).document("highscore_global").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreglob = documentSnapshot.get(KEY_SCORE).toString();
                            highscore_user=Integer.parseInt(scoreglob);
                        }
                        else{
                            Toast.makeText(CandyCrush.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CandyCrush.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        db.collection("CandyCrush_level_"+level).document("highscore_"+username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String scoreus = documentSnapshot.getString(KEY_SCORE);
                            highscore_user=Integer.parseInt(scoreus);                        }
                        else{
                            Toast.makeText(CandyCrush.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CandyCrush.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    public void saveNote() {
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_USER,username);
        note.put(KEY_SCORE,score);
        note.put(KEY_DATE,currentTime);
        int level = getIntent().getExtras().getInt("level");

        if (score>highscore_global){
        db.collection("CandyCrush_level_"+level).document("highscore_global").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CandyCrush.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CandyCrush.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

        }
        if (score>highscore_user){
        db.collection("CandyCrush_level_"+level).document("highscore_"+username).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CandyCrush.this, "Sucess", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CandyCrush.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        }
    }
}