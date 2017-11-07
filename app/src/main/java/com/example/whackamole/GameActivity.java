package com.example.whackamole;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        int level = 3;
        int lives = 2;
        int id = 0;

        TextView levelText = (TextView) findViewById(R.id.levelText);
        levelText.setText(level + "");

        TextView livesText = (TextView) findViewById(R.id.livesText);
        livesText.setText(lives + "");

        TextView scoreboardText = (TextView) findViewById(R.id.scoreboardText);

        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
        Map<Integer, LinearLayout> buttonHoles = new HashMap<>();

        for (int i = 0; i < level; i++) {
            LinearLayout lineLayout = new LinearLayout(this);

            lineLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1));
            lineLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < level; j++) {
                LinearLayout buttonHole = new LinearLayout(this);

                buttonHole.setLayoutParams(
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                1));

                buttonHoles.put(id, buttonHole);
                id++;

                lineLayout.addView(buttonHole);
            }

            gameLayout.addView(lineLayout);
        }

        Handler handler = new Handler();
        int delay = 6000 / level;

        Button button = new Button(this);
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.skyblue));

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {
                TextView scoreboard = (TextView) findViewById(R.id.scoreboardText);
                scoreboard.setText((Integer.parseInt(scoreboard.getText().toString()) + 1) + "");

                LinearLayout buttonHole = (LinearLayout) button.getParent();
                buttonHole.removeAllViews();
            }
        });

        handler.postDelayed(
                new GameRunnable(0, handler, delay, buttonHoles, true, button, livesText,
                        scoreboardText, this.getApplicationContext()), delay);
    }

    public static class GameRunnable implements Runnable {
        private int random;
        private Handler handler;
        private int delay;
        private Map<Integer, LinearLayout> buttonHoles;
        private boolean first;
        private Button button;
        private TextView livesText;
        private TextView scoreboardText;
        private Context context;

        public GameRunnable(int random, Handler handler, int delay,
                            Map<Integer, LinearLayout> buttonHoles, boolean first, Button button,
                            TextView livesText, TextView scoreboardText, Context context) {
            this.random = random;
            this.handler = handler;
            this.delay = delay;
            this.buttonHoles = buttonHoles;
            this.first = first;
            this.button = button;
            this.livesText = livesText;
            this.scoreboardText = scoreboardText;
            this.context = context;
        }

        public void run() {
            if (!first) {
                LinearLayout buttonHole = buttonHoles.get(this.random);

                if (buttonHole.getChildCount() > 0) {
                    buttonHole.removeAllViews();

                    int newLives = Integer.parseInt(this.livesText.getText().toString()) - 1;

                    if (newLives > 0) {
                        livesText.setText(newLives + "");
                    } else {
                        Intent scoreIntent = new Intent(this.context, ScoreboardActivity.class);
                        scoreIntent.putExtra("SCORE", this.scoreboardText.getText().toString());

                        this.context.startActivity(scoreIntent);
                        return;
                    }
                }
            }

            this.random = (new Random()).nextInt(buttonHoles.size() - 1);

            LinearLayout buttonHole = buttonHoles.get(this.random);
            buttonHole.addView(button);

            button.setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));

            handler.postDelayed(
                    new GameRunnable(this.random, this.handler, this.delay, this.buttonHoles, false,
                            this.button, this.livesText, this.scoreboardText, this.context),
                    delay);
        }
    }
}
