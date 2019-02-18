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

public class Oef1Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    private MediaPlayer audioPlayer = null;
    private int[] tracks = new int[4];
    private int currentTrack = 0;

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

        setAudioPlayer();
        playAudioPlayer();

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        TextView textWoord = (TextView) findViewById(R.id.textViewWoord);
        textWoord.setText(huidigDoelwoord.getNaam());

        TextView textDefenitie = (TextView) findViewById(R.id.textViewDefenitie);
        textDefenitie.setText(huidigDoelwoord.getDefenitie());

        ImageView afbeelding = (ImageView) findViewById(R.id.imageViewAfbeelding);
        afbeelding.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase(), "drawable", getPackageName()));

        FloatingActionButton soundFab = (FloatingActionButton) findViewById(R.id.soundFAB);
        soundFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButton_onClick();
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
        tracks[0] = R.raw.oef1_1; //Wat zie je hier?
        tracks[1] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase(), "raw", getPackageName());  //oefenwoord
        tracks[2] = R.raw.oef1_2; //Weet je wat dat is
        tracks[3] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_1", "raw", getPackageName());  //oefenwoord_def
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

    public void playButton_onClick() {
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

    public void volgendeButton_onClick(View view) {

        stopPlaying();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", "1");
        returnIntent.putExtra("aantalPogingen", "1");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
