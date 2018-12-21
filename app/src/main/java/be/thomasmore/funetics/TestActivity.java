package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private String huidigDoelwoordUpper;
    private String huidigDoelwoordLower;

    public int score = 0;
    public int aantalPogingen = 0;

    private TextView textViewWoord;

    private MediaPlayer foutPlayer;
    private MediaPlayer woordPlayer;
    private ImageButton playSound;

    private ImageButton ImageButton1;
    private ImageButton ImageButton2;
    private ImageButton ImageButton3;
    private ImageButton ImageButton4;

    private Kind huidigKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        Bundle bundle = getIntent().getExtras();
        Long doelwoordId = bundle.getLong("doelwoordId");
        Long kindId = bundle.getLong("kindId");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.soundFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
            }
        });


        db = new DatabaseHelper(this);

        huidigDoelwoord = db.getDoelwoordById(doelwoordId);
        huidigDoelwoordUpper = huidigDoelwoord.getNaam().toUpperCase();
        huidigDoelwoordLower = huidigDoelwoord.getNaam().toLowerCase();

        huidigKind = db.getKindById(kindId);

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        textViewWoord = (TextView) this.findViewById(R.id.textViewWoord);
        textViewWoord.setText(huidigDoelwoord.getNaam());

        foutPlayer = MediaPlayer.create(this, R.raw.fout);
        if (huidigDoelwoord.getId() == 0){
            woordPlayer = MediaPlayer.create(this, R.raw.duikbril);
        }
        else if (huidigDoelwoord.getId() == 1){
            woordPlayer = MediaPlayer.create(this, R.raw.klimtouw);
        }
        else if (huidigDoelwoord.getId() == 2){
            woordPlayer = MediaPlayer.create(this, R.raw.kroos);
        }
        else if (huidigDoelwoord.getId() == 3){
            woordPlayer = MediaPlayer.create(this, R.raw.riet);
        }
        else if (huidigDoelwoord.getId() == 4){
            woordPlayer = MediaPlayer.create(this, R.raw.val);
        }
        else if (huidigDoelwoord.getId() == 5){
            woordPlayer = MediaPlayer.create(this, R.raw.kompas);
        }
        else if (huidigDoelwoord.getId() == 6){
            woordPlayer = MediaPlayer.create(this, R.raw.steil);
        }
        else if (huidigDoelwoord.getId() == 7){
            woordPlayer = MediaPlayer.create(this, R.raw.zwaan);
        }
        else if (huidigDoelwoord.getId() == 8){
            woordPlayer = MediaPlayer.create(this, R.raw.kamp);
        }
        else if (huidigDoelwoord.getId() == 9){
            woordPlayer = MediaPlayer.create(this, R.raw.zaklamp);
        }

        //playSound = (ImageButton) this.findViewById(R.id.replayButton);

        ImageButton1 = (ImageButton) this.findViewById(R.id.imageButton1);
        ImageButton2 = (ImageButton) this.findViewById(R.id.imageButton2);
        ImageButton3 = (ImageButton) this.findViewById(R.id.imageButton3);
        ImageButton4 = (ImageButton) this.findViewById(R.id.imageButton4);

        ImageButton1.setImageResource(R.drawable.kompas);
        ImageButton2.setImageResource(R.drawable.kroos);
        ImageButton3.setImageResource(R.drawable.duikbril);
        ImageButton4.setImageResource(R.drawable.klimtouw);

//        playSound.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                woordPlayer.start();
//            }
//        });

        playSound();
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

    public void playSound(){
        woordPlayer.start();
    }
}
