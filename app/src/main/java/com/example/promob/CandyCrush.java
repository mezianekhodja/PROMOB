package com.example.promob;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CandyCrush extends AppCompatActivity {

    int[] candies = {
        R.drawable.candycrush_bluecandy, R.drawable.candycrush_greencandy,R.drawable.candycrush_orangecandy,
            R.drawable.candycrush_purplecandy,R.drawable.candycrush_redcandy,R.drawable.candycrush_yellowcandy,
            R.drawable.bombcc, R.drawable.stripedbluecc,R.drawable.stripedgreencc,R.drawable.stripedorangecc,
            R.drawable.stripedpurplecc,R.drawable.stripedredcc,R.drawable.stripedyellowcc,R.drawable.ccwrappedblue,
            R.drawable.ccwrappedgreen,R.drawable.ccwrappedorange,R.drawable.ccwrappedpurple,R.drawable.ccwrappedred,
            R.drawable.ccwrappedyellow
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
    int move  = 5;
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

        createBoard();
        for (final ImageView imageView : candy){

            imageView.setOnTouchListener(new OnSwipeListener(CandyCrush.this){
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged -1;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                    if(((int) candy.get(candyToBeDragged).getTag() == R.drawable.bombcc)||((int) candy.get(candyToBeReplaced).getTag() == R.drawable.bombcc)){
                        removeKindCandy((int) candy.get(candyToBeDragged).getTag());
                        removeKindCandy((int) candy.get(candyToBeReplaced).getTag());
                    }
                    if((((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedyellowcc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_yellowcandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedredcc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_redcandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedgreencc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_greencandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedbluecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_bluecandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedorangecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_orangecandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedpurplecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_purplecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedyellowcc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_yellowcandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedredcc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_redcandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedgreencc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_greencandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedbluecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_bluecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedorangecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_orangecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedpurplecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_purplecandy))){
                        removeRow(candy.get(candyToBeDragged).getId());
                    }
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
                    if(((int) candy.get(candyToBeDragged).getTag() == R.drawable.bombcc)||((int) candy.get(candyToBeReplaced).getTag() == R.drawable.bombcc)){
                        removeKindCandy((int) candy.get(candyToBeDragged).getTag());
                        removeKindCandy((int) candy.get(candyToBeReplaced).getTag());
                    }
                    if((((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedyellowcc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_yellowcandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedredcc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_redcandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedgreencc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_greencandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedbluecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_bluecandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedorangecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_orangecandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedpurplecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_purplecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedyellowcc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_yellowcandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedredcc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_redcandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedgreencc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_greencandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedbluecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_bluecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedorangecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_orangecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedpurplecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_purplecandy))){
                        removeRow(candy.get(candyToBeDragged).getId());
                    }
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
                    if(((int) candy.get(candyToBeDragged).getTag() == R.drawable.bombcc)||((int) candy.get(candyToBeReplaced).getTag() == R.drawable.bombcc)){
                        removeKindCandy((int) candy.get(candyToBeDragged).getTag());
                        removeKindCandy((int) candy.get(candyToBeReplaced).getTag());
                    }
                    if((((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedyellowcc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_yellowcandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedredcc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_redcandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedgreencc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_greencandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedbluecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_bluecandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedorangecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_orangecandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedpurplecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_purplecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedyellowcc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_yellowcandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedredcc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_redcandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedgreencc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_greencandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedbluecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_bluecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedorangecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_orangecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedpurplecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_purplecandy))){
                        removeColumn(candy.get(candyToBeDragged).getId());
                    }
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
                    if(((int) candy.get(candyToBeDragged).getTag() == R.drawable.bombcc)||((int) candy.get(candyToBeReplaced).getTag() == R.drawable.bombcc)){
                        removeKindCandy((int) candy.get(candyToBeDragged).getTag());
                        removeKindCandy((int) candy.get(candyToBeReplaced).getTag());
                    }
                    if((((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedyellowcc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_yellowcandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedredcc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_redcandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedgreencc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_greencandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedbluecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_bluecandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedorangecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_orangecandy))||
                            (((int) candy.get(candyToBeDragged).getTag() == R.drawable.stripedpurplecc)&&((int) candy.get(candyToBeReplaced).getTag() == R.drawable.candycrush_purplecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedyellowcc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_yellowcandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedredcc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_redcandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedgreencc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_greencandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedbluecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_bluecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedorangecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_orangecandy))||
                            (((int) candy.get(candyToBeReplaced).getTag() == R.drawable.stripedpurplecc)&&((int) candy.get(candyToBeDragged).getTag() == R.drawable.candycrush_purplecandy))){
                        removeColumn(candy.get(candyToBeDragged).getId());
                    }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Votre score est de  : "+String.valueOf(score));
        builder.setMessage("Le nombre de mouvements a été atteint");
        builder.create().show();
    }
    private void finishGame(){
        Intent intent = new Intent(this, CandyCrush_Home.class);
        intent.putExtra("score", score);
        startActivity(intent);
        CandyCrush.this.finish();
    }
    private void createBoard() {
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
                        candy.get(x).setImageResource( R.drawable.stripedbluecc);
                        candy.get(x).setTag(R.drawable.stripedbluecc);
                    }
                    else if (chosedCandy == R.drawable.candycrush_greencandy) {
                        candy.get(x).setImageResource( R.drawable.stripedgreencc);
                        candy.get(x).setTag(R.drawable.stripedgreencc);
                    }
                    else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                        candy.get(x).setImageResource( R.drawable.stripedyellowcc);
                        candy.get(x).setTag(R.drawable.stripedyellowcc);
                    }
                    else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                        candy.get(x).setImageResource( R.drawable.stripedorangecc);
                        candy.get(x).setTag(R.drawable.stripedorangecc);
                    }
                    else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                        candy.get(x).setImageResource( R.drawable.stripedpurplecc);
                        candy.get(x).setTag(R.drawable.stripedpurplecc);
                    }
                    else {
                        candy.get(x).setImageResource( R.drawable.stripedredcc);
                        candy.get(x).setTag(R.drawable.stripedredcc);
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
                    candy.get(x).setImageResource( R.drawable.stripedbluecc);
                    candy.get(x).setTag(R.drawable.stripedbluecc);
                }
                else if (chosedCandy == R.drawable.candycrush_greencandy) {
                    candy.get(x).setImageResource( R.drawable.stripedgreencc);
                    candy.get(x).setTag(R.drawable.stripedgreencc);
                }
                else if (chosedCandy == R.drawable.candycrush_yellowcandy) {
                    candy.get(x).setImageResource( R.drawable.stripedyellowcc);
                    candy.get(x).setTag(R.drawable.stripedyellowcc);
                }
                else if (chosedCandy == R.drawable.candycrush_orangecandy) {
                    candy.get(x).setImageResource( R.drawable.stripedorangecc);
                    candy.get(x).setTag(R.drawable.stripedorangecc);
                }
                else if (chosedCandy == R.drawable.candycrush_purplecandy) {
                    candy.get(x).setImageResource( R.drawable.stripedpurplecc);
                    candy.get(x).setTag(R.drawable.stripedpurplecc);
                }
                else {
                    candy.get(x).setImageResource( R.drawable.stripedredcc);
                    candy.get(x).setTag(R.drawable.stripedredcc);
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
}