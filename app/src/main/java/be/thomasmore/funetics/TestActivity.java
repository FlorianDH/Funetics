package be.thomasmore.funetics;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final MediaPlayer testMediaPlayer = MediaPlayer.create(this, R.raw.Duikbril);

        final Button playTest = (Button) this.findViewById(R.id.replayButton);

        playTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMediaPlayer.start();
            }
        });
    }

}
