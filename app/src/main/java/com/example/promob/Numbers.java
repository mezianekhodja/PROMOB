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

    int[] numberslist = {R.drawable.nb2048_0,R.drawable.nb2048_2, R.drawable.nb2048_4, R.drawable.nb2048_8,
            R.drawable.nb2048_16, R.drawable.nb2048_32, R.drawable.nb2048_64, R.drawable.nb2048_128,
            R.drawable.nb2048_256, R.drawable.nb2048_512, R.drawable.nb2048_1024, R.drawable.nb2048_2048};

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
        setContentView(R.layout.activity_numbers);

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
                    instructions();
                }

                @Override
                void onSwipeRight(){
                    instructions();
                }

                @Override
                void onSwipeTop(){
                    instructions();
                }

                @Override
                void onSwipeBottom(){
                    instructions();
                }
            });
        }
        mHandler = new Handler();
        startRepeat();
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
            imageView.setImageResource(numberslist[0]);//image vierge
            imageView.setTag(numberslist[0]);
            number.add(imageView);
            gridLayout.addView(imageView);
        }
        int randomnb = randomNumber(numberslist.length);
        number.get(randomnb).setImageResource(numberslist[1]);
        number.get(randomnb).setTag(numberslist[1]);
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

    private int randomNumber(int size){
        return ((int)Math.floor(Math.random() * size));
    }

    private boolean testVictory(){
        for(int index = 0; index<number.size();index++ ){
            if ((int)number.get(index).getTag()==R.drawable.nb2048_2048){
                return true;
            }
        }
        return false;
    }

    private boolean testLoose(){
        for(int index = 0; index<number.size();index++ ){
            if ((int)number.get(index).getTag()==R.drawable.nb2048_0){
                return false;
            }
        }
        return true;
    }

    private void addBlock(){
        int x = randomNumber(numberslist.length);
        boolean changement = false;
        while (!changement){
           if ((int)number.get(x).getTag()==R.drawable.nb2048_0){
               number.get(x).setImageResource(numberslist[1]);
               number.get(x).setTag(numberslist[1]);
               changement = true;
               break;
           }
           x++;
           if (x==number.size()){
               x=0;
           }
        }
    }
    private void instructions(){
        if (testVictory()){
            createDialog(true);
        }
        else if(testLoose()){
            createDialog(false);
        }
        else {
            addBlock();
        }
    }
    public void createDialog(boolean win) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dommage... ");
        builder.setMessage("Votre score est de  : "+String.valueOf(score));
        if (win){
            builder.setTitle("Belle victoire ! ");
        }
        builder.create().show();
        finishGame();
    }
    private void finishGame(){
        Intent intent = new Intent(this, Numbers_Home.class);
        intent.putExtra("score", score);
        startActivity(intent);
        Numbers.this.finish();
    }

}