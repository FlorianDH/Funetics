package be.thomasmore.funetics;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Oef1Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    private MediaPlayer audioPlayer = null;
    int[] tracks = new int[3];
    int currentTrack = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef1);

        setAudioPlayer();
        playAudioPlayer();
    }

    public void setAudioPlayer() {
        tracks[0] = R.raw.oef1_wat_zie_je_hier;
        tracks[1] = R.raw.duikbril;                     // Oefenwoord
        tracks[2] = R.raw.oef1_weet_je_wat_dat_is;
        tracks[3] = R.raw.duikbril_def;                 // Uitleg
        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);
    }

    public void playAudioPlayer(){
        audioPlayer.start();
    }

    public void onCompletion(MediaPlayer arg0) {
        arg0.release();
        if (currentTrack < tracks.length) {
            currentTrack++;
            arg0 = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
            arg0.setOnCompletionListener(this);
            arg0.start();
        }
    }

}
