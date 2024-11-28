package com.example.louage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "louage.db";
    private static final int DATABASE_VERSION = 1;


    // Table "chauffeurs"
    public static final String TABLE_CHAUFFEURS = "chauffeurs";
    public static final String COLUMN_CHAUFFEUR_ID = "chauffeur_id";
    public static final String COLUMN_NOM_CHAUF = "nom";
    public static final String COLUMN_PRENOM_CHAUF = "prenom";
    public static final String COLUMN_TELEPHONE_CHAUF = "telephone";
    public static final String COLUMN_EMAIL_CHAUF = "email";
    public static final String COLUMN_MATRICULE_CHAUF = "matricule";
    public static final String COLUMN_MOT_DE_PASSE_CHAUF = "mot_de_passe";

    // Table "utilisateur"
    public static final String TABLE_UTILISATEUR = "utilisateur";
    public static final String COLUMN_ID_UTILISATEUR = "utilisateur_id";
    public static final String COLUMN_NOM_UTILISATEUR = "nom";
    public static final String COLUMN_PRENOM_UTILISATEUR = "prenom";
    public static final String COLUMN_TELEPHONE_UTILISATEUR = "telephone";
    public static final String COLUMN_EMAIL_UTILISATEUR = "email";
    public static final String COLUMN_MOT_DE_PASSE_UTILISATEUR = "mot_de_passe";


    // Table "voitures"
    public static final String TABLE_VOITURES = "voitures";
    public static final String COLUMN_MATRICULE = "matricule";
    public static final String COLUMN_FROM = "from_destination";
    public static final String COLUMN_TO = "to_destination";


    // SQL query to create the "chauffeurs" table
    private static final String CREATE_TABLE_CHAUFFEURS =
            "CREATE TABLE " + TABLE_CHAUFFEURS + " (" +
                    COLUMN_CHAUFFEUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOM_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_PRENOM_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_TELEPHONE_CHAUF + " INTEGER NOT NULL, " +
                    COLUMN_EMAIL_CHAUF + " TEXT, " +
                    COLUMN_MATRICULE_CHAUF + " TEXT, " +
                    COLUMN_MOT_DE_PASSE_CHAUF + " TEXT" +
                    ");";



    // SQL query to create the "utilisateur" table
    private static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE " + TABLE_UTILISATEUR + " (" +
                    COLUMN_ID_UTILISATEUR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOM_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_PRENOM_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_TELEPHONE_UTILISATEUR + " INTEGER NOT NULL, " +
                    COLUMN_EMAIL_UTILISATEUR  + " TEXT, " +
                    COLUMN_MOT_DE_PASSE_UTILISATEUR  + " TEXT" +
                    ");";


    // SQL query to create the "voitures" table
    private static final String CREATE_TABLE_VOITURES =
            "CREATE TABLE " + TABLE_VOITURES + " (" +
                    COLUMN_MATRICULE + " TEXT PRIMARY KEY, " + // La matricule est unique
                    COLUMN_FROM + " TEXT NOT NULL, " +
                    COLUMN_TO + " TEXT NOT NULL" +
                    ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_CHAUFFEURS);
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_VOITURES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAUFFEURS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOITURES);
        onCreate(db);
    }


    //APIs
    public void insertVoiture(String matricule, String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MATRICULE, matricule);
        values.put(COLUMN_FROM, from);
        values.put(COLUMN_TO, to);

        long result = db.insert(TABLE_VOITURES, null, values);
        db.close();

        if (result == -1) {
            System.out.println("Insertion échouée");
        } else {
            System.out.println("Voyage insérée avec succès");
        }
    }

    //get all matricules
    public ArrayList<String> getAllMatricules() {
        ArrayList<String> matricules = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_MATRICULE + " FROM " + TABLE_CHAUFFEURS, null);

        if (cursor.moveToFirst()) {
            do {
                matricules.add(cursor.getString(0)); // Récupère la matricule
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return matricules;
    }



}

