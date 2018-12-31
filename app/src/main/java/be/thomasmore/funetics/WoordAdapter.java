package be.thomasmore.funetics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WoordAdapter extends ArrayAdapter<GetestWoord> {
    private final Context context;
    private final List<GetestWoord> values;

    private DatabaseHelper db;

    public WoordAdapter(Context context, List<GetestWoord> values) {
        super(context, R.layout.woordlistviewitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.woordlistviewitem, parent, false);

        final TextView textViewWoord = (TextView) rowView.findViewById(R.id.textViewWoord);

        final TextView textViewVoormeting = (TextView) rowView.findViewById(R.id.textViewVoormetingResultaat);
        final TextView textViewOef1 = (TextView) rowView.findViewById(R.id.textViewOef1Resultaat);
        final TextView textViewOef2 = (TextView) rowView.findViewById(R.id.textViewOef2Resultaat);
        final TextView textViewOef3 = (TextView) rowView.findViewById(R.id.textViewOef3Resultaat);
        final TextView textViewOef4 = (TextView) rowView.findViewById(R.id.textViewOef4Resultaat);
        final TextView textViewOef5 = (TextView) rowView.findViewById(R.id.textViewOef5Resultaat);
        final TextView textViewOef6 = (TextView) rowView.findViewById(R.id.textViewOef6Resultaat);
        final TextView textViewOefNameting = (TextView) rowView.findViewById(R.id.textViewNametingResultaat);

        db = new DatabaseHelper(context);
        GetestWoord getestWoord = values.get(position);
        Doelwoord doelwoord = db.getDoelwoordById(values.get(position).getDoelwoordId());

        textViewWoord.setText(doelwoord.getNaam());

        return rowView;
    }
}
