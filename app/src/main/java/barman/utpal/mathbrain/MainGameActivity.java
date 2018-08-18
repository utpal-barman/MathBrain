package barman.utpal.mathbrain;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainGameActivity extends AppCompatActivity {
    TextView tv_num1,tv_num2, point, comments, tv_timer;
    int main_result, user_result, count = 0, points = 0;
    Button btn_ans1, btn_ans2, btn_ans3;
    ImageView image;
    RelativeLayout relayout;
    MediaPlayer mp1, mp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Developed by Utpal", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tv_timer = (TextView) findViewById(R.id.textView_timer);

        new CountDownTimer(99999, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("" + millisUntilFinished / 1000);
                if ((Integer.parseInt(tv_timer.getText().toString())) < 10) {
                    tv_timer.setTextColor(Color.RED);
                }
            }
            public void onFinish() {
                count = 2;
            }
        }.start();



        mp1 = MediaPlayer.create(this, R.raw.back);
        mp1.setLooping(true);
        mp1.start();

        startNewGame();
    }

    private void startNewGame() {
        if(count==2){
            mp1.stop();
            Intent intent = new Intent(MainGameActivity.this, GameOverActivity.class);
            String string_points = Integer.toString(points);
            intent.putExtra("gamePoints", string_points);
            startActivity(intent);
            finish();
        }


        tv_num1 = (TextView) findViewById(R.id.text_number1);
        tv_num1.setText("" + (int) (10 + Math.random() * 10));
        tv_num1.startAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_in));

        tv_num2 = (TextView) findViewById(R.id.text_number2);
        tv_num2.setText("" + (int) (10 + Math.random() * 10));
        tv_num2.startAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_in));

        point = (TextView) findViewById(R.id.textView_points);

        main_result = Integer.parseInt(tv_num1.getText().toString()) + Integer.parseInt(tv_num2.getText().toString());

        btn_ans1 = (Button) findViewById(R.id.button1);
        btn_ans2 = (Button) findViewById(R.id.button2);
        btn_ans3 = (Button) findViewById(R.id.button3);

        int random = (int) (1 + (Math.random() * 150) % 3);
        if (random == 1) {
            btn_ans1.setText("" + main_result);
            btn_ans2.setText("" + (main_result - 2));
            btn_ans3.setText("" + (main_result + 16));
        } else if (random == 2) {
            btn_ans1.setText("" + (main_result + 20));
            btn_ans2.setText("" + (main_result));
            btn_ans3.setText("" + (main_result + 28));
        } else if (random == 3) {
            btn_ans1.setText("" + (main_result + 40));
            btn_ans2.setText("" + (main_result + 16));
            btn_ans3.setText("" + main_result);
        } else
            Toast.makeText(MainGameActivity.this, "Random number (0,1 or 2) not generated!", Toast.LENGTH_SHORT).show();

        image = (ImageView) findViewById(R.id.imageView_emo);
        comments = (TextView) findViewById(R.id.textView_comments);
        relayout = (RelativeLayout) findViewById(R.id.layout);

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Go to Main Menu?");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Main Menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(MainGameActivity.this, StartMenuActivity.class);
                finish();
                startActivity(intent);

            }
        });

        builder.setNegativeButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(MainGameActivity.this, MainGameActivity.class);
                finish();
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp1.stop();
    }

    public void button1Clicked(View view) {
        try{
            user_result = Integer.parseInt(btn_ans1.getText().toString());
            if(user_result == main_result) {
                comments.setText("Correct");
                comments.setTextColor(Color.parseColor("#006600"));
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_in));
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_out));


                image.setImageResource(R.drawable.yes);
                points = points + 3;
                point.setText("" + points);
                relayout.setBackgroundColor(Color.parseColor("#ccffcc"));

                mp2 = MediaPlayer.create(this, R.raw.correct);
                mp2.start();

            }
            else{
                comments.setText("Wrong");
                comments.setTextColor(Color.RED);
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_in));
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_out));

                image.setImageResource(R.drawable.no);
                points = points - 5;
                point.setText("" + points);
                relayout.setBackgroundColor(Color.parseColor("#ffcccc"));

                mp2 = MediaPlayer.create(this, R.raw.wrong);
                mp2.start();

                ++count;

            }


            startNewGame();
        }catch(Exception ex){

            Toast.makeText(MainGameActivity.this, "ERROR OCCURRED! ERROR-U02", Toast.LENGTH_SHORT).show();
        }
    }

    public void button2Clicked(View view) {
        try{
            user_result = Integer.parseInt(btn_ans2.getText().toString());
            if(user_result == main_result) {
                comments.setText("Correct");
                comments.setTextColor(Color.parseColor("#006600"));
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_in));
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_out));

                image.setImageResource(R.drawable.yes);
                points = points + 3;
                point.setText("" + points);
                relayout.setBackgroundColor(Color.parseColor("#ccffcc"));

                mp2 = MediaPlayer.create(this, R.raw.correct);
                mp2.start();
            }
            else{
                comments.setText("Wrong");
                comments.setTextColor(Color.RED);
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_in));
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_out));

                image.setImageResource(R.drawable.no);
                points = points - 5;
                point.setText("" + points);
                relayout.setBackgroundColor(Color.parseColor("#ffcccc"));

                mp2 = MediaPlayer.create(this, R.raw.wrong);
                mp2.start();

                ++count;
            }



            startNewGame();
        }catch(Exception ex){

            Toast.makeText(MainGameActivity.this, "ERROR OCCURRED! ERROR-U02", Toast.LENGTH_SHORT).show();
        }

    }

    public void button3Clicked(View view) {
        try{
            user_result = Integer.parseInt(btn_ans3.getText().toString());
            if(user_result == main_result) {
                comments.setText("Correct");
                comments.setTextColor(Color.parseColor("#006600"));
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_in));
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_out));

                image.setImageResource(R.drawable.yes);
                points = points + 3;
                point.setText("" + points);
                relayout.setBackgroundColor(Color.parseColor("#ccffcc"));

                mp2 = MediaPlayer.create(this, R.raw.correct);
                mp2.start();
            }
            else{
                comments.setText("Wrong");
                comments.setTextColor(Color.RED);
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_in));
                comments.setAnimation(AnimationUtils.loadAnimation(MainGameActivity.this, android.R.anim.fade_out));

                image.setImageResource(R.drawable.no);
                points = points - 5;
                point.setText("" + points);
                relayout.setBackgroundColor(Color.parseColor("#ffcccc"));

                mp2 = MediaPlayer.create(this, R.raw.wrong);
                mp2.start();

                ++count;
            }


            startNewGame();
        }catch(Exception ex){

            Toast.makeText(MainGameActivity.this, "ERROR OCCURRED! ERROR-U02", Toast.LENGTH_SHORT).show();
        }

    }
}
