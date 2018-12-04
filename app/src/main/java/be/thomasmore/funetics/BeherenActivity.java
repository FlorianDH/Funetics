package be.thomasmore.funetics;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

        leesGroepen();
        leesKinderenWhereGroep(0);
    }

    private void leesGroepen(){
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

//        listViewGroepen.setOnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
//                int selectedGroep = (int) groepen.get(position).getId();
//                leesKinderenWhereGroep(selectedGroep);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//        });

//        ArrayAdapter<Groep> aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1, groepen);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
//        listViewGroepen.setAdapter(aa);
    }

    private void leesKinderenWhereGroep(int groepId){

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

        TextView textNaamKind = (TextView) findViewById(R.id.naamKind);
        textNaamKind.setText(huidigKind.toString());

        TextView textGroepKind = (TextView) findViewById(R.id.naamKind);
        textNaamKind.setText(huidigKind.toString());
    }
}
