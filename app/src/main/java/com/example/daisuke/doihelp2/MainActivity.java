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
    private int layoutWidth, layoutHeight;

    private Handler handler;
    private Runnable spawnRough = new Runnable() {
        private int x,y;
        @Override
        public void run() {
            for(int i = 0; i < 10; i++) {
                if (!roughs[i].getUseFlag()){
                    x = rand.nextInt(layoutHeight - ROUGH_WIDTH);
                    y = rand.nextInt(layoutWidth - ROUGH_HEIGHT);
//                    x = 100*i;
//                    y = 100*i;
                    roughs[i].spawn(x, y);
                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rand = new Random();
        varLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        varLayout.setBackgroundColor(Color.rgb(176,110,53));

        roughImages = new ImageView[10];
        roughs = new Rough[10];
        for (int i = 0; i < 10; i++) {
            roughImages[i] = new ImageView(this);
            roughImages[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rough));
            roughImages[i].setLayoutParams(new ViewGroup.LayoutParams(ROUGH_WIDTH, ROUGH_HEIGHT));
            varLayout.addView(roughImages[i]);
        }

        tonboImage = new ImageView(this);
        tonboImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tonbo));
        tonboImage.setLayoutParams(new ViewGroup.LayoutParams(TONBO_WIDTH, TONBO_HEIGHT));
        tonboImage.setOnTouchListener(this);
        varLayout.addView(tonboImage);

        tonbo = new Tonbo(tonboImage, TONBO_WIDTH, TONBO_HEIGHT/10);
        for(int i = 0; i < 10; i++) roughs[i] = new Rough(roughImages[i], ROUGH_WIDTH, ROUGH_HEIGHT);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        layoutWidth = findViewById(R.id.mainLayout).getWidth();
        layoutHeight = findViewById(R.id.mainLayout).getHeight();
        handler = new Handler();
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
        }

        lastX = x;
        lastY = y;
        return true;
    }
}
