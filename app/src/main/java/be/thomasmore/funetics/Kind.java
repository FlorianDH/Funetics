package be.thomasmore.funetics;

public class Kind {
    private long id;
    private String voornaam;
    private String naam;
    private int actief;
    private int klasId;
    private int groepId;
    private int voormetingId;
    private int nametingId;

    public Kind() {
    }

    public Kind(long id, String voornaam, String naam, int actief, int klasId, int groepId, int voormetingId, int nametingId) {
        this.id = id;
        this.voornaam = voornaam;
        this.naam = naam;
        this.actief = actief;
        this.klasId = klasId;
        this.groepId = groepId;
        this.voormetingId = voormetingId;
        this.nametingId = nametingId;
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

    public int getVoormetingId() {
        return voormetingId;
    }

    public void setVoormetingId(int voormetingId) {
        this.voormetingId = voormetingId;
    }

    public int getNametingId() {
        return nametingId;
    }

    public void setNametingId(int nametingId) {
        this.nametingId = nametingId;
    }

    @Override
    public String toString () {
        return voornaam + " " + naam;
    }
}
