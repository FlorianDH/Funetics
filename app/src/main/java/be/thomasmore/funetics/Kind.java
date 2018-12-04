package be.thomasmore.funetics;

public class Kind {
    private long id;
    private String voornaam;
    private String naam;
    private int leeftijd;
    private int actief;
    private int groepId;

    public Kind() {
    }

    public Kind(long id, String voornaam, String naam, int leeftijd, int actief, int groepId) {
        this.id = id;
        this.voornaam = voornaam;
        this.naam = naam;
        this.leeftijd = leeftijd;
        this.actief = actief;
        this.groepId = groepId;
    }

    public Kind(long id, String voornaam, String naam, int actief, int groepId) {
        this.id = id;
        this.voornaam = voornaam;
        this.naam = naam;
        this.actief = actief;
        this.groepId = groepId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public void setLeeftijd(int leeftijd) {
        this.leeftijd = leeftijd;
    }

    public int getActief() {
        return actief;
    }

    public void setActief(int actief) {
        this.actief = actief;
    }

    public int getGroepId() {
        return groepId;
    }

    public void setGroepId(int groepId) {
        this.groepId = groepId;
    }

    @Override
    public String toString () {
        return voornaam + " " + naam;
    }
}
