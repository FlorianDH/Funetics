package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Oef2Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    private MediaPlayer audioPlayer = null;
    int[] tracks = new int[3];
    int currentTrack = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef2);

        currentTrack = 0;
        playAudioPlayer();
    }

    public void setAudioPlayer() {
        tracks[0] = R.raw.duikbril_herhaal;
        tracks[1] = R.raw.duikbril;                     // Oefenwoord
        tracks[2] = R.raw.oef2_doe_maar;
        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);
    }

    public void playAudioPlayer(){
        setAudioPlayer();
        audioPlayer.start();
    }

    public void onCompletion(MediaPlayer audioPlayer) {
        audioPlayer.release();
        if (currentTrack < tracks.length-1) {
            currentTrack++;
            audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
            audioPlayer.setOnCompletionListener(this);
            audioPlayer.start();
        }
    }

    public void playButton_onClick(View view) {
        currentTrack = 0;
        playAudioPlayer();
    }

    public void volgendeButton_onClick(View view) {
        if(audioPlayer.isPlaying())
        {
            audioPlayer.pause();
            audioPlayer.release();
        }
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
