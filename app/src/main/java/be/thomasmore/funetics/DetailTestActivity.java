package be.thomasmore.funetics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class DetailTestActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Test huidigeTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_test);

        Bundle bundle = getIntent().getExtras();
        Long testId = bundle.getLong("testId");

        db = new DatabaseHelper(this);

        huidigeTest = db.getTestById(testId);
    }
}
