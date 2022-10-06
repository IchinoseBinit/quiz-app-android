package com.example.myquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    String difficulty;

    TextView totalQuestionTextView;
    TextView questionTextView;
    TextView scoreTextView;
    LinearLayout layout;

    Button ansOne, ansTwo, ansThree, ansFour;
    Button submitBtn;

    int score = 0;
    List<QuestionAnswer> questionAnswers = new ArrayList<>();
    int totalQuestion = 0;
    int currentIndex = 0;
    String selectedAnswer = "";
    ProgressBar progressBar;

    public void callApi() {
        layout = findViewById(R.id.layout);
        progressBar = findViewById(R.id.progress_bar);
        layout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://opentdb.com/api.php?amount=10&difficulty="+difficulty+"&type=multiple&encode=url3986";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    questionAnswers = parseJson(response);

                    showQuestions();
                } catch (Exception e) {
                    try {
                        InputStream stream = getAssets().open(difficulty+".json");
                        int size = stream.available();
                        byte[] buffer = new byte[size];
                        stream.read(buffer);
                        stream.close();
                        String data = new String(buffer, "UTF-8");
                        JSONObject jsonObject = new JSONObject(data);

                        questionAnswers = parseJson(jsonObject);
                        showQuestions();



                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            // this is the error listener method which
            // we will call if we get any error from API.
            @Override
            public void onErrorResponse(VolleyError error) {
                // below line is use to display a toast message along with our error.
                try {
                    InputStream stream = getAssets().open(difficulty+".json");

                    int size = stream.available();
                    byte[] buffer = new byte[size];
                    stream.read(buffer);
                    stream.close();
                    String data = new String(buffer, "UTF-8");
                    JSONObject jsonObject = new JSONObject(data);

                    questionAnswers = parseJson(jsonObject);
                    showQuestions();



                } catch (IOException ex) {
                    System.out.println("ma tyayaha xu");

                    ex.printStackTrace();
                } catch (JSONException ex)
                {
                    System.out.println("ma yaha xu");
                    ex.printStackTrace();
                }
            }
        });
        requestQueue.add(jsonObjectRequest);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        difficulty = b.getString("difficulty");
        callApi();


//        layout.setVisibility(View.GONE);


        totalQuestionTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question_description);
        scoreTextView = findViewById(R.id.score);
        ansOne = findViewById(R.id.ans_1);
        ansTwo = findViewById(R.id.ans_2);
        ansThree = findViewById(R.id.ans_3);
        ansFour = findViewById(R.id.ans_4);

        submitBtn = findViewById(R.id.submit);

//
//

        ansOne.setOnClickListener(this);
        ansTwo.setOnClickListener(this);
        ansThree.setOnClickListener(this);
        ansFour.setOnClickListener(this);
        submitBtn.setOnClickListener(this);


    }

    public void loadNewQuestion() {

        if (currentIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        questionTextView.setText(questionAnswers.get(currentIndex).question);
        ansOne.setText(questionAnswers.get(currentIndex).options.get(0));
        ansTwo.setText(questionAnswers.get(currentIndex).options.get(1));
        ansThree.setText(questionAnswers.get(currentIndex).options.get(2));
        ansFour.setText(questionAnswers.get(currentIndex).options.get(3));
    }

    public void finishQuiz() {
        String passStatus = "";
        if (score > totalQuestion * 0.6) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }


    }

    public void saveScore() {
        Intent activityChangeIntent = new Intent( MainActivity.this, SaveActivity.class);
        activityChangeIntent.putExtra("score", score);
        startActivity(activityChangeIntent);
    }

    @Override
    public void onClick(View view) {

        ansOne.setBackgroundColor(Color.WHITE);
        ansTwo.setBackgroundColor(Color.WHITE);
        ansThree.setBackgroundColor(Color.WHITE);
        ansFour.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit) {
            if (selectedAnswer.equals(questionAnswers.get(currentIndex).correrctAnswer)) {
                score++;
                currentIndex++;
                loadNewQuestion();
                updateScore();
            }
            else {
                String answer = questionAnswers.get(currentIndex).correrctAnswer;
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("The answer is "+answer+" and your Score is " + score )
                        .setPositiveButton("Ok", (dialogInterface, i) -> saveScore())
                        .setCancelable(false)
                        .show();
            }



        } else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
        }

    }

    private static String decoder(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return  "";
        }
    }

    private void updateScore() {
        scoreTextView.setText("Your score: "+score);
    }

    private ArrayList<QuestionAnswer> parseJson(JSONObject response) {
        ArrayList list = new ArrayList<QuestionAnswer>();
        try {
            JSONArray objects = (JSONArray) response.get("results");

            for (int i = 0; i < objects.length(); i++) {
                String question = decoder(objects.getJSONObject(i).getString("question"));
                String correctAnswer = decoder(objects.getJSONObject(i).getString("correct_answer"));
                JSONArray incorrectAnswers = (JSONArray) objects.getJSONObject(i).get("incorrect_answers");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < incorrectAnswers.length(); j++) {
                    options.add(decoder(incorrectAnswers.getString(j)));

                }
                options.add(correctAnswer);

                list.add(new QuestionAnswer(question, correctAnswer, options));
//                        System.out.println(objects.getJSONObject(i).getString("question"));

            }
        } catch ( Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private void showQuestions() {
        System.out.println(questionAnswers);
        progressBar.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        totalQuestion = questionAnswers.size();
        totalQuestionTextView.setText("Total Questions: " + totalQuestion);

        loadNewQuestion();
    }
}