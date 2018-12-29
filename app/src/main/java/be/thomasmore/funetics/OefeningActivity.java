package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OefeningActivity extends Activity {
    private DatabaseHelper db;
    private Kind huidigKind;
    private Conditie huidigeConditie;
    private Woordenset huidigeWoordenset;
    private List<Doelwoord> doelwoorden = new ArrayList<Doelwoord>();
    private Doelwoord huidigDoelwoord;
    private long huidigGetestWoordId;
    private long huidigeTestId;

    //Requestcodes
    final int requestVoormeting = 1;
    final int requestPreteaching = 2;
    final int requestOef1 = 3;
    final int requestOef2 = 4;
    final int requestOef3 = 5;
    final int requestOef4 = 6;
    final int requestOef5 = 7;
    final int requestOef6 = 8;
    final int requestNameting = 9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_oefening);

        Bundle bundle = getIntent().getExtras();
        Long kindId = bundle.getLong("kindId");
        Long conditieId = bundle.getLong("conditie");

        db = new DatabaseHelper(this);

        huidigKind = db.getKindById(kindId);
        huidigeConditie = db.getConditieById(conditieId);

        //Groep 1
        if (huidigKind.getGroepId() == 1){
            //Conditie 1
            if (huidigeConditie.getId() == 1){
                //Woordenset A
                huidigeWoordenset = db.getWoordensetById(1);
            }
            //Conditie 2
            if (huidigeConditie.getId() == 2){
                //Woordenset B
                huidigeWoordenset = db.getWoordensetById(2);
            }
            //Conditie 3
            if (huidigeConditie.getId() == 3){
                //Woordenset C
                huidigeWoordenset = db.getWoordensetById(3);
            }
        }
        //Groep 2
        if (huidigKind.getGroepId() == 2){
            //Conditie 1
            if (huidigeConditie.getId() == 1){
                //Woordenset C
                huidigeWoordenset = db.getWoordensetById(3);
            }
            //Conditie 2
            if (huidigeConditie.getId() == 2){
                //Woordenset A
                huidigeWoordenset = db.getWoordensetById(1);
            }
            //Conditie 3
            if (huidigeConditie.getId() == 3){
                //Woordenset B
                huidigeWoordenset = db.getWoordensetById(2);
            }
        }
        //Groep 3
        if (huidigKind.getGroepId() == 3){
            //Conditie 1
            if (huidigeConditie.getId() == 1){
                //Woordenset B
                huidigeWoordenset = db.getWoordensetById(2);
            }
            //Conditie 2
            if (huidigeConditie.getId() == 2){
                //Woordenset C
                huidigeWoordenset = db.getWoordensetById(3);
            }
            //Conditie 3
            if (huidigeConditie.getId() == 3){
                //Woordenset A
                huidigeWoordenset = db.getWoordensetById(1);
            }
        }

        //Alle doelwoorden van de huidige woordenset in een lijst zetten
        doelwoorden = db.getDoelwoordenWhereWoordensetId((int) huidigeWoordenset.getId());
        doelwoorden.add(0, db.getDoelwoordById(0));
        //Eerste woord uit de lijst
        huidigDoelwoord = doelwoorden.get(0);

        //Nieuwe test toevoegen in de database
        Test nieuweTest = new Test();

        String huidigeDateTime = getDateTime();

        nieuweTest.setDatumTijd(huidigeDateTime);
        nieuweTest.setKindId((int) huidigKind.getId());
        nieuweTest.setConditieId((int) huidigeConditie.getId());


        huidigeTestId = db.insertTest(nieuweTest);

        //Voormeting opstarten
        startVoormeting();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Voormeting is beeindigd
        if (requestCode == requestVoormeting) {
            if(resultCode == Activity.RESULT_OK){
                //Score ophalen
                int score = Integer.parseInt(data.getStringExtra("score"));
                int aantalPogingen = Integer.parseInt(data.getStringExtra("aantalPogingen"));

                //Opslaan in database
                //Eerst een nieuw getest woord aanmaken
                GetestWoord nieuwGetestWoord = new GetestWoord();
                nieuwGetestWoord.setDoelwoordId((int) huidigDoelwoord.getId());
                nieuwGetestWoord.setTestId((int) huidigeTestId);
                huidigGetestWoordId = db.insertGetestWoord(nieuwGetestWoord);
                //Nu een nieuwe geteste oefening maken
                GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                nieuweGetesteOefening.setScore(score);
                nieuweGetesteOefening.setAantalPogingen(aantalPogingen);
                nieuweGetesteOefening.setOefeningId(0); //Id van de oefening
                nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                db.insertGetesteOefening(nieuweGetesteOefening);

                //Volgende activity starten
                startPreteaching();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        //Preteaching is beeindigd
        else if (requestCode == requestPreteaching){
            if(resultCode == Activity.RESULT_OK){
                //Volgende activity starten
                startOef1();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        //OEF1 is beeindigd
        else if (requestCode == requestOef1){
            if(resultCode == Activity.RESULT_OK){
                //Nieuwe geteste oefening opslaan in database
                GetesteOefening nieuweGetesteOefening = new GetesteOefening();
                nieuweGetesteOefening.setOefeningId(1); //Id van de oefening
                nieuweGetesteOefening.setGetestWoordId((int) huidigGetestWoordId);
                db.insertGetesteOefening(nieuweGetesteOefening);

                //Volgende activity starten
                startOef2();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        //Preteaching is beeindigd
        else if (requestCode == requestOef2){
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
            }
        }
        //Preteaching is beeindigd
        else if (requestCode == requestOef3){
            if(resultCode == Activity.RESULT_OK){

                //Volgende activity starten
                startOef4();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        //Preteaching is beeindigd
        else if (requestCode == requestOef4){
            if(resultCode == Activity.RESULT_OK){

                //Volgende activity starten
                startOef5();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        //Preteaching is beeindigd
        else if (requestCode == requestOef5){
            if(resultCode == Activity.RESULT_OK){

                //Volgende activity starten
                startOef6();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        //Preteaching is beeindigd
        else if (requestCode == requestOef6){
            if(resultCode == Activity.RESULT_OK){

                //Volgende activity starten
                startNameting();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        //Preteaching is beeindigd
        else if (requestCode == requestNameting){
            if(resultCode == Activity.RESULT_OK){

                //Volgende activity starten
//                startOef3();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void startVoormeting() {
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        bundle.putLong("woordensetId", huidigeWoordenset.getId());

        Intent intent = new Intent(this, VoormetingActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestVoormeting);
    }

    public void startPreteaching(){
        Intent intent = new Intent(this, PreteachingActivity.class);
        startActivityForResult(intent, requestPreteaching);
    }

    public void startOef1(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());

        Intent intent = new Intent(this, Oef1Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestOef1);
    }

    public void startOef2(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());

        Intent intent = new Intent(this, Oef2Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestOef2);
    }

    public void startOef3(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        Intent intent = new Intent(this, Oef3Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestOef3);
    }

    public void startOef4(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        Intent intent = new Intent(this, Oef4Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestOef4);
    }

    public void startOef5(){
        Bundle bundle = new Bundle();
        bundle.putLong("doelwoordId", huidigDoelwoord.getId());
        bundle.putLong("kindId", huidigKind.getId());
        Intent intent = new Intent(this, Oef5Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestOef5);
    }

    public void startOef6(){
        Intent intent = new Intent(this, Oef6Activity.class);
        startActivityForResult(intent, requestOef6);
    }

    public void startNameting(){
        Intent intent = new Intent(this, NametingActivity.class);
        startActivityForResult(intent, requestNameting);
    }

}
