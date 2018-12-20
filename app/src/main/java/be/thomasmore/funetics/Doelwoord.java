package be.thomasmore.funetics;

public class Doelwoord {
    private long id;
    private String naam;
    private String defenitie;
    private String juisteZin;
    private String fouteZin;
    private String keuzeWeb;
    private int woordensetId;

    public Doelwoord() {
    }

    public Doelwoord(long id, String naam, String defenitie, String juisteZin, String fouteZin, String keuzeWeb, int woordensetId) {
        this.id = id;
        this.naam = naam;
        this.defenitie = defenitie;
        this.juisteZin = juisteZin;
        this.fouteZin = fouteZin;
        this.keuzeWeb = keuzeWeb;
        this.woordensetId = woordensetId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getDefenitie() {
        return defenitie;
    }

    public void setDefenitie(String defenitie) {
        this.defenitie = defenitie;
    }

    public String getJuisteZin() {
        return juisteZin;
    }

    public void setJuisteZin(String juisteZin) {
        this.juisteZin = juisteZin;
    }

    public String getFouteZin() {
        return fouteZin;
    }

    public void setFouteZin(String fouteZin) {
        this.fouteZin = fouteZin;
    }

    public String getKeuzeWeb() {
        return keuzeWeb;
    }

    public void setKeuzeWeb(String keuzeWeb) {
        this.keuzeWeb = keuzeWeb;
    }

    public int getWoordensetId() {
        return woordensetId;
    }

    public void setWoordensetId(int woordensetId) {
        this.woordensetId = woordensetId;
    }

    @Override
    public String toString () {
        return naam;
    }
}
