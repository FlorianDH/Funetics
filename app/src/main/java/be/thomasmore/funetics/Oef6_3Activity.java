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
import android.widget.ImageView;
import android.widget.TextView;

public class Oef6_3Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private MediaPlayer audioPlayer = null;
    private int[] tracks = new int[3];
    private int currentTrack = 0;

    public int score = 0;
    public int aantalPogingen = 0;

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    private boolean isPlaying = false; //false by default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef6_3);

        Bundle bundle = getIntent().getExtras();
        Long doelwoordId = bundle.getLong("doelwoordId");
        Long kindId = bundle.getLong("kindId");

        db = new DatabaseHelper(this);

        huidigDoelwoord = db.getDoelwoordById(doelwoordId);
        huidigKind = db.getKindById(kindId);

        checkLetterGreep();

        setAudioPlayer();
        playAudioPlayer();

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

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

    public void checkLetterGreep(){
        int lettergrepen = 0;
        String deel1 = "";
        String deel2 = "";

        TextView textWoordDeel1 = (TextView) findViewById(R.id.textViewWoordDeel1);
        TextView textWoordDeel2 = (TextView) findViewById(R.id.textViewWoordDeel2);
        View lijn1 = (View) findViewById(R.id.lijn1);
        View lijn2 = (View) findViewById(R.id.lijn2);

        switch (huidigDoelwoord.getNaam().toLowerCase()){
            case "duikbril":
                lettergrepen = 2;
                deel1 = "Duik";
                deel2 = "bril";
                break;
            case "klimtouw":
                lettergrepen = 2;
                deel1 = "Klim";
                deel2 = "touw";
                break;
            case "kroos":
                lettergrepen = 1;
                deel1 = "Kroos";
                deel2 = "";
                break;
            case "riet":
                lettergrepen = 1;
                deel1 = "Riet";
                deel2 = "";
                break;
            case "kompas":
                lettergrepen = 2;
                deel1 = "Kom";
                deel2 = "pas";
                break;
            case "steil":
                lettergrepen = 1;
                deel1 = "Steil";
                deel2 = "";
                break;
            case "zwaan":
                lettergrepen = 1;
                deel1 = "Zwaan";
                deel2 = "";
                break;
            case "kamp":
                lettergrepen = 1;
                deel1 = "Kamp";
                deel2 = "";
                break;
            case "zaklamp":
                lettergrepen = 2;
                deel1 = "Zak";
                deel2 = "lamp";
                break;
        }

        textWoordDeel1.setText(deel1);

        if(lettergrepen == 2){
            textWoordDeel2.setText(" - " + deel2);
        }else{
            textWoordDeel2.setText("");
            lijn2.setAlpha(0.0f);
        }

    }

    public void foutButton_onClick(View view) {
        aantalPogingen++;

        if (isPlaying){
            audioPlayer.stop();
        }

        //Terug naar oefening activity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", String.valueOf(score));
        returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void goedButton_onClick(View view) {
        aantalPogingen++;
        score++;

        if (isPlaying){
            audioPlayer.stop();
        }

        //Terug naar oefening activity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", String.valueOf(score));
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
        isPlaying = false;
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

        isPlaying = true;
        currentTrack = 0;
        playAudioPlayer();
    }
}
