package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Oef4Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    private int score = 0;
    private int aantalPogingen = 0;

    private Button buttonWoord1;
    private Button buttonWoord2;
    private Button buttonWoord3;
    private Button buttonWoord4;

    private FloatingActionButton nextFAB;

    private boolean woord1Selected;
    private boolean woord2Selected;
    private boolean woord3Selected;
    private boolean woord4Selected;

    private int[] tracks = new int[2];
    private int currentTrack = 0;

    private MediaPlayer audioPlayer = null;
    private MediaPlayer juistPlayer = null;
    private MediaPlayer foutPlayer = null;

    private boolean isPlaying = false; //false by default


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef4);

        woord1Selected = false;
        woord2Selected = false;
        woord3Selected = false;
        woord4Selected = false;

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

        nextFAB = (FloatingActionButton) findViewById(R.id.nextFAB);
        nextFAB.setVisibility(View.INVISIBLE); //Maak de volgende-knop onzichtbaar tot er 3 woorden zijn aangeduid

        //Alle woorden in een array zetten (laatste woord is altijd fout)
        String keuzeWeb = huidigDoelwoord.getKeuzeWeb();
        String[] keuzeWoorden = keuzeWeb.split(",");

        //De woorden uit de array in de knoppen plaatsen
        buttonWoord1 = (Button) findViewById(R.id.webButton1);
        buttonWoord1.setText(keuzeWoorden[0]);
        buttonWoord2 = (Button) findViewById(R.id.webButton2);
        buttonWoord2.setText(keuzeWoorden[1]);
        buttonWoord3 = (Button) findViewById(R.id.webButton3);
        buttonWoord3.setText(keuzeWoorden[2]);
        buttonWoord4 = (Button) findViewById(R.id.webButton4);
        buttonWoord4.setText(keuzeWoorden[3]);

        setAudioPlayer();

        playAudioPlayer();
    }

    public void nextFAB_onClick(View view) {
        if (woord1Selected && woord2Selected && woord3Selected){
            juistPlayer.start();
            score++;
            aantalPogingen++;

            //Terug naar oefening activity
            Intent returnIntent = new Intent();
            returnIntent.putExtra("score", String.valueOf(score));
            returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
        else {
            foutPlayer.start();
            aantalPogingen++;
        }
    }

    public void setAudioPlayer() {
        tracks[0] = R.raw.oef4_1; //Hier zie je 4 prentjes staan
        tracks[1] = getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + "_4", "raw", getPackageName());

        juistPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef4_2);
        foutPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef4_3);
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

//    public void volgendeButton_onClick(View view) {
//        Intent returnIntent = new Intent();
//        setResult(Activity.RESULT_OK,returnIntent);
//        finish();
//    }

    public void webButton1_onClick(View view){
        if (woord1Selected){
            buttonWoord1.setBackgroundColor(getColor(R.color.web_red));
        }
        else {
            buttonWoord1.setBackgroundColor(getColor(R.color.web_green));
        }
        woord1Selected = !woord1Selected;
        checkFabNext();
    }

    public void webButton2_onClick(View view){
        if (woord2Selected){
            buttonWoord2.setBackgroundColor(getColor(R.color.web_red));
        }
        else {
            buttonWoord2.setBackgroundColor(getColor(R.color.web_green));
        }
        woord2Selected = !woord2Selected;
        checkFabNext();
    }

    public void webButton3_onClick(View view){
        if (woord3Selected){
            buttonWoord3.setBackgroundColor(getColor(R.color.web_red));
        }
        else {
            buttonWoord3.setBackgroundColor(getColor(R.color.web_green));
        }
        woord3Selected = !woord3Selected;
        checkFabNext();
    }

    public void webButton4_onClick(View view){
        if (woord4Selected){
            buttonWoord4.setBackgroundColor(getColor(R.color.web_red));
        }
        else {
            buttonWoord4.setBackgroundColor(getColor(R.color.web_green));
        }
        woord4Selected = !woord4Selected;
        checkFabNext();
    }

    public void checkFabNext(){
        int aantalChecked = 0;
        if (woord1Selected){
            aantalChecked++;
        }
        if (woord2Selected){
            aantalChecked++;
        }
        if (woord3Selected){
            aantalChecked++;
        }
        if (woord4Selected){
            aantalChecked++;
        }

        if (aantalChecked == 3){
            nextFAB.setVisibility(View.VISIBLE);
        }
        else {
            nextFAB.setVisibility(View.INVISIBLE);
        }
    }

}
