package com.example.whackamole;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.whackamole.WhackAMoleContract.ScoresEntry;

public class ScoreboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        String score = getIntent().getStringExtra("SCORE");
        TextView scoreText = (TextView) findViewById(R.id.scoreValue);
        scoreText.setText(getIntent().getStringExtra("SCORE"));

        SQLiteDatabase db = new ScoreOpenHelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ScoresEntry.COLUMN_NAME_SCORE, score);
        db.insert(ScoresEntry.TABLE_NAME, null, values);

        TableLayout scoreTable = (TableLayout) findViewById(R.id.scoreTable);

        String[] projections = {ScoresEntry.COLUMN_NAME_SCORE};
        Cursor cursor = db.query(ScoresEntry.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView scoreField = new TextView(this);
            scoreField.setText(cursor.getString(cursor.getColumnIndex(ScoresEntry.COLUMN_NAME_SCORE)));
            scoreField.setTextColor(ContextCompat.getColor(this, R.color.skyblue));

            tr.addView(scoreField);
            scoreTable.addView(tr);
        }
    }
}