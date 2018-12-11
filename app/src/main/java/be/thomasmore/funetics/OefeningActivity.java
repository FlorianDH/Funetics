package be.thomasmore.funetics;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class OefeningActivity extends Activity {
    private DatabaseHelper db;
    private Kind huidigKind;
    private Conditie huidigeConditie;
    private Woordenset huidigeWoordenset;
    private List<Doelwoord> doelwoorden = new ArrayList<Doelwoord>();
    private Doelwoord huidigDoelwoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_oefening);

        Bundle bundle = getIntent().getExtras();
        Long kindId = bundle.getLong("kindId");
        Long conditie = bundle.getLong("conditie");

        db = new DatabaseHelper(this);

        huidigKind = db.getKindById(kindId);
        huidigeConditie = db.getConditieById(conditie);

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
        //Eerste woord uit de lijst
        huidigDoelwoord = doelwoorden.get(0);

        startVoormeting();
    }

    public void startVoormeting(){

    }
}
