package com.example.daisuke.doihelp2;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends Activity implements View.OnTouchListener{
    Tonbo tonbo;
    Rough roughs[];
    private static final int TONBO_WIDTH = 400;
    private static final int TONBO_HEIGHT = 500;
    private static final int ROUGH_WIDTH = 60;
    private static final int ROUGH_HEIGHT = 30;
    private int lastX, lastY;
    private RelativeLayout varLayout;
    ImageView tonboImage;
    ImageView roughImages[];
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rand = new Random();
        setContentView(R.layout.activity_main);
        varLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        varLayout.setBackgroundColor(Color.rgb(176,110,53));
//        varLayout = new RelativeLayout(this);
//        varLayout.setLayoutParams(
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        setContentView(varLayout);


        roughImages = new ImageView[10];
        roughs = new Rough[10];
        for (int i = 0; i < 10; i++) {
            roughImages[i] = new ImageView(this);
            //roughImage.setImageResource(R.drawable.rough);
            roughImages[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rough));
            roughImages[i].setLayoutParams(new ViewGroup.LayoutParams(ROUGH_WIDTH, ROUGH_HEIGHT));
            varLayout.addView(roughImages[i]);

//            int x = rand.nextInt(varLayout.getWidth() - ROUGH_WIDTH);
//            int y = rand.nextInt(varLayout.getHeight() - ROUGH_HEIGHT);
            int x,y;
            x = i*30;
            y = i*30;
            roughs[i] = new Rough(x, y, roughImages[i], ROUGH_WIDTH, ROUGH_HEIGHT);
            roughs[i].draw();
        }

        tonboImage = new ImageView(this);
//        tonboImage.setImageResource(R.drawable.tonbo);
        tonboImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tonbo));
        tonboImage.setLayoutParams(new ViewGroup.LayoutParams(TONBO_WIDTH, TONBO_HEIGHT));
        tonboImage.setOnTouchListener(this);
        varLayout.addView(tonboImage);

        tonbo = new Tonbo(0, 0, tonboImage, TONBO_WIDTH, TONBO_HEIGHT/10);
//        tonbo = new Tonbo(50, 0, tonboImage, TONBO_WIDTH, TONBO_HEIGHT/10);
//        tonbo.draw();
//        tonboImage.layout(100, 500, 100 + tonboImage.getWidth(), 500 + tonboImage.getHeight());
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();

        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int diffx = lastX - x;
                int diffy = lastY - y;

//                view.layout(x, y, x + view.getWidth(), y + view.getHeight());
                tonbo.move(-diffx, -diffy, varLayout.getWidth(), varLayout.getHeight());
                tonbo.draw();
        }

        lastX = x;
        lastY = y;
        return true;
    }
}