package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Oef2Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    private MediaPlayer audioPlayer = null;
    int[] tracks = new int[3];
    int currentTrack = 0;

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

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        TextView textWoord = (TextView) findViewById(R.id.textViewWoord);
        textWoord.setText(huidigDoelwoord.getNaam());

        TextView textUitleg = (TextView) findViewById(R.id.textViewUitleg);
        String uitleg = "Wat is " + huidigDoelwoord.getNaam().toLowerCase() + " een leuk woord. Kan jij het ook zeggen, " + huidigDoelwoord.getNaam().toLowerCase() + "? Doe maar!";
        textUitleg.setText(uitleg);

        setAudioPlayer();
        playAudioPlayer();
    }

    public void foutButton_onClick(View view) {
        aantalPogingen++;

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

        //Terug naar oefening activity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", String.valueOf(score));
        returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void setAudioPlayer() {
        tracks[0] = R.raw.duikbril_herhaal;
        tracks[1] = R.raw.duikbril;                     // Oefenwoord
        tracks[2] = R.raw.oef2_doe_maar;
        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);
    }

    public void playAudioPlayer(){

        audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
        audioPlayer.setOnCompletionListener(this);

        audioPlayer.start();
    }

    public void onCompletion(MediaPlayer audioPlayer) {
        audioPlayer.release();
        if (currentTrack < tracks.length-1) {
            currentTrack++;
            audioPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
            audioPlayer.setOnCompletionListener(this);
            audioPlayer.start();
        }
    }

    public void playButton_onClick(View view) {
        currentTrack = 0;
        playAudioPlayer();
    }

    public void volgendeButton_onClick(View view) {
//        if(audioPlayer.isPlaying())
//        {
//            audioPlayer.pause();
//            audioPlayer.release();
//        }
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        audioPlayer.release();
//    }

}
