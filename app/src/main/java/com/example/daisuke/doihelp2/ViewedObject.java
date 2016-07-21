package com.example.daisuke.doihelp2;

import android.widget.ImageView;

/**
 * Created by Daisuke on 2016/07/21.
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
        //TODO: flag で非表示
        image.layout(x, y, x + image.getWidth(), y + image.getHeight());
    }

    public void changeImage(){
        //TODO: 画像切り替えるや～つ
    }

    public void setUseFlag(boolean flag) {
        this.useFlag = flag;
    }
}
