package be.thomasmore.funetics;

public class GetestWoord {
    private long id;
    private int doelwoordId;
    private int testId;

    public GetestWoord() {
    }

    public GetestWoord(long id, int doelwoordId, int testId) {
        this.id = id;
        this.doelwoordId = doelwoordId;
        this.testId = testId;
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

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    @Override
    public String toString () {
        return "Getest woord";
    }
}
