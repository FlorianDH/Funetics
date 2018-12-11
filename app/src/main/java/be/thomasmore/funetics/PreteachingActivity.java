package be.thomasmore.funetics;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PreteachingActivity extends AppCompatActivity {

    private MediaPlayer sound1;
    private MediaPlayer sound2;
    private MediaPlayer sound3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preteaching);

        sound1 = MediaPlayer.create(this, R.raw.preteach1);
        sound2 = MediaPlayer.create(this, R.raw.preteach2);
        sound3 = MediaPlayer.create(this, R.raw.preteach3);

        final FloatingActionButton playSound = (FloatingActionButton) this.findViewById(R.id.replayButton);

        playSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound1.isPlaying() || sound2.isPlaying() || sound3.isPlaying()){
                    stopGeluid();
                }
                else {
                    startGeluid();
                }
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
