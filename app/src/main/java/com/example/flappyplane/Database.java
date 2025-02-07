package com.example.flappyplane;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "csdl";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS highscore (id INTEGER PRIMARY KEY, score INTEGER)");

        db.execSQL("INSERT INTO highscore (id, score) VALUES (1, 0)");
    }

    public void querydata(String sql) {
        SQLiteDatabase a = getWritableDatabase();
        a.execSQL(sql);
    }


    public Cursor getdata(String sql) {
        SQLiteDatabase a = getReadableDatabase();
        return a.rawQuery(sql, null);
    }

    public void sethighscore(int score) {
        querydata("UPDATE highscore SET score = " + score + " WHERE id = 1");
    }

    public int gethighscore() {
        Cursor cursor = getdata("SELECT score FROM highscore WHERE id = 1");
        if (cursor != null && cursor.moveToFirst()) {
            int highScore = cursor.getInt(0);
            cursor.close();
            return highScore;
        }
        return 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
