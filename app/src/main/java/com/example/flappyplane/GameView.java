package com.example.flappyplane;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private volatile boolean playing;
    private Thread gameThread = null;
    private static Plane plane;
    private Bitmap background;
    private Display display;
    private Point point;
    private Rect rect;
    public static int dWidth, dHeight;
    private Context context;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Block[] blocks;
    private boolean blockStart = false;
    private int score = 0;
    private Bitmap pausebutton;
    private Bitmap playbutton;
    private Rect pauserect;
    private Bitmap speakerbutton;
    private Rect speakerrect;
    private Bitmap replaybutton;
    private Rect replayrect;
    private  SoundPool soundpool;
    private AudioAttributes audioAttributes;
    private int  boostsound;
    private int  gameoversound;
    private int scoredsound;

    private Bitmap speakeroffbutton;
    public static int volume=1;






    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;

        plane = new Plane(context, screenX, screenY);
        surfaceHolder = getHolder();
        paint = new Paint();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=5;
        options.inPreferredConfig=Bitmap.Config.RGB_565;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bautroi, options);

//        background = BitmapFactory.decodeResource(getResources(), R.drawable.bautroi);

        display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0, 0, dWidth, dHeight);



        int blockSpacing = 700;
        blocks = new Block[4];
        for (int i = 0; i < 4; i++) {
            blocks[i] = new Block(context, screenX, screenY);
            blocks[i].setBlockX(screenX + i * blockSpacing);
        }


        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);



//        pausebutton = BitmapFactory.decodeResource(getResources(), R.drawable.pause);  // Tạo icon pause}
//        playbutton = BitmapFactory.decodeResource(getResources(), R.drawable.playicon);  // Tạo icon play
//        pauserect = new Rect(screenX-200, 50, screenX - 50, 200);  // Vị trí nút
//        speakerbutton = BitmapFactory.decodeResource(getResources(), R.drawable.speaker);  // Tạo icon speaker
//        speakeroffbutton = BitmapFactory.decodeResource(getResources(), R.drawable.speakeroff);  // Tạo icon speaker
//        speakerrect = new Rect(screenX - 200, 250, screenX-50, 400 );  // Vị trí nút
//
//       replaybutton= BitmapFactory.decodeResource(getResources(),R.drawable.replay);
//        replayrect= new Rect(50,50,200,200);






        pausebutton = BitmapFactory.decodeResource(getResources(), R.drawable.pause,options);  // Tạo icon pause}
        playbutton = BitmapFactory.decodeResource(getResources(), R.drawable.playicon,options);  // Tạo icon play
        pauserect = new Rect(screenX-200, 50, screenX - 50, 200);  // Vị trí nút
        speakerbutton = BitmapFactory.decodeResource(getResources(), R.drawable.speaker,options);  // Tạo icon speaker
        speakeroffbutton = BitmapFactory.decodeResource(getResources(), R.drawable.speakeroff,options);  // Tạo icon speaker
        speakerrect = new Rect(screenX - 200, 250, screenX-50, 400 );  // Vị trí nút

        replaybutton= BitmapFactory.decodeResource(getResources(),R.drawable.replay,options);
        replayrect= new Rect(50,50,200,200);




        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundpool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();
        boostsound= soundpool.load(context, R.raw.boosting, 1);
        scoredsound= soundpool.load(context, R.raw.scored, 1);
        gameoversound= soundpool.load(context, R.raw.gameover, 1);}







    @Override
    public void run() {


        while (playing) {

            draw();

            update();

            sleep();


        }


    }


    private void update() {

        plane.update();

        if (blockStart) {
            for (int i = 0; i < 4; i++) {
                blocks[i].update();
                if (blocks[i].getBlockX()<0){
                    blocks[i].setHeightTop(50+ new Random().nextInt(dHeight/2-50));
                    if (i==0){blocks[i].setBlockX(blocks[3].getBlockX()+700);}
                    else{blocks[i].setBlockX(blocks[i-1].getBlockX()+700);}
                    blocks[i].setScored(false);

                }

//VA CHẠM
                if (
                        plane.getDetectCollision().bottom >= dHeight ||
                                plane.getY() <= 0 ||
                                Rect.intersects(plane.getDetectCollision(), blocks[i].getDetectCollision()) ||
                                Rect.intersects(plane.getDetectCollision(), blocks[i].getDetectCollision1())) {
                    soundpool.play(gameoversound,volume,volume,1,0,1);




                    Intent gameOverIntent = new Intent(context, GameOverActivity.class);
                    gameOverIntent.putExtra("score", score);
                    context.startActivity(gameOverIntent);
                }

                if ((plane.getX()>blocks[i].getDetectCollision().right)&&!blocks[i].getScored()){
                    soundpool.play(scoredsound,volume,volume,1,0,1);
                    score++;
                    blocks[i].setScored(true);
                }
                blocks[i].setSpeeextra(score+15);

            }
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(background, null, rect, null);

            canvas.save();
            canvas.rotate(plane.getRotation(), plane.getX() , plane.getY() );
            canvas.drawBitmap(plane.getBitmap(), plane.getX(), plane.getY(), paint);
            canvas.restore();

            if (blockStart) {
                for (int i = 0; i < 4; i++) {
                    canvas.drawBitmap(blocks[i].getTopBlock(), blocks[i].getBlockX(),
                            blocks[i].getHeightTop() - blocks[i].getTopBlock().getHeight(), null);

                    canvas.drawBitmap(blocks[i].getBottonBlock(), blocks[i].getBlockX(),
                            blocks[i].getHeightTop() + 800, null);
                }
            }

            if (playing)
            {
                canvas.drawBitmap(pausebutton, null, pauserect, null);}
            else


            {

                canvas.drawBitmap(playbutton, null, pauserect, null);
            }
            if (volume!=0)
            {canvas.drawBitmap(speakerbutton, null, speakerrect, null);}

            else{
                canvas.drawBitmap(speakeroffbutton,null,speakerrect,null);

            }
            canvas.drawBitmap(replaybutton,null,replayrect,null);






            canvas.drawText(String.valueOf(score), getWidth()/2, 200, paint);
            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e){e.printStackTrace();}


    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void sleep(){
        try {
            Thread.sleep(1);

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {



        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (pauserect.contains((int) event.getX(), (int) event.getY())) {
                if (playing){

                    pause();


                }
                else{
                    resume();

                }
            }else

            if (speakerrect.contains((int) event.getX(), (int) event.getY())) {
               volume= (volume+1)%2;
                if(!playing){draw();}



            } else if (replayrect.contains((int) event.getX(), (int) event.getY())) {
                resume();
                plane.stopBoosting();
                blockStart = false;
                score = 0;
                plane.planereset();


                for (int i = 0; i < 4; i++) {
                    blocks[i].setBlockX(dWidth + i * 700);}





            } else {
                if (playing)
                {soundpool.play(boostsound,volume,volume,1,0,1);
                    plane.setBoosting();}

                blockStart = true;


            }
        }
        return true;
    }
}