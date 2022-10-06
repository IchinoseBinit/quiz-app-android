package com.example.myquizapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity  {

    ListView listView;
    Switch sortSwitch;
    ArrayList<Score> scores;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scoreboard);

        System.out.println("Changed scoreboard");
        listView = findViewById(R.id.listview);
        sortSwitch = findViewById(R.id.sort_switch);
        scores = getFromDb();
        setAdapter(scores);

        sortSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setAdapter(sortArrayList(scores));
                } else {
                    setAdapter(scores);
                }
            }
        });

    }

    private ArrayList<Score> sortArrayList(ArrayList<Score> scores) {

        ArrayList<Score> sortedScore = (ArrayList<Score>) scores.clone();

        int n = scores.size();
        Score temp;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(sortedScore.get(j-1).score < sortedScore.get(j).score){
                    temp = sortedScore.get(j-1);
                    sortedScore.set( j-1,  sortedScore.get(j));
                    sortedScore.set( j,temp);
                }
            }
        }
        return  sortedScore;
    }

    private void setAdapter(ArrayList<Score> scores) {
        ArrayList<String> text = new ArrayList<>();
        for (Score x : scores) {
            text.add(x.name + ": " + x.score);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.score_board, R.id.scores, text);
        listView.setAdapter(adapter);
    }

    private ArrayList<Score> getFromDb() {
        TinyDB tinyDB = new TinyDB(this);

        ArrayList<Object> scores =  tinyDB.getListObject("scores", Score.class);

        ArrayList<Score> arrayList = new ArrayList<>();
        for (Object obj : scores) {
            arrayList.add((Score) obj);
        }
        return arrayList;
    }
}
