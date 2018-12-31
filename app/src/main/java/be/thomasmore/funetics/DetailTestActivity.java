package be.thomasmore.funetics;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DetailTestActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Test huidigeTest;
    private Conditie huidigeConditie;
    private Kind huidigKind;
    private List<GetestWoord> woorden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_test);

        Bundle bundle = getIntent().getExtras();
        Long testId = bundle.getLong("testId");

        db = new DatabaseHelper(this);

        huidigeTest = db.getTestById(testId);
        huidigeConditie = db.getConditieById(huidigeTest.getConditieId());
        huidigKind = db.getKindById(huidigeTest.getKindId());

        String datumTijd = huidigeTest.getDatumTijd();
        String[] values = datumTijd.split(" ");

        TextView textKindTest = (TextView) findViewById(R.id.kindTest);
        textKindTest.setText(huidigKind.toString());
        TextView textDatumTest = (TextView) findViewById(R.id.datumTest);
        textDatumTest.setText(values[0]);
        TextView textUurTest = (TextView) findViewById(R.id.uurTest);
        textUurTest.setText(values[1]);
        TextView textConditieTest = (TextView) findViewById(R.id.conditieTest);
        textConditieTest.setText(huidigeConditie.getNaam());

        leesWoorden();
    }

    public void goTerug_onClick(View v) {
        finish();
    }

    public void buttonDelete_onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ben je zeker dat je deze test definitief wilt verwijderen?")

                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.deleteTest(huidigeTest.getId());
                        finish();
                    }
                })
                .setNegativeButton(R.string.dialog_annuleer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void leesWoorden(){
        woorden = db.getGetesteWoordenWhereTestId((int) huidigeTest.getId());

        WoordAdapter woordAdapter =
                new WoordAdapter(getApplicationContext(), woorden);

        final ListView listViewWoorden = (ListView) findViewById(R.id.listViewGetesteWoorden);
        listViewWoorden.setAdapter(woordAdapter);
    }
}
