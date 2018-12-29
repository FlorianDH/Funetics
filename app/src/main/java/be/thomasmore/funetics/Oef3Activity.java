package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Oef3Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private MediaPlayer audioPlayer = null;
    private MediaPlayer contextPlayer = null;
    private MediaPlayer context2Player = null;
    private MediaPlayer juistPlayer = null;
    private MediaPlayer juist2Player = null;
    private MediaPlayer foutPlayer = null;
    private int[] tracks = new int[7];
    private int currentTrack = 0;

    private int oefening = 1;

    public int score = 0;
    public int aantalPogingen = 0;

    private TextView textViewContext;

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    private boolean isPlaying = false; //false by default
    private boolean isContextPlaying = false; //false by default

    private ImageButton goedButton;
    private ImageButton foutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef3);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        Long doelwoordId = bundle.getLong("doelwoordId");
        Long kindId = bundle.getLong("kindId");

        huidigDoelwoord = db.getDoelwoordById(doelwoordId);
        huidigKind = db.getKindById(kindId);

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        setOefening();

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
        String waarde = (String) view.getContentDescription();
        aantalPogingen++;

        if (isPlaying){
            audioPlayer.stop();
            isPlaying = false;
        }

        if(waarde=="juist"){
            score++;
            juist2Player.start();
            while (juist2Player.isPlaying()){}

            if(oefening == 2){
                //Terug naar oefening activity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("score", String.valueOf(score));
                returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }else{
                context2Player.start();
                while (context2Player.isPlaying()){}
                oefening ++;
                setOefening();
                contextPlayer.start();
            }
        }else{
            foutPlayer.start();
            while (foutPlayer.isPlaying()){}
        }
    }

    public void goedButton_onClick(View view) {
        String waarde = (String) view.getContentDescription();
        aantalPogingen++;

        if (isPlaying){
            audioPlayer.stop();
            isPlaying = false;
        }

        if(waarde=="juist"){
            score++;
            juistPlayer.start();
            while (juistPlayer.isPlaying()){}

            if(oefening == 2){
                //Terug naar oefening activity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("score", String.valueOf(score));
                returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }else{
                context2Player.start();
                while (context2Player.isPlaying()){}
                oefening ++;
                setOefening();
                contextPlayer.start();
            }
        }else{
            foutPlayer.start();
            while (foutPlayer.isPlaying()){}
        }

    }


    public void setAudioPlayer() {
        tracks[0] = R.raw.duikbril_3_1;
        tracks[1] = R.raw.oef3_help_je_mij;
        tracks[2] = R.raw.oef3_ik_zeg_een_zinnetje;
        tracks[3] = R.raw.oef3_zin_juist;
        tracks[4] = R.raw.oef3_zin_fout;
        tracks[5] = R.raw.oef3_hier_gaan_we;
        tracks[6] = R.raw.duikbril_3_2;
    }

    public void setContextPlayer() {
        contextPlayer = MediaPlayer.create(getApplicationContext(), R.raw.duikbril_3_2);
        context2Player = MediaPlayer.create(getApplicationContext(), R.raw.oef3_4);
        juistPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef3_2);
        juist2Player = MediaPlayer.create(getApplicationContext(), R.raw.oef3_5);
        foutPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef3_3);
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

    public void setOefening(){
        goedButton = (ImageButton) this.findViewById(R.id.buttonGoed);
        foutButton = (ImageButton) this.findViewById(R.id.buttonFout);

        switch (oefening){
            case 1:
                textViewContext = (TextView) this.findViewById(R.id.textViewContext);
                textViewContext.setText(huidigDoelwoord.getJuisteZin());
                contextPlayer = MediaPlayer.create(getApplicationContext(), R.raw.duikbril_3_2);
                goedButton.setContentDescription("juist");
                foutButton.setContentDescription("fout");
                break;
            case 2:
                textViewContext = (TextView) this.findViewById(R.id.textViewContext);
                textViewContext.setText(huidigDoelwoord.getFouteZin());
                contextPlayer = MediaPlayer.create(getApplicationContext(), R.raw.duikbril_3_3);
                goedButton.setContentDescription("fout");
                foutButton.setContentDescription("juist");
                break;
        }
    }
}
