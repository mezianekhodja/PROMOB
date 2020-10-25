package com.example.promob;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
            R.drawable.bombcc
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
                    move--;
                    moveRes.setText(String.valueOf(move));
                    if(((int) candy.get(candyToBeDragged).getTag() == R.drawable.bombcc)||((int) candy.get(candyToBeReplaced).getTag() == R.drawable.bombcc)){
                        removeKindCandy((int) candy.get(candyToBeDragged).getTag());
                        removeKindCandy((int) candy.get(candyToBeReplaced).getTag());
                    }
                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged +1;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                    move--;
                    moveRes.setText(String.valueOf(move));
                    if(((int) candy.get(candyToBeDragged).getTag() == R.drawable.bombcc)||((int) candy.get(candyToBeReplaced).getTag() == R.drawable.bombcc)){
                        removeKindCandy((int) candy.get(candyToBeDragged).getTag());
                        removeKindCandy((int) candy.get(candyToBeReplaced).getTag());
                    }
                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged -numberBlocks;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                    move--;
                    moveRes.setText(String.valueOf(move));
                    if(((int) candy.get(candyToBeDragged).getTag() == R.drawable.bombcc)||((int) candy.get(candyToBeReplaced).getTag() == R.drawable.bombcc)){
                        removeKindCandy((int) candy.get(candyToBeDragged).getTag());
                        removeKindCandy((int) candy.get(candyToBeReplaced).getTag());
                    }
                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged +numberBlocks;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                    move--;
                    moveRes.setText(String.valueOf(move));
                    if(((int) candy.get(candyToBeDragged).getTag() == R.drawable.bombcc)||((int) candy.get(candyToBeReplaced).getTag() == R.drawable.bombcc)){
                        removeKindCandy((int) candy.get(candyToBeDragged).getTag());
                        removeKindCandy((int) candy.get(candyToBeReplaced).getTag());
                    }
                }
            });
        }
        mHandler = new Handler();
        startRepeat();
        if (move==0){
            createDialog();
        }
    }

    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Votre score est de  : "+String.valueOf(score));
        builder.setMessage("Le nombre de mouvements a été atteint");
        builder.create().show();
    }

    private void createBoard() {
        GridLayout gridLayout = findViewById(R.id.boardCC);
        gridLayout.setRowCount(numberBlocks);
        gridLayout.setColumnCount(numberBlocks);
        gridLayout.getLayoutParams().width = widthScreen;
        gridLayout.getLayoutParams().height = heightScreen;

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
                checkRowForFive();
                checkColumnForFive();
                checkRowForThree();
                checkColumnForThree();
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