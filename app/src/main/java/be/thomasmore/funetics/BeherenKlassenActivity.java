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
import android.widget.Toast;

import java.util.List;

public class BeherenKlassenActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Klas selectedKlas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beheren_klassen);

        db = new DatabaseHelper(this);

        leesKlassen();
    }

    private void leesKlassen(){
        final List<Klas> klassen = db.getKlassen();

        ArrayAdapter<Klas> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, klassen);

        final ListView listViewKlassen =
                findViewById(R.id.listViewKlassen);
        listViewKlassen.setAdapter(adapter);

        listViewKlassen.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView,
                                            View childView, int position, long id) {
                        selectedKlas = klassen.get(position);
                        int selectedKlasId = (int) selectedKlas.getId();
                        leesKlasDetail(selectedKlasId);
                    }
                });
    }

    private void leesKlasDetail(long klasId){

        final Klas huidigKlas = db.getKlasById(klasId);

        EditText textNaamKlas = findViewById(R.id.naamInput);
        textNaamKlas.setText(huidigKlas.getNaam());
    }

    public void kinderenBeheren_onClick(View v) {
        if (selectedKlas == null){
            Toast.makeText(getBaseContext(), "Geen klas geselecteerd om te tonen", Toast.LENGTH_SHORT).show();
        } else {
            long selectedKlasId = selectedKlas.getId();

            Bundle bundle = new Bundle();
            bundle.putLong("klasId", selectedKlasId);

            Intent intent = new Intent(this, BeherenActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    public void klasToevoegen_onClick(View v){

        EditText textNaamKlas = findViewById(R.id.naamInput);
        final String naam = textNaamKlas.getText().toString();

        if(naam.isEmpty()){
            Toast.makeText(getBaseContext(), "Geen naam ingevuld!", Toast.LENGTH_SHORT).show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Wil je '" + naam + "' toevoegen?")

                    .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Klas newKlas = new Klas();
                            newKlas.setNaam(naam);

                            db.insertKlas(newKlas);

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

    public void klasWijzigen_onClick(View v){

        if(selectedKlas == null) {
            Toast.makeText(getBaseContext(), "Geen klas geselecteerd om te wijzigen", Toast.LENGTH_SHORT).show();
        }else{
            EditText textNaamKlas = findViewById(R.id.naamInput);
            final String naam = textNaamKlas.getText().toString();

            if(naam.isEmpty()){
                Toast.makeText(getBaseContext(), "Geen naam ingevuld!", Toast.LENGTH_SHORT).show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Wil je de klasnaam van '" + selectedKlas.getNaam() + "' wijzigen naar '" + naam +"'?")

                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Klas newKlas = selectedKlas;
                                newKlas.setNaam(naam);

                                db.updateKlas(newKlas);

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

    public void klasVerwijderen_onClick(View v){

        if(selectedKlas == null){
            Toast.makeText(getBaseContext(), "Geen klas geselecteerd om te verwijderen", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ben je zeker dat je '" + selectedKlas.getNaam() + "' wilt verwijderen? Alle kinderen in de klas zullen mee verwijderd worden.")

                    .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            db.deleteKlas(selectedKlas.getId());

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
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
