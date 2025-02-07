package com.example.flappyplane;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    Button a, b;
    TextView a1, a2;
    int highscore = 0;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Intent intent1 = getIntent();
        int score = intent1.getIntExtra("score", 0);


        database = new Database(this);

        highscore = database.gethighscore();
        if (score>highscore){
            highscore=score;
            database.sethighscore(score);
            System.gc();
        }



        a1 = findViewById(R.id.hightscore);




        a1.setText("Highscore  " + highscore);
        a2 = findViewById(R.id.score);
        a2.setText(""+score);


        a = findViewById(R.id.butChoiLai);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentchoilai = new Intent(GameOverActivity.this, MainActivity.class);


                startActivity(intentchoilai);
                finish();
            }
        });

        // Nút thoát
        b = findViewById(R.id.butExit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);;
            }
        });
    }


}
