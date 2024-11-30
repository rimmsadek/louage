package com.example.louage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                    COLUMN_TELEPHONE_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_EMAIL_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_MATRICULE_CHAUF + " TEXT NOT NULL, " +
                    COLUMN_MOT_DE_PASSE_CHAUF + " TEXT NOT NULL" +
                    ");";

    // SQL query to create the "utilisateur" table
    private static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE " + TABLE_UTILISATEUR + " (" +
                    COLUMN_ID_UTILISATEUR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOM_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_PRENOM_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_TELEPHONE_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_EMAIL_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_MOT_DE_PASSE_UTILISATEUR + " TEXT NOT NULL" +
                    ");";

    // SQL query to create the "voitures" table
    private static final String CREATE_TABLE_VOITURES =
            "CREATE TABLE " + TABLE_VOITURES + " (" +
                    COLUMN_MATRICULE + " TEXT, " +
                    COLUMN_FROM + " TEXT NOT NULL, " +
                    COLUMN_TO + " TEXT NOT NULL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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

    // Méthode pour insérer un chauffeur
    public long insertChauffeur(String nom, String prenom, String telephone, String email, String matricule, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM_CHAUF, nom);
        values.put(COLUMN_PRENOM_CHAUF, prenom);
        values.put(COLUMN_TELEPHONE_CHAUF, telephone);
        values.put(COLUMN_EMAIL_CHAUF, email);
        values.put(COLUMN_MATRICULE_CHAUF, matricule);
        values.put(COLUMN_MOT_DE_PASSE_CHAUF, motDePasse);

        long result = db.insert(TABLE_CHAUFFEURS, null, values);
        db.close();
        return result;
    }

    // Méthode pour insérer un utilisateur
    public long insertUtilisateur(String nom, String prenom, String telephone, String email, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM_UTILISATEUR, nom);
        values.put(COLUMN_PRENOM_UTILISATEUR, prenom);
        values.put(COLUMN_TELEPHONE_UTILISATEUR, telephone);
        values.put(COLUMN_EMAIL_UTILISATEUR, email);
        values.put(COLUMN_MOT_DE_PASSE_UTILISATEUR, motDePasse);

        long result = db.insert(TABLE_UTILISATEUR, null, values);
        db.close();
        return result;
    }

    // Méthode pour récupérer la matricule à partir de l'ID du chauffeur
    public String getMatriculeFromChauffeurId(int chauffeurId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_MATRICULE_CHAUF + " FROM " + TABLE_CHAUFFEURS +
                        " WHERE " + COLUMN_CHAUFFEUR_ID + " = ?",
                new String[]{String.valueOf(chauffeurId)}
        );

        String matricule = null;
        if (cursor.moveToFirst()) {
            matricule = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return matricule;
    }

    // Méthode pour insérer un trajet dans la table "voitures"
    public void insertVoiture(String matricule, String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATRICULE, matricule);
        values.put(COLUMN_FROM, from);
        values.put(COLUMN_TO, to);

        long result = db.insert(TABLE_VOITURES, null, values);
        db.close();

        if (result == -1) {
            System.out.println("Erreur lors de l'insertion du trajet.");
        } else {
            System.out.println("Trajet inséré avec succès.");
        }
    }
}
