package be.thomasmore.funetics;

public class Doelwoord {
    private long id;
    private String naam;
    private int woordensetId;

    public Doelwoord() {
    }

    public Doelwoord(long id, String naam, int woordensetId) {
        this.id = id;
        this.naam = naam;
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
