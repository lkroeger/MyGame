package com.lindsaykroeger.mygame;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class GameActivity extends AppCompatActivity {

    GameView  gameView;
    int width;
    int height;
    SoundManager soundManager;
    HighScoresCursorAdapter cursorAdapter;
    Tracker mTracker;
    public int numBullets;
    public int enemySpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        soundManager = new SoundManager();
        soundManager.loadSounds(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        gameView = new GameView(this, width, height);
        gameView.setScreenWidth(width);
        gameView.setScreenHeight(height);

        setContentView(gameView);

        Intent intent = getIntent();
        numBullets = intent.getIntExtra("numBullets", 10);
        enemySpeed = intent.getIntExtra("enemySpeed", 250);

        DatabaseManager databaseManager = DatabaseManager.getInstance(this);
        Cursor cursor = databaseManager.getCursorForAllTableData();
        cursorAdapter = new HighScoresCursorAdapter(GameActivity.this, cursor, 0);

        mTracker = ((AnalyticsApplication)this.getApplication()).getDefaultTracker();
        mTracker.setScreenName("Play Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("In App")
                .setAction("Playing Game")
                .build());
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        soundManager.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.soundManager.stop();
    }

    public void endGame(int score) {
        Intent intent = new Intent(GameActivity.this, AddHighScoreActivity.class);
        intent.putExtra("HighScore", score);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cursorAdapter.swapCursor(DatabaseManager.getInstance(this).getCursorForAllTableData());
    }
}
