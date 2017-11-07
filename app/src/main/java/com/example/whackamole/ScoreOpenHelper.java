package com.example.whackamole;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.whackamole.WhackAMoleContract.ScoresEntry;

/**
 * Created by jason on 01/11/2017.
 */

public class ScoreOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WhackAMole.db";

    ScoreOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ScoresEntry.TABLE_NAME + " (" + ScoresEntry._ID
                   + " INTEGER PRIMARY KEY, " + ScoresEntry.COLUMN_NAME_SCORE + " TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ScoresEntry.TABLE_NAME);
        onCreate(db);
    }
}
