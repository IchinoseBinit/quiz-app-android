package com.example.myquizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first);

        final Button playButton = (Button) findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(activityChangeIntent);
            }
        });

        final Button scoreBoardButton = (Button) findViewById(R.id.scoreboard);
        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(FirstActivity.this, ScoreBoardActivity.class);
                startActivity(activityChangeIntent);
            }
        });
    }
}
