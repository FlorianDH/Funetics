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

public class Oef6_1Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

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
        setContentView(R.layout.activity_oef6_1);

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

    public void droevigButton_onClick(View view) {
        aantalPogingen++;

        stopPlaying();

        //Terug naar oefening activity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", String.valueOf(score));
        returnIntent.putExtra("nummer", String.valueOf(1));
        returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void blijButton_onClick(View view) {
        aantalPogingen++;
        score++;

        stopPlaying();

        //Terug naar oefening activity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", String.valueOf(score));
        returnIntent.putExtra("nummer", String.valueOf(1));
        returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void setAudioPlayer() {
        tracks[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_1", "raw", getPackageName()); //oefenwoord_herhaal
        tracks[1] = R.raw.oef6_1_1;
        tracks[2] = R.raw.oef6_1_2;
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
                mediaPlayer.start();
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
                    mediaPlayer.start();
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

    private void stopPlaying() {
        if (audioPlayer != null) {
            try{
                audioPlayer.reset();
                audioPlayer.prepare();
                audioPlayer.stop();
                audioPlayer.release();
                audioPlayer = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
