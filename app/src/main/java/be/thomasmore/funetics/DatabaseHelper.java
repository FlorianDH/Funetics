package be.thomasmore.funetics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 12;
    // Database Name
    private static final String DATABASE_NAME = "funetics";

    // uitgevoerd bij instantiatie van DatabaseHelper
    // -> als database nog niet bestaat, dan creëren (call onCreate)
    // -> als de DATABASE_VERSION hoger is dan de huidige versie,
    //    dan upgraden (call onUpgrade)

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // methode wordt uitgevoerd als de database gecreëerd wordt
    // hierin de tables creëren
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_KLAS = "CREATE TABLE klas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "naam TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_KLAS);

        String CREATE_TABLE_GROEP = "CREATE TABLE groep (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "naam TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_GROEP);

        String CREATE_TABLE_KIND = "CREATE TABLE kind (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "voornaam TEXT NOT NULL," +
                "naam TEXT NOT NULL, " +
                "actief INTEGER, " +
                "klasId INTEGER NOT NULL, " +
                "groepId INTEGER NOT NULL, " +
                "voormetingId INTEGER DEFAULT 0, " +
                "FOREIGN KEY (klasId) REFERENCES klas(id)," +
                "FOREIGN KEY (groepId) REFERENCES groep(id)," +
                "FOREIGN KEY (voormetingId) REFERENCES voormeting(id))";

                db.execSQL(CREATE_TABLE_KIND);

        String CREATE_TABLE_VOORMETING = "CREATE TABLE voormeting (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "duikbril INTEGER NOT NULL, " +
                "klimtouw INTEGER NOT NULL, " +
                "kroos INTEGER NOT NULL, " +
                "riet INTEGER NOT NULL, " +
                "val INTEGER NOT NULL, " +
                "kompas INTEGER NOT NULL, " +
                "steil INTEGER NOT NULL, " +
                "zwaan INTEGER NOT NULL, " +
                "kamp INTEGER NOT NULL, " +
                "zaklamp INTEGER NOT NULL)";
        db.execSQL(CREATE_TABLE_VOORMETING);

        String CREATE_TABLE_TEST = "CREATE TABLE test (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "datumTijd TEXT NOT NULL," +
                "kindId INTEGER NOT NULL, " +
                "conditieId INTEGER NOT NULL, " +
                "FOREIGN KEY (kindId) REFERENCES kind(id), " +
                "FOREIGN KEY (conditieId) REFERENCES conditie(id))";
        db.execSQL(CREATE_TABLE_TEST);

        String CREATE_TABLE_CONDITIE = "CREATE TABLE conditie (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "naam TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_CONDITIE);

        String CREATE_TABLE_GETESTWOORD = "CREATE TABLE getestWoord (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "doelwoordId INTEGER NOT NULL, " +
                "testId INTEGER NOT NULL, " +
                "FOREIGN KEY (doelwoordId) REFERENCES doelwoord(id), " +
                "FOREIGN KEY (testId) REFERENCES test(id))";
        db.execSQL(CREATE_TABLE_GETESTWOORD);

        String CREATE_TABLE_DOELWOORD = "CREATE TABLE doelwoord (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "naam TEXT NOT NULL," +
                "defenitie TEXT NOT NULL," +
                "juisteZin TEXT NOT NULL," +
                "fouteZin TEXT NOT NULL," +
                "keuzeWeb TEXT NOT NULL," +
                "woordensetId INTEGER NOT NULL, " +
                "FOREIGN KEY (woordensetId) REFERENCES woordenset(id))";
        db.execSQL(CREATE_TABLE_DOELWOORD);

        String CREATE_TABLE_WOORDENSET = "CREATE TABLE woordenset (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "naam TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_WOORDENSET);

        String CREATE_TABLE_GETESTEOEFENING = "CREATE TABLE getesteOefening (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "score INTEGER, " +
                "aantalPogingen INTEGER, " +
                "oefeningId INTEGER NOT NULL, " +
                "getestWoordId INTEGER NOT NULL, " +
                "FOREIGN KEY (oefeningId) REFERENCES oefening(id), " +
                "FOREIGN KEY (getestWoordId) REFERENCES getestWoord(id))";
        db.execSQL(CREATE_TABLE_GETESTEOEFENING);

        String CREATE_TABLE_OEFENING = "CREATE TABLE oefening (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "naam TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_OEFENING);

        insertKlassen(db);
        insertGroepen(db);
        insertVoormeting(db);
        insertKinderen(db);
        insertCondities(db);
        insertWoordensets(db);
        insertDoelwoorden(db);
        insertOefeningen(db);

    }

    private void insertKlassen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO klas (naam) VALUES ('Antwerpse klas');");
        db.execSQL("INSERT INTO klas (naam) VALUES ('Limburgse klas');");
    }

    private void insertGroepen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO groep (id, naam) VALUES (1, 'Groep 1');");
        db.execSQL("INSERT INTO groep (id, naam) VALUES (2, 'Groep 2');");
        db.execSQL("INSERT INTO groep (id, naam) VALUES (3, 'Groep 3');");
    }

    private void insertKinderen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, klasId, groepId, voormetingId) VALUES ('Henk', 'Destoute', 1, 1, 1, null);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, klasId, groepId, voormetingId) VALUES ('Joske', 'Deflinke', 1, 1, 1, null);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, klasId, groepId, voormetingId) VALUES ('Louisa', 'Depennensteler', 1, 2, 1, null);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, klasId, groepId, voormetingId) VALUES ('Jefke', 'Devervelende', 1, 1, 2, null);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, klasId, groepId, voormetingId) VALUES ('Jeanneke', 'Debrave', 1, 1, 2, null);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, klasId, groepId, voormetingId) VALUES ('Koen', 'Degrave', 1, 2, 2, null);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, klasId, groepId, voormetingId) VALUES ('Evert', 'Deslaper', 1, 1, 3, null);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, klasId, groepId, voormetingId) VALUES ('Marianne', 'Destrever', 1, 1, 3, 0);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, klasId, groepId, voormetingId) VALUES ('Sven', 'Despieker', 1, 2, 3, null);");
    }

    private void insertCondities(SQLiteDatabase db) {
        db.execSQL("INSERT INTO conditie (id, naam) VALUES (1, 'Conditie 1');");
        db.execSQL("INSERT INTO conditie (id, naam) VALUES (2, 'Conditie 2');");
        db.execSQL("INSERT INTO conditie (id, naam) VALUES (3, 'Conditie 3');");
    }

    private void insertWoordensets(SQLiteDatabase db) {
        db.execSQL("INSERT INTO woordenset (id, naam) VALUES (1, 'Woordenset A');");
        db.execSQL("INSERT INTO woordenset (id, naam) VALUES (2, 'Woordenset B');");
        db.execSQL("INSERT INTO woordenset (id, naam) VALUES (3, 'Woordenset C');");
    }

    private void insertDoelwoorden(SQLiteDatabase db) {
        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (0, 'Duikbril', 'Een duikbril is een bril voor onder water. Daarmee kun je onder water je ogen open houden.', 'Met zijn duikbril kan de jongen de vissen onder water goed bekijken.', 'Met een duikbril kan ik schrijven op papier.', 'Ogen,Zwemmen,In de zee,Schrijven', 10);");

        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (1, 'Klimtouw', 'Een klimtouw is een touw waarin je omhoog kunt klimmen.', 'In de turnzaal klim ik omhoog in het klimtouw.', 'Ik wacht op de bus in het klimtouw.', 'Klimmen,Sterk,De turnzaal,Het zwembad', 1);");
        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (2, 'Kroos', 'Kroos bestaat uit kleine, groene plantjes die op het water groeien. Je ziet het bijvoorbeeld in een sloot.', 'De vijver is groen door het kroos.', 'Oma en het kroos zitten in de auto.', 'Groen,In de vijver,De eend,De lamp', 1);");
        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (3, 'Riet', 'Riet lijkt op hoog gras. Het heeft lange stengels en groeit langs het water.', 'De eenden zitten bij het water tussen het riet.', 'Ik ga naar buiten met mijn jas en het riet aan.', 'De vijver,De eend,Het bos,De bril', 1);");

        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (4, 'Val', 'Als je een val maakt, val je op de grond.', 'Wat was dat een pijnlijke val!', 'Jan zit op de val aan tafel.', 'De pijn,Naar voor,De pleister,De appel', 2);");
        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (5, 'Kompas', 'Met een kompas weet je waar je naartoe moet. De naald van het kompas geeft het noorden aan.', 'Omdat papa niet weet waar we naartoe moeten lopen, kijkt hij op zijn kompas.', 'Mama belt met het kompas naar papa.', 'Wandelen,De rugzak,De landkaart,Het bad', 2);");
        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (6, 'Steil', 'Een steile berg gaat heel schuin omhoog of omlaag.', 'Jan loopt de steile berg omhoog.', 'Papa leest een steil verhaaltje voor.', 'De berg,Beklimmen,De trap,De bloem', 2);");

        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (7, 'Zwaan', 'Een zwaan is een grote vogel met een lange, kromme hals. Meestal zijn zwanen wit, maar soms zwart.', 'In de vijver in het park zwemt een witte zwaan.', 'De zwaan fietst in het park.', 'De vijver,Vleugels,Wit,Het boek', 3);");
        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (8, 'Kamp', 'Een kamp is een plaats om buiten te wonen en te slapen, bijvoorbeeld in tenten.', 'De kinderen zitten te eten tussen de tenten van het kamp.', 'Jonas wast zich met het kamp.', 'De tent,Kampvuur,In de slaapzak,De deur', 3);");
        db.execSQL("INSERT INTO doelwoord (id, naam, defenitie, juisteZin, fouteZin, keuzeWeb, woordensetId) VALUES (9, 'Zaklamp', 'Een zaklamp is een kleine lamp die je overal mee naartoe kunt nemen.', 'De jongen schijnt met de zaklamp in de donkere grot.', 'Jef opent de deur met de zaklamp.', 'Het licht,De batterij,In het donker,Het paard', 3);");
    }

    private void insertOefeningen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (0, 'Voormeting');");
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (1, 'Oefening 1');");
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (2, 'Oefening 2');");
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (3, 'Oefening 3');");
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (4, 'Oefening 4');");
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (5, 'Oefening 5');");
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (6, 'Oefening 6.1');");
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (7, 'Oefening 6.2');");
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (8, 'Oefening 6.3');");
        db.execSQL("INSERT INTO oefening (id, naam) VALUES (9, 'Nameting');");
    }

    private void insertVoormeting(SQLiteDatabase db) {
        db.execSQL("INSERT INTO voormeting (id, duikbril, klimtouw, kroos, riet, val, kompas, steil, zwaan, kamp, zaklamp) VALUES (0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1);");
    }

    // methode wordt uitgevoerd als database geupgrade wordt
    // hierin de vorige tabellen wegdoen en opnieuw creëren
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS klas");
        db.execSQL("DROP TABLE IF EXISTS groep");
        db.execSQL("DROP TABLE IF EXISTS kind");
        db.execSQL("DROP TABLE IF EXISTS test");
        db.execSQL("DROP TABLE IF EXISTS conditie");
        db.execSQL("DROP TABLE IF EXISTS getestWoord");
        db.execSQL("DROP TABLE IF EXISTS doelwoord");
        db.execSQL("DROP TABLE IF EXISTS woordenset");
        db.execSQL("DROP TABLE IF EXISTS getesteOefening");
        db.execSQL("DROP TABLE IF EXISTS oefening");
        db.execSQL("DROP TABLE IF EXISTS voormeting");

        // Create tables again
        onCreate(db);
    }

    //-------------------------------------------------------------------------------------------------
    //  CRUD Operations
    //-------------------------------------------------------------------------------------------------

    // insert-methode met ContentValues
    public long insertKlas(Klas klas) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("naam", klas.getNaam());

        long id = db.insert("klas", null, values);

        db.close();
        return id;
    }

    // update-methode met ContentValues
    public boolean updateKlas(Klas klas) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("naam", klas.getNaam());

        int numrows = db.update(
                "klas",
                values,
                "id = ?",
                new String[] { String.valueOf(klas.getId()) });

        db.close();
        return numrows > 0;
    }

    // delete-methode
    public boolean deleteKlas(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "klas",
                "id = ?",
                new String[] { String.valueOf(id) });

        db.close();
        return numrows > 0;
    }

    // query-methode
    public Klas getKlasById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "klas",      // tabelnaam
                new String[] { "id", "naam" }, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Klas klas = new Klas();

        if (cursor.moveToFirst()) {
            klas = new Klas(cursor.getLong(0),
                    cursor.getString(1));
        }
        cursor.close();
        db.close();
        return klas;
    }

    // rawQuery-methode
    public List<Klas> getKlassen() {
        List<Klas> lijst = new ArrayList<Klas>();
        String selectQuery;

        selectQuery = "SELECT  * FROM klas ORDER BY naam";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Klas klas = new Klas(cursor.getLong(0),
                        cursor.getString(1));
                lijst.add(klas);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // insert-methode met ContentValues
    public long insertKind(Kind kind) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("voornaam", kind.getVoornaam());
        values.put("naam", kind.getNaam());
        values.put("actief", kind.getActief());
        values.put("groepId", kind.getGroepId());
        values.put("klasId", kind.getKlasId());
        values.put("voormetingId", kind.getVoormetingId());

        long id = db.insert("kind", null, values);

        db.close();
        return id;
    }

    // update-methode met ContentValues
    public boolean updateKind(Kind kind) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("voornaam", kind.getVoornaam());
        values.put("naam", kind.getNaam());
        values.put("actief", kind.getActief());
        values.put("groepId", kind.getGroepId());
        values.put("klasId", kind.getKlasId());
        values.put("voormetingId", kind.getVoormetingId());

        int numrows = db.update(
                "kind",
                values,
                "id = ?",
                new String[] { String.valueOf(kind.getId()) });

        db.close();
        return numrows > 0;
    }

    // delete-methode
    public boolean deleteKind(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "kind",
                "id = ?",
                new String[] { String.valueOf(id) });

        db.close();
        return numrows > 0;
    }

    // query-methode
    public Kind getKindById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "kind",      // tabelnaam
                new String[] { "id", "voornaam", "naam", "actief", "klasId", "groepId", "voormetingId" }, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Kind kind = new Kind();

        if (cursor.moveToFirst()) {
            kind = new Kind(cursor.getLong(0),
                    cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
        }
        cursor.close();
        db.close();
        return kind;
    }

    // rawQuery-methode
    public List<Kind> getKinderen(int actief) {
        List<Kind> lijst = new ArrayList<Kind>();
        String selectQuery;

        if (actief == 0){
            selectQuery = "SELECT  * FROM kind ORDER BY voornaam";
        }
        else {
            selectQuery = "SELECT  * FROM kind WHERE actief = 1 ORDER BY voornaam";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Kind kind = new Kind(cursor.getLong(0),
                        cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
                lijst.add(kind);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // rawQuery-methode
    public List<Kind> getKinderenWhereGroepId(int groepId, int actief) {
        List<Kind> lijst = new ArrayList<Kind>();
        String selectQuery;

        if (actief == 0){
            selectQuery = "SELECT * FROM kind WHERE groepId = " + groepId + " ORDER BY voornaam";
        }
        else {
            selectQuery = "SELECT * FROM kind WHERE groepId = " + groepId + " AND actief = 1 ORDER BY voornaam";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Kind kind = new Kind(cursor.getLong(0),
                        cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
                lijst.add(kind);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // rawQuery-methode
    public List<Kind> getKinderenWhereKlasIdAndGroepId(int klasId, int groepId, int actief) {
        List<Kind> lijst = new ArrayList<Kind>();
        String selectQuery;

        if (actief == 0){
            selectQuery = "SELECT * FROM kind WHERE groepId = " + groepId + " AND klasId = " + klasId + " ORDER BY voornaam";
        }
        else {
            selectQuery = "SELECT * FROM kind WHERE groepId = " + groepId + " AND klasId = " + klasId + " AND actief = 1 ORDER BY voornaam";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Kind kind = new Kind(cursor.getLong(0),
                        cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
                lijst.add(kind);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // rawQuery-methode
    public List<Test> getTestenWhereKindId(int kindId) {
        List<Test> lijst = new ArrayList<Test>();
        String selectQuery;

            selectQuery = "SELECT * FROM test WHERE kindId = " + kindId + " ORDER BY datumTijd DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Test test = new Test(cursor.getLong(0),
                        cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                lijst.add(test);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // query-methode
    public Groep getGroepById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "groep",      // tabelnaam
                new String[] { "id", "naam" }, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Groep groep = new Groep();

        if (cursor.moveToFirst()) {
            groep = new Groep(cursor.getLong(0),
                    cursor.getString(1));
        }
        cursor.close();
        db.close();
        return groep;
    }

    // rawQuery-methode
    public List<Groep> getGroepen() {
        List<Groep> lijst = new ArrayList<Groep>();

        String selectQuery = "SELECT  * FROM groep ORDER BY naam";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Groep groep = new Groep(cursor.getLong(0), cursor.getString(1));
                lijst.add(groep);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // rawQuery-methode
    public int getCountKinderen() {
        String selectQuery = "SELECT  * FROM kind";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int aantal = cursor.getCount();

        cursor.close();
        db.close();
        return aantal;
    }

    // query-methode
    public Conditie getConditieById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "conditie",      // tabelnaam
                new String[] { "id", "naam"}, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Conditie conditie = new Conditie();

        if (cursor.moveToFirst()) {
            conditie = new Conditie(cursor.getLong(0), cursor.getString(1));
        }
        cursor.close();
        db.close();
        return conditie;
    }

    // query-methode
    public Woordenset getWoordensetById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "woordenset",      // tabelnaam
                new String[] { "id", "naam"}, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Woordenset woordenset = new Woordenset();

        if (cursor.moveToFirst()) {
            woordenset = new Woordenset(cursor.getLong(0), cursor.getString(1));
        }
        cursor.close();
        db.close();
        return woordenset;
    }

    // query-methode
    public Doelwoord getDoelwoordById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "doelwoord",      // tabelnaam
                new String[] { "id", "naam", "defenitie", "juisteZin", "fouteZin", "keuzeWeb", "woordensetId"}, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Doelwoord doelwoord = new Doelwoord();

        if (cursor.moveToFirst()) {
            doelwoord = new Doelwoord(cursor.getLong(0),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
        }
        cursor.close();
        db.close();
        return doelwoord;
    }

    // rawQuery-methode
    public List<Doelwoord> getDoelwoordenWhereWoordensetId(int woordensetId) {
        List<Doelwoord> lijst = new ArrayList<Doelwoord>();
        String selectQuery;

        selectQuery = "SELECT * FROM doelwoord WHERE woordensetId = " + woordensetId + " ORDER BY id";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Doelwoord doelwoord = new Doelwoord(cursor.getLong(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
                lijst.add(doelwoord);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // insert-methode met ContentValues
    public long insertTest(Test test) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("datumTijd", test.getDatumTijd());
        values.put("kindId", test.getKindId());
        values.put("conditieId", test.getConditieId());

        long id = db.insert("test", null, values);

        db.close();
        return id;
    }

    // insert-methode met ContentValues
    public long insertGetestWoord(GetestWoord getestWoord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("doelwoordId", getestWoord.getDoelwoordId());
        values.put("testId", getestWoord.getTestId());

        long id = db.insert("getestWoord", null, values);

        db.close();
        return id;
    }

    // insert-methode met ContentValues
    public long insertGetesteOefening(GetesteOefening getesteOefening) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("score", getesteOefening.getScore());
        values.put("aantalPogingen", getesteOefening.getAantalPogingen());
        values.put("oefeningId", getesteOefening.getOefeningId());
        values.put("getestWoordId", getesteOefening.getGetestWoordId());

        long id = db.insert("getesteOefening", null, values);

        db.close();
        return id;
    }

    // query-methode
    public Test getTestById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "test",      // tabelnaam
                new String[] { "id", "datumTijd", "kindId", "conditieId" }, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Test test = new Test();

        if (cursor.moveToFirst()) {
            test = new Test(cursor.getLong(0),
                    cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        }
        cursor.close();
        db.close();
        return test;
    }

    // delete-methode
    public boolean deleteTest(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "test",
                "id = ?",
                new String[] { String.valueOf(id) });

        db.close();
        return numrows > 0;
    }

    // query-methode
    public GetestWoord getGetestWoordById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "getestWoord",      // tabelnaam
                new String[] { "id", "doelwoordId", "testId"}, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        GetestWoord getestWoord = new GetestWoord();

        if (cursor.moveToFirst()) {
            getestWoord = new GetestWoord(cursor.getLong(0),
                    cursor.getInt(1), cursor.getInt(2));
        }
        cursor.close();
        db.close();
        return getestWoord;
    }

    // rawQuery-methode
    public List<GetestWoord> getGetesteWoordenWhereTestId(int testId) {
        List<GetestWoord> lijst = new ArrayList<GetestWoord>();
        String selectQuery;

        selectQuery = "SELECT * FROM getestWoord WHERE testId = " + testId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                GetestWoord getestWoord = new GetestWoord(cursor.getLong(0),
                        cursor.getInt(1), cursor.getInt(2));
                lijst.add(getestWoord);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // rawQuery-methode
    public List<GetesteOefening> getGetesteOefeningenWhereGetestWoordId(int getestWoordId) {
        List<GetesteOefening> lijst = new ArrayList<GetesteOefening>();
        String selectQuery;

        selectQuery = "SELECT * FROM getesteOefening WHERE getestWoordId = " + getestWoordId + " ORDER BY id";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                GetesteOefening getesteOefening = new GetesteOefening(cursor.getLong(0),
                        cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
                lijst.add(getesteOefening);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // insert-methode met ContentValues
    public long insertVoormeting(Voormeting voormeting) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("duikbril", voormeting.getDuikbril());
        values.put("klimtouw", voormeting.getKlimtouw());
        values.put("kroos", voormeting.getKroos());
        values.put("riet", voormeting.getRiet());
        values.put("val", voormeting.getVal());
        values.put("kompas", voormeting.getKompas());
        values.put("Steil", voormeting.getSteil());
        values.put("Zwaan", voormeting.getZwaan());
        values.put("Kamp", voormeting.getKamp());
        values.put("zaklamp", voormeting.getZaklamp());

        long id = db.insert("voormeting", null, values);

        db.close();
        return id;
    }

    // update-methode met ContentValues
    public boolean updateVoormeting(Voormeting voormeting) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("duikbril", voormeting.getDuikbril());
        values.put("klimtouw", voormeting.getKlimtouw());
        values.put("kroos", voormeting.getKroos());
        values.put("riet", voormeting.getRiet());
        values.put("val", voormeting.getVal());
        values.put("kompas", voormeting.getKompas());
        values.put("Steil", voormeting.getSteil());
        values.put("Zwaan", voormeting.getZwaan());
        values.put("Kamp", voormeting.getKamp());
        values.put("zaklamp", voormeting.getZaklamp());

        int numrows = db.update(
                "voormeting",
                values,
                "id = ?",
                new String[] { String.valueOf(voormeting.getId()) });

        db.close();
        return numrows > 0;
    }

    // delete-methode
    public boolean deleteVoormeting(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "voormeting",
                "id = ?",
                new String[] { String.valueOf(id) });

        db.close();
        return numrows > 0;
    }

    // query-methode
    public Voormeting getVoormetingById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "voormeting",      // tabelnaam
                new String[] { "id", "duikbril", "klimtouw", "kroos", "riet", "val", "kompas", "steil", "zwaan", "kamp", "zaklamp" }, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Voormeting voormeting = new Voormeting();

        if (cursor.moveToFirst()) {
            voormeting = new Voormeting(cursor.getLong(0),
                    cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getInt(9),cursor.getInt(10));
        }
        cursor.close();
        db.close();
        return voormeting;
    }


}

