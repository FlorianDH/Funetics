package be.thomasmore.funetics;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Oef6_2Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    private MediaPlayer zoemPlayer = null;
    private MediaPlayer audioPlayer = null;
    private int[] tracks = new int[3];
    private int currentTrack = 0;
    private int zoemDuur;

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    private ObjectAnimator bijAnimation = new ObjectAnimator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef6_2);

        Bundle bundle = getIntent().getExtras();
        Long doelwoordId = bundle.getLong("doelwoordId");
        Long kindId = bundle.getLong("kindId");

        db = new DatabaseHelper(this);

        huidigDoelwoord = db.getDoelwoordById(doelwoordId);
        huidigKind = db.getKindById(kindId);

        setAudioPlayer();
        setZoemPlayer();
        zoemDuur = zoemPlayer.getDuration();
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
                bijAnimation.start();
                playButton_onClick(view);
            }
        });

        FloatingActionButton nextFab = (FloatingActionButton) findViewById(R.id.nextFAB);
        nextFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                //Terug naar oefening activity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("score", String.valueOf(1));
                returnIntent.putExtra("nummer", String.valueOf(2));
                returnIntent.putExtra("aantalPogingen", String.valueOf(1));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        final LinearLayout layout = (LinearLayout)findViewById(R.id.layoutOef6_2);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float afstand = (float) layout.getMeasuredWidth() - 60;

                ImageView afbeeldingBij = (ImageView) findViewById(R.id.imageViewBij);
                bijAnimation = ObjectAnimator.ofFloat(afbeeldingBij, "translationX", afstand);
                bijAnimation.setDuration(zoemDuur);
                bijAnimation.start();
            }
        });
    }

    public void setAudioPlayer() {
        tracks[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_2", "raw", getPackageName());

        tracks[1] = R.raw.oef6_2;
        tracks[2] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_2", "raw", getPackageName());

    }

    public void setZoemPlayer() {
        if(this.zoemPlayer != null){
            this.zoemPlayer.release();
        }
        zoemPlayer = MediaPlayer.create(this, getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_2", "raw", getPackageName()));
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

    public void playZoemPlayer(){
        setZoemPlayer();
        zoemPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                zoemPlayer.start();
            }
        });
    }

    public void onCompletion(MediaPlayer audioPlayer2) {
        if (currentTrack == 1){
            bijAnimation.start();
        }

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

        playZoemPlayer();
    }

    private void stopPlaying() {
        if (audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer.release();
            audioPlayer = null;
        }

        if (zoemPlayer != null) {
            zoemPlayer.stop();
            zoemPlayer.release();
            zoemPlayer = null;
        }
    }

}
