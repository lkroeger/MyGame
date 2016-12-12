package com.lindsaykroeger.mygame;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Arrays;

import static com.lindsaykroeger.mygame.R.id.scores;

public class HighScoresActivity extends AppCompatActivity {

    private ListView listView;
    private Scores[] scores;
    private HighScoresCursorAdapter cursorAdapter;
    Tracker mTracker;


    private class CustomAdapter extends ArrayAdapter<Scores> {

        public CustomAdapter(Context context, Scores[] score) {
            super(context, 0, score);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Scores temp = scores[position];

            if(convertView == null) {
                convertView = LayoutInflater.from(HighScoresActivity.this).inflate(R.layout.listview_highscores, null, false);
            }

            TextView text_name = (TextView) convertView.findViewById(R.id.name);
            TextView text_score = (TextView) convertView.findViewById(R.id.score);
            text_name.setText(temp.getName());
            text_score.setText(temp.getScore());

            return convertView;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        mTracker = ((AnalyticsApplication)this.getApplication()).getDefaultTracker();
        mTracker.setScreenName("High Scores Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("In App")
                .setAction("Check High Scores")
                .build());

        listView = (ListView)findViewById(R.id.high_score_list);

        DatabaseManager databaseManager = DatabaseManager.getInstance(this);
//        Cursor cursor = databaseManager.getCursorForAllTableData();
//        cursorAdapter = new HighScoresCursorAdapter(HighScoresActivity.this, cursor, 0);
//        listView.setAdapter(cursorAdapter);

        Scores[] scores = databaseManager.getAllData();

        for(int i = 0; i < scores.length; i++) {
            for(int j = 1; j < scores.length; j++) {
                if(scores[j-1].getScore() < scores[j].getScore()) {
                    Scores temp = scores[j-1];
                    scores[j-1] = scores[j];
                    scores[j] = temp;
                }
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<Scores>(this, android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(arrayAdapter);


    }

}
