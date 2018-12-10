package be.thomasmore.funetics;

public class Oefening {
    private long id;
    private String naam;

    public Oefening() {
    }

    public Oefening(long id, String naam) {
        this.id = id;
        this.naam = naam;
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

    @Override
    public String toString () {
        return naam;
    }
}
