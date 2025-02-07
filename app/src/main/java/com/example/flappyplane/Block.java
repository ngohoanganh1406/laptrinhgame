package com.example.flappyplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Block {
    private Bitmap topBlock, bottomBlock;
    private int BlockX, heightTop;
    private int screenHeight;
    private int speed;
    private boolean scored;


    public Block(Context context, int screenX, int screenY) {
        this.screenHeight = screenY;


        topBlock = BitmapFactory.decodeResource(context.getResources(), R.drawable.thienthach);
        bottomBlock = BitmapFactory.decodeResource(context.getResources(), R.drawable.nha);
        heightTop = 50+ new Random().nextInt(screenHeight/2-50);
        BlockX = screenX;
        scored = false;
        speed=15;

    }
    public void setScored(boolean sc){
        this.scored=sc;
    }
    public boolean getScored(){
        return scored;
    }


    public void setBlockX(int x) {
        BlockX = x;
    }
    void setHeightTop(int x){
        heightTop=x;
    }

    public void setSpeeextra(int a){
        speed=a;
    }

    public Bitmap getTopBlock() {
        return topBlock;
    }

    public Bitmap getBottonBlock() {
        return bottomBlock;
    }

    public int getBlockX() {
        return BlockX;
    }

    public int getHeightTop() {
        return heightTop;
    }

    public void update() {
        BlockX -= speed; // Di chuyển ống sang trái

    }

    public Rect getDetectCollision() {
        return new Rect(BlockX, 0, BlockX + topBlock.getWidth(), heightTop); // Hình chữ nhật va chạm cho ống trên
    }

    public Rect getDetectCollision1() {
        return new Rect(BlockX, heightTop + 800, BlockX + bottomBlock.getWidth(), screenHeight); // Hình chữ nhật va chạm cho ống dưới
    }

}
