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
    private int[] tracks = new int[3];
    private int currentTrack = 0;

    private int oefening = 1;

    public int score = 0;
    public int aantalPogingen = 2;
    public int pogingen = 0;

    private TextView textViewContext;

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

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

        if (contextPlayer != null) {
            try{
                contextPlayer.reset();
                contextPlayer.prepare();
                contextPlayer.stop();
                contextPlayer.release();
                contextPlayer = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        if (context2Player != null) {
            try{
                context2Player.reset();
                context2Player.prepare();
                context2Player.stop();
                context2Player.release();
                context2Player = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        if (juistPlayer != null) {
            try{
                juistPlayer.reset();
                juistPlayer.prepare();
                juistPlayer.stop();
                juistPlayer.release();
                juistPlayer = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        if (juist2Player != null) {
            try{
                juist2Player.reset();
                juist2Player.prepare();
                juist2Player.stop();
                juist2Player.release();
                juist2Player = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        if (foutPlayer != null) {
            try{
                foutPlayer.reset();
                foutPlayer.prepare();
                foutPlayer.stop();
                foutPlayer.release();
                foutPlayer = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public void foutButton_onClick(View view) {
        String waarde = (String) view.getContentDescription();
        pogingen++;

        stopPlaying();

        setContextPlayer();

        if(waarde=="juist"){
            if(pogingen == 1){
                score++;
            }

            juist2Player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
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

                        context2Player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                context2Player.start();
                                while (context2Player.isPlaying()){}

                                oefening ++;
                                pogingen = 0;
                                setOefening();

                                contextPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mediaPlayer) {
                                        contextPlayer.start();
                                    }
                                });
                            }
                        });

                    }
                }
            });


        }else{
            foutPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    foutPlayer.start();
                    while (foutPlayer.isPlaying()){}
                }
            });
        }
    }

    public void goedButton_onClick(View view) {
        String waarde = (String) view.getContentDescription();
        pogingen++;

        stopPlaying();

        setContextPlayer();

        if(waarde=="juist"){
            if(pogingen == 1){
                score++;
            }

            juistPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
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

                        setContextPlayer();
                        context2Player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {

                                context2Player.start();
                                while (context2Player.isPlaying()){}

                                oefening ++;
                                pogingen = 0;
                                setOefening();

                                contextPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mediaPlayer) {
                                        contextPlayer.start();
                                    }
                                });
                            }
                        });

                    }
                }
            });
        }else{
            foutPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    foutPlayer.start();
                    while (foutPlayer.isPlaying()){}
                }
            });
        }

    }


    public void setAudioPlayer() {
        tracks[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_3_1", "raw", getPackageName());
        tracks[1] = R.raw.oef3_1;
        tracks[2] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_3_2", "raw", getPackageName());
    }

    public void setContextPlayer() {
        if(this.contextPlayer != null){
            this.contextPlayer.release();
        }
        if(this.context2Player != null){
            this.context2Player.release();
        }
        if(this.juistPlayer != null){
            this.juistPlayer.release();
        }
        if(this.juist2Player != null){
            this.juist2Player.release();
        }
        if(this.foutPlayer != null){
            this.foutPlayer.release();
        }
        contextPlayer = MediaPlayer.create(getApplicationContext(),getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_3_2", "raw", getPackageName()));
        context2Player = MediaPlayer.create(getApplicationContext(), R.raw.oef3_4);
        juistPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef3_2);
        juist2Player = MediaPlayer.create(getApplicationContext(), R.raw.oef3_5);
        foutPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef3_3);
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

        setOefening();

        contextPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                contextPlayer.start();
            }
        });
    }

    public void setOefening(){
        goedButton = (ImageButton) this.findViewById(R.id.buttonGoed);
        foutButton = (ImageButton) this.findViewById(R.id.buttonFout);

        switch (oefening){
            case 1:
                textViewContext = (TextView) this.findViewById(R.id.textViewContext);
                textViewContext.setText(huidigDoelwoord.getJuisteZin());
                if(this.contextPlayer != null){
                    this.contextPlayer.release();
                }
                contextPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_3_2", "raw", getPackageName()));
                goedButton.setContentDescription("juist");
                foutButton.setContentDescription("fout");
                break;
            case 2:
                textViewContext = (TextView) this.findViewById(R.id.textViewContext);
                textViewContext.setText(huidigDoelwoord.getFouteZin());
                if(this.contextPlayer != null){
                    this.contextPlayer.release();
                }
                contextPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_3_3", "raw", getPackageName()));
                goedButton.setContentDescription("fout");
                foutButton.setContentDescription("juist");
                break;
        }
    }
}
