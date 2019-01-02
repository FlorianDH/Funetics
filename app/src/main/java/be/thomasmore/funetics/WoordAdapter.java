package be.thomasmore.funetics;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
        final TextView textViewOef6Naam = (TextView) rowView.findViewById(R.id.textViewOef6);
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
            textViewVoormeting.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        }
        else {
            if (oefening0.getScore() == 1){
                textViewVoormeting.setText("Juist");
                textViewVoormeting.setTextColor(ContextCompat.getColor(context, R.color.color_green));
            }
            else {
                textViewVoormeting.setText("Fout");
                textViewVoormeting.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
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
            textViewOef1.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        }
        else {
            if (oefening1.getScore() == 1){
                textViewOef1.setText("Voltooid");
                textViewOef1.setTextColor(ContextCompat.getColor(context, R.color.color_green));
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
            textViewOef2.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        }
        else {
            if (oefening2.getScore() == 1){
                textViewOef2.setText("Juiste uitspraak");
                textViewOef2.setTextColor(ContextCompat.getColor(context, R.color.color_green));
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
            textViewOef3.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        }
        else {
            textViewOef3.setText(oefening3.getScore() + " / " + oefening3.getAantalPogingen());
            if (oefening3.getScore() == 2){
                textViewOef3.setTextColor(ContextCompat.getColor(context, R.color.color_green));
            }
            else {
                textViewOef3.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
            }
        }

        //Oefening 4
        GetesteOefening oefening4 = null;
        try {
            oefening4 = oefeningen.get(4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oefening4 == null){
            textViewOef4.setText("Niet voltooid");
            textViewOef4.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        }
        else {
            textViewOef4.setText(oefening4.getScore() + " / " + oefening4.getAantalPogingen());
            if (oefening4.getAantalPogingen() > 1){
                textViewOef3.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
            }
            else {
                textViewOef3.setTextColor(ContextCompat.getColor(context, R.color.color_green));
            }
        }

        //Oefening 5
        GetesteOefening oefening5 = null;
        try {
            oefening5 = oefeningen.get(5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oefening5 == null){
            textViewOef5.setText("Niet voltooid");
            textViewOef5.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        }
        else {
            textViewOef5.setText(oefening5.getScore() + " / " + oefening5.getAantalPogingen());
            if (oefening5.getAantalPogingen() > 1){
                textViewOef5.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
            }
            else {
                textViewOef5.setTextColor(ContextCompat.getColor(context, R.color.color_green));
            }
        }

        //Oefening 6
        GetesteOefening oefening6 = null;
        try {
            oefening6 = oefeningen.get(6);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oefening6 == null){
            textViewOef6.setText("Niet voltooid");
            textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        }
        else {
            //Oefening 6.1
            if (oefening6.getOefeningId() == 6){
                textViewOef6Naam.setText("Oefening 6.1: ");
                if (oefening6.getScore() == 1){
                    textViewOef6.setText("Goed gevoel");
                    textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                }
                else {
                    textViewOef6.setText("Slecht gevoel");
                    textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
                }
            }
            //Oefening 6.2
            else if (oefening6.getOefeningId() == 7){
                textViewOef6Naam.setText("Oefening 6.2: ");
                textViewOef6.setText("Voltooid");
                textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_green));
            }
            //Oefening 6.3
            else if (oefening6.getOefeningId() == 8){
                textViewOef6Naam.setText("Oefening 6.3: ");
                textViewOef6.setText("Voltooid");
                textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_green));
            }
        }

        //Nameting
        GetesteOefening oefening7 = null;
        try {
            oefening7 = oefeningen.get(7);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oefening7 == null){
            textViewOefNameting.setText("Niet voltooid");
            textViewOefNameting.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        }
        else {
            if (oefening7.getScore() == 1){
                textViewOefNameting.setText("Juist");
                textViewOefNameting.setTextColor(ContextCompat.getColor(context, R.color.color_green));
            }
            else {
                textViewOefNameting.setText("Fout");
                textViewOefNameting.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
            }
        }

        return rowView;
    }
}
