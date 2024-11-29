package com.example.louage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "louage.db";
    private static final int DATABASE_VERSION = 2;

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
                    COLUMN_TELEPHONE_UTILISATEUR + " TEXT NOT NULL, " +
                    COLUMN_EMAIL_UTILISATEUR + " TEXT, " +
                    COLUMN_MOT_DE_PASSE_UTILISATEUR + " TEXT" +
                    ");";

    // SQL query to create the "voitures" table
    private static final String CREATE_TABLE_VOITURES =
            "CREATE TABLE " + TABLE_VOITURES + " (" +
                    COLUMN_MATRICULE + " TEXT PRIMARY KEY, " +
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

    // Insert a voiture
    public void insertVoiture(String matricule, String from, String to) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_MATRICULE, matricule);
            values.put(COLUMN_FROM, from);
            values.put(COLUMN_TO, to);

            long result = db.insert(TABLE_VOITURES, null, values);

            if (result == -1) {
                Log.e(TAG, "Insertion échouée");
            } else {
                Log.d(TAG, "Voiture insérée avec succès");
            }
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'insertion de la voiture", e);
        } finally {
            // Assurez-vous de fermer la base de données si elle est ouverte
            if (db != null) {
                db.close();
            }
        }
    }


    // Get all matricules
    public ArrayList<String> getAllMatricules() {
        ArrayList<String> matricules = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT " + COLUMN_MATRICULE + " FROM " + TABLE_VOITURES, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    matricules.add(cursor.getString(0));  // Ajoute chaque matricule à la liste
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de la récupération des matricules", e);
        } finally {
            // Assurez-vous que le curseur et la base de données sont fermés
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return matricules;
    }

    private long insertRecord(String tableName, ContentValues values) {
        SQLiteDatabase db = null;
        long result = -1;
        try {
            db = this.getWritableDatabase();
            result = db.insert(tableName, null, values);
        } catch (Exception e) {
            Log.e(TAG, "Error inserting into " + tableName, e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }
    // Insert a chauffeur
    public void insertChauffeur(String nom, String prenom, String telephone, String email, String matricule, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Vérifier si un chauffeur avec le même matricule existe déjà
            Cursor cursor = db.query(TABLE_CHAUFFEURS, null, COLUMN_MATRICULE_CHAUF + "=?",
                    new String[]{matricule}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                Log.e(TAG, "Chauffeur avec ce matricule existe déjà.");
                cursor.close();
                db.close();
                return;  // Empêche l'insertion si le chauffeur existe déjà
            }
            cursor.close();

            // Insérer le chauffeur dans la base de données
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOM_CHAUF, nom);
            values.put(COLUMN_PRENOM_CHAUF, prenom);
            values.put(COLUMN_TELEPHONE_CHAUF, telephone);
            values.put(COLUMN_EMAIL_CHAUF, email);
            values.put(COLUMN_MATRICULE_CHAUF, matricule);
            values.put(COLUMN_MOT_DE_PASSE_CHAUF, motDePasse);

            // Insérer le chauffeur et vérifier le résultat
            long result = db.insert(TABLE_CHAUFFEURS, null, values);

            if (result == -1) {
                Log.e(TAG, "Insertion de chauffeur échouée");
            } else {
                Log.d(TAG, "Chauffeur inséré avec succès");
            }

        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'insertion du chauffeur", e);
        } finally {
            db.close();
        }
    }


    // Insert a utilisateur
    // Exemple d'insertion avec gestion d'erreur et transaction
    public void insertUtilisateur(String nom, String prenom, String telephone, String email, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction(); // Démarrer la transaction
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOM_UTILISATEUR, nom);
            values.put(COLUMN_PRENOM_UTILISATEUR, prenom);
            values.put(COLUMN_TELEPHONE_UTILISATEUR, telephone);
            values.put(COLUMN_EMAIL_UTILISATEUR, email);
            values.put(COLUMN_MOT_DE_PASSE_UTILISATEUR, motDePasse);

            long result = db.insert(TABLE_UTILISATEUR, null, values);

            if (result == -1) {
                Log.e(TAG, "Insertion d'utilisateur échouée");
            } else {
                Log.d(TAG, "Utilisateur inséré avec succès");
            }

            db.setTransactionSuccessful(); // Confirmer la transaction
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'insertion d'un utilisateur", e);
        } finally {
            db.endTransaction(); // Fin de la transaction
            db.close();
        }
    }

}
