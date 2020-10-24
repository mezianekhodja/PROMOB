package com.example.promob;

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
};
    int widthBlock, widthScreen, heightScreen, numberBlocks=8,candyToBeDragged, candyToBeReplaced, notCandy=R.drawable.ic_launcher_background;
    ArrayList<ImageView> candy = new ArrayList<>();
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
        scoreRes = (TextView) findViewById(R.id.tvscoreCC);

        createBoard();
        for (final ImageView imageView : candy){
            imageView.setOnTouchListener(new OnSwipeListener(CandyCrush.this){
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged -1;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged +1;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged -numberBlocks;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged +numberBlocks;
                    candyInterchange(candyToBeDragged,candyToBeReplaced);
                }
            });
        }
        mHandler = new Handler();
        startRepeat();
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
            int randomCandy = (int) Math.floor(Math.random()*candies.length);
            imageView.setImageResource(candies[randomCandy]);
            imageView.setTag(candies[randomCandy]);
            candy.add(imageView);
            gridLayout.addView(imageView);
        }
    }
    private void checkRowForThree(){
        for (int i=1; i<(numberBlocks*numberBlocks)-1;i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            if ((i%numberBlocks!=0)&&((i%numberBlocks!=1))) {
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

    private void moveDownCandies(){
        for(int i=((numberBlocks*numberBlocks)-1);i>(numberBlocks-1);i--){
            if ((int)candy.get(i).getTag() == notCandy){
                candyInterchange(i,(i-numberBlocks));
            }
        }
        for(int i=0;i<(numberBlocks*numberBlocks);i++){
            if ((int)candy.get(i).getTag()==notCandy){
                int randomColor = (int) Math.floor(Math.random()*candies.length);
                candy.get(i).setImageResource(candies[randomColor]);
                candy.get(i).setTag(candies[randomColor]);
            }
        }
    }
    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try{
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