package be.thomasmore.funetics;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class BeherenActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Groep selectedGroep = null;
    private Kind selectedKind = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beheren);

        db = new DatabaseHelper(this);
        final List<Groep> groepen = db.getGroepen();

        leesGroepen();
        leesKinderenWhereGroep(0);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerGroep);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
                selectedGroep = groepen.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedGroep = null;
            }
        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, groepen);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
    }

    private void leesGroepen(){
        // todo duid geselecteerde groep aan
        final List<Groep> groepen = db.getGroepen();

        ArrayAdapter<Groep> adapter =
                new ArrayAdapter<Groep>(this,
                        android.R.layout.simple_list_item_1, groepen);

        final ListView listViewGroepen =
                (ListView) findViewById(R.id.listViewGroepen);
        listViewGroepen.setAdapter(adapter);

        listViewGroepen.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView,
                                            View childView, int position, long id) {
                        selectedGroep = groepen.get(position);
                        int selectedGroepId = (int) selectedGroep.getId();
                        leesKinderenWhereGroep(selectedGroepId);
                    }
                });
    }

    private void leesGroepenInSpinner(){
        final List<Groep> groepen = db.getGroepen();
        int groepId = selectedKind.getGroepId();

        Spinner spinner = (Spinner) findViewById(R.id.spinnerGroep);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
                selectedGroep = groepen.get(position);
                int selectedGroepId = (int) selectedGroep.getId();
                leesKinderenWhereGroep(selectedGroepId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedGroep = null;
            }
        });

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, groepen);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        spinner.setSelection(groepId-1);
    }

    private void leesKinderenWhereGroep(int groepId){

        //todo duid geselecteerd kind aan

        List<Kind> kinderenLijst;

        if(groepId == 0){
            kinderenLijst = db.getKinderen(0);

        }else{
            kinderenLijst = db.getKinderenWhereGroepId(groepId, 0);
        }

        final List<Kind> kinderen = kinderenLijst;

        final ListView listViewKinderen = (ListView) findViewById(R.id.listViewKinderen);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1, kinderen);
        listViewKinderen.setAdapter(aa);

        listViewKinderen.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView,
                                            View childView, int position, long id) {
                        selectedKind = kinderen.get(position);
                        long selectedKindId = (long) selectedKind.getId();
                        leesKindDetail(selectedKindId);
                    }
                });

    }

    private void leesKindDetail(long kindId){

        final Kind huidigKind = db.getKindById(kindId);

        EditText textVoornaamKind = (EditText) findViewById(R.id.voornaamInput);
        textVoornaamKind.setText(huidigKind.getVoornaam());

        EditText textNaamKind = (EditText) findViewById(R.id.naamInput);
        textNaamKind.setText(huidigKind.getNaam());

//        EditText textLeeftijdKind = (EditText) findViewById(R.id.leeftijdInput);
//        textLeeftijdKind.setText(huidigKind.getLeeftijd());

        leesGroepenInSpinner();

        Switch switchIsActief = (Switch) findViewById(R.id.switchActief);
        if(huidigKind.getActief() == 1){
            switchIsActief.setChecked(true);
        }else{
            switchIsActief.setChecked(false);
        }
    }

    public void kindToevoegen_onClick(View v){

        // todo Niet lege persoon toevoegen

        EditText textVoornaamKind = (EditText) findViewById(R.id.voornaamInput);
        final String voornaam = textVoornaamKind.getText().toString();

        EditText textNaamKind = (EditText) findViewById(R.id.naamInput);
        final String naam = textNaamKind.getText().toString();

        Spinner groepSpinner = (Spinner) findViewById(R.id.spinnerGroep);
        String groep = groepSpinner.getSelectedItem().toString();

        Switch actiefSwitch = (Switch) findViewById(R.id.switchActief);
        final int isActief;
        if( actiefSwitch.isChecked()){
            isActief = 1;
        }else{
            isActief = 0;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wil je '" + voornaam + " " + naam + "' toevoegen aan " + groep + "?")

                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Kind newKind = new Kind();
                        newKind.setVoornaam(voornaam);
                        newKind.setNaam(naam);
                        newKind.setActief(isActief);
                        newKind.setGroepId((int)selectedGroep.getId());

                        db.insertKind(newKind);

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                })
                .setNegativeButton(R.string.dialog_annuleer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void kindWijzigen_onClick(View v){

        // todo Niet lege persoon wijzigen

        EditText textVoornaamKind = (EditText) findViewById(R.id.voornaamInput);
        final String voornaam = textVoornaamKind.getText().toString();

        EditText textNaamKind = (EditText) findViewById(R.id.naamInput);
        final String naam = textNaamKind.getText().toString();

        Spinner groepSpinner = (Spinner) findViewById(R.id.spinnerGroep);
        String groep = groepSpinner.getSelectedItem().toString();

        Switch actiefSwitch = (Switch) findViewById(R.id.switchActief);
        final int isActief;
        if( actiefSwitch.isChecked()){
            isActief = 1;
        }else{
            isActief = 0;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wil je de gegevens van '" + voornaam + " " + naam + "' wijzigen?")

                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Kind newKind = selectedKind;
                        newKind.setVoornaam(voornaam);
                        newKind.setNaam(naam);
                        newKind.setActief(isActief);
                        newKind.setGroepId((int)selectedGroep.getId());

                        db.updateKind(newKind);

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                })
                .setNegativeButton(R.string.dialog_annuleer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void kindVerwijderen_onClick(View v){

        // Todo Niet lege persoon verwijderen

        EditText textVoornaamKind = (EditText) findViewById(R.id.voornaamInput);
        final String voornaam = textVoornaamKind.getText().toString();

        EditText textNaamKind = (EditText) findViewById(R.id.naamInput);
        final String naam = textNaamKind.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ben je zeker dat je '" + voornaam + " " + naam + "' wilt verwijderen?")

                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.deleteKind(selectedKind.getId());

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.dialog_annuleer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
