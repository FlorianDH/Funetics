package be.thomasmore.funetics;

public class Test {
    private long id;
    private String datumTijd;
    private int kindId;
    private int conditieId;

    public Test() {
    }

    public Test(long id, String datumTijd, int kindId, int conditieId) {
        this.id = id;
        this.datumTijd = datumTijd;
        this.kindId = kindId;
        this.conditieId = conditieId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatumTijd() {
        return datumTijd;
    }

    public void setDatumTijd(String datumTijd) {
        this.datumTijd = datumTijd;
    }

    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    public int getConditieId() {
        return conditieId;
    }

    public void setConditieId(int conditieId) {
        this.conditieId = conditieId;
    }

    @Override
    public String toString () {
        return datumTijd;
    }
}
