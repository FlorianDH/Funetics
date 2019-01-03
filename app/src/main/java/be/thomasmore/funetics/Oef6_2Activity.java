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
    private int[] tracks2 = new int[1];
    private int currentTrack = 0;
    private int zoemDuur;

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    private boolean isPlaying = false; //false by default
    private boolean isPlaying2 = false; //false by default

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
                //Terug naar oefening activity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("score", String.valueOf(1));
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
        //tracks[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_2", "raw", getPackageName()); //oefenwoord_6_2
        tracks[1] = R.raw.oef6_2;
        tracks[2] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_2", "raw", getPackageName());
        //tracks[2] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_2", "raw", getPackageName()); //oefenwoord_6_2
    }

    public void setZoemPlayer() {
        zoemPlayer = MediaPlayer.create(this, getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_2", "raw", getPackageName()));
        //tracks2[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase(), "raw", getPackageName());
        //tracks[0] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_6_2", "raw", getPackageName()); //oefenwoord_6_2
    }

    public void playAudioPlayer(){
        isPlaying = true;
        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);
        audioPlayer.start();
    }

    public void playZoemPlayer(){
        isPlaying2 = true;
        zoemPlayer.start();
    }

    public void onCompletion(MediaPlayer audioPlayer2) {
        audioPlayer2.release();
        if (currentTrack == 1){
            bijAnimation.start();
        }

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

        isPlaying2 = true;
        playZoemPlayer();
    }

}
