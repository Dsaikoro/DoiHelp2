package com.example.daisuke.doihelp2;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity implements View.OnTouchListener{
    private static final int ROUGH_WIDTH = 60;
    private static final int ROUGH_HEIGHT = 30;
    private static final int ROUGH_SPAWN_MAX = 100;
    private static final int ROUGH_RESPAWN_TIME = 100;
    private static final int TONBO_WIDTH = 400;
    private static final int TONBO_HEIGHT = 500;

    private RelativeLayout scoreLayout;
    private RelativeLayout gameLayout;
    private int gameLayoutWidth, gameLayoutHeight;
    private TextView scoreText;
    private int score;
    private Rough roughs[];
    private ImageView roughImages[];
    private boolean startFlag;
    private Random rand;
    private Handler handler;
    private Runnable spawnRough;


    private Tonbo tonbo;
    private ImageView tonboImage;
    private int lastX, lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreLayout = (RelativeLayout) findViewById(R.id.scoreBoard);
        gameLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        scoreText = (TextView)findViewById(R.id.score);
        score = 0;
        scoreText.setText("Score:" + score);

        roughImages = new ImageView[ROUGH_SPAWN_MAX];
        roughs = new Rough[ROUGH_SPAWN_MAX];
        for (int i = 0; i < roughs.length; i++) {
            roughImages[i] = new ImageView(this);
            roughImages[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grass));
            roughImages[i].setLayoutParams(new ViewGroup.LayoutParams(ROUGH_WIDTH, ROUGH_HEIGHT));
            gameLayout.addView(roughImages[i]);
        }
        for(int i = 0; i < roughs.length; i++) roughs[i] = new Rough(roughImages[i], ROUGH_WIDTH, ROUGH_HEIGHT);
        startFlag = true;
        score = 0;

        tonboImage = new ImageView(this);
        tonboImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tonbo));
        tonboImage.setLayoutParams(new ViewGroup.LayoutParams(TONBO_WIDTH, TONBO_HEIGHT));
        tonboImage.setOnTouchListener(this);
        gameLayout.addView(tonboImage);
        tonbo = new Tonbo(tonboImage, TONBO_WIDTH, TONBO_HEIGHT/10);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        gameLayoutWidth = findViewById(R.id.mainLayout).getWidth();
        gameLayoutHeight = findViewById(R.id.mainLayout).getHeight();
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
                        x = rand.nextInt(gameLayoutWidth - ROUGH_WIDTH);
                        y = rand.nextInt(gameLayoutHeight - ROUGH_HEIGHT);
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

                tonbo.move(-diffx, -diffy, gameLayout.getWidth(), gameLayout.getHeight());
                tonbo.draw();

                for (int i = 0; i < roughs.length; i++) {
                    if (roughs[i].getUseFlag() && roughs[i].collision(tonbo)) {
                        roughs[i].despawn();
                        score++;
                        scoreText.setText("Score:" + score);
                    }
                }
        }
        lastX = x;
        lastY = y;
        return true;
    }
}
