package com.example.daisuke.doihelp2;

import android.widget.ImageView;

/**
 * Created by Daisuke on 2016/07/21.
 */
public class CollisionObject extends ViewedObject{
    protected int width, height;

    public CollisionObject(ImageView image, int width, int height){
        super(image);

        this.width = width;
        this.height = height;
    }

    public boolean collision(CollisionObject otherObj) {
        if (this.y > otherObj.y + otherObj.height)
            return false;
        if (this.y + this.height < otherObj.y)
            return false;
        if (this.x > otherObj.x + otherObj.width)
            return false;
        if (this.x + this.width < otherObj.x)
            return false;
        return true;
    }
}
