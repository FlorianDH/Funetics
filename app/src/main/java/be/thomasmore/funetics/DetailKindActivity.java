package be.thomasmore.funetics;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DetailKindActivity extends Activity {

    private DatabaseHelper db;
    private Kind huidigKind;
    private Test selectedTest = null;
    private int selectedConditie = 0;
    private List<Test> testen;
    private Long kindId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kind);

        Bundle bundle = getIntent().getExtras();
        kindId = bundle.getLong("kindId");

        db = new DatabaseHelper(this);

        huidigKind = db.getKindById(kindId);

        TextView textNaamKind = (TextView) findViewById(R.id.naamKind);
        textNaamKind.setText(huidigKind.toString());
        TextView textNaamKlas = (TextView) findViewById(R.id.naamKlas);
        textNaamKlas.setText(db.getKlasById(huidigKind.getKlasId()).getNaam());
        TextView textNaamGroep = (TextView) findViewById(R.id.naamGroep);
        textNaamGroep.setText(db.getGroepById(huidigKind.getGroepId()).getNaam());

        TextView textVoormetingText = (TextView) findViewById(R.id.voormetingGedaanText);
        if(huidigKind.getVoormetingId() == 0){
            textVoormetingText.setText("Heeft geen voormeting gedaan");
            textVoormetingText.setTextColor(ContextCompat.getColor(this, R.color.color_orange_dark));
        }else{
            textVoormetingText.setText("Heeft voormeting gedaan");
            textVoormetingText.setTextColor(ContextCompat.getColor(this, R.color.color_green));

        }

        leesTesten();
    }

    //Lees testen opnieuw in wanneer activity hervat wordt
    @Override
    public void onResume(){
        super.onResume();
        leesTesten();
    }

    public void goTerug_onClick(View v) {
        finish();
    }

    public void goStartTest_onClick(View v){
        beginTest();
    }

    public void beginTest(){
        Bundle bundle = new Bundle();
        bundle.putLong("kindId", huidigKind.getId());

        Intent intent = new Intent(this, OefeningActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void leesTesten(){
        testen = db.getTestenWhereKindId((int) huidigKind.getId());

        TestAdapter testAdapter =
                new TestAdapter(getApplicationContext(), testen);

        final ListView listViewTesten =
                (ListView) findViewById(R.id.listViewTesten);
        listViewTesten.setAdapter(testAdapter);

        listViewTesten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parentView, View childView, int position, long id) {
                selectedTest = testen.get(position);
                long selectedTestId = selectedTest.getId();
                leesTestDetail(selectedTestId);
            }
        });

        huidigKind = db.getKindById(kindId);

        TextView textVoormeting2 = (TextView) findViewById(R.id.voormetingGedaanText);
        if(huidigKind.getVoormetingId() == 0){
            textVoormeting2.setText("Heeft geen voormeting gedaan");
            textVoormeting2.setTextColor(ContextCompat.getColor(this, R.color.color_orange_dark));
        }else{
            textVoormeting2.setText("Heeft voormeting gedaan");
            textVoormeting2.setTextColor(ContextCompat.getColor(this, R.color.color_green));

        }
    }

    private void leesTestDetail(long testId){
        Bundle bundle = new Bundle();
        bundle.putLong("testId", testId);

        Intent intent = new Intent(this, DetailTestActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
