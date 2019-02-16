package be.thomasmore.funetics;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OefeningActivity extends Activity {
    private DatabaseHelper db;
    private Kind huidigKind; //Huidig kind waarmee gewerkt wordt
    private Conditie huidigeConditie; //Huidige gekozen conditie
    private Woordenset huidigeWoordenset; //Huidige woordenset
    private List<Doelwoord> doelwoorden = new ArrayList<Doelwoord>();
    private Doelwoord huidigDoelwoord;
    private long huidigGetestWoordId;
    private long huidigeTestId;
    private int selectedConditie = 0;

    int alleScoreVoormeting[]; //Alle scores van alle woorden van de voormeting
    int getesteWoordenId[] = new int[4]; //Lijst met id's van reeds aangemaakte geteste woorden
    int getesteWoordenPositie; //Huidige positie in de lijst van doelwoorden en getesteWoordenId
    int alleScoreNameting[]; //Alle scores van alle woorden van de nameting
    int[] nametingArray; //Alle scores van alle woorden van de nameting uit de tabel nameting


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        Long kindId = bundle.getLong("kindId");

        db = new DatabaseHelper(this);

        huidigKind = db.getKindById(kindId);

        //Check of er al een voormeting is gedaan
        if(huidigKind.getVoormetingId() == 0){
            //Voormeting opstarten
            startVoormeting();
        }

        else{
            //Start Preteaching
            showConditieDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 1: //Preteaching
                if(resultCode == Activity.RESULT_OK){
                    //Scores ophalen
                    alleScoreVoormeting = data.getIntArrayExtra("voormeting");
                    huidigeConditie = db.getConditieById(Long.parseLong(data.getStringExtra("conditie")));

                    setNewTest();

                    //Scores opslaan van de woorden uit de huidige lijst
                    int tempPositie = 0;
                    for (Doelwoord d: doelwoorden){
                        int tempScore = alleScoreVoormeting[(int) d.getId()];
                        //Nieuwe geteste oefening maken
                        GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                        nieuweGetesteOefening.setScore(tempScore);
                        nieuweGetesteOefening.setAantalPogingen(1);
                        nieuweGetesteOefening.setOefeningId(0); //Id van de oefening
                        nieuweGetesteOefening.setGetestWoordId(getesteWoordenId[tempPositie]);
                        db.insertGetesteOefening(nieuweGetesteOefening);
                        tempPositie++;
                    }

                    //Volgende activity starten
                    startPreteaching();
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    finish();
                }
                break;
            case 2: //Oef 1
                if(resultCode == Activity.RESULT_OK){
                    //Volgende activity starten
                    startOef1();
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    finish();
                }
                break;
            case 3: //Oef 2
                if(resultCode == Activity.RESULT_OK){
                    //Score ophalen
                    int score = Integer.parseInt(data.getStringExtra("score"));
                    int aantalPogingen = Integer.parseInt(data.getStringExtra("aantalPogingen"));

                    //Nieuwe geteste oefening opslaan in database
                    GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                    nieuweGetesteOefening.setScore(score);
                    nieuweGetesteOefening.setAantalPogingen(aantalPogingen);
                    nieuweGetesteOefening.setOefeningId(1); //Id van de oefening
                    nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                    db.insertGetesteOefening(nieuweGetesteOefening);

                    //Volgende activity starten
                    startOef2();
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    finish();
                }
                break;
            case 4: //Oef 3
                if(resultCode == Activity.RESULT_OK){
                    //Score ophalen
                    int score = Integer.parseInt(data.getStringExtra("score"));
                    int aantalPogingen = Integer.parseInt(data.getStringExtra("aantalPogingen"));

                    //Nieuwe geteste oefening opslaan in database
                    GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                    nieuweGetesteOefening.setScore(score);
                    nieuweGetesteOefening.setAantalPogingen(aantalPogingen);
                    nieuweGetesteOefening.setOefeningId(2); //Id van de oefening
                    nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                    db.insertGetesteOefening(nieuweGetesteOefening);

                    //Volgende activity starten
                    startOef3();
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    finish();
                }
                break;
            case 5: //Oef 4
                if(resultCode == Activity.RESULT_OK){
                    //Score ophalen
                    int score = Integer.parseInt(data.getStringExtra("score"));
                    int aantalPogingen = Integer.parseInt(data.getStringExtra("aantalPogingen"));

                    //Nieuwe geteste oefening opslaan in database
                    GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                    nieuweGetesteOefening.setScore(score);
                    nieuweGetesteOefening.setAantalPogingen(aantalPogingen);
                    nieuweGetesteOefening.setOefeningId(3); //Id van de oefening
                    nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                    db.insertGetesteOefening(nieuweGetesteOefening);

                    //Volgende activity starten
                    startOef4();
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    finish();
                }
                break;
            case 6: //Oef 5
                if(resultCode == Activity.RESULT_OK){
                    //Score ophalen
                    int score = Integer.parseInt(data.getStringExtra("score"));
                    int aantalPogingen = Integer.parseInt(data.getStringExtra("aantalPogingen"));

                    //Nieuwe geteste oefening opslaan in database
                    GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                    nieuweGetesteOefening.setScore(score);
                    nieuweGetesteOefening.setAantalPogingen(aantalPogingen);
                    nieuweGetesteOefening.setOefeningId(4); //Id van de oefening
                    nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                    db.insertGetesteOefening(nieuweGetesteOefening);

                    //Volgende activity starten
                    startOef5();
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    finish();
                }
                break;
            case 7: //oef 6
                if(resultCode == Activity.RESULT_OK){
                    //Score ophalen
                    int score = Integer.parseInt(data.getStringExtra("score"));
                    int aantalPogingen = Integer.parseInt(data.getStringExtra("aantalPogingen"));

                    //Nieuwe geteste oefening opslaan in database
                    GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                    nieuweGetesteOefening.setScore(score);
                    nieuweGetesteOefening.setAantalPogingen(aantalPogingen);
                    nieuweGetesteOefening.setOefeningId(5); //Id van de oefening
                    nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                    db.insertGetesteOefening(nieuweGetesteOefening);

                    //Bij oefenwoord altijd eerst naar oef 6.1 gaan
                    if (huidigDoelwoord.getId() == 0){
                        startOef6_1();
                    }
                    else {
                        //Volgende activity starten
                        if (huidigeConditie.getId() == 1){
                            startOef6_1();
                        }
                        else if (huidigeConditie.getId() == 2){
                            startOef6_2();
                        }
                        else {
                            startOef6_3();
                        }
                    }
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    finish();
                }
                break;
            case 8: //Nameting
                if(resultCode == Activity.RESULT_OK){
                    //Score ophalen
                    int score = Integer.parseInt(data.getStringExtra("score"));
                    int aantalPogingen = Integer.parseInt(data.getStringExtra("aantalPogingen"));
                    int nummer = Integer.parseInt(data.getStringExtra("nummer"));

                    if (huidigDoelwoord.getId() == 0){
                        //Nieuwe geteste oefening opslaan in database
                        GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                        nieuweGetesteOefening.setScore(score);
                        nieuweGetesteOefening.setAantalPogingen(aantalPogingen);
                        if (huidigeConditie.getId() == 1){
                            nieuweGetesteOefening.setOefeningId(6); //Id van de oefening
                        }
                        else if (huidigeConditie.getId() == 2){
                            nieuweGetesteOefening.setOefeningId(7); //Id van de oefening
                        }
                        else {
                            nieuweGetesteOefening.setOefeningId(8); //Id van de oefening
                        }
                        nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                        db.insertGetesteOefening(nieuweGetesteOefening);
                    }

                    //Oefenwoord werd getest
                    if (huidigDoelwoord.getId() == 0){
                        if (nummer == 1){
                            //Nieuwe geteste oefening opslaan in database
                            GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                            nieuweGetesteOefening.setScore(score);
                            nieuweGetesteOefening.setAantalPogingen(aantalPogingen);
                            nieuweGetesteOefening.setOefeningId(7); //Id van de oefening
                            nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                            db.insertGetesteOefening(nieuweGetesteOefening);

                            startOef6_2();
                            return;
                        }
                        else if (nummer == 2){
                            //Nieuwe geteste oefening opslaan in database
                            GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                            nieuweGetesteOefening.setScore(score);
                            nieuweGetesteOefening.setAantalPogingen(aantalPogingen);
                            nieuweGetesteOefening.setOefeningId(8); //Id van de oefening
                            nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                            db.insertGetesteOefening(nieuweGetesteOefening);

                            startOef6_3();
                            return;
                        }
                    }
                    else {
                        //Naar volgende woord gaan
                        if (getesteWoordenPositie != 3){
                            //Getest woord verhogen
                            getesteWoordenPositie++;
                            huidigGetestWoordId = getesteWoordenId[getesteWoordenPositie];

                            //Huidig getest woord verhogen
                            huidigDoelwoord = doelwoorden.get(getesteWoordenPositie);

                            //Start oefening 1
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Wilt u het volgende woord '" + huidigDoelwoord.getNaam() + "' testen?")
                                    .setPositiveButton(R.string.dialog_start, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            startOef1();
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
                        //nameting enkel starten na conditie 3
                        else if (huidigeConditie.getId() == 3) {
                            //Nameting starten
                            startNameting();
                        }
                        else {
                            finish();
                        }
                    }
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    finish();
                }
                break;
            case 9: //Einde test met nameting
                if(resultCode == Activity.RESULT_OK){
                    //Scores nameting ophalen
                    leesNameting();

                    //Alle testen van huidig kind ophalen
                    List<Test> testen = db.getTestenWhereKindId((int) huidigKind.getId());

                    for (Test test: testen) {
                        List<GetestWoord> getesteWoorden = db.getGetesteWoordenWhereTestId((int) test.getId());
                        for (GetestWoord getestWoord:getesteWoorden){
                            int tempScore = nametingArray[getestWoord.getDoelwoordId()];
                            //Nieuwe geteste oefening maken bij bestaand getestwoord
                            GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                            nieuweGetesteOefening.setScore(tempScore);
                            nieuweGetesteOefening.setAantalPogingen(1);
                            nieuweGetesteOefening.setOefeningId(9); //Id van de oefening
                            nieuweGetesteOefening.setGetestWoordId((int) getestWoord.getId());
                            db.insertGetesteOefening(nieuweGetesteOefening);
                        }
                    }

                    finish();
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    finish();
                }
                break;
            default:
                if(resultCode == Activity.RESULT_OK){

                }
                if (resultCode == Activity.RESULT_CANCELED) {

                }
                break;
        }
    }

    public void setNewTest(){

        //Groep 1
        if (huidigKind.getGroepId() == 1){
            //Conditie 1
            if (huidigeConditie.getId() == 1){
                //Woordenset A
                huidigeWoordenset = db.getWoordensetById(1);
            }
            //Conditie 2
            else if (huidigeConditie.getId() == 2){
                //Woordenset B
                huidigeWoordenset = db.getWoordensetById(2);
            }
            //Conditie 3
            else if (huidigeConditie.getId() == 3){
                //Woordenset C
                huidigeWoordenset = db.getWoordensetById(3);
            }
        }
        //Groep 2
        else if (huidigKind.getGroepId() == 2){
            //Conditie 1
            if (huidigeConditie.getId() == 1){
                //Woordenset C
                huidigeWoordenset = db.getWoordensetById(3);
            }
            //Conditie 2
            else if (huidigeConditie.getId() == 2){
                //Woordenset A
                huidigeWoordenset = db.getWoordensetById(1);
            }
            //Conditie 3
            else if (huidigeConditie.getId() == 3){
                //Woordenset B
                huidigeWoordenset = db.getWoordensetById(2);
            }
        }
        //Groep 3
        else if (huidigKind.getGroepId() == 3){
            //Conditie 1
            if (huidigeConditie.getId() == 1){
                //Woordenset B
                huidigeWoordenset = db.getWoordensetById(2);
            }
            //Conditie 2
            else if (huidigeConditie.getId() == 2){
                //Woordenset C
                huidigeWoordenset = db.getWoordensetById(3);
            }
            //Conditie 3
            else if (huidigeConditie.getId() == 3){
                //Woordenset A
                huidigeWoordenset = db.getWoordensetById(1);
            }
        }

        //Alle doelwoorden van de huidige woordenset in een lijst zetten
        doelwoorden = db.getDoelwoordenWhereWoordensetId((int) huidigeWoordenset.getId());
        if (huidigeConditie.getId() == 1){
            doelwoorden.add(0, db.getDoelwoordById(0));
        }
        //Eerste woord uit de lijst
        huidigDoelwoord = doelwoorden.get(0);

        //Nieuwe test toevoegen in de database
        Test nieuweTest = new Test();

        String huidigeDateTime = getDateTime();

        nieuweTest.setDatumTijd(huidigeDateTime);
        nieuweTest.setKindId((int) huidigKind.getId());
        nieuweTest.setConditieId((int) huidigeConditie.getId());


        huidigeTestId = db.insertTest(nieuweTest);

        //Nieuwe geteste woorden aanmaken
        int tempPositie = 0;
        for (Doelwoord d: doelwoorden){
            GetestWoord nieuwGetestWoord = new GetestWoord();
            nieuwGetestWoord.setDoelwoordId((int) d.getId());
            nieuwGetestWoord.setTestId((int) huidigeTestId);
            huidigGetestWoordId = db.insertGetestWoord(nieuwGetestWoord);
            getesteWoordenId[tempPositie] = (int) huidigGetestWoordId;
            tempPositie++;
        }

        getesteWoordenPositie = 0;
        huidigGetestWoordId = getesteWoordenId[getesteWoordenPositie];

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void startVoormeting() {
        Bundle bundle = new Bundle();
        bundle.putLong("kindId", huidigKind.getId());

        Intent intent = new Intent(this, VoormetingActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    public void startPreteaching(){
        Intent intent = new Intent(this, PreteachingActivity.class);
        startActivityForResult(intent, 2);
    }

    public void startOef1(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());

        Intent intent = new Intent(this, Oef1Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,3);
    }

    public void startOef2(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());

        Intent intent = new Intent(this, Oef2Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 4);
    }

    public void startOef3(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        Intent intent = new Intent(this, Oef3Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 5);
    }

    public void startOef4(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        Intent intent = new Intent(this, Oef4Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 6);
    }

    public void startOef5(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        Intent intent = new Intent(this, Oef5Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,7);
    }

    public void startOef6_1(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        Intent intent = new Intent(this, Oef6_1Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 8);
    }

    public void startOef6_2(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        Intent intent = new Intent(this, Oef6_2Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 8);
    }

    public void startOef6_3(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        Intent intent = new Intent(this, Oef6_3Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 8);
    }

    public void startNameting(){
        Bundle bundle = new Bundle();
        bundle.putLong("kindId", huidigKind.getId());

        Intent intent = new Intent(this, NametingActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 9);
    }

    private void showConditieDialog() {
        final String[] items = { "Conditie 1", "Conditie 2", "Conditie 3" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecteer de conditie die u wilt testen")
                .setSingleChoiceItems(items, 0, null)
                .setPositiveButton(R.string.dialog_start, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        selectedConditie = ((AlertDialog)dialog).getListView().getCheckedItemPosition() + 1; //conditie 1 geeft als waarde ook 1

                        Voormeting voormeting = db.getVoormetingById(huidigKind.getVoormetingId());

                        int[] voormetingArray = new int[10];

                        voormetingArray[0] = voormeting.getDuikbril();
                        voormetingArray[1] = voormeting.getKlimtouw();
                        voormetingArray[2] = voormeting.getKroos();
                        voormetingArray[3] = voormeting.getRiet();
                        voormetingArray[4] = voormeting.getVal();
                        voormetingArray[5] = voormeting.getKompas();
                        voormetingArray[6] = voormeting.getSteil();
                        voormetingArray[7] = voormeting.getZwaan();
                        voormetingArray[8] = voormeting.getKamp();
                        voormetingArray[9] = voormeting.getZaklamp();

                        //Terug naar oefeningactivity
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("voormeting", voormetingArray);
                        returnIntent.putExtra("conditie", String.valueOf(selectedConditie));

                        onActivityResult( 1, Activity.RESULT_OK, returnIntent);
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

    private void leesNameting() {
        huidigKind = db.getKindById(huidigKind.getId());

        Nameting nameting = db.getNametingById(huidigKind.getNametingId());

        nametingArray = new int[10];

        nametingArray[0] = nameting.getDuikbril();
        nametingArray[1] = nameting.getKlimtouw();
        nametingArray[2] = nameting.getKroos();
        nametingArray[3] = nameting.getRiet();
        nametingArray[4] = nameting.getVal();
        nametingArray[5] = nameting.getKompas();
        nametingArray[6] = nameting.getSteil();
        nametingArray[7] = nameting.getZwaan();
        nametingArray[8] = nameting.getKamp();
        nametingArray[9] = nameting.getZaklamp();
    }
}
