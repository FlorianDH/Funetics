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

public class Oef3Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private MediaPlayer audioPlayer = null;
    private MediaPlayer contextPlayer = null;
    int[] tracks = new int[6];
    int currentTrack = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef3);

        setAudioPlayer();
        setContextPlayer();
        playAudioPlayer();

    }

    public void volgendeButton_onClick(View view) {
//        if(audioPlayer.isPlaying())
//        {
//            audioPlayer.pause();
//            audioPlayer.release();
//        }
//
//        if( contextPlayer.isPlaying())
//        {
//            contextPlayer.pause();
//            contextPlayer.release();
//        }
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void setAudioPlayer() {
        tracks[0] = R.raw.duikbril_context1;
        tracks[1] = R.raw.oef3_help_je_mij;
        tracks[2] = R.raw.oef3_ik_zeg_een_zinnetje;
        tracks[3] = R.raw.oef3_zin_juist;
        tracks[4] = R.raw.oef3_zin_fout;
        tracks[5] = R.raw.oef3_hier_gaan_we;
        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);
    }

    public void setContextPlayer() {
        contextPlayer = MediaPlayer.create(getApplicationContext(), R.raw.duikbril_context2);
    }

    public void playAudioPlayer(){
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
        contextPlayer.start();
    }


}
