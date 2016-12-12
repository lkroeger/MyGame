package com.lindsaykroeger.mygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddHighScoreActivity extends AppCompatActivity {

    Button add;
    EditText name;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_high_score);

        name = (EditText) findViewById(R.id.name);
        add = (Button) findViewById(R.id.add);
        score = (TextView) findViewById(R.id.score);

        Intent intent = getIntent();
        int num = intent.getIntExtra("HighScore", 0);

        score.setText(String.valueOf(num));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseManager db = DatabaseManager.getInstance(AddHighScoreActivity.this);
                String theName = name.getText().toString();
                String theScore = score.getText().toString();

                if(TextUtils.isEmpty(theName) || TextUtils.isEmpty(theScore)) {
                    Toast.makeText(AddHighScoreActivity.this, "All fields must contain a value.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Scores score = new Scores(theName, Integer.parseInt(theScore));
                    db.addRow(score);
                    finish();
                }

                Intent intent = new Intent(AddHighScoreActivity.this, StartActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
