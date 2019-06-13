import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button answer1,answer2,answer3,answer4;
    TextView question,score;
    private Question  mQuestions = new Question();
    private int  mScore=0;
    private int mQuestionlength= mQuestions.mQuestions.length;
    private String mAnswer;
    Random r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r = new Random();
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);
        answer5 = (Button) findViewById(R.id.answer5);
        answer6 = (Button) findViewById(R.id.answer6);
        answer7 = (Button) findViewById(R.id.answer7);
        answer8 = (Button) findViewById(R.id.answer8);
        answer9 = (Button) findViewById(R.id.answer9);
        answer10 = (Button) findViewById(R.id.answer10);

        question = (TextView) findViewById(R.id.question);
        score = (TextView) findViewById(R.id.score);
        score.setText("Score : " + mScore);
        updateQuestion(r.nextInt(mQuestionlength));
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer1.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer2.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();


            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer3.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();
            }

        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();
            }
        });
        
        answer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();
            }
        });
        
        answer6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();
            }
        });
        
        answer7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();
            }
        });
        
        answer8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();
            }
        });
        
        answer9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();
            }
        });
        
        answer10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer4.getText()== mAnswer){
                    mScore++;
                    score.setText("Score : " + mScore);
                    updateQuestion(r.nextInt(mQuestionlength));
                }else
                    gameOver();
            }
        });
    }

        private void updateQuestion(int num){
            question.setText(mQuestions.getQuestion(num));
            answer1.setText(mQuestions.getChoice1(num));
            answer2.setText(mQuestions.getChoice2(num));
            answer3.setText(mQuestions.getChoice3(num));
            answer4.setText(mQuestions.getChoice4(num));
            answer5.setText(mQuestions.getChoice4(num));
            answer6.setText(mQuestions.getChoice4(num));
            answer7.setText(mQuestions.getChoice4(num));
            answer8.setText(mQuestions.getChoice4(num));
            answer9.setText(mQuestions.getChoice4(num));
            answer10.setText(mQuestions.getChoice4(num));
            mAnswer = mQuestions.getCorrectAnswer(num);
        }

        private void gameOver(){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder
                    .setMessage("Game Over ! Your score is "+ mScore +" points.")
                    .setCancelable(false)
                    .setPositiveButton("New Game",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                }
                            })
                    .setNegativeButton("Exit  ",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }


    }
