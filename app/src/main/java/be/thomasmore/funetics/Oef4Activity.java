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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    private int aantalJuistSelected = 0;

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

        setAudioPlayer();

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        TextView textWoord = (TextView) findViewById(R.id.textViewWoord);
        textWoord.setText(huidigDoelwoord.getNaam());

        setImages();

        nextFAB = (FloatingActionButton) findViewById(R.id.nextFAB);
        nextFAB.setVisibility(View.INVISIBLE); //Maak de volgende-knop onzichtbaar tot er 3 woorden zijn aangeduid

        playAudioPlayer();
    }

    private void setImages(){
        String keuzeWeb = huidigDoelwoord.getKeuzeWeb();
        String[] keuzeWoorden = keuzeWeb.split(",");

        String shuffleArray[][] = new String [][] {
                { "_4_1", "juist", keuzeWoorden[0]},
                { "_4_2", "juist", keuzeWoorden[1]},
                { "_4_3", "juist", keuzeWoorden[2]},
                { "_4_4", "fout", keuzeWoorden[3]}};
        Collections.shuffle(Arrays.asList(shuffleArray));

        //De woorden uit de array in de knoppen plaatsen
        buttonWoord1 = (Button) findViewById(R.id.webButton1);
        buttonWoord1.setText(shuffleArray[0][2]);
        buttonWoord1.setContentDescription(shuffleArray[0][1]);
        buttonWoord2 = (Button) findViewById(R.id.webButton2);
        buttonWoord2.setText(shuffleArray[1][2]);
        buttonWoord2.setContentDescription(shuffleArray[1][1]);
        buttonWoord3 = (Button) findViewById(R.id.webButton3);
        buttonWoord3.setText(shuffleArray[2][2]);
        buttonWoord3.setContentDescription(shuffleArray[2][1]);
        buttonWoord4 = (Button) findViewById(R.id.webButton4);
        buttonWoord4.setText(shuffleArray[3][2]);
        buttonWoord4.setContentDescription(shuffleArray[3][1]);

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
        ImageView imageView4 = (ImageView) findViewById(R.id.imageView4);

        imageView1.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + shuffleArray[0][0], "drawable", getPackageName()));
        imageView1.setContentDescription(shuffleArray[0][1]);
        imageView2.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + shuffleArray[1][0], "drawable", getPackageName()));
        imageView2.setContentDescription(shuffleArray[1][1]);
        imageView3.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + shuffleArray[2][0], "drawable", getPackageName()));
        imageView3.setContentDescription(shuffleArray[2][1]);
        imageView4.setImageResource(getResources().getIdentifier(huidigDoelwoord.getNaam().toLowerCase() + shuffleArray[3][0], "drawable", getPackageName()));
        imageView4.setContentDescription(shuffleArray[3][1]);
    }

    public void nextFAB_onClick(View view) {
        if (aantalJuistSelected == 3){
            juistPlayer.start();
            while (juistPlayer.isPlaying()){

            }
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

    public void webButton1_onClick(View view){
        if (woord1Selected){
            buttonWoord1.setBackgroundColor(getColor(R.color.web_red));
        }
        else {
            buttonWoord1.setBackgroundColor(getColor(R.color.web_green));
        }

        if (!woord1Selected){
            if (buttonWoord1.getContentDescription().equals("juist")){
                aantalJuistSelected++;
            }
        }
        else {
            if (buttonWoord1.getContentDescription().equals("juist")){
                aantalJuistSelected--;
            }
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

        if (!woord2Selected){
            if (buttonWoord2.getContentDescription().equals("juist")){
                aantalJuistSelected++;
            }
        }
        else {
            if (buttonWoord2.getContentDescription().equals("juist")){
                aantalJuistSelected--;
            }
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

        if (!woord3Selected){
            if (buttonWoord3.getContentDescription().equals("juist")){
                aantalJuistSelected++;
            }
        }
        else {
            if (buttonWoord3.getContentDescription().equals("juist")){
                aantalJuistSelected--;
            }
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

        if (!woord4Selected){
            if (buttonWoord4.getContentDescription().equals("juist")){
                aantalJuistSelected++;
            }
        }
        else {
            if (buttonWoord4.getContentDescription().equals("juist")){
                aantalJuistSelected--;
            }
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
