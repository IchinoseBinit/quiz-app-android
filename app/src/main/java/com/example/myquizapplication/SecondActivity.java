package com.example.myquizapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity  {

    Button easyBtn, mediumBtn, hardBtn;
    String selectedButton = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);
        easyBtn = findViewById(R.id.easy_btn);
        mediumBtn = findViewById(R.id.medium_btn);
        hardBtn = findViewById(R.id.hard_btn);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearBtn();

                selectedButton = "easy";
                easyBtn.setBackgroundColor(Color.MAGENTA);
            }
        });
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearBtn();

                selectedButton = "medium";
                mediumBtn.setBackgroundColor(Color.MAGENTA);
            }
        });
        hardBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearBtn();
                selectedButton = "hard";
                hardBtn.setBackgroundColor(Color.MAGENTA);
            }
        });

        final Button submitBtn = (Button) findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedButton.isEmpty()) {
                    Toast.makeText(SecondActivity.this, "Please select a difficulty first", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent activityChangeIntent = new Intent( SecondActivity.this, MainActivity.class);
                activityChangeIntent.putExtra("difficulty", selectedButton);
                startActivity(activityChangeIntent);
            }
        });
    }

    private void clearBtn() {
        easyBtn.setBackgroundColor(Color.WHITE);
        mediumBtn.setBackgroundColor(Color.WHITE);
        hardBtn.setBackgroundColor(Color.WHITE);
    }
}
