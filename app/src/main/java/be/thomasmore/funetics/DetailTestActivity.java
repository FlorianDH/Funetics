package be.thomasmore.funetics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class DetailTestActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Test huidigeTest;
    private Conditie huidigeConditie;
    private Kind huidigKind;

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
    }

    public void goTerug_onClick(View v) {
        finish();
    }

    public void buttonDelete_onClick(View v) {
        finish();
    }
}
