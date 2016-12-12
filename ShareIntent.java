package com.lindsaykroeger.mygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ShareIntent extends AppCompatActivity {

    Button button;
    EditText editText;
    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_intent);

        mTracker = ((AnalyticsApplication)this.getApplication()).getDefaultTracker();
        mTracker.setScreenName("Share Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("In App")
                .setAction("Sharing Game")
                .build());

        button = (Button)findViewById(R.id.button_share);
        editText = (EditText)findViewById(R.id.editText_share);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = editText.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, temp);
                intent.setType("text/plain");
//                ComponentName name = intent.resolveActivity(getPackageManager());
                if(intent.resolveActivity(getPackageManager()) == null)
                {
                    //bail out; end this; do nothing;
                    return;
                }

                startActivity(Intent.createChooser(intent, "Make It So..."));
            }
        });
    }
}
