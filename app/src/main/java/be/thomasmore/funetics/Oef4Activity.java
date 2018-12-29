package be.thomasmore.funetics;

import android.app.Activity;
import android.content.Intent;
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

public class Oef4Activity extends AppCompatActivity {

    private DatabaseHelper db;
    private Doelwoord huidigDoelwoord;
    private Kind huidigKind;

    private Button buttonWoord1;
    private Button buttonWoord2;
    private Button buttonWoord3;
    private Button buttonWoord4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef4);

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

        FloatingActionButton nextFAB = (FloatingActionButton) findViewById(R.id.nextFAB);
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
    }

    public void nextFAB_onClick(View view) {

    }

//    public void volgendeButton_onClick(View view) {
//        Intent returnIntent = new Intent();
//        setResult(Activity.RESULT_OK,returnIntent);
//        finish();
//    }

    public void webButton1_onClick(View view){
        buttonWoord1.setBackgroundColor(getColor(R.color.web_green));
    }

    public void webButton2_onClick(View view){
        buttonWoord1.setBackgroundColor(getColor(R.color.web_green));
    }

    public void webButton3_onClick(View view){

    }

    public void webButton4_onClick(View view){

    }

}
