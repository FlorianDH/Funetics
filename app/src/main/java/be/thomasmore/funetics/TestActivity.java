package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TestActivity extends AppCompatActivity {

    public int score = 0;
    public int aantalPogingen = 0;

    private MediaPlayer foutPlayer;

    private MediaPlayer duikbrilPlayer;
    private ImageButton playSound;

    private ImageButton ImageButton1;
    private ImageButton ImageButton2;
    private ImageButton ImageButton3;
    private ImageButton ImageButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        foutPlayer = MediaPlayer.create(this, R.raw.fout);
        duikbrilPlayer = MediaPlayer.create(this, R.raw.duikbril);
        playSound = (ImageButton) this.findViewById(R.id.replayButton);

        ImageButton1 = (ImageButton) this.findViewById(R.id.imageButton1);
        ImageButton2 = (ImageButton) this.findViewById(R.id.imageButton2);
        ImageButton3 = (ImageButton) this.findViewById(R.id.imageButton3);
        ImageButton4 = (ImageButton) this.findViewById(R.id.imageButton4);

        ImageButton1.setImageResource(R.drawable.duikbril_fout1);
        ImageButton2.setImageResource(R.drawable.duikbril_fout2);
        ImageButton3.setImageResource(R.drawable.duikbril_juist);
        ImageButton4.setImageResource(R.drawable.duikbril_fout3);

        playSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duikbrilPlayer.start();
            }
        });
    }

    //Wanneer er op een van de afbeeldingen gedrukt wordt
    public void imageButton_onClick(View view) {
        String waarde = (String) view.getContentDescription();
        //Controleren op welke knop er gedrukt is
        if(waarde.equals("juist")){
            aantalPogingen++;
            score++;

            //Terug naar oefening activity
            Intent returnIntent = new Intent();
            returnIntent.putExtra("score", String.valueOf(score));
            returnIntent.putExtra("aantalPogingen", String.valueOf(aantalPogingen));
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
        //Er is op de foute afbeelding gedrukt
        else {
            aantalPogingen++;
            foutPlayer.start();
        }
    }
}
