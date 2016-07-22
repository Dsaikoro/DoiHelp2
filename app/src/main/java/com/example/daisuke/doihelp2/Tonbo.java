package com.example.daisuke.doihelp2;

import android.widget.ImageView;

/**
 * Created by Daisuke on 2016/07/21.
 */
public class Tonbo extends CollisionObject {
    public Tonbo(ImageView image, int width, int height) {
        super(image, width, height);
        setUseFlag(true);
    }

    public void move(int vx, int vy) {
        x += vx;
        y += vy;
    }

    public void move(int vx, int vy, int width, int height) {
        move(vx, vy);
        if (x < 0) x = 0;
        if (x > width - this.width) x = width - this.width;
        if (y < 0) y = 0;
        if (y > height - this.height) y = height - this.height;
    }
}
