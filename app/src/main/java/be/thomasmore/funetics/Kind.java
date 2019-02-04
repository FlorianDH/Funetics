package be.thomasmore.funetics;

public class Kind {
    private long id;
    private String voornaam;
    private String naam;
    private int actief;
    private int groepId;
    private int klasId;

    public Kind() {
    }

    public Kind(long id, String voornaam, String naam, int actief, int groepId, int klasId) {
        this.id = id;
        this.voornaam = voornaam;
        this.naam = naam;
        this.actief = actief;
        this.groepId = groepId;
        this.klasId = klasId;
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


    public int getKlasId() {
        return klasId;
    }

    public void setKlasId(int klasId) {
        this.klasId = klasId;
    }

    @Override
    public String toString () {
        return voornaam + " " + naam;
    }
}
