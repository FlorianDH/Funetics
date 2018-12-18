package be.thomasmore.funetics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.List;

public class TestAdapter extends ArrayAdapter<Test> {
    private final Context context;
    private final List<Test> values;

    private DatabaseHelper db;

    public TestAdapter(Context context, List<Test> values) {
        super(context, R.layout.testlistviewitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.testlistviewitem, parent, false);

        final TextView textViewConditie = (TextView) rowView.findViewById(R.id.textViewConditie);
        final TextView textViewDatum = (TextView) rowView.findViewById(R.id.textViewDatum);

        db = new DatabaseHelper(context);
        Conditie conditie = db.getConditieById(values.get(position).getConditieId());

        textViewConditie.setText(conditie.getNaam());
        textViewDatum.setText(values.get(position).getDatumTijd());

        return rowView;
    }
}
