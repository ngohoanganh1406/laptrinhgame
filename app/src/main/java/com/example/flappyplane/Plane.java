package com.example.flappyplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;



public class Plane {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;
    private int rotation;

    private boolean boosting;


    private Rect detectCollision;


    public Plane (Context context, int screenX, int screenY) {
        x = screenX/4;
        y = screenY/4;
        speed = 0;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane);
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());


        boosting = false;
    }


    public void setBoosting()
    {
        boosting = true;
        speed=-42;
        rotation=-45;

    }

    public void stopBoosting() {

        boosting = false;
    }
    public void planereset(){
        this.x = GameView.dWidth/4;
        this.y = GameView.dHeight/4;;
    rotation=0;}

    public void update() {

        if (boosting){

            speed += 4;
            this.y+=speed;
            rotation += 3;
            if (rotation>45){rotation=45;}
        }
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();


        if (y<0){y=0;}
        if (y>GameView.dHeight-bitmap.getHeight()){y=GameView.dHeight-bitmap.getHeight();}


    }
    public Rect getDetectCollision() {
        return detectCollision;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRotation() {
    return rotation;
    }







}
