package com.example.louage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "louage.db";
    private static final int DATABASE_VERSION = 3;


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

    // Table "voyage"
    public static final String TABLE_VOYAGE = "voyage";
    public static final String COLUMN_VOYAGE_ID = "voyage_id";
    public static final String COLUMN_FROM = "from_destination";
    public static final String COLUMN_TO = "to_destination";
    public static final String COLUMN_NB_RESERVATION = "nb_reservation";
    public static final String COLUMN_NB_PLACES_DISPO = "nb_places_disponibles";

    // create the "chauffeurs" table
    private static final String CREATE_TABLE_CHAUFFEURS =
            "CREATE TABLE " + TABLE_CHAUFFEURS + " (" +
                    COLUMN_CHAUFFEUR_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    COLUMN_NOM_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_PRENOM_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_TELEPHONE_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_EMAIL_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_MATRICULE_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_MOT_DE_PASSE_CHAUF + " TEXT NOT NULL" +
                    ");";

    // create the "utilisateur" table
    private static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE " + TABLE_UTILISATEUR + " (" +
                    COLUMN_ID_UTILISATEUR + " INTEGER PRIMARY KEY NOT NULL, " +
                    COLUMN_NOM_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_PRENOM_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_TELEPHONE_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_EMAIL_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_MOT_DE_PASSE_UTILISATEUR + " TEXT NOT NULL" +
                    ");";

    // create the "voyage" table
    private static final String CREATE_TABLE_VOYAGE =
            "CREATE TABLE " + TABLE_VOYAGE + " (" +
                    COLUMN_VOYAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CHAUFFEUR_ID + " INTEGER NOT NULL, " + // Déclaration de la clé étrangère
                    COLUMN_FROM + " TEXT NOT NULL, " +
                    COLUMN_TO + " TEXT NOT NULL, " +
                    COLUMN_NB_RESERVATION + " INTEGER DEFAULT 0, " +
                    COLUMN_NB_PLACES_DISPO + " INTEGER DEFAULT 8, " + // Nouvelle colonne
                    "FOREIGN KEY(" + COLUMN_CHAUFFEUR_ID + ") REFERENCES " +
                    TABLE_CHAUFFEURS + "(" + COLUMN_CHAUFFEUR_ID + ") ON DELETE CASCADE" +
                    ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHAUFFEURS);
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_VOYAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAUFFEURS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOYAGE);
        onCreate(db);
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_UTILISATEUR, new String[]{DatabaseHelper.COLUMN_EMAIL_UTILISATEUR},
                DatabaseHelper.COLUMN_EMAIL_UTILISATEUR + "=?", new String[]{email}, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }


    // Insérer un chauffeur avec gestion manuelle de l'ID
    public long insertChauffeur(String nom, String prenom, String telephone, String email, String matricule, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Récupérer le dernier ID utilisé
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_CHAUFFEUR_ID + ") FROM " + TABLE_CHAUFFEURS, null);
        int lastId = 0;
        if (cursor.moveToFirst()) {
            lastId = cursor.getInt(0);  // Dernier ID utilisé
        }
        cursor.close();

        int newId = lastId + 1;  // Incrémenter l'ID pour le nouvel enregistrement

        // Préparer les valeurs à insérer
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHAUFFEUR_ID, newId);  // Utiliser l'ID incrémenté
        values.put(COLUMN_NOM_CHAUF, nom);
        values.put(COLUMN_PRENOM_CHAUF, prenom);
        values.put(COLUMN_TELEPHONE_CHAUF, telephone);
        values.put(COLUMN_EMAIL_CHAUF, email);
        values.put(COLUMN_MATRICULE_CHAUF, matricule);
        values.put(COLUMN_MOT_DE_PASSE_CHAUF, motDePasse);

        // Insérer le chauffeur dans la table
        long result = db.insert(TABLE_CHAUFFEURS, null, values);
        db.close();
        return result;
    }



    // Insert utilisateur avec gestion manuelle de l'ID
    public long insertUtilisateur(String nom, String prenom, String telephone, String email, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Récupération de l'ID maximum actuel pour l'incrémenter
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_ID_UTILISATEUR + ") FROM " + TABLE_UTILISATEUR, null);
        int id = 1; // Valeur par défaut
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0) + 1; // Incrémente l'ID max
        }
        cursor.close();

        // Insertion avec l'ID manuel
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_UTILISATEUR, id);
        values.put(COLUMN_NOM_UTILISATEUR, nom);
        values.put(COLUMN_PRENOM_UTILISATEUR, prenom);
        values.put(COLUMN_TELEPHONE_UTILISATEUR, telephone);
        values.put(COLUMN_EMAIL_UTILISATEUR, email);
        values.put(COLUMN_MOT_DE_PASSE_UTILISATEUR, motDePasse);

        long result = db.insert(TABLE_UTILISATEUR, null, values);
        db.close();
        return result;
    }

    // Insert voyage avec gestion manuelle de l'ID
    public long insertVoyage(int chauffeurId, String from, String to, int nbReservation, int nbPlacesDispo) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Récupération de l'ID maximum actuel pour l'incrémenter
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_VOYAGE_ID + ") FROM " + TABLE_VOYAGE, null);
        int id = 1; // Valeur par défaut
        if (cursor.moveToFirst() && cursor.getInt(0) != 0) {
            id = cursor.getInt(0) + 1; // Incrémente l'ID max
        }
        cursor.close();

        // Préparer les valeurs à insérer
        ContentValues values = new ContentValues();
        values.put(COLUMN_VOYAGE_ID, id);
        values.put(COLUMN_CHAUFFEUR_ID, chauffeurId);
        values.put(COLUMN_FROM, from);
        values.put(COLUMN_TO, to);
        values.put(COLUMN_NB_RESERVATION, nbReservation);
        values.put(COLUMN_NB_PLACES_DISPO, nbPlacesDispo); // Ajout de la colonne des places disponibles

        // Insertion dans la table
        long result = db.insert(TABLE_VOYAGE, null, values);
        db.close();
        return result;
    }



}


