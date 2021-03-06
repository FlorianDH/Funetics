package be.thomasmore.funetics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Klas selectedKlas = null;
    private Groep selectedGroep = null;
    private Kind selectedKind = null;
    private int selectedKlasId = 0;
    private int selectedGroepId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        leesKlassenEnGroepen();
    }

    private void leesKlassenEnGroepen(){
        final List<Klas> klassen = db.getKlassen();
        final List<Groep> groepen = db.getGroepen();

        Spinner spinnerKlas = (Spinner) findViewById(R.id.spinnerKlas);
        Spinner spinnerGroep = (Spinner) findViewById(R.id.spinnerGroep);

        spinnerKlas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
                selectedKlas = klassen.get(position);
                 selectedKlasId = (int) selectedKlas.getId();
//                selectedGroep = groepen.get(position);
//                int selectedGroepId = (int) selectedGroep.getId();
                leesKinderenWhereKlasEnGroep(selectedKlasId, selectedGroepId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedKlas = null;
                selectedGroep = null;
            }
        });

        spinnerGroep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
//                selectedKlas = klassen.get(position);
//                int selectedKlasId = (int) selectedKlas.getId();
                selectedGroep = groepen.get(position);
                selectedGroepId = (int) selectedGroep.getId();
                leesKinderenWhereKlasEnGroep(selectedKlasId, selectedGroepId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedKlas = null;
                selectedGroep = null;
            }
        });

        ArrayAdapter aaKlas = new ArrayAdapter(this,android.R.layout.simple_spinner_item, klassen);
        aaKlas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerKlas.setAdapter(aaKlas);

        ArrayAdapter aaGroep = new ArrayAdapter(this,android.R.layout.simple_spinner_item, groepen);
        aaGroep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerGroep.setAdapter(aaGroep);
    }

//    private void leesGroepen(){
//        final List<Groep> groepen = db.getGroepen();
//
//        Spinner spinner = (Spinner) findViewById(R.id.spinnerGroep);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
//                selectedGroep = groepen.get(position);
//                int selectedGroepId = (int) selectedGroep.getId();
//                leesKinderenWhereGroep(selectedGroepId);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                selectedGroep = null;
//            }
//        });
//
//        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, groepen);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        spinner.setAdapter(aa);
//    }

//    private void leesKinderen(){
//        final List<Kind> kinderen = db.getKinderen(1);
//
//        Spinner spinner = (Spinner) findViewById(R.id.spinnerKind);
//
//        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, kinderen);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        spinner.setAdapter(aa);
//    }

    private void leesKinderenWhereKlasEnGroep(int klasId, int groepId){
        final List<Kind> kinderen = db.getKinderenWhereKlasIdAndGroepId(klasId, groepId, 1);

        if (kinderen.isEmpty()){
            selectedKind = null;
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerKind);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
                selectedKind = kinderen.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedKind = null;
            }
        });

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, kinderen);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
    }

    public void goBeheren_onClick(View v) {
        Intent intent = new Intent(this, BeherenKlassenActivity.class);
        startActivity(intent);
    }

    public void goDetail_onClick(View v) {
        if (selectedKlas == null){
            showErrorDialog(1);
        } else if (selectedKind == null){
            showErrorDialog(2);
        }
        else {
            long selectedKindId = selectedKind.getId();

            Bundle bundle = new Bundle();
            bundle.putLong("kindId", selectedKindId);

            Intent intent = new Intent(this, DetailKindActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    private void showErrorDialog(final int melding){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.errordialog_message)
                .setPositiveButton(R.string.dialog_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int whichButton) {
                                switch (melding){
                                    case 1:
                                        Toast.makeText(getBaseContext(), getString(R.string.dialog_kies_klas),
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        Toast.makeText(getBaseContext(), getString(R.string.dialog_kies_kind),
                                                Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
