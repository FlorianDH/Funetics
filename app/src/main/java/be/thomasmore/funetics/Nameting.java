package be.thomasmore.funetics;

public class Nameting {

    private long id;
    private int duikbril;
    private int klimtouw;
    private int kroos;
    private int riet;
    private int val;
    private int kompas;
    private int steil;
    private int zwaan;
    private int kamp;
    private int zaklamp;

    public Nameting(){

    }

    public Nameting(long id, int duikbril, int klimtouw, int kroos, int riet, int val, int kompas, int steil, int zwaan, int kamp, int zaklamp) {
        this.id = id;
        this.duikbril = duikbril;
        this.klimtouw = klimtouw;
        this.kroos = kroos;
        this.riet = riet;
        this.val = val;
        this.kompas = kompas;
        this.steil = steil;
        this.zwaan = zwaan;
        this.kamp = kamp;
        this.zaklamp = zaklamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDuikbril() {
        return duikbril;
    }

    public void setDuikbril(int duikbril) {
        this.duikbril = duikbril;
    }

    public int getKlimtouw() {
        return klimtouw;
    }

    public void setKlimtouw(int klimtouw) {
        this.klimtouw = klimtouw;
    }

    public int getKroos() {
        return kroos;
    }

    public void setKroos(int kroos) {
        this.kroos = kroos;
    }

    public int getRiet() {
        return riet;
    }

    public void setRiet(int riet) {
        this.riet = riet;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getKompas() {
        return kompas;
    }

    public void setKompas(int kompas) {
        this.kompas = kompas;
    }

    public int getSteil() {
        return steil;
    }

    public void setSteil(int steil) {
        this.steil = steil;
    }

    public int getZwaan() {
        return zwaan;
    }

    public void setZwaan(int zwaan) {
        this.zwaan = zwaan;
    }

    public int getKamp() {
        return kamp;
    }

    public void setKamp(int kamp) {
        this.kamp = kamp;
    }

    public int getZaklamp() {
        return zaklamp;
    }

    public void setZaklamp(int zaklamp) {
        this.zaklamp = zaklamp;
    }
}
