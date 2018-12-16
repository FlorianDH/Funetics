package be.thomasmore.funetics;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class DetailKindActivity extends Activity {

    private DatabaseHelper db;
    private Kind huidigKind;
    private Test selectedTest = null;
    private int selectedConditie = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kind);

        Bundle bundle = getIntent().getExtras();
        Long kindId = bundle.getLong("kindId");

        db = new DatabaseHelper(this);

        huidigKind = db.getKindById(kindId);

        TextView textNaamKind = (TextView) findViewById(R.id.naamKind);
        textNaamKind.setText(huidigKind.toString());
    }

    public void goTerug_onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void goStartTest_onClick(View v){
        showConditieDialog();
        //tot hier
    }

    private void showConditieDialog() {
        final String[] items = { "Conditie 1", "Conditie 2", "Conditie 3" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecteer de conditie die u wilt testen")
                .setSingleChoiceItems(items, 0, null)
                .setPositiveButton(R.string.dialog_start, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        selectedConditie = ((AlertDialog)dialog).getListView().getCheckedItemPosition() + 1; //conditie 1 geeft als waarde ook 1
                        beginTest();
                    }
                })
                .setNegativeButton(R.string.dialog_annuleer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void beginTest(){
        Bundle bundle = new Bundle();
        bundle.putLong("kindId", huidigKind.getId());
        bundle.putLong("conditie", selectedConditie);

        Intent intent = new Intent(this, OefeningActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void leesTestenWhereKind(int kindId){

        List<Test> testenLijst;

        testenLijst = db.getTestenWhereKindId(kindId);

        final List<Test> testen = testenLijst;

        final ListView listViewTesten = (ListView) findViewById(R.id.listViewTesten);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1, testen);
        listViewTesten.setAdapter(aa);

        listViewTesten.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView,
                                            View childView, int position, long id) {
                        selectedTest = testen.get(position);
                        long selectedTestId = (long) selectedTest.getId();
                        leesTestDetail(selectedTestId);
                    }
                });

    }

    private void leesTestDetail(long testId){

//        final Test huidigTest = db.getKindById(testId);
//
//        EditText textVoornaamKind = (EditText) findViewById(R.id.voornaamInput);
//        textVoornaamKind.setText(huidigKind.getVoornaam());
//
//        EditText textNaamKind = (EditText) findViewById(R.id.naamInput);
//        textNaamKind.setText(huidigKind.getNaam());
//
////        EditText textLeeftijdKind = (EditText) findViewById(R.id.leeftijdInput);
////        textLeeftijdKind.setText(huidigKind.getLeeftijd());
//
//        leesGroepenInSpinner();
//
//        Switch switchIsActief = (Switch) findViewById(R.id.switchActief);
//        if(huidigKind.getActief() == 1){
//            switchIsActief.setChecked(true);
//        }else{
//            switchIsActief.setChecked(false);
//        }
    }

}
