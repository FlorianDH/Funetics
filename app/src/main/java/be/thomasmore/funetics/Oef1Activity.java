package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Oef1Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    private MediaPlayer audioPlayer = null;
    int[] tracks = new int[4];
    int currentTrack = 0;

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef1);

        Bundle bundle = getIntent().getExtras();
        Long doelwoordId = bundle.getLong("doelwoordId");
        Long kindId = bundle.getLong("kindId");

        db = new DatabaseHelper(this);

        huidigDoelwoord = db.getDoelwoordById(doelwoordId);
        huidigKind = db.getKindById(kindId);

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        TextView textWoord = (TextView) findViewById(R.id.textViewWoord);
        textWoord.setText(huidigDoelwoord.getNaam());

        TextView textDefenitie = (TextView) findViewById(R.id.textViewDefenitie);
        textDefenitie.setText(huidigDoelwoord.getDefenitie());

        setAudioPlayer();
        playAudioPlayer();

        FloatingActionButton soundFab = (FloatingActionButton) findViewById(R.id.soundFAB);
        soundFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButton_onClick(view);
            }
        });

        FloatingActionButton nextFab = (FloatingActionButton) findViewById(R.id.nextFAB);
        nextFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volgendeButton_onClick(view);
            }
        });
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
        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);

        audioPlayer.start();
    }

    public void onCompletion(MediaPlayer audioPlayer2) {
        audioPlayer2.release();
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
