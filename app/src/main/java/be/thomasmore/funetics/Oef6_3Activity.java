package be.thomasmore.funetics;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
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
    int lettergrepen = 0;

    private MediaPlayer audioPlayer = null;
    private MediaPlayer woordPlayer = null;

    private int[] tracks;
    private int[] woordTracks = new int[2];

    private int currentTrack = 0;

    public int score = 0;
    public int aantalPogingen = 0;

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    private boolean isPlaying = false; //false by default

    private ObjectAnimator konijnAnimation1 = new ObjectAnimator();
    private ObjectAnimator konijnAnimation2 = new ObjectAnimator();


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

        FloatingActionButton nextFab = (FloatingActionButton) findViewById(R.id.nextFAB);
        nextFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Terug naar oefening activity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("score", String.valueOf(1));
                returnIntent.putExtra("aantalPogingen", String.valueOf(1));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        ImageView afbeeldingKonijn = (ImageView) findViewById(R.id.imageKonijn);
        konijnAnimation1 = ObjectAnimator.ofFloat(afbeeldingKonijn, "translationY", -200f);
        konijnAnimation1.setDuration(500);
        konijnAnimation2 = ObjectAnimator.ofFloat(afbeeldingKonijn, "translationY", 200f);
        konijnAnimation2.setDuration(500);

        playAudioPlayer();
    }

    public void checkLetterGreep(){
        String deel1 = "";
        String deel2 = "";

        TextView textWoordDeel1 = (TextView) findViewById(R.id.textViewWoordDeel1);
        TextView textWoordStreep = (TextView) findViewById(R.id.textViewWoordStreep);
        TextView textWoordDeel2 = (TextView) findViewById(R.id.textViewWoordDeel2);

        textWoordDeel1.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textWoordDeel2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

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
            tracks = new int [5];
            textWoordStreep.setText(" - ");
            textWoordDeel2.setText(deel2);
            tracks[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_3_1", "raw", getPackageName());
            tracks[1] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_3_2", "raw", getPackageName());
            tracks[2] = R.raw.oef6_3;
            tracks[3] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_3_1", "raw", getPackageName());
            tracks[4] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_3_2", "raw", getPackageName());
        }else{
            tracks = new int [3];
            woordPlayer = MediaPlayer.create(this, getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase(), "raw", getPackageName()));
            tracks[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase(), "raw", getPackageName());
            tracks[1] = R.raw.oef6_3;
            tracks[2] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase(), "raw", getPackageName());
            textWoordStreep.setText("");
            textWoordDeel2.setText("");
        }
    }

    public void playAudioPlayer(){
        konijnAnimation1.start();
        konijnAnimation2.start();
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
            if(lettergrepen == 2){
                if (currentTrack == 1 || currentTrack == 4 ||currentTrack == 5 ){
                    konijnAnimation1.start();
                    konijnAnimation2.start();
                }
            }else{
                if (currentTrack == 3){
                    konijnAnimation1.start();
                    konijnAnimation2.start();
                }
            }

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
