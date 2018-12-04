package be.thomasmore.funetics;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        leesGroepen();
        leesKinderenWhereGroep(1);
    }

    private void leesGroepen(){
        final List<Groep> groepen = db.getGroepen();

        Spinner spinner = (Spinner) findViewById(R.id.spinnerGroep);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, groepen);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
    }

    private void leesKinderen(){
        final List<Kind> kinderen = db.getKinderen();

        Spinner spinner = (Spinner) findViewById(R.id.spinnerKind);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, kinderen);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
    }

    private void leesKinderenWhereGroep(int groepId){
        final List<Kind> kinderen = db.getKinderenWhereGroepId(groepId);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerKind);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, kinderen);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        return super.onOptionsItemSelected(item);
//    }

//    public void goDetail_onClick(View v) {
////        Bundle bundle = new Bundle();
////        bundle.putString("naam", "Jeff Hermans");
////        bundle.putLong("groep", 1);
//
//        Intent intent = new Intent(this, DetailActivity.class);
////        intent.putExtras(bundle);
//
//        startActivity(intent);
//
////        startActivityForResult(intent, 1);  // 1 is requestCode
//    }

    public void goBeheren_onClick(View v) {
        Intent intent = new Intent(this, BeherenActivity.class);

        startActivity(intent);

//        startActivityForResult(intent, 1);  // 1 is requestCode
    }
}
