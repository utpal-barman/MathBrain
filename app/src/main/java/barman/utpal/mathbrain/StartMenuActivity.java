package barman.utpal.mathbrain;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StartMenuActivity extends AppCompatActivity {

    private MediaPlayer mp;
    TextView tv_high;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mp = MediaPlayer.create(this, R.raw.welcome_music);
        mp.setLooping(true);
        setContentView(R.layout.activity_start_menu);
        mp.start();

        tv_high = (TextView) findViewById(R.id.textView_highScore);
/*
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("MathBrainData", Context.MODE_PRIVATE);
            tv_high.setText("" + sharedPreferences.getInt("highScore", 0));
        }catch(Exception e){
            Toast.makeText(StartMenuActivity.this, "Problem in reading high score: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
*/
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //tv_high.setText("" + sharedPreferences.getString("highScores", "0"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit?");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                else {
                    ActivityCompat.finishAffinity(StartMenuActivity.this);
                }

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void click_playNow(View view) {
        startActivity(new Intent(this, MainGameActivity.class));
        finish();
    }

    public void click_exit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit?");
        builder.setMessage("Are You Sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                else {
                    ActivityCompat.finishAffinity(StartMenuActivity.this);
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void click_about(View view) {
        startActivity(new Intent(this, AboutActivity.class));

    }
}
