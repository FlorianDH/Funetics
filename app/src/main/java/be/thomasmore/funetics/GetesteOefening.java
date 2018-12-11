package be.thomasmore.funetics;

public class GetesteOefening {
    private long id;
    private int score;
    private int aantalPogingen;
    private int oefeningId;
    private int getestWoordId;

    public GetesteOefening() {
    }

    public GetesteOefening(long id, int score, int aantalPogingen, int oefeningId, int getestWoordId) {
        this.id = id;
        this.score = score;
        this.aantalPogingen = aantalPogingen;
        this.oefeningId = oefeningId;
        this.getestWoordId = getestWoordId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAantalPogingen() {
        return aantalPogingen;
    }

    public void setAantalPogingen(int aantalPogingen) {
        this.aantalPogingen = aantalPogingen;
    }

    public int getOefeningId() {
        return oefeningId;
    }

    public void setOefeningId(int oefeningId) {
        this.oefeningId = oefeningId;
    }

    public int getGetestWoordId() {
        return getestWoordId;
    }

    public void setGetestWoordId(int getestWoordId) {
        this.getestWoordId = getestWoordId;
    }

    @Override
    public String toString () {
        return score + " / " + aantalPogingen;
    }
}
