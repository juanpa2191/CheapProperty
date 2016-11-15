package com.cheapproperty.trabajosena.cheapproperty.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.cheapproperty.trabajosena.cheapproperty.data.PropertyContract.PropertyEntry;

/**
 * Manejador de la base de datos
 */
public class PropertyDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Property.db";

    public PropertyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PropertyEntry.TABLE_NAME + " ("
                + PropertyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PropertyEntry.ID + " TEXT NOT NULL,"
                + PropertyEntry.NAME + " TEXT NOT NULL,"
                + PropertyEntry.SPECIALTY + " TEXT NOT NULL,"
                + PropertyEntry.PHONE_NUMBER + " TEXT NOT NULL,"
                + PropertyEntry.BIO + " TEXT NOT NULL,"
                + PropertyEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + PropertyEntry.ID + "))");



        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockProperty(sqLiteDatabase, new Property("Carlos Perez", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "carlos_perez.jpg"));
        mockProperty(sqLiteDatabase, new Property("Daniel Samper", "Abogado accidentes de tráfico",
                "300 200 2222", "Gran profesional con experiencia de 5 años en accidentes de tráfico.",
                "daniel_samper.jpg"));
        mockProperty(sqLiteDatabase, new Property("Lucia Aristizabal", "Abogado de derechos laborales",
                "300 200 3333", "Gran profesional con más de 3 años de experiencia en defensa de los trabajadores.",
                "lucia_aristizabal.jpg"));
        mockProperty(sqLiteDatabase, new Property("Marina Acosta", "Abogado de familia",
                "300 200 4444", "Gran profesional con experiencia de 5 años en casos de familia.",
                "marina_acosta.jpg"));
        mockProperty(sqLiteDatabase, new Property("Olga Ortiz", "Abogado de administración pública",
                "300 200 5555", "Gran profesional con experiencia de 5 años en casos en expedientes de urbanismo.",
                "olga_ortiz.jpg"));
        mockProperty(sqLiteDatabase, new Property("Pamela Briger", "Abogado fiscalista",
                "300 200 6666", "Gran profesional con experiencia de 5 años en casos de derecho financiero",
                "pamela_briger.jpg"));
        mockProperty(sqLiteDatabase, new Property("Rodrigo Benavidez", "Abogado Mercantilista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en redacción de contratos mercantiles",
                "rodrigo_benavidez.jpg"));
        mockProperty(sqLiteDatabase, new Property("Tom Bonz", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "tom_bonz.jpg"));
    }

    public long mockProperty(SQLiteDatabase db, Property property) {
        return db.insert(
                PropertyEntry.TABLE_NAME,
                null,
                property.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveLawyer(Property property) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
               PropertyEntry.TABLE_NAME,
                null,
                property.toContentValues());

    }

    public Cursor getAllProperty() {
        return getReadableDatabase()
                .query(
                        PropertyEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getLawyerById(String propertyId) {
        Cursor c = getReadableDatabase().query(
                PropertyEntry.TABLE_NAME,
                null,
                PropertyEntry.ID + " LIKE ?",
                new String[]{propertyId},
                null,
                null,
                null);
        return c;
    }

    public int deleteLawyer(String propertyId) {
        return getWritableDatabase().delete(
                PropertyEntry.TABLE_NAME,
                PropertyEntry.ID + " LIKE ?",
                new String[]{propertyId});
    }

    public int updateLawyer(Property property, String PropertyId) {
        return getWritableDatabase().update(
                PropertyEntry.TABLE_NAME,
                property.toContentValues(),
                PropertyEntry.ID + " LIKE ?",
                new String[]{PropertyId}
        );
    }
}
