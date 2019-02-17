package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Oef2Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    private MediaPlayer audioPlayer = null;
    private int[] tracks = new int[3];
    private int currentTrack = 0;

    public int score = 0;
    public int aantalPogingen = 0;

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef2);

        Bundle bundle = getIntent().getExtras();
        Long doelwoordId = bundle.getLong("doelwoordId");
        Long kindId = bundle.getLong("kindId");

        db = new DatabaseHelper(this);

        huidigDoelwoord = db.getDoelwoordById(doelwoordId);
        huidigKind = db.getKindById(kindId);

        setAudioPlayer();
        playAudioPlayer();

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        TextView textWoord = (TextView) findViewById(R.id.textViewWoord);
        textWoord.setText(huidigDoelwoord.getNaam());

        ImageView afbeelding = (ImageView) findViewById(R.id.imageViewAfbeelding);
        afbeelding.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase(), "drawable", getPackageName()));

        FloatingActionButton soundFab = (FloatingActionButton) findViewById(R.id.soundFAB);
        soundFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButton_onClick(view);
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

    public void foutButton_onClick(View view) {

        stopPlaying();

        aantalPogingen++;

        //Terug naar oefening activity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", String.valueOf(score));
        returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void goedButton_onClick(View view) {

        stopPlaying();

        aantalPogingen++;
        score++;

        //Terug naar oefening activity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", String.valueOf(score));
        returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void setAudioPlayer() {
        tracks[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_2", "raw", getPackageName()); //oefenwoord_herhaal
        tracks[1] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase(), "raw", getPackageName());  // oefenwoord
        tracks[2] = R.raw.oef2; //Doe maar
    }

    public void playAudioPlayer(){
        if(this.audioPlayer != null){
            this.audioPlayer.release();
        }
        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);
        audioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                audioPlayer.start();
            }
        });
    }

    public void onCompletion(MediaPlayer audioPlayer2) {
        if (currentTrack < tracks.length-1) {
            currentTrack++;
            audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
            audioPlayer.setOnCompletionListener(this);
            audioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    audioPlayer.start();
                }
            });
        }else {
            stopPlaying();
        }
    }

    public void playButton_onClick(View view) {
        stopPlaying();

        currentTrack = 0;
        playAudioPlayer();
    }
}
