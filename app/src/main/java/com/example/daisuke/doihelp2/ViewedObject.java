package com.example.daisuke.doihelp2;

import android.widget.ImageView;

/**
 * Created by Daisuke on 2016/07/21.
 */
public class ViewedObject {
    protected int x, y;
    private ImageView image;
    private boolean useFlag;

    public ViewedObject(ImageView image) {
        this.x = image.getLeft();
        this.y = image.getTop();
        this.image = image;
        this.useFlag = false;
    }

    public void draw() {
        if (useFlag)
            image.layout(x, y, x + image.getWidth(), y + image.getHeight());
        else
            image.layout(-1, -1, -1, -1);
    }

    public void changeImage(){
        //TODO: 画像切り替えるや～つ
    }

    public void setUseFlag(boolean flag) {
        this.useFlag = flag;
    }
}
