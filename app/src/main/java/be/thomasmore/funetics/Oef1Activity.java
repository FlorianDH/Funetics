package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class Oef1Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    private MediaPlayer audioPlayer = null;
    int[] tracks = new int[4];
    int currentTrack = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef1);

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
        setAudioPlayer();
        audioPlayer.start();
    }

    public void onCompletion(MediaPlayer audioPlayer2) {
        audioPlayer2.release();
        if (currentTrack < tracks.length-1) {
            currentTrack++;
            audioPlayer2 = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
            audioPlayer2.setOnCompletionListener(this);
            audioPlayer2.start();
        }
    }

    public void playButton_onClick(View view) {
        currentTrack = 0;
        playAudioPlayer();
    }

    public void volgendeButton_onClick(View view) {
//        if(audioPlayer.isPlaying())
//        {
//            try {
//                audioPlayer.pause();
//            } catch (Exception e) {}
//
//            try {
//                audioPlayer.reset();
//            } catch (Exception e) {}
//
//            try {
//                audioPlayer.stop();
//            } catch (Exception e) {}
//
//            try {
//                audioPlayer.release();
//            } catch (Exception e) {}

//            audioPlayer.reset();
//            audioPlayer.stop();
//        }
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        audioPlayer.release();
    }

}
