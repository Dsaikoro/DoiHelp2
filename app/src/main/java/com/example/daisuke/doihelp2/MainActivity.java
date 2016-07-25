package com.example.daisuke.doihelp2;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends Activity implements View.OnTouchListener{
    private static final int ROUGH_WIDTH = 60;
    private static final int ROUGH_HEIGHT = 30;
    private static final int ROUGH_SPAWN_MAX = 100;
    private static final int ROUGH_RESPAWN_TIME = 200;
    private static final int TONBO_WIDTH = 400;
    private static final int TONBO_HEIGHT = 500;

    private RelativeLayout varLayout;
    private int layoutWidth, layoutHeight;
    Rough roughs[];
    ImageView roughImages[];
    private boolean startFlag;
    Random rand;
    private Handler handler;
    private Runnable spawnRough;
    Tonbo tonbo;
    ImageView tonboImage;
    private int lastX, lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        varLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        varLayout.setBackgroundColor(Color.rgb(176,110,53));

        roughImages = new ImageView[ROUGH_SPAWN_MAX];
        roughs = new Rough[ROUGH_SPAWN_MAX];
        for (int i = 0; i < roughs.length; i++) {
            roughImages[i] = new ImageView(this);
            roughImages[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rough));
            roughImages[i].setLayoutParams(new ViewGroup.LayoutParams(ROUGH_WIDTH, ROUGH_HEIGHT));
            varLayout.addView(roughImages[i]);
        }
        for(int i = 0; i < roughs.length; i++) roughs[i] = new Rough(roughImages[i], ROUGH_WIDTH, ROUGH_HEIGHT);
        startFlag = true;

        tonboImage = new ImageView(this);
        tonboImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tonbo));
        tonboImage.setLayoutParams(new ViewGroup.LayoutParams(TONBO_WIDTH, TONBO_HEIGHT));
        tonboImage.setOnTouchListener(this);
        varLayout.addView(tonboImage);
        tonbo = new Tonbo(tonboImage, TONBO_WIDTH, TONBO_HEIGHT/10);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        layoutWidth = findViewById(R.id.mainLayout).getWidth();
        layoutHeight = findViewById(R.id.mainLayout).getHeight();
        handler = new Handler();
        rand = new Random();
        spawnRough = new Runnable() {
            private int x,y;

            @Override
            public void run() {
                if(startFlag){
                    for (int i = 0; i < roughs.length; i++) {
                        roughs[i].draw();
                    }
                    startFlag = false;
                }
                for (int i = 0; i < roughs.length; i++) {
                    if (!roughs[i].getUseFlag()) {
                        x = rand.nextInt(layoutWidth - ROUGH_WIDTH);
                        y = rand.nextInt(layoutHeight - ROUGH_HEIGHT);
                        roughs[i].spawn(x, y);
                        break;
                    }
                }
                handler.postDelayed(spawnRough, ROUGH_RESPAWN_TIME);
            }
        };
        handler.postDelayed(spawnRough, 0);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();

        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int diffx = lastX - x;
                int diffy = lastY - y;

                tonbo.move(-diffx, -diffy, varLayout.getWidth(), varLayout.getHeight());
                tonbo.draw();

                for (int i = 0; i < roughs.length; i++) {
                    if (roughs[i].getUseFlag() && roughs[i].collision(tonbo)) {
                        roughs[i].despawn();
                    }
                }
        }
        lastX = x;
        lastY = y;
        return true;
    }
}
