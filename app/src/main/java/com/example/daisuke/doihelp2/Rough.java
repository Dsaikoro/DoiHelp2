package com.example.daisuke.doihelp2;

import android.widget.ImageView;

/**
 * Created by Daisuke on 2016/07/21.
 */
public class Rough extends CollisionObject {

    public Rough(int x, int y, ImageView image, int width, int height) {
        super(x, y, image, width, height);
    }

    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
        setUseFlag(true);
    }

    public void despawn() {
        setUseFlag(false);
    }
}
