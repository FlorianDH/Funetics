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

public class PreteachingActivity extends AppCompatActivity{

    private MediaPlayer audioPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preteaching);

        playAudioPlayer();

        FloatingActionButton soundFab = (FloatingActionButton) findViewById(R.id.soundFAB);
        soundFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButton_onClick(view);
            }
        });

        final ImageButton imageButton = (ImageButton) this.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopPlaying();

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });
    }

    public void playAudioPlayer(){
        if(this.audioPlayer != null){
            this.audioPlayer.release();
        }
        audioPlayer = MediaPlayer.create(getApplicationContext(), R.raw.preteach);
        audioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                audioPlayer.start();
            }
        });
    }

    private void stopPlaying() {
        if (audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer.release();
            audioPlayer = null;
        }
    }

    public void playButton_onClick(View view) {
        stopPlaying();
        playAudioPlayer();
    }

}
