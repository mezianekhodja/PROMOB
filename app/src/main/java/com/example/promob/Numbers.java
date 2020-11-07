package com.example.promob;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
    TextView scoreRes;
    int score = 2;

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
                    for (int i = 0; i< 3;i++){
                        decalageGauche();
                        correspondanceGauche();
                    }
                    instructions();
                }

                @Override
                void onSwipeRight(){
                    for (int i = 0; i< 3;i++){
                        correspondanceDroite();
                        decalageDroite();
                    }
                    instructions();
                }
                @Override
                void onSwipeTop(){
                    for (int i = 0; i< 3;i++){
                        correspondanceHaut();
                        decalageHaut();
                    }
                    instructions();
                }

                @Override
                void onSwipeBottom(){
                    for (int i = 0; i< 3;i++){
                        correspondanceBas();
                        decalageBas();
                    }
                    instructions();
                }
            });
        }
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

    private int randomNumber(int size){
        return ((int)Math.floor(Math.random() * size));
    }

    private void blockInterchange(int a, int b){
        int background = (int) number.get(b).getTag();
        int background1 = (int) number.get(a).getTag();
        number.get(a).setImageResource(background);
        number.get(b).setImageResource(background1);
        number.get(a).setTag(background);
        number.get(b).setTag(background1);
    }

    private void decalageDroite(){
        for(int a = 0; a<(numberBlocks-1);a++ ){
            for(int index = (numberBlocks-1); index<number.size();index+=numberBlocks ){
             for(int j = index; j>index-(numberBlocks-1);j-- ){
                if ((int)number.get(j).getTag()==R.drawable.nb2048_0){
                    blockInterchange(j,j-1);
                }
             }
            }
        }
    }
    private void correspondanceDroite(){
        for(int a = 0; a<(numberBlocks-1);a++ ){
            for(int index = (numberBlocks-1); index<number.size();index+=numberBlocks ){
                for(int j = index; j>index-(numberBlocks-1);j-- ){
                    if ((int)number.get(j).getTag()==(int)number.get(j-1).getTag()){
                        int level=0;
                        switch((int)number.get(j).getTag()){
                            case R.drawable.nb2048_1024: level=11;
                                break;
                            case R.drawable.nb2048_512: level=10;
                                break;
                            case R.drawable.nb2048_256: level=9;
                                break;
                            case R.drawable.nb2048_128: level=8;
                                break;
                            case R.drawable.nb2048_64: level=7;
                                break;
                            case R.drawable.nb2048_32: level=6;
                                break;
                            case R.drawable.nb2048_16: level=5;
                                break;
                            case R.drawable.nb2048_8: level=4;
                                break;
                            case R.drawable.nb2048_4: level=3;
                                break;
                            case R.drawable.nb2048_2: level=2;
                                break;
                            default: level=0;
                                break;
                        }
                        number.get(j).setImageResource(numberslist[level]);
                        number.get(j).setTag(numberslist[level]);
                        number.get(j-1).setImageResource(numberslist[0]);
                        number.get(j-1).setTag(numberslist[0]);
                    }
                }
            }
        }
    }
    private void decalageHaut(){
        for(int a = 0; a<(numberBlocks-1);a++ ){
            for(int index = 0; index<numberBlocks;index++ ){
                for(int j = index; j<(number.size()-numberBlocks);j+=numberBlocks ){
                    if ((int)number.get(j).getTag()==R.drawable.nb2048_0){
                        blockInterchange(j,j+numberBlocks);
                    }
                }
            }
        }
    }
    private void correspondanceHaut(){
        for(int a = 0; a<(numberBlocks-1);a++ ){
            for(int index = 0; index<numberBlocks;index++ ){
                for(int j = index; j<(number.size()-numberBlocks);j+=numberBlocks ){
                    if ((int)number.get(j).getTag()==(int)number.get(j+numberBlocks).getTag()){
                        int level=0;
                        switch((int)number.get(j).getTag()){
                            case R.drawable.nb2048_1024: level=11;
                                break;
                            case R.drawable.nb2048_512: level=10;
                                break;
                            case R.drawable.nb2048_256: level=9;
                                break;
                            case R.drawable.nb2048_128: level=8;
                                break;
                            case R.drawable.nb2048_64: level=7;
                                break;
                            case R.drawable.nb2048_32: level=6;
                                break;
                            case R.drawable.nb2048_16: level=5;
                                break;
                            case R.drawable.nb2048_8: level=4;
                                break;
                            case R.drawable.nb2048_4: level=3;
                                break;
                            case R.drawable.nb2048_2: level=2;
                                break;
                            default: level=0;
                                break;
                        }
                        number.get(j).setImageResource(numberslist[level]);
                        number.get(j).setTag(numberslist[level]);
                        number.get(j+numberBlocks).setImageResource(numberslist[0]);
                        number.get(j+numberBlocks).setTag(numberslist[0]);
                    }
                }
            }
        }
    }
    private void decalageBas(){
        for(int a = 0; a<(numberBlocks-1);a++ ){
            for(int index = (number.size()-numberBlocks); index<number.size();index++ ){
                for(int j = index; j>=numberBlocks;j-=numberBlocks ){
                    if ((int)number.get(j).getTag()==R.drawable.nb2048_0){
                        blockInterchange(j,j-numberBlocks);
                    }
                }
            }
        }
    }
    private void correspondanceBas(){
        for(int a = 0; a<(numberBlocks-1);a++ ){
            for(int index = (number.size()-numberBlocks); index<number.size();index++ ){
                for(int j = index; j>=numberBlocks;j-=numberBlocks ){
                    if ((int)number.get(j).getTag()==(int)number.get(j-numberBlocks).getTag()){
                        int level=0;
                        switch((int)number.get(j).getTag()){
                            case R.drawable.nb2048_1024: level=11;
                                break;
                            case R.drawable.nb2048_512: level=10;
                                break;
                            case R.drawable.nb2048_256: level=9;
                                break;
                            case R.drawable.nb2048_128: level=8;
                                break;
                            case R.drawable.nb2048_64: level=7;
                                break;
                            case R.drawable.nb2048_32: level=6;
                                break;
                            case R.drawable.nb2048_16: level=5;
                                break;
                            case R.drawable.nb2048_8: level=4;
                                break;
                            case R.drawable.nb2048_4: level=3;
                                break;
                            case R.drawable.nb2048_2: level=2;
                                break;
                            default: level=0;
                                break;
                        }
                        number.get(j).setImageResource(numberslist[level]);
                        number.get(j).setTag(numberslist[level]);
                        number.get(j-numberBlocks).setImageResource(numberslist[0]);
                        number.get(j-numberBlocks).setTag(numberslist[0]);
                    }
                }
            }
        }
    }


    private void decalageGauche(){
        for(int a = 0; a<(numberBlocks-1);a++ ){
            for(int index = 0; index<number.size();index+=numberBlocks ){
                for(int j = index; j<index+(numberBlocks-1);j++ ){
                    if ((int)number.get(j).getTag()==R.drawable.nb2048_0){
                        blockInterchange(j,j+1);
                    }
                }
            }
        }
    }

    private void correspondanceGauche(){
        for(int a = 0; a<(numberBlocks-1);a++ ){
            for(int index = 0; index<number.size();index+=numberBlocks ){
                for(int j = index; j<index+(numberBlocks-1);j++ ){
                    if ((int)number.get(j).getTag()==(int)number.get(j+1).getTag()){
                        int level=0;
                        switch((int)number.get(j).getTag()){
                            case R.drawable.nb2048_1024: level=11;
                                break;
                            case R.drawable.nb2048_512: level=10;
                                break;
                            case R.drawable.nb2048_256: level=9;
                                break;
                            case R.drawable.nb2048_128: level=8;
                                break;
                            case R.drawable.nb2048_64: level=7;
                                break;
                            case R.drawable.nb2048_32: level=6;
                                break;
                            case R.drawable.nb2048_16: level=5;
                                break;
                            case R.drawable.nb2048_8: level=4;
                                break;
                            case R.drawable.nb2048_4: level=3;
                                break;
                            case R.drawable.nb2048_2: level=2;
                                break;
                            default: level=0;
                                break;
                        }
                    number.get(j).setImageResource(numberslist[level]);
                    number.get(j).setTag(numberslist[level]);
                    number.get(j+1).setImageResource(numberslist[0]);
                    number.get(j+1).setTag(numberslist[0]);
                    }
                }
            }
        }
    }

    private boolean testVictory(){
        int level = getIntent().getExtras().getInt("level");
        for(int index = 0; index<number.size();index++ ){
            if (level == 1){
                if ((int)number.get(index).getTag()==R.drawable.nb2048_512){
                    return true;
                }
            }
            else if (level == 2){
                if ((int)number.get(index).getTag()==R.drawable.nb2048_1024){
                    return true;
                }
            }
            else{
                if ((int)number.get(index).getTag()==R.drawable.nb2048_2048){
                    return true;
                }
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
            gestionScore();
        }
    }

    private void gestionScore(){
        int value = 0;
        for(int index = 0; index<number.size();index++ ){
            switch((int)number.get(index).getTag()){
                case R.drawable.nb2048_2048: value+=2048;
                    break;
                case R.drawable.nb2048_1024: value+=1024;
                    break;
                case R.drawable.nb2048_512: value+=512;
                    break;
                case R.drawable.nb2048_256: value+=256;
                    break;
                case R.drawable.nb2048_128: value+=128;
                    break;
                case R.drawable.nb2048_64: value+=64;
                    break;
                case R.drawable.nb2048_32: value+=32;
                    break;
                case R.drawable.nb2048_16: value+=16;
                    break;
                case R.drawable.nb2048_8: value+=8;
                    break;
                case R.drawable.nb2048_4: value+=4;
                    break;
                case R.drawable.nb2048_2: value+=2;
                    break;
                default: value+=0;
                    break;
            }
        }
        score = value;
        scoreRes.setText(String.valueOf(score));
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