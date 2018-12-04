package be.thomasmore.funetics;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class DetailKindActivity extends Activity {

    private DatabaseHelper db;
    private Kind huidigKind;

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

}
