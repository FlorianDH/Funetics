package be.thomasmore.funetics;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class VoormetingActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Kind huidigKind;

    private int selectedConditie = 0;

    private int[] voormeting = new int[10];

    private Doelwoord huidigDoelwoord;

    private int oefening = 0;

    private TextView textViewWoord;

    private MediaPlayer woordPlayer;
    private MediaPlayer audioPlayer;

    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voormeting);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        Long kindId = bundle.getLong("kindId");

        huidigKind = db.getKindById(kindId);

        TextView textNaamKind = (TextView) findViewById(R.id.textViewNaam);
        textNaamKind.setText(huidigKind.toString());

        setVoormeting();

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
            voormeting[oefening] ++;
        }

        if (oefening < 9){
            oefening ++;
            setVoormeting();
            playAudio();
        }else {
            textViewWoord.setVisibility(view.INVISIBLE);
            imageButton1.setVisibility(view.INVISIBLE);
            imageButton2.setVisibility(view.INVISIBLE);
            imageButton3.setVisibility(view.INVISIBLE);
            imageButton4.setVisibility(view.INVISIBLE);
            fab.setVisibility(view.INVISIBLE);

            saveVoormeting();

            showConditieDialog();
        }
    }

    private void saveVoormeting(){
        Voormeting newVoormeting = new Voormeting();
        newVoormeting.setDuikbril(voormeting[0]);
        newVoormeting.setKlimtouw(voormeting[1]);
        newVoormeting.setKroos(voormeting[2]);
        newVoormeting.setRiet(voormeting[3]);
        newVoormeting.setVal(voormeting[4]);
        newVoormeting.setKompas(voormeting[5]);
        newVoormeting.setSteil(voormeting[6]);
        newVoormeting.setZwaan(voormeting[7]);
        newVoormeting.setKamp(voormeting[8]);
        newVoormeting.setZaklamp(voormeting[9]);

        long voormetingId = db.insertVoormeting(newVoormeting);

        Kind newKind = huidigKind;
        newKind.setVoormetingId((int)voormetingId);

        db.updateKind(newKind);
    }

    private void showConditieDialog() {
        final String[] items = { "Conditie 1", "Conditie 2", "Conditie 3" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecteer de conditie die u wilt testen")
                .setSingleChoiceItems(items, 0, null)
                .setPositiveButton(R.string.dialog_start, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        selectedConditie = ((AlertDialog)dialog).getListView().getCheckedItemPosition() + 1; //conditie 1 geeft als waarde ook 1

                        //Terug naar oefeningactivity
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("voormeting", voormeting);
                        returnIntent.putExtra("conditie", String.valueOf(selectedConditie));
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.dialog_annuleer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
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

    public void setVoormeting(){
        db = new DatabaseHelper(this);
        huidigDoelwoord = db.getDoelwoordById(oefening);

        textViewWoord = (TextView) this.findViewById(R.id.textViewWoord);
        textViewWoord.setText(huidigDoelwoord.getNaam());

        setWoordPlayer();
        setImages();

        voormeting[oefening] = 0;
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
        imageButton1 = (ImageButton) this.findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) this.findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) this.findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) this.findViewById(R.id.imageButton4);

        switch (oefening){
            case 0: //duikbril
                imageButton1.setImageResource(R.drawable.duikbril);
                imageButton1.setContentDescription("juist");
                imageButton2.setImageResource(R.drawable.kompas);
                imageButton2.setContentDescription("fout");
                imageButton3.setImageResource(R.drawable.riet);
                imageButton3.setContentDescription("fout");
                imageButton4.setImageResource(R.drawable.klimtouw);
                imageButton4.setContentDescription("fout");
                break;
            case 1: //klimtouw
                imageButton1.setImageResource(R.drawable.zaklamp);
                imageButton1.setContentDescription("fout");
                imageButton2.setImageResource(R.drawable.klimtouw);
                imageButton2.setContentDescription("juist");
                imageButton3.setImageResource(R.drawable.steil);
                imageButton3.setContentDescription("fout");
                imageButton4.setImageResource(R.drawable.duikbril);
                imageButton4.setContentDescription("fout");
                break;
            case 2: //kroos
                imageButton1.setImageResource(R.drawable.val);
                imageButton1.setContentDescription("fout");
                imageButton2.setImageResource(R.drawable.zaklamp);
                imageButton2.setContentDescription("fout");
                imageButton3.setImageResource(R.drawable.kompas);
                imageButton3.setContentDescription("fout");
                imageButton4.setImageResource(R.drawable.kroos);
                imageButton4.setContentDescription("juist");
                break;
            case 3: //riet
                imageButton1.setImageResource(R.drawable.klimtouw);
                imageButton1.setContentDescription("fout");
                imageButton2.setImageResource(R.drawable.riet);
                imageButton2.setContentDescription("juist");
                imageButton3.setImageResource(R.drawable.kamp);
                imageButton3.setContentDescription("fout");
                imageButton4.setImageResource(R.drawable.zwaan);
                imageButton4.setContentDescription("fout");
                break;
            case 4: //val
                imageButton1.setImageResource(R.drawable.klimtouw);
                imageButton1.setContentDescription("fout");
                imageButton2.setImageResource(R.drawable.val);
                imageButton2.setContentDescription("juist");
                imageButton3.setImageResource(R.drawable.duikbril);
                imageButton3.setContentDescription("fout");
                imageButton4.setImageResource(R.drawable.kroos);
                imageButton4.setContentDescription("fout");
                break;
            case 5: //kompas
                imageButton1.setImageResource(R.drawable.riet);
                imageButton1.setContentDescription("fout");
                imageButton2.setImageResource(R.drawable.zwaan);
                imageButton2.setContentDescription("fout");
                imageButton3.setImageResource(R.drawable.kompas);
                imageButton3.setContentDescription("juist");
                imageButton4.setImageResource(R.drawable.duikbril);
                imageButton4.setContentDescription("fout");
                break;
            case 6: //steil
                imageButton1.setImageResource(R.drawable.kroos);
                imageButton1.setContentDescription("fout");
                imageButton2.setImageResource(R.drawable.steil);
                imageButton2.setContentDescription("juist");
                imageButton3.setImageResource(R.drawable.kamp);
                imageButton3.setContentDescription("fout");
                imageButton4.setImageResource(R.drawable.zaklamp);
                imageButton4.setContentDescription("fout");
                break;
            case 7: //zwaan
                imageButton1.setImageResource(R.drawable.val);
                imageButton1.setContentDescription("fout");
                imageButton2.setImageResource(R.drawable.riet);
                imageButton2.setContentDescription("fout");
                imageButton3.setImageResource(R.drawable.kompas);
                imageButton3.setContentDescription("fout");
                imageButton4.setImageResource(R.drawable.zwaan);
                imageButton4.setContentDescription("juist");
                break;
            case 8: //kamp
                imageButton1.setImageResource(R.drawable.kamp);
                imageButton1.setContentDescription("juist");
                imageButton2.setImageResource(R.drawable.klimtouw);
                imageButton2.setContentDescription("fout");
                imageButton3.setImageResource(R.drawable.kompas);
                imageButton3.setContentDescription("fout");
                imageButton4.setImageResource(R.drawable.riet);
                imageButton4.setContentDescription("fout");
                break;
            case 9: //zaklamp
                imageButton1.setImageResource(R.drawable.val);
                imageButton1.setContentDescription("fout");
                imageButton2.setImageResource(R.drawable.kroos);
                imageButton2.setContentDescription("fout");
                imageButton3.setImageResource(R.drawable.zaklamp);
                imageButton3.setContentDescription("juist");
                imageButton4.setImageResource(R.drawable.steil);
                imageButton4.setContentDescription("fout");
                break;
        }
    }
}
