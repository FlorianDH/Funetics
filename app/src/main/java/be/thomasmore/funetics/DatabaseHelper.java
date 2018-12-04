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
    private static final int DATABASE_VERSION = 1;
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

        insertGroepen(db);
        insertKinderen(db);
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

    // methode wordt uitgevoerd als database geupgrade wordt
    // hierin de vorige tabellen wegdoen en opnieuw creëren
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS groep");
        db.execSQL("DROP TABLE IF EXISTS kind");

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
    public List<Kind> getKinderen() {
        List<Kind> lijst = new ArrayList<Kind>();

        String selectQuery = "SELECT  * FROM kind ORDER BY voornaam";

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
    public List<Kind> getKinderenWhereGroepId(int groepId) {
        List<Kind> lijst = new ArrayList<Kind>();

        String selectQuery = "SELECT * FROM kind WHERE groepId = " + groepId + " ORDER BY voornaam";

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

}

