package com.lindsaykroeger.mygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class StartActivity extends AppCompatActivity {

    private Button easy;
    private Button medium;
    private Button hard;
    private Button share;
    private Button scores;

    int numBullets;
    int enemySpeed;

    SoundManager soundManager;
    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        soundManager = new SoundManager();
        soundManager.loadSounds(this);

        setContentView(R.layout.activity_start);

        mTracker = ((AnalyticsApplication)this.getApplication()).getDefaultTracker();
        mTracker.setScreenName("Start Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("In App")
                .setAction("Opened App")
                .build());

        easy = (Button) findViewById(R.id.easy);
        medium = (Button) findViewById(R.id.medium);
        hard = (Button) findViewById(R.id.hard);
        share = (Button)findViewById(R.id.share);
        scores = (Button)findViewById(R.id.scores);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, GameActivity.class);
                intent.putExtra("numBullets", 10);
                intent.putExtra("enemySpeed", 250);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("In App")
                        .setAction("Selected Easy")
                        .build());
                startActivity(intent);
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, GameActivity.class);
                intent.putExtra("numBullets", 5);
                intent.putExtra("enemySpeed", 350);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("In App")
                        .setAction("Selected Medium")
                        .build());
                startActivity(intent);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, GameActivity.class);
                intent.putExtra("numBullets", 3);
                intent.putExtra("enemySpeed", 450);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("In App")
                        .setAction("Selected Hard")
                        .build());
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ShareIntent.class);
                startActivity(intent);
            }
        });

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, HighScoresActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        soundManager.stop();
        soundManager.playSound("galaga_theme_cut.mp3", 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundManager.stop();
        soundManager.playSound("galaga_theme_cut.mp3", 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundManager.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.stop();
    }
}