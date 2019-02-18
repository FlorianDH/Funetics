package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class NametingActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Kind huidigKind;

    private int[] nameting = new int[10];

    private Doelwoord huidigDoelwoord;

    private int oefening = 0;

    private TextView textViewWoord;

    private MediaPlayer woordPlayer;
    private MediaPlayer audioPlayer;

    private ImageButton ImageButton1;
    private ImageButton ImageButton2;
    private ImageButton ImageButton3;
    private ImageButton ImageButton4;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nameting);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        Long kindId = bundle.getLong("kindId");

        huidigKind = db.getKindById(kindId);

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        setNameting();

        fab = (FloatingActionButton) findViewById(R.id.soundFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio();
            }
        });

        playAudio();
    }

    public void imageButton_onClick(View view) {
        String waarde = (String) view.getContentDescription();

        stopPlaying();

        // Juiste foto
        if(waarde.equals("juist")){
            nameting[oefening] ++;
        }

        if (oefening < 9){
            oefening ++;
            setNameting();
            playAudio();
        }else {
            if(this.audioPlayer != null){
                this.audioPlayer.release();
            }
            audioPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef0_einde);
            audioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    while (mediaPlayer.isPlaying()){}
                }
            });

            while(audioPlayer.isPlaying());

            saveNameting();

            //Terug naar oefening activity
            Intent returnIntent = new Intent();
            returnIntent.putExtra("nameting", nameting);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }

    private void saveNameting(){
        Nameting newNameting = new Nameting();
        newNameting.setDuikbril(nameting[0]);
        newNameting.setKlimtouw(nameting[1]);
        newNameting.setKroos(nameting[2]);
        newNameting.setRiet(nameting[3]);
        newNameting.setVal(nameting[4]);
        newNameting.setKompas(nameting[5]);
        newNameting.setSteil(nameting[6]);
        newNameting.setZwaan(nameting[7]);
        newNameting.setKamp(nameting[8]);
        newNameting.setZaklamp(nameting[9]);

        long nametingId = db.insertNameting(newNameting);

        Kind newKind = huidigKind;
        newKind.setNametingId((int)nametingId);

        db.updateKind(newKind);
    }

    public void playAudio(){
        if(oefening == 0){
            playOpdrachtPlayer();
        }
        else{
            playWoordPlayer();
        }
    }

    private void stopPlaying() {
        if (woordPlayer != null) {
            try{
                woordPlayer.reset();
                woordPlayer.prepare();
                woordPlayer.stop();
                woordPlayer.release();
                woordPlayer = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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

    public void playOpdrachtPlayer(){
        if(this.audioPlayer != null){
            this.audioPlayer.release();
        }
        audioPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oef0_voormeting);
        audioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                while (mediaPlayer.isPlaying()){}
                playWoordPlayer();
            }
        });
    }

    public void playWoordPlayer(){
        setWoordPlayer();
        woordPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    public void setNameting(){
        db = new DatabaseHelper(this);
        huidigDoelwoord = db.getDoelwoordById(oefening);

        textViewWoord = (TextView) this.findViewById(R.id.textViewWoord);
        textViewWoord.setText(huidigDoelwoord.getNaam());

        setWoordPlayer();
        setImages();

        nameting[oefening] = 0;
    }

    public void setWoordPlayer() {
        if(this.woordPlayer != null){
            this.woordPlayer.release();
        }
        switch (oefening){
            case 0:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.duikbril);
                break;
            case 1:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.klimtouw);
                break;
            case 2:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.kroos);
                break;
            case 3:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.riet);
                break;
            case 4:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.val);
                break;
            case 5:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.kompas);
                break;
            case 6:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.steil);
                break;
            case 7:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.zwaan);
                break;
            case 8:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.kamp);
                break;
            case 9:
                woordPlayer = MediaPlayer.create(getApplicationContext(), R.raw.zaklamp);
                break;
        }
    }

    public void setImages() {
        ImageButton1 = (ImageButton) this.findViewById(R.id.imageButton1);
        ImageButton2 = (ImageButton) this.findViewById(R.id.imageButton2);
        ImageButton3 = (ImageButton) this.findViewById(R.id.imageButton3);
        ImageButton4 = (ImageButton) this.findViewById(R.id.imageButton4);

        switch (oefening){
            case 0: //duikbril
                ImageButton1.setImageResource(R.drawable.duikbril);
                ImageButton1.setContentDescription("juist");
                ImageButton2.setImageResource(R.drawable.kompas);
                ImageButton2.setContentDescription("fout");
                ImageButton3.setImageResource(R.drawable.riet);
                ImageButton3.setContentDescription("fout");
                ImageButton4.setImageResource(R.drawable.klimtouw);
                ImageButton4.setContentDescription("fout");
                break;
            case 1: //klimtouw
                ImageButton1.setImageResource(R.drawable.zaklamp);
                ImageButton1.setContentDescription("fout");
                ImageButton2.setImageResource(R.drawable.klimtouw);
                ImageButton2.setContentDescription("juist");
                ImageButton3.setImageResource(R.drawable.steil);
                ImageButton3.setContentDescription("fout");
                ImageButton4.setImageResource(R.drawable.duikbril);
                ImageButton4.setContentDescription("fout");
                break;
            case 2: //kroos
                ImageButton1.setImageResource(R.drawable.val);
                ImageButton1.setContentDescription("fout");
                ImageButton2.setImageResource(R.drawable.zaklamp);
                ImageButton2.setContentDescription("fout");
                ImageButton3.setImageResource(R.drawable.kompas);
                ImageButton3.setContentDescription("fout");
                ImageButton4.setImageResource(R.drawable.kroos);
                ImageButton4.setContentDescription("juist");
                break;
            case 3: //riet
                ImageButton1.setImageResource(R.drawable.klimtouw);
                ImageButton1.setContentDescription("fout");
                ImageButton2.setImageResource(R.drawable.riet);
                ImageButton2.setContentDescription("juist");
                ImageButton3.setImageResource(R.drawable.kamp);
                ImageButton3.setContentDescription("fout");
                ImageButton4.setImageResource(R.drawable.zwaan);
                ImageButton4.setContentDescription("fout");
                break;
            case 4: //val
                ImageButton1.setImageResource(R.drawable.klimtouw);
                ImageButton1.setContentDescription("fout");
                ImageButton2.setImageResource(R.drawable.val);
                ImageButton2.setContentDescription("juist");
                ImageButton3.setImageResource(R.drawable.duikbril);
                ImageButton3.setContentDescription("fout");
                ImageButton4.setImageResource(R.drawable.kroos);
                ImageButton4.setContentDescription("fout");
                break;
            case 5: //kompas
                ImageButton1.setImageResource(R.drawable.riet);
                ImageButton1.setContentDescription("fout");
                ImageButton2.setImageResource(R.drawable.zwaan);
                ImageButton2.setContentDescription("fout");
                ImageButton3.setImageResource(R.drawable.kompas);
                ImageButton3.setContentDescription("juist");
                ImageButton4.setImageResource(R.drawable.duikbril);
                ImageButton4.setContentDescription("fout");
                break;
            case 6: //steil
                ImageButton1.setImageResource(R.drawable.kroos);
                ImageButton1.setContentDescription("fout");
                ImageButton2.setImageResource(R.drawable.steil);
                ImageButton2.setContentDescription("juist");
                ImageButton3.setImageResource(R.drawable.kamp);
                ImageButton3.setContentDescription("fout");
                ImageButton4.setImageResource(R.drawable.zaklamp);
                ImageButton4.setContentDescription("fout");
                break;
            case 7: //zwaan
                ImageButton1.setImageResource(R.drawable.val);
                ImageButton1.setContentDescription("fout");
                ImageButton2.setImageResource(R.drawable.riet);
                ImageButton2.setContentDescription("fout");
                ImageButton3.setImageResource(R.drawable.kompas);
                ImageButton3.setContentDescription("fout");
                ImageButton4.setImageResource(R.drawable.zwaan);
                ImageButton4.setContentDescription("juist");
                break;
            case 8: //kamp
                ImageButton1.setImageResource(R.drawable.kamp);
                ImageButton1.setContentDescription("juist");
                ImageButton2.setImageResource(R.drawable.klimtouw);
                ImageButton2.setContentDescription("fout");
                ImageButton3.setImageResource(R.drawable.kompas);
                ImageButton3.setContentDescription("fout");
                ImageButton4.setImageResource(R.drawable.riet);
                ImageButton4.setContentDescription("fout");
                break;
            case 9: //zaklamp
                ImageButton1.setImageResource(R.drawable.val);
                ImageButton1.setContentDescription("fout");
                ImageButton2.setImageResource(R.drawable.kroos);
                ImageButton2.setContentDescription("fout");
                ImageButton3.setImageResource(R.drawable.zaklamp);
                ImageButton3.setContentDescription("juist");
                ImageButton4.setImageResource(R.drawable.steil);
                ImageButton4.setContentDescription("fout");
                break;
        }
    }

}
