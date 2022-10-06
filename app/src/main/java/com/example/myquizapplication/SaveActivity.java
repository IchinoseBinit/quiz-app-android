package com.example.myquizapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity  {

    Button saveBtn;
    TextView scoreTxtView;
    EditText nameTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_save);
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        saveBtn = findViewById(R.id.save);
        nameTxt = findViewById(R.id.txtName);
        scoreTxtView = findViewById(R.id.your_score);
        scoreTxtView.setText("Your score: "+score);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
              String name = nameTxt.getText().toString();
              saveToDb(new Score(name, score, LocalDateTime.now()));
            }
        });
    }

    private void saveToDb(Score score) {
        TinyDB tinyDB = new TinyDB(this);
        ArrayList<Object> scores =  tinyDB.getListObject("scores", Score.class);
        scores.add((Object) score);
        tinyDB.putListObject("scores", scores);

        Intent activityChangeIntent = new Intent( this, FirstActivity.class);
        startActivity(activityChangeIntent);
    }
}
