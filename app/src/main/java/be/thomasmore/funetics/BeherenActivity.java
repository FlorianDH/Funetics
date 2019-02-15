package be.thomasmore.funetics;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class BeherenActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Klas huidigKlas = null;
    private Groep selectedGroep = null;
    private Klas selectedKlas = null;
    private Kind selectedKind = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beheren);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Long klasId = bundle.getLong("klasId");

        db = new DatabaseHelper(this);

        huidigKlas = db.getKlasById(klasId);

        final List<Groep> groepen = db.getGroepen();
        final List<Klas> klassen = db.getKlassen();

        leesGroepen();
        leesKinderenWhereKlasEnGroep( (int) huidigKlas.getId(), 0);

        Spinner spinnerGroep = findViewById(R.id.spinnerGroep);
        spinnerGroep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
                selectedGroep = groepen.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedGroep = null;
            }
        });

        Spinner spinnerKlas = findViewById(R.id.spinnerKlas);
        spinnerKlas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
                selectedKlas = klassen.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedKlas = null;
            }
        });

        ArrayAdapter <Groep> aaGroep = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, groepen);
        ArrayAdapter <Klas> aaKlas = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, klassen);
        aaGroep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aaKlas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroep.setAdapter(aaGroep);
        spinnerKlas.setAdapter(aaKlas);

        int spinnerPositionKlas = aaKlas.getPosition(huidigKlas);
        spinnerKlas.setSelection(spinnerPositionKlas);
    }

    private void leesGroepenInSpinner(){
        final List<Groep> groepen = db.getGroepen();

        Spinner spinner = findViewById(R.id.spinnerGroep);
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

        ArrayAdapter <Groep> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, groepen);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        int spinnerPosition = aa.getPosition(groepen.get(selectedKind.getGroepId()-1));
        spinner.setSelection(spinnerPosition);
    }

    private void leesKlassenInSpinner(){
        final List<Klas> klassen = db.getKlassen();

        Spinner spinner = findViewById(R.id.spinnerKlas);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
                selectedKlas = klassen.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedKlas = null;
            }
        });

        ArrayAdapter <Klas> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, klassen);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        int spinnerPosition = aa.getPosition(klassen.get(selectedKind.getKlasId()-1));
        spinner.setSelection(spinnerPosition);
    }

    private void leesGroepen(){
        final List<Groep> groepen = db.getGroepen();

        ArrayAdapter<Groep> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, groepen);

        final ListView listViewGroepen =
                findViewById(R.id.listViewGroepen);
        listViewGroepen.setAdapter(adapter);

        listViewGroepen.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView,
                                            View childView, int position, long id) {
                        selectedGroep = groepen.get(position);
                        int selectedGroepId = (int) selectedGroep.getId();
                        leesKinderenWhereKlasEnGroep( (int) huidigKlas.getId(),  selectedGroepId);
                    }
                });
    }

    private void leesKinderenWhereKlasEnGroep(int klasId, int groepId){
        List<Kind> kinderenLijst;

        if(groepId == 0){
            kinderenLijst = db.getKinderenWhereKlasIdAndGroepId(klasId, 1, 0);

        }else{
            kinderenLijst = db.getKinderenWhereKlasIdAndGroepId(klasId, groepId, 0);
        }

        final List<Kind> kinderen = kinderenLijst;

        ArrayAdapter <Kind> aa =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, kinderen);

        final ListView listViewKinderen = findViewById(R.id.listViewKinderen);

        listViewKinderen.setAdapter(aa);

        listViewKinderen.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView,
                                            View childView, int position, long id) {
                        selectedKind = kinderen.get(position);
                        long selectedKindId = selectedKind.getId();
                        leesKindDetail(selectedKindId);
                    }
                });
    }

    private void leesKindDetail(long kindId){

        final Kind huidigKind = db.getKindById(kindId);

        EditText textVoornaamKind = findViewById(R.id.voornaamInput);
        textVoornaamKind.setText(huidigKind.getVoornaam());

        EditText textNaamKind = findViewById(R.id.naamInput);
        textNaamKind.setText(huidigKind.getNaam());

        leesGroepenInSpinner();
        leesKlassenInSpinner();

        Switch switchIsActief = findViewById(R.id.switchActief);
        if(huidigKind.getActief() == 1){
            switchIsActief.setChecked(true);
        }else{
            switchIsActief.setChecked(false);
        }
    }

    public void kindToevoegen_onClick(View v){

        EditText textVoornaamKind = findViewById(R.id.voornaamInput);
        final String voornaam = textVoornaamKind.getText().toString();

        EditText textNaamKind = findViewById(R.id.naamInput);
        final String naam = textNaamKind.getText().toString();

        Spinner klasSpinner = findViewById(R.id.spinnerKlas);
        String klas = klasSpinner.getSelectedItem().toString();

        Spinner groepSpinner = findViewById(R.id.spinnerGroep);
        String groep = groepSpinner.getSelectedItem().toString();

        Switch actiefSwitch = findViewById(R.id.switchActief);
        final int isActief;
        if( actiefSwitch.isChecked()){
            isActief = 1;
        }else{
            isActief = 0;
        }

        if(voornaam.isEmpty() || naam.isEmpty()){
            Toast.makeText(getBaseContext(), "Geen voornaam of naam ingevuld!", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Wil je '" + voornaam + " " + naam + "' toevoegen aan " + klas + ", " + groep + "?")

                    .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Kind newKind = new Kind();
                            newKind.setVoornaam(voornaam);
                            newKind.setNaam(naam);
                            newKind.setActief(isActief);
                            newKind.setKlasId((int)selectedKlas.getId());
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
    }

    public void kindWijzigen_onClick(View v){

        if(selectedKind == null) {
            Toast.makeText(getBaseContext(), "Geen kind geselecteerd om te wijzigen", Toast.LENGTH_SHORT).show();
        }else{
            EditText textVoornaamKind = findViewById(R.id.voornaamInput);
            final String voornaam = textVoornaamKind.getText().toString();

            EditText textNaamKind = findViewById(R.id.naamInput);
            final String naam = textNaamKind.getText().toString();

            Switch actiefSwitch = findViewById(R.id.switchActief);
            final int isActief;
            if( actiefSwitch.isChecked()){
                isActief = 1;
            }else{
                isActief = 0;
            }

            if(voornaam.isEmpty() || naam.isEmpty()){
                Toast.makeText(getBaseContext(), "Geen voornaam of naam ingevuld!", Toast.LENGTH_SHORT).show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Wil je de gegevens van '" + selectedKind.toString() + "' wijzigen?")

                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Kind newKind = selectedKind;
                                newKind.setVoornaam(voornaam);
                                newKind.setNaam(naam);
                                newKind.setActief(isActief);
                                newKind.setGroepId((int)selectedGroep.getId());
                                newKind.setKlasId((int)selectedKlas.getId());

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
        }
    }

    public void kindVerwijderen_onClick(View v){

        if(selectedKind == null){
            Toast.makeText(getBaseContext(), "Geen kind geselecteerd om te verwijderen", Toast.LENGTH_SHORT).show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ben je zeker dat je '" + selectedKind.toString() + "' wilt verwijderen?")

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

    public void goTerug_onClick(View v) {
        Intent intent = new Intent(this, BeherenKlassenActivity.class);
        finish();
        startActivity(intent);
    }
}
