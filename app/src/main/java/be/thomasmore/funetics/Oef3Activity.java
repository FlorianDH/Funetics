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
import android.widget.TextView;

public class Oef3Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private MediaPlayer audioPlayer = null;
    private MediaPlayer contextPlayer = null;
    private MediaPlayer juistPlayer = null;
    private MediaPlayer foutPlayer = null;
    private int[] tracks = new int[7];
    private int currentTrack = 0;

    public int score = 0;
    public int aantalPogingen = 0;

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    private boolean isPlaying = false; //false by default
    private boolean isContextPlaying = false; //false by default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef3);

        Bundle bundle = getIntent().getExtras();
        Long doelwoordId = bundle.getLong("doelwoordId");
        Long kindId = bundle.getLong("kindId");

        db = new DatabaseHelper(this);

        huidigDoelwoord = db.getDoelwoordById(doelwoordId);
        huidigKind = db.getKindById(kindId);

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        setAudioPlayer();
        setContextPlayer();
        playAudioPlayer();

        FloatingActionButton soundFab = (FloatingActionButton) findViewById(R.id.soundFAB);
        soundFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButton_onClick(view);
            }
        });
    }

    public void foutButton_onClick(View view) {
        aantalPogingen++;

        if (isPlaying){
            audioPlayer.stop();
            isPlaying = false;
        }

        foutPlayer.start();
        while (foutPlayer.isPlaying()){}

        //Terug naar oefening activity
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("score", String.valueOf(score));
//        returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
//        setResult(Activity.RESULT_OK,returnIntent);
//        finish();
    }

    public void goedButton_onClick(View view) {
        aantalPogingen++;
        score++;

        if (isPlaying){
            audioPlayer.stop();
            isPlaying = false;
        }

        juistPlayer.start();
        while (juistPlayer.isPlaying()){}

        //Terug naar oefening activity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", String.valueOf(score));
        returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
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
        tracks[6] = R.raw.duikbril_context2;
    }

    public void setContextPlayer() {
        contextPlayer = MediaPlayer.create(getApplicationContext(), R.raw.duikbril_context2);
        juistPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef3_goede_zin);
        foutPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef3_oeps);
    }

    public void playAudioPlayer(){
        isPlaying = true;
        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);
        audioPlayer.start();
    }

    public void onCompletion(MediaPlayer audioPlayer2) {
        audioPlayer2.release();
        if (currentTrack < tracks.length-1) {
            isPlaying = true;
            currentTrack++;
            audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
            audioPlayer.setOnCompletionListener(this);
            audioPlayer.start();
        }else {
            isPlaying = false;
        }
    }

    public void playButton_onClick(View view) {
        if (isPlaying){
            audioPlayer.stop();
        }
        isPlaying = false;

        currentTrack = 0;

        contextPlayer.start();
    }


}
