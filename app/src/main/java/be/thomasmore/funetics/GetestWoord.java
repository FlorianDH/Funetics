package be.thomasmore.funetics;

public class GetestWoord {
    private long id;
    private int doelwoordId;

    public GetestWoord() {
    }

    public GetestWoord(long id, int doelwoordId) {
        this.id = id;
        this.doelwoordId = doelwoordId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDoelwoordId() {
        return doelwoordId;
    }

    public void setDoelwoordId(int doelwoordId) {
        this.doelwoordId = doelwoordId;
    }

    @Override
    public String toString () {
        return "Getest woord";
    }
}
