package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PreteachingActivity extends AppCompatActivity {

    private MediaPlayer sound1;
    private MediaPlayer sound2;
    private MediaPlayer sound3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preteaching);


//        final MediaPlayer sound1 = MediaPlayer.create(this, R.raw.preteach1);
//        final MediaPlayer sound2 = MediaPlayer.create(this, R.raw.preteach2);
//        final MediaPlayer sound3 = MediaPlayer.create(this, R.raw.preteach3);

        sound1 = MediaPlayer.create(this, R.raw.preteach1);
        sound2 = MediaPlayer.create(this, R.raw.preteach2);
        sound3 = MediaPlayer.create(this, R.raw.preteach3);


        final FloatingActionButton playSound = (FloatingActionButton) this.findViewById(R.id.replayButton);
        final ImageButton imageButton = (ImageButton) this.findViewById(R.id.imageButton);

        playSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sound1.start();
                sound2.start();
                sound3.start();

                if (sound1.isPlaying() || sound2.isPlaying() || sound3.isPlaying()){
                    stopGeluid();
                }
                else {
                    startGeluid();
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public void startGeluid(){
        sound1.start();
        while (sound1.isPlaying()){
        }
        sound2.start();
        while (sound2.isPlaying()){
        }
        sound3.start();
        while (sound3.isPlaying()){
        }
    }

    public void stopGeluid(){
        sound1.stop();
        sound2.stop();
        sound3.stop();
    }

}
