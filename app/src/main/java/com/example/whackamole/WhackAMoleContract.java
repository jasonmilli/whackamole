package com.example.whackamole;

import android.provider.BaseColumns;

/**
 * Created by jason on 02/11/2017.
 */

public final class WhackAMoleContract {
    private WhackAMoleContract() {}

    public static class ScoresEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";
        public static final String COLUMN_NAME_SCORE = "score";
    }
}
