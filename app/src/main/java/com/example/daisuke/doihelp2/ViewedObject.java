package com.example.daisuke.doihelp2;

import android.widget.ImageView;

/**
 * ViewObject is extended display object class
 */
public class ViewedObject {
    protected int x, y;
    private ImageView image;
    private boolean useFlag;

    public ViewedObject(int x, int y, ImageView image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.useFlag = false;
    }

    public void draw() {
        if (this.useFlag) {
            image.layout(x, y, x + image.getWidth(), y + image.getHeight());
        } else {
            image.layout(-1, -1, -1, -1);
        }
    }

    public void changeImage(){
        //TODO: 画像切り替えるや～つ
    }

    public void setUseFlag(boolean flag) {
        this.useFlag = flag;
    }
}
