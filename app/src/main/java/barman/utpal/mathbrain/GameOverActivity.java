package barman.utpal.mathbrain;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static android.graphics.Color.parseColor;

public class GameOverActivity extends AppCompatActivity {
    MediaPlayer mp;
    TextView gamePoints, highScore;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        s = getIntent().getExtras().getString("gamePoints");
        gamePoints = (TextView) findViewById(R.id.textView_gamePoints);
        gamePoints.setText(s+" Points");

        saveScore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mp.stop();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Go to Main Menu?");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Main Menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(GameOverActivity.this, StartMenuActivity.class);
                finish();
                startActivity(intent);

            }
        });

        builder.setNegativeButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(GameOverActivity.this, MainGameActivity.class);
                finish();
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    //Saving new highest score
    public void saveScore(){
        highScore = (TextView) findViewById(R.id.textView_highScore);
        /*SharedPreferences sharedPreferences = getSharedPreferences("MathBrainData", Context.MODE_PRIVATE);*/
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int newScore =  Integer.parseInt(s);
        int oldScore = Integer.parseInt(sharedPreferences.getString("highScores","0"));

        if(newScore > oldScore ){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("highScores", s);
            editor.commit();

            mp = MediaPlayer.create(GameOverActivity.this, R.raw.clap);
            mp.start();

            highScore.setTextSize(30);
            highScore.setTextColor(parseColor("#FFFFFF"));
        }
        else{
            mp = MediaPlayer.create(GameOverActivity.this, R.raw.gameover);
            mp.start();


        }
        highScore.setText("High Score: "+ sharedPreferences.getString("highScores", "0"));
    }



    public void click_playAgain(View view) {
        startActivity(new Intent(this, MainGameActivity.class));
        finish();
    }

    public void click_home(View view) {
        startActivity(new Intent(this, StartMenuActivity.class));
        finish();
    }
}
