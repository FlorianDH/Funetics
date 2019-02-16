package be.thomasmore.funetics;

import android.app.Activity;
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

        //Textviews van de resultaten definieren
        final TextView textViewVoormeting = (TextView) rowView.findViewById(R.id.textViewVoormetingResultaat);
        final TextView textViewOef1 = (TextView) rowView.findViewById(R.id.textViewOef1Resultaat);
        final TextView textViewOef2 = (TextView) rowView.findViewById(R.id.textViewOef2Resultaat);
        final TextView textViewOef3 = (TextView) rowView.findViewById(R.id.textViewOef3Resultaat);
        final TextView textViewOef4 = (TextView) rowView.findViewById(R.id.textViewOef4Resultaat);
        final TextView textViewOef5 = (TextView) rowView.findViewById(R.id.textViewOef5Resultaat);
        final TextView textViewOef6 = (TextView) rowView.findViewById(R.id.textViewOef6Resultaat);
        final TextView textViewOef6Naam = (TextView) rowView.findViewById(R.id.textViewOef6);
        final TextView textViewOefNameting = (TextView) rowView.findViewById(R.id.textViewNametingResultaat);

        //Textviews van de resultaten default op niet voltooid zetten
        textViewVoormeting.setText("Niet voltooid");
        textViewVoormeting.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        textViewOef1.setText("Niet voltooid");
        textViewOef1.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        textViewOef2.setText("Niet voltooid");
        textViewOef2.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        textViewOef3.setText("Niet voltooid");
        textViewOef3.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        textViewOef4.setText("Niet voltooid");
        textViewOef4.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        textViewOef5.setText("Niet voltooid");
        textViewOef5.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        textViewOef6.setText("Niet voltooid");
        textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));
        textViewOefNameting.setText("Niet voltooid");
        textViewOefNameting.setTextColor(ContextCompat.getColor(context, R.color.color_red_dark));

        db = new DatabaseHelper(context);
        GetestWoord getestWoord = values.get(position);
        Doelwoord doelwoord = db.getDoelwoordById(getestWoord.getDoelwoordId());

        //Oefenwoord zetten achter duibril
        if (doelwoord.getId() == 0){
            textViewWoord.setText(doelwoord.getNaam() + " (oefenwoord)");
        }
        else {
            textViewWoord.setText(doelwoord.getNaam());
        }

        //Alle oefeningen bij het woord ophalen
        List<GetesteOefening> oefeningen = new ArrayList<GetesteOefening>();
        oefeningen = db.getGetesteOefeningenWhereGetestWoordId((int) getestWoord.getId());

        //Alle resultaten bij de juiste oefening zetten
        for (GetesteOefening getesteOefening: oefeningen){
            switch (getesteOefening.getOefeningId()){
                case 0:
                    //Voormeting
                        if (getesteOefening.getScore() == 1){
                            textViewVoormeting.setText("Juist");
                            textViewVoormeting.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                        }
                        else {
                            textViewVoormeting.setText("Fout");
                            textViewVoormeting.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
                        }
                        break;
                case 1:
                    //Oefening 1
                        if (getesteOefening.getScore() == 1){
                            textViewOef1.setText("Voltooid");
                            textViewOef1.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                        }
                        break;
                case 2:
                    //Oefening 2
                    if (getesteOefening.getScore() == 1){
                        textViewOef2.setText("Juiste uitspraak");
                        textViewOef2.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                    }
                    else if (getesteOefening.getScore() == 0 && getesteOefening.getAantalPogingen() == 1){
                        textViewOef2.setText("Foute uitspraak");
                        textViewOef2.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
                    }
                    break;
                case 3:
                    //Oefening 3
                    textViewOef3.setText(getesteOefening.getScore() + " / " + getesteOefening.getAantalPogingen());
                    if (getesteOefening.getScore() == 2){
                        textViewOef3.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                    }
                    else {
                        textViewOef3.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
                    }
                    break;
                case 4:
                    //Oefening 4
                    textViewOef4.setText(getesteOefening.getScore() + " / " + getesteOefening.getAantalPogingen());
                    if (getesteOefening.getAantalPogingen() > 1){
                        textViewOef4.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
                    }
                    else {
                        textViewOef4.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                    }
                    break;
                case 5:
                    //Oefening 5
                    textViewOef5.setText(getesteOefening.getScore() + " / " + getesteOefening.getAantalPogingen());
                    if (getesteOefening.getAantalPogingen() > 1){
                        textViewOef5.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
                    }
                    else {
                        textViewOef5.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                    }
                    break;
                case 6:
                    //Oefening 6.1
                        textViewOef6Naam.setText("Oefening 6.1: ");
                        if (getesteOefening.getScore() == 1){
                            textViewOef6.setText("Goed gevoel");
                            textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                        }
                        else {
                            textViewOef6.setText("Slecht gevoel");
                            textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
                        }
                    break;
                case 7:
                    //Oefening 6.2
                        textViewOef6Naam.setText("Oefening 6.2: ");
                        textViewOef6.setText("Voltooid");
                        textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                        break;
                case 8:
                    //Oefening 6.3
                    textViewOef6Naam.setText("Oefening 6.3: ");
                    textViewOef6.setText("Voltooid");
                    textViewOef6.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                    break;
                case 9:
                    //Nameting
                    if (getesteOefening.getScore() == 1){
                        textViewOefNameting.setText("Juist");
                        textViewOefNameting.setTextColor(ContextCompat.getColor(context, R.color.color_green));
                    }
                    else {
                        textViewOefNameting.setText("Fout");
                        textViewOefNameting.setTextColor(ContextCompat.getColor(context, R.color.color_orange_dark));
                    }
                default:
                    break;
            }
        }
        return rowView;
    }
}
