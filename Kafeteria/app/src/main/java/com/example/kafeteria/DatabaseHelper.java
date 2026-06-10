package com.example.kafeteria;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kafeteria.db";
    private static final int DATABASE_VERSION = 6;

    public static final String TABLE_NAPOJE = "Napoje";
    public static final String TABLE_PRZEKASKI = "Przekaski";
    public static final String TABLE_LOKALE = "Lokale";

    public static final String COL_ID = "_id";
    public static final String COL_NAZWA = "nazwa";
    public static final String COL_OPIS = "opis";
    public static final String COL_URL_ZDJECIA = "url_zdjecia";
    public static final String COL_CENA = "cena";

    public static final String COL_ADRES = "adres";
    public static final String COL_GODZINY = "godziny";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNapojeTable = "CREATE TABLE " + TABLE_NAPOJE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAZWA + " TEXT, " +
                COL_OPIS + " TEXT, " +
                COL_URL_ZDJECIA + " TEXT, " +
                COL_CENA + " REAL)";

        String createPrzekaskiTable = "CREATE TABLE " + TABLE_PRZEKASKI + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAZWA + " TEXT, " +
                COL_OPIS + " TEXT, " +
                COL_URL_ZDJECIA + " TEXT, " +
                COL_CENA + " REAL)";

        String createLokaleTable = "CREATE TABLE " + TABLE_LOKALE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAZWA + " TEXT, " +
                COL_ADRES + " TEXT, " +
                COL_GODZINY + " TEXT, " +
                COL_URL_ZDJECIA + " TEXT)";

        db.execSQL(createNapojeTable);
        db.execSQL(createPrzekaskiTable);
        db.execSQL(createLokaleTable);

        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAPOJE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRZEKASKI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOKALE);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        String urlEspresso = "https://images.unsplash.com/photo-1510707577719-ae7c14805e3a?w=400&q=80";
        String urlCappuccino = "https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=400&q=80";
        String urlLatte = "https://images.unsplash.com/photo-1561882468-9110e03e0f78?w=400&q=80";

        String urlSernik = "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=400&q=80";
        String urlSzarlotka = "https://images.unsplash.com/photo-1568571780765-9276ac8b75a2?w=400&q=80";
        String urlBrownie = "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400&q=80";

        String urlLokal1 = "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400&q=80";
        String urlLokal2 = "https://images.unsplash.com/photo-1497935586351-b67a49e012bf?w=400&q=80";

        insertNapoj(db, "Espresso", "Mocna, klasyczna kawa włoska.", urlEspresso, 6.00);
        insertNapoj(db, "Cappuccino", "Kawa z dużą ilością spienionego mleka.", urlCappuccino, 12.00);
        insertNapoj(db, "Latte", "Delikatna kawa z mlekiem.", urlLatte, 14.00);

        insertPrzekaska(db, "Sernik", "Pyszny domowy sernik.", urlSernik, 15.00);
        insertPrzekaska(db, "Szarlotka", "Ciepła szarlotka z kruszonką.", urlSzarlotka, 12.00);
        insertPrzekaska(db, "Brownie", "Mocno czekoladowe ciastko.", urlBrownie, 10.00);

        insertLokal(db, "Kafeteria Centrum", "ul. Główna 1, Warszawa", "08:00 - 20:00", urlLokal1);
        insertLokal(db, "Kafeteria Rynek", "Rynek 15, Kraków", "09:00 - 22:00", urlLokal2);
    }

    private void insertNapoj(SQLiteDatabase db, String nazwa, String opis, String urlZdjecia, double cena) {
        ContentValues values = new ContentValues();
        values.put(COL_NAZWA, nazwa);
        values.put(COL_OPIS, opis);
        values.put(COL_URL_ZDJECIA, urlZdjecia);
        values.put(COL_CENA, cena);
        db.insert(TABLE_NAPOJE, null, values);
    }

    private void insertPrzekaska(SQLiteDatabase db, String nazwa, String opis, String urlZdjecia, double cena) {
        ContentValues values = new ContentValues();
        values.put(COL_NAZWA, nazwa);
        values.put(COL_OPIS, opis);
        values.put(COL_URL_ZDJECIA, urlZdjecia);
        values.put(COL_CENA, cena);
        db.insert(TABLE_PRZEKASKI, null, values);
    }

    private void insertLokal(SQLiteDatabase db, String nazwa, String adres, String godziny, String urlZdjecia) {
        ContentValues values = new ContentValues();
        values.put(COL_NAZWA, nazwa);
        values.put(COL_ADRES, adres);
        values.put(COL_GODZINY, godziny);
        values.put(COL_URL_ZDJECIA, urlZdjecia);
        db.insert(TABLE_LOKALE, null, values);
    }
}
