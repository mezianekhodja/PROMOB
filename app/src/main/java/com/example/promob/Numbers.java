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

import java.util.ArrayList;

public class Numbers extends AppCompatActivity {

    int[] numberslist = {
             R.drawable.nb2048_2, R.drawable.nb2048_4, R.drawable.nb2048_8, R.drawable.nb2048_16,
            R.drawable.nb2048_32, R.drawable.nb2048_64, R.drawable.nb2048_128, R.drawable.nb2048_256,
            R.drawable.nb2048_512, R.drawable.nb2048_1024, R.drawable.nb2048_2048
    };

    int widthBlock, widthScreen, heightScreen, numberBlocks=4;
    ArrayList<ImageView> number = new ArrayList<>();
    Handler mHandler = new Handler();
    int interval = 100;
    TextView scoreRes;
    int score = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candy_crush);

        DisplayMetrics displayMetrics =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthScreen = displayMetrics.widthPixels;
        heightScreen = displayMetrics.heightPixels;
        widthBlock = widthScreen / numberBlocks;
        scoreRes = (TextView) findViewById(R.id.tvscoreNB);

        createBoard();
        for (final ImageView imageView : number){

            imageView.setOnTouchListener(new OnSwipeListener(Numbers.this){
                @Override
                void onSwipeLeft() {

                }

                @Override
                void onSwipeRight(){

                }

                @Override
                void onSwipeTop(){

                }

                @Override
                void onSwipeBottom(){

                }
            });
        }
        mHandler = new Handler();
        startRepeat();
    }

    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Votre score est de  : "+String.valueOf(score));
        builder.create().show();
        finishGame();
    }
    private void finishGame(){
        Intent intent = new Intent(this, Numbers_Home.class);
        intent.putExtra("score", score);
        startActivity(intent);
        Numbers.this.finish();
    }
    private void createBoard() {
        GridLayout gridLayout = findViewById(R.id.boardnb);
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
            int randomnb = (int) Math.floor(Math.random()*numberslist.length);
            imageView.setImageResource(numberslist[randomnb]);
            imageView.setTag(numberslist[randomnb]);
            number.add(imageView);
            gridLayout.addView(imageView);
        }
    }

    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try{

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