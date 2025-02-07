package com.example.flappyplane;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);



        VideoView videoView = findViewById(R.id.introVideoView);





        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro); // Đường dẫn tới video trong thư mục res/raw
        videoView.setVideoURI(videoUri);



        videoView.start();




        videoView.setOnCompletionListener(mp -> {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();}
            return true;
    }
}
