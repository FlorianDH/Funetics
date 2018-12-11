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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preteaching);

        final MediaPlayer Sound1 = MediaPlayer.create(this, R.raw.preteach1);
        final MediaPlayer Sound2 = MediaPlayer.create(this, R.raw.preteach2);
        final MediaPlayer Sound3 = MediaPlayer.create(this, R.raw.preteach3);

        final Button playSound = (Button) this.findViewById(R.id.replayButton);

        playSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sound1.start();
                Sound2.start();
                Sound3.start();
            }
        });
    }

}
