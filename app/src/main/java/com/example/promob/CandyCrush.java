package com.example.promob;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class CandyCrush extends AppCompatActivity {

    int[] candies = {
        R.drawable.candycrush_bluecandy, R.drawable.candycrush_greencandy,R.drawable.candycrush_orangecandy,
            R.drawable.candycrush_purplecandy,R.drawable.candycrush_redcandy,R.drawable.candycrush_yellowcandy,
};
    int widthBlock, widthScreen, heightScreen, numberBlocks=8;
    ArrayList<ImageView> candy = new ArrayList<>();

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

        createBoard();
        for (ImageView imageView : candy){
            imageView.setOnTouchListener(new OnSwipeListener(CandyCrush.this){
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    Toast.makeText(CandyCrush.this, "Left", Toast.LENGTH_SHORT).show();
                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    Toast.makeText(CandyCrush.this, "Right", Toast.LENGTH_SHORT).show();
                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();
                    Toast.makeText(CandyCrush.this, "Top", Toast.LENGTH_SHORT).show();
                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();
                    Toast.makeText(CandyCrush.this, "Bottom", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
            candy.add(imageView);
            gridLayout.addView(imageView);
        }
    }
}