package com.lindsaykroeger.mygame;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Lindsay on 12/9/2016.
 */

public class HighScoresCursorAdapter extends CursorAdapter {

    public HighScoresCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.listview_highscores, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView text_id = (TextView) view.findViewById(R.id.id);
        TextView text_name = (TextView) view.findViewById(R.id.name);
        TextView text_score = (TextView) view.findViewById(R.id.score);

        int id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.TABLE_COL_ID));
        String name = cursor.getString(cursor.getColumnIndex(DatabaseManager.TABLE_COL_NAME));
        int score = cursor.getInt(cursor.getColumnIndex(DatabaseManager.TABLE_COL_SCORE));

        text_id.setText(String.valueOf(id));
        text_name.setText(name);
        text_score.setText(String.valueOf(score));

    }
}
