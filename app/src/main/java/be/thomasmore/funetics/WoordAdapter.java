package be.thomasmore.funetics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
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
        Doelwoord doelwoord = db.getDoelwoordById(getestWoord.getDoelwoordId());

        textViewWoord.setText(doelwoord.getNaam());

        //Alle oefeningen bij het woord ophalen
        List<GetesteOefening> oefeningen = new ArrayList<GetesteOefening>();
        oefeningen = db.getGetesteOefeningenWhereGetestWoordId((int) getestWoord.getId());

        //Alle resultaten bij de juiste oefening zetten
        //Voormeting
        GetesteOefening oefening0 = null;
        try {
            oefening0 = oefeningen.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oefening0 == null){
            textViewVoormeting.setText("Niet voltooid");
        }
        else {
            if (oefening0.getScore() == 1){
                textViewVoormeting.setText("Juist");
            }
            else {
                textViewVoormeting.setText("Fout");
            }
        }

        //Oefening 1
        GetesteOefening oefening1 = null;
        try {
            oefening1 = oefeningen.get(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oefening1 == null){
            textViewOef1.setText("Niet voltooid");
        }
        else {
            if (oefening1.getScore() == 1){
                textViewOef1.setText("Voltooid");
            }
        }

        //Oefening 2
        GetesteOefening oefening2 = null;
        try {
            oefening2 = oefeningen.get(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oefening2 == null){
            textViewOef2.setText("Niet voltooid");
            //textViewOef2.setTextColor(getResources().getColor(R.color.color_red));
        }
        else {
            if (oefening2.getScore() == 1){
                textViewOef2.setText("Juiste uitspraak");
            }
            else if (oefening2.getScore() == 0 && oefening2.getAantalPogingen() == 1){
                textViewOef2.setText("Foute uitspraak");
            }
        }

        //Oefening 3
        GetesteOefening oefening3 = null;
        try {
            oefening3 = oefeningen.get(3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oefening3 == null){
            textViewOef3.setText("Niet voltooid");
            //textViewOef3.setTextColor(getResources().getColor(R.color.color_red));
        }
        else {
            textViewOef3.setText(oefening3.getScore() + " / " + oefening3.getAantalPogingen());
        }

        return rowView;
    }
}
