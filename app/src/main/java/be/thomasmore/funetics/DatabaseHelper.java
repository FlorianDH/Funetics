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
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "funatics";

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
        String CREATE_TABLE_GROEP = "CREATE TABLE groep (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "naam TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_GROEP);

        String CREATE_TABLE_KIND = "CREATE TABLE kind (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "voornaam TEXT NOT NULL," +
                "naam TEXT NOT NULL, " +
                "leeftijd INTEGER," +
                "actief INTEGER, " +
                "groepId INTEGER NOT NULL, " +
                "FOREIGN KEY (groepId) REFERENCES party(id))";
        db.execSQL(CREATE_TABLE_KIND);

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
                "FOREIGN KEY (doelwoordId) REFERENCES doelwoord(id))";
        db.execSQL(CREATE_TABLE_GETESTWOORD);

        String CREATE_TABLE_DOELWOORD = "CREATE TABLE doelwoord (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "naam TEXT NOT NULL," +
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

        insertGroepen(db);
        insertKinderen(db);
        insertCondities(db);
        insertWoordensets(db);
        insertDoelwoorden(db);
        insertOefeningen(db);
    }

    private void insertGroepen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO groep (id, naam) VALUES (1, 'Groep 1');");
        db.execSQL("INSERT INTO groep (id, naam) VALUES (2, 'Groep 2');");
        db.execSQL("INSERT INTO groep (id, naam) VALUES (3, 'Groep 3');");
    }

    private void insertKinderen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO kind (voornaam, naam, leeftijd, actief, groepId) VALUES ('Henk', 'Destoute', 12, 1, 1);");
        db.execSQL("INSERT INTO kind (voornaam, naam, leeftijd, actief, groepId) VALUES ('Joske', 'Deflinke', 11, 1, 1);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, groepId) VALUES ('Jefke', 'Devervelende', 1, 2);");
        db.execSQL("INSERT INTO kind (voornaam, naam, actief, groepId) VALUES ('Jeanneke', 'Debrave', 1, 2);");
        db.execSQL("INSERT INTO kind (voornaam, naam, leeftijd, actief, groepId) VALUES ('Evert', 'Deslaper', 9, 0, 3);");
        db.execSQL("INSERT INTO kind (voornaam, naam, leeftijd, actief, groepId) VALUES ('Marianne', 'Destrever', 7, 0, 3);");
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
        db.execSQL("INSERT INTO doelwoord (id, naam, woordensetId) VALUES (1, 'Klimtouw', 1);");
        db.execSQL("INSERT INTO doelwoord (id, naam, woordensetId) VALUES (2, 'Kroos', 1);");
        db.execSQL("INSERT INTO doelwoord (id, naam, woordensetId) VALUES (3, 'Riet', 1);");

        db.execSQL("INSERT INTO doelwoord (id, naam, woordensetId) VALUES (4, 'Val', 2);");
        db.execSQL("INSERT INTO doelwoord (id, naam, woordensetId) VALUES (5, 'Kompas', 2);");
        db.execSQL("INSERT INTO doelwoord (id, naam, woordensetId) VALUES (6, 'Steil', 2);");

        db.execSQL("INSERT INTO doelwoord (id, naam, woordensetId) VALUES (7, 'Zwaan', 3);");
        db.execSQL("INSERT INTO doelwoord (id, naam, woordensetId) VALUES (8, 'Kamp', 3);");
        db.execSQL("INSERT INTO doelwoord (id, naam, woordensetId) VALUES (9, 'Zaklamp', 3);");
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

    // methode wordt uitgevoerd als database geupgrade wordt
    // hierin de vorige tabellen wegdoen en opnieuw creëren
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS groep");
        db.execSQL("DROP TABLE IF EXISTS kind");
        db.execSQL("DROP TABLE IF EXISTS test");
        db.execSQL("DROP TABLE IF EXISTS conditie");
        db.execSQL("DROP TABLE IF EXISTS getestWoord");
        db.execSQL("DROP TABLE IF EXISTS doelwoord");
        db.execSQL("DROP TABLE IF EXISTS woordenset");
        db.execSQL("DROP TABLE IF EXISTS getesteOefening");
        db.execSQL("DROP TABLE IF EXISTS oefening");

        // Create tables again
        onCreate(db);
    }

    //-------------------------------------------------------------------------------------------------
    //  CRUD Operations
    //-------------------------------------------------------------------------------------------------

    // insert-methode met ContentValues
    public long insertKind(Kind kind) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("voornaam", kind.getVoornaam());
        values.put("naam", kind.getNaam());
        values.put("leeftijd", kind.getLeeftijd());
        values.put("actief", kind.getActief());
        values.put("groepId", kind.getGroepId());

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
        values.put("leeftijd", kind.getLeeftijd());
        values.put("actief", kind.getActief());
        values.put("groepId", kind.getGroepId());

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
                new String[] { "id", "voornaam", "naam", "leeftijd", "actief", "groepId" }, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Kind kind = new Kind();

        if (cursor.moveToFirst()) {
            kind = new Kind(cursor.getLong(0),
                    cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
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
                        cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
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
                        cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
                lijst.add(kind);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
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

}

