//package com.example.louage;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//
//    private static final String DATABASE_NAME = "louage.db";
//    private static final int DATABASE_VERSION = 6;
//
//
//    // Table "chauffeurs"
//    public static final String TABLE_CHAUFFEURS = "chauffeurs";
//    public static final String COLUMN_CHAUFFEUR_ID = "chauffeur_id";
//    public static final String COLUMN_NOM_CHAUF = "nom";
//    public static final String COLUMN_PRENOM_CHAUF = "prenom";
//    public static final String COLUMN_TELEPHONE_CHAUF = "telephone";
//    public static final String COLUMN_EMAIL_CHAUF = "email";
//    public static final String COLUMN_MATRICULE_CHAUF = "matricule";
//    public static final String COLUMN_MOT_DE_PASSE_CHAUF = "mot_de_passe";
//
//    // Table "utilisateur"
//    public static final String TABLE_UTILISATEUR = "utilisateur";
//    public static final String COLUMN_ID_UTILISATEUR = "utilisateur_id";
//    public static final String COLUMN_NOM_UTILISATEUR = "nom";
//    public static final String COLUMN_PRENOM_UTILISATEUR = "prenom";
//    public static final String COLUMN_TELEPHONE_UTILISATEUR = "telephone";
//    public static final String COLUMN_EMAIL_UTILISATEUR = "email";
//    public static final String COLUMN_MOT_DE_PASSE_UTILISATEUR = "mot_de_passe";
//
//    // Table "voyage"
//    public static final String TABLE_VOYAGE = "voyage";
//    public static final String COLUMN_VOYAGE_ID = "voyage_id";
//    public static final String COLUMN_FROM = "from_destination";
//    public static final String COLUMN_TO = "to_destination";
//    public static final String COLUMN_NB_RESERVATION = "nb_reservation";
//    public static final String COLUMN_NB_PLACES_DISPO = "nb_places_disponibles";
//
//    /// Attributs de la table "reservations"
//    public static final String TABLE_RESERVATIONS = "reservation";
//    public static final String COLUMN_RESERVATION_ID = "reservation_id";
//    public static final String COLUMN_VOYAGE_ID_RES = "voyage_id";
//    public static final String COLUMN_UTILISATEUR_ID = "utilisateur_id";
//    public static final String COLUMN_HEURE_RESERVATION = "heure_reservation";
//
//
//    // create the "chauffeurs" table
//    private static final String CREATE_TABLE_CHAUFFEURS =
//            "CREATE TABLE " + TABLE_CHAUFFEURS + " (" +
//                    COLUMN_CHAUFFEUR_ID + " INTEGER PRIMARY KEY NOT NULL, " +
//                    COLUMN_NOM_CHAUF + " TEXT NOT NULL, " +
//                    COLUMN_PRENOM_CHAUF + " TEXT NOT NULL, " +
//                    COLUMN_TELEPHONE_CHAUF + " TEXT NOT NULL, " +
//                    COLUMN_EMAIL_CHAUF + " TEXT NOT NULL, " +
//                    COLUMN_MATRICULE_CHAUF + " TEXT NOT NULL, " +
//                    COLUMN_MOT_DE_PASSE_CHAUF + " TEXT NOT NULL" +
//                    ");";
//
//    // create the "utilisateur" table
//    private static final String CREATE_TABLE_UTILISATEUR =
//            "CREATE TABLE " + TABLE_UTILISATEUR + " (" +
//                    COLUMN_ID_UTILISATEUR + " INTEGER PRIMARY KEY NOT NULL, " +
//                    COLUMN_NOM_UTILISATEUR + " TEXT NOT NULL, " +
//                    COLUMN_PRENOM_UTILISATEUR + " TEXT NOT NULL, " +
//                    COLUMN_TELEPHONE_UTILISATEUR + " TEXT NOT NULL, " +
//                    COLUMN_EMAIL_UTILISATEUR + " TEXT NOT NULL, " +
//                    COLUMN_MOT_DE_PASSE_UTILISATEUR + " TEXT NOT NULL" +
//                    ");";
//
//    // create the "voyage" table
//    private static final String CREATE_TABLE_VOYAGE =
//            "CREATE TABLE " + TABLE_VOYAGE + " (" +
//                    COLUMN_VOYAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_CHAUFFEUR_ID + " INTEGER NOT NULL, " + // Déclaration de la clé étrangère
//                    COLUMN_FROM + " TEXT NOT NULL, " +
//                    COLUMN_TO + " TEXT NOT NULL, " +
//                    COLUMN_NB_RESERVATION + " INTEGER DEFAULT 0, " +
//                    COLUMN_NB_PLACES_DISPO + " INTEGER DEFAULT 8, " + // Nouvelle colonne
//                    "FOREIGN KEY(" + COLUMN_CHAUFFEUR_ID + ") REFERENCES " +
//                    TABLE_CHAUFFEURS + "(" + COLUMN_CHAUFFEUR_ID + ") ON DELETE CASCADE" +
//                    ");";
//
//
//
//
//    // Création de la table "reservations" avec les variables
//    private static final String CREATE_TABLE_RESERVATIONS =
//            "CREATE TABLE "+TABLE_RESERVATIONS+"(" +
//                    COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_VOYAGE_ID_RES + " INTEGER NOT NULL, " +
//                    COLUMN_UTILISATEUR_ID + " INTEGER NOT NULL, " +
//                    COLUMN_HEURE_RESERVATION + " TEXT NOT NULL, " +
//                    "FOREIGN KEY (" + COLUMN_VOYAGE_ID_RES + ") REFERENCES " + TABLE_VOYAGE + "(" + COLUMN_VOYAGE_ID + ") ON DELETE CASCADE, " +
//                    "FOREIGN KEY (" + COLUMN_UTILISATEUR_ID + ") REFERENCES " + TABLE_UTILISATEUR + "(" + COLUMN_ID_UTILISATEUR + ") ON DELETE CASCADE" +
//                    ");";
//
//
//
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_TABLE_CHAUFFEURS);
//        db.execSQL(CREATE_TABLE_UTILISATEUR);
//        db.execSQL(CREATE_TABLE_VOYAGE);
//        db.execSQL(CREATE_TABLE_RESERVATIONS);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAUFFEURS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOYAGE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
//        onCreate(db);
//    }
//
//    public boolean isEmailExists(String email) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(DatabaseHelper.TABLE_UTILISATEUR, new String[]{DatabaseHelper.COLUMN_EMAIL_UTILISATEUR},
//                DatabaseHelper.COLUMN_EMAIL_UTILISATEUR + "=?", new String[]{email}, null, null, null);
//
//        boolean exists = cursor.getCount() > 0;
//        cursor.close();
//        db.close();
//        return exists;
//    }
//
//
//    // Insérer un chauffeur avec gestion manuelle de l'ID
//    public long insertChauffeur(String nom, String prenom, String telephone, String email, String matricule, String motDePasse) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Récupérer le dernier ID utilisé
//        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_CHAUFFEUR_ID + ") FROM " + TABLE_CHAUFFEURS, null);
//        int lastId = 0;
//        if (cursor.moveToFirst()) {
//            lastId = cursor.getInt(0);  // Dernier ID utilisé
//        }
//        cursor.close();
//
//        int newId = lastId + 1;  // Incrémenter l'ID pour le nouvel enregistrement
//
//        // Préparer les valeurs à insérer
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_CHAUFFEUR_ID, newId);  // Utiliser l'ID incrémenté
//        values.put(COLUMN_NOM_CHAUF, nom);
//        values.put(COLUMN_PRENOM_CHAUF, prenom);
//        values.put(COLUMN_TELEPHONE_CHAUF, telephone);
//        values.put(COLUMN_EMAIL_CHAUF, email);
//        values.put(COLUMN_MATRICULE_CHAUF, matricule);
//        values.put(COLUMN_MOT_DE_PASSE_CHAUF, motDePasse);
//
//        // Insérer le chauffeur dans la table
//        long result = db.insert(TABLE_CHAUFFEURS, null, values);
//        db.close();
//        return result;
//    }
//
//
//
//    // Insert utilisateur avec gestion manuelle de l'ID
//    public long insertUtilisateur(String nom, String prenom, String telephone, String email, String motDePasse) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Récupération de l'ID maximum actuel pour l'incrémenter
//        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_ID_UTILISATEUR + ") FROM " + TABLE_UTILISATEUR, null);
//        int id = 1; // Valeur par défaut
//        if (cursor.moveToFirst()) {
//            id = cursor.getInt(0) + 1; // Incrémente l'ID max
//        }
//        cursor.close();
//
//        // Insertion avec l'ID manuel
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_ID_UTILISATEUR, id);
//        values.put(COLUMN_NOM_UTILISATEUR, nom);
//        values.put(COLUMN_PRENOM_UTILISATEUR, prenom);
//        values.put(COLUMN_TELEPHONE_UTILISATEUR, telephone);
//        values.put(COLUMN_EMAIL_UTILISATEUR, email);
//        values.put(COLUMN_MOT_DE_PASSE_UTILISATEUR, motDePasse);
//
//        long result = db.insert(TABLE_UTILISATEUR, null, values);
//        db.close();
//        return result;
//    }
//
//    // Insert voyage avec gestion manuelle de l'ID
//    public long insertVoyage(int chauffeurId, String from, String to, int nbReservation, int nbPlacesDispo) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Récupération de l'ID maximum actuel pour l'incrémenter
//        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_VOYAGE_ID + ") FROM " + TABLE_VOYAGE, null);
//        int id = 1; // Valeur par défaut
//        if (cursor.moveToFirst() && cursor.getInt(0) != 0) {
//            id = cursor.getInt(0) + 1; // Incrémente l'ID max
//        }
//        cursor.close();
//
//        // Préparer les valeurs à insérer
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_VOYAGE_ID, id);
//        values.put(COLUMN_CHAUFFEUR_ID, chauffeurId);
//        values.put(COLUMN_FROM, from);
//        values.put(COLUMN_TO, to);
//        values.put(COLUMN_NB_RESERVATION, nbReservation);
//        values.put(COLUMN_NB_PLACES_DISPO, nbPlacesDispo);
//
//        // Insertion dans la table
//        long result = db.insert(TABLE_VOYAGE, null, values);
//        db.close();
//        return result;
//    }
//
//
//
//
//    // Méthode pour obtenir les détails d'un voyage
//    public Voyage getVoyageDetails(int voyageId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT v." + COLUMN_FROM + ", v." + COLUMN_TO + ", v." + COLUMN_NB_RESERVATION +
//                ", c." + COLUMN_NOM_CHAUF + ", c." + COLUMN_PRENOM_CHAUF +
//                " FROM " + TABLE_VOYAGE + " v " +
//                "JOIN " + TABLE_CHAUFFEURS + " c ON v." + COLUMN_CHAUFFEUR_ID + " = c." + COLUMN_CHAUFFEUR_ID +
//                " WHERE v." + COLUMN_VOYAGE_ID + " = ?";
//
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(voyageId)});
//        Voyage voyage = null;
//
//        if (cursor.moveToFirst()) {
//            String from = cursor.getString(0);
//            String to = cursor.getString(1);
//            int nbReservation = cursor.getInt(2);
//            int ChauffeurId = cursor.getInt(5);
//            String chauffeurNom = cursor.getString(3);
//            String chauffeurPrenom = cursor.getString(4);
//            voyage = new Voyage(voyageId, from, to, nbReservation, 8 - nbReservation,ChauffeurId, chauffeurNom, chauffeurPrenom);
//        }
//
//        cursor.close();
//        db.close();
//        return voyage;
//    }
//    public Cursor getVoyagesByDestination(String from, String to) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT " +
//                COLUMN_VOYAGE_ID + ", " +
//                COLUMN_CHAUFFEUR_ID + ", " +
//                COLUMN_NB_RESERVATION +
//                " FROM " + TABLE_VOYAGE +
//                " WHERE " + COLUMN_FROM + " = ?" +
//                " AND " + COLUMN_TO + " = ?" +
//                " AND " + COLUMN_NB_RESERVATION + " < 8";
//        return db.rawQuery(query, new String[]{from, to});
//    }
//
//    // Méthode pour obtenir le nom du chauffeur à partir de son ID
//    public String getChauffeurNom(int chauffeurId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_CHAUFFEURS, new String[]{COLUMN_NOM_CHAUF},
//                COLUMN_CHAUFFEUR_ID + "=?", new String[]{String.valueOf(chauffeurId)}, null, null, null);
//
//        String nom = "Nom inconnu";
//        if (cursor != null) {
//            try {
//                if (cursor.moveToFirst()) {
//                    int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_NOM_CHAUF);
//                    nom = cursor.getString(columnIndex);
//                }
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();  // Affiche une erreur si la colonne n'est pas trouvée
//            } finally {
//                cursor.close();
//            }
//        }
//        return nom;
//    }
//
//
//    // Méthode pour obtenir le prénom du chauffeur à partir de son ID
//    public String getChauffeurPrenom(int chauffeurId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_CHAUFFEURS, new String[]{COLUMN_PRENOM_CHAUF},
//                COLUMN_CHAUFFEUR_ID + "=?", new String[]{String.valueOf(chauffeurId)}, null, null, null);
//
//        String prenom = "Prénom inconnu";
//        if (cursor != null) {
//            try {
//                if (cursor.moveToFirst()) {
//                    // Utilisation de getColumnIndexOrThrow pour éviter l'erreur -1
//                    int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_PRENOM_CHAUF);
//                    prenom = cursor.getString(columnIndex);
//                }
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();  // Log l'erreur en cas de problème
//            } finally {
//                cursor.close();
//            }
//        }
//        return prenom;
//    }
//
//    public boolean addReservation(int voyageId, int utilisateurId, int placesReservees) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.beginTransaction();
//        boolean success = false;
//
//        try {
//            // 1. Récupérer le nombre de places disponibles et les réservations actuelles
//            String query = "SELECT " + COLUMN_NB_PLACES_DISPO + ", " + COLUMN_NB_RESERVATION +
//                    " FROM " + TABLE_VOYAGE +
//                    " WHERE " + COLUMN_VOYAGE_ID + " = ?";
//            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(voyageId)});
//
//            if (cursor != null && cursor.moveToFirst()) {
//                int availablePlaces = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NB_PLACES_DISPO));
//                int currentReservations = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NB_RESERVATION));
//                cursor.close();
//
//                // 2. Vérifier si suffisamment de places sont disponibles
//                if (availablePlaces >= placesReservees) {
//                    // Mettre à jour les places disponibles et les réservations
//                    ContentValues values = new ContentValues();
//                    values.put(COLUMN_NB_RESERVATION, currentReservations + placesReservees);
//                    values.put(COLUMN_NB_PLACES_DISPO, availablePlaces - placesReservees);
//                    db.update(TABLE_VOYAGE, values, COLUMN_VOYAGE_ID + " = ?", new String[]{String.valueOf(voyageId)});
//
//                    // 3. Ajouter la réservation dans la table "reservations"
//                    ContentValues reservationValues = new ContentValues();
//                    reservationValues.put("voyage_id", voyageId);
//                    reservationValues.put("utilisateur_id", utilisateurId);
//                    reservationValues.put("heure_reservation", getCurrentTime());  // You can define a method to get the current time
//
//                    db.insert("reservations", null, reservationValues);
//
//                    success = true;
//                    db.setTransactionSuccessful();  // Mark the transaction as successful
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.endTransaction();  // Commit or rollback the transaction
//            db.close();
//        }
//        return success;
//    }
//
//
//    public String getCurrentTime() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        return sdf.format(date);  // Format the date to a string
//    }
//
//
//
//
//    private int getCurrentNbReservations(int voyageId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT " + COLUMN_NB_RESERVATION + " FROM " + TABLE_VOYAGE +
//                " WHERE " + COLUMN_VOYAGE_ID + " = ?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(voyageId)});
//
//        if (cursor != null && cursor.moveToFirst()) {
//            int nbReservations = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NB_RESERVATION));
//            cursor.close();
//            return nbReservations;
//        }
//        if (cursor != null) cursor.close();
//        return 0;
//    }
//    private boolean updateNbReservations(int voyageId, int newNbReservations) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NB_RESERVATION, newNbReservations);
//
//        int rowsUpdated = db.update(TABLE_VOYAGE, values, COLUMN_VOYAGE_ID + " = ?", new String[]{String.valueOf(voyageId)});
//        return rowsUpdated > 0;
//    }
//
//
//
//
//    public Cursor getChauffeurById(int userId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_CHAUFFEURS, null, COLUMN_CHAUFFEUR_ID + "=?",
//                new String[]{String.valueOf(userId)}, null, null, null);
//    }
//
//
//    public boolean modifyChauffeurById(int id, String firstName, String lastName, String phone, String email, String matricule) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(COLUMN_NOM_CHAUF, firstName);
//        values.put(COLUMN_PRENOM_CHAUF, lastName);
//        values.put(COLUMN_TELEPHONE_CHAUF, phone);
//        values.put(COLUMN_EMAIL_CHAUF, email);
//        values.put(COLUMN_MATRICULE_CHAUF, matricule);
//
//        // Mise à jour de la base de données, on spécifie l'ID du chauffeur pour identifier l'enregistrement
//        int rows = db.update(TABLE_CHAUFFEURS, values, COLUMN_CHAUFFEUR_ID + "=?", new String[]{String.valueOf(id)});
//
//        // Retourner true si au moins une ligne a été mise à jour
//        return rows > 0;
//    }
//
//
//
//
//
//}














package com.example.louage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "louage.db";
    private static final int DATABASE_VERSION = 7;


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

    /// Attributs de la table "reservations"
    public static final String TABLE_RESERVATIONS = "reservation";
    public static final String COLUMN_RESERVATION_ID = "reservation_id";
    public static final String COLUMN_VOYAGE_ID_RES = "voyage_id";
    public static final String COLUMN_UTILISATEUR_ID = "utilisateur_id";
    public static final String COLUMN_HEURE_RESERVATION = "heure_reservation";
    public static final String COLUMN_NB_PLACES_RES = "nb_place_reserve";

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




    // Création de la table "reservations" avec les variables
    private static final String CREATE_TABLE_RESERVATIONS =
            "CREATE TABLE "+TABLE_RESERVATIONS+"(" +
                    COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_VOYAGE_ID_RES + " INTEGER NOT NULL, " +
                    COLUMN_UTILISATEUR_ID + " INTEGER NOT NULL, " +
                    COLUMN_NB_PLACES_RES + " INTEGER NOT NULL, " +
                    COLUMN_HEURE_RESERVATION + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + COLUMN_VOYAGE_ID_RES + ") REFERENCES " + TABLE_VOYAGE + "(" + COLUMN_VOYAGE_ID + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + COLUMN_UTILISATEUR_ID + ") REFERENCES " + TABLE_UTILISATEUR + "(" + COLUMN_ID_UTILISATEUR + ") ON DELETE CASCADE" +
                    ");";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHAUFFEURS);
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_VOYAGE);
        db.execSQL(CREATE_TABLE_RESERVATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAUFFEURS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOYAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
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
    public long insertVoyage(int chauffeurId, String from, String to, int nbReservation, int nbPlacesDispo)  {
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




    // Méthode pour obtenir les détails d'un voyage
    public Voyage getVoyageDetails(int voyageId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT v." + COLUMN_FROM + ", v." + COLUMN_TO + ", v." + COLUMN_NB_RESERVATION +
                ", v." + COLUMN_NB_PLACES_DISPO + ", v." + COLUMN_CHAUFFEUR_ID +
                ", c." + COLUMN_NOM_CHAUF + ", c." + COLUMN_PRENOM_CHAUF +
                " FROM " + TABLE_VOYAGE + " v " +
                "JOIN " + TABLE_CHAUFFEURS + " c ON v." + COLUMN_CHAUFFEUR_ID + " = c." + COLUMN_CHAUFFEUR_ID +
                " WHERE v." + COLUMN_VOYAGE_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(voyageId)});
        Voyage voyage = null;

        if (cursor.moveToFirst()) {
            // Assurez-vous que les indices correspondent exactement à l'ordre des colonnes dans la requête
            String from = cursor.getString(0);               // v.from
            String to = cursor.getString(1);                 // v.to
            int nbReservation = cursor.getInt(2);            // v.nb_reservation
            int placesDisponibles = cursor.getInt(3);        // v.nb_places_dispo
            int chauffeurId = cursor.getInt(4);              // v.chauffeur_id
            String chauffeurNom = cursor.getString(5);       // c.nom_chauf
            String chauffeurPrenom = cursor.getString(6);    // c.prenom_chauf

            // Construire l'objet Voyage avec les 8 paramètres
            voyage = new Voyage(voyageId, from, to, nbReservation, placesDisponibles, chauffeurId, chauffeurNom, chauffeurPrenom);
        }

        cursor.close();
        db.close();
        return voyage;
    }


    public Cursor getVoyagesByDestination(String from, String to) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                COLUMN_VOYAGE_ID + ", " +
                COLUMN_CHAUFFEUR_ID + ", " +
                COLUMN_NB_PLACES_DISPO +
                " FROM " + TABLE_VOYAGE +
                " WHERE " + COLUMN_FROM + " = ?" +
                " AND " + COLUMN_TO + " = ?" +
                " AND " + COLUMN_NB_PLACES_DISPO + " > 0";
        return db.rawQuery(query, new String[]{from, to});
    }

    // Méthode pour obtenir le nom du chauffeur à partir de son ID
    public String getChauffeurNom(int chauffeurId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHAUFFEURS, new String[]{COLUMN_NOM_CHAUF},
                COLUMN_CHAUFFEUR_ID + "=?", new String[]{String.valueOf(chauffeurId)}, null, null, null);

        String nom = "Nom inconnu";
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_NOM_CHAUF);
                    nom = cursor.getString(columnIndex);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();  // Affiche une erreur si la colonne n'est pas trouvée
            } finally {
                cursor.close();
            }
        }
        return nom;
    }


    // Méthode pour obtenir le prénom du chauffeur à partir de son ID
    public String getChauffeurPrenom(int chauffeurId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHAUFFEURS, new String[]{COLUMN_PRENOM_CHAUF},
                COLUMN_CHAUFFEUR_ID + "=?", new String[]{String.valueOf(chauffeurId)}, null, null, null);

        String prenom = "Prénom inconnu";
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    // Utilisation de getColumnIndexOrThrow pour éviter l'erreur -1
                    int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_PRENOM_CHAUF);
                    prenom = cursor.getString(columnIndex);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();  // Log l'erreur en cas de problème
            } finally {
                cursor.close();
            }
        }
        return prenom;
    }

    public boolean addReservation(int voyageId, int utilisateurId, int placesReservees) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        boolean success = false;

        try {
            // 1. Récupérer le nombre de places disponibles et les réservations actuelles
            String query = "SELECT " + COLUMN_NB_PLACES_DISPO + ", " + COLUMN_NB_RESERVATION +
                    " FROM " + TABLE_VOYAGE +
                    " WHERE " + COLUMN_VOYAGE_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(voyageId)});

            if (cursor != null && cursor.moveToFirst()) {
                int availablePlaces = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NB_PLACES_DISPO));
                int currentReservations = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NB_RESERVATION));
                cursor.close();

                // 2. Vérifier si suffisamment de places sont disponibles
                if (availablePlaces >= placesReservees) {
                    // Mettre à jour les places disponibles et les réservations
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_NB_RESERVATION, currentReservations + placesReservees);
                    values.put(COLUMN_NB_PLACES_DISPO, availablePlaces - placesReservees);
                    db.update(TABLE_VOYAGE, values, COLUMN_VOYAGE_ID + " = ?", new String[]{String.valueOf(voyageId)});

                    // 3. Ajouter la réservation dans la table "reservations"
                    ContentValues reservationValues = new ContentValues();
                    reservationValues.put(COLUMN_VOYAGE_ID_RES, voyageId);
                    reservationValues.put(COLUMN_UTILISATEUR_ID, utilisateurId);
                    reservationValues.put(COLUMN_NB_PLACES_RES,placesReservees );
                    reservationValues.put(COLUMN_HEURE_RESERVATION, getCurrentTime());  // You can define a method to get the current time

                    db.insert(TABLE_RESERVATIONS, null, reservationValues);

                    success = true;
                    db.setTransactionSuccessful();  // Mark the transaction as successful
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();  // Commit or rollback the transaction
            db.close();
        }
        return success;
    }




    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        return sdf.format(new Date());
    }





    public int getCurrentNbReservations(int voyageId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_NB_PLACES_DISPO + " FROM " + TABLE_VOYAGE +
                " WHERE " + COLUMN_VOYAGE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(voyageId)});

        if (cursor != null && cursor.moveToFirst()) {
            int nbPlaceDispo = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NB_PLACES_DISPO));
            cursor.close();
            return nbPlaceDispo;
        }
        if (cursor != null) cursor.close();
        return 0;
    }

    public Cursor getChauffeurById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CHAUFFEURS, null, COLUMN_CHAUFFEUR_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, null);
    }
    public Cursor getUtulisateurById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_UTILISATEUR, null, COLUMN_UTILISATEUR_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, null);
    }

    public List<Reservation> getReservationsByUserId(int utilisateurId) {
        List<Reservation> reservations = new ArrayList<>();

        // Ouverture de la base de données en lecture
        SQLiteDatabase db = this.getReadableDatabase();

        // Requête SQL pour récupérer les réservations par utilisateur, triées par heure (la plus récente en premier),
        // en incluant les informations "FROM" et "TO" de la table "voyage"
        String query = "SELECT r.*, v." + COLUMN_FROM + ", v." + COLUMN_TO + " FROM " + TABLE_RESERVATIONS + " r " +
                "JOIN " + TABLE_VOYAGE + " v ON r." + COLUMN_VOYAGE_ID_RES + " = v." + COLUMN_VOYAGE_ID + " " +
                "WHERE r." + COLUMN_UTILISATEUR_ID + " = ? " +
                "ORDER BY r." + COLUMN_HEURE_RESERVATION + " DESC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(utilisateurId)});

        // Parcours du curseur pour remplir la liste des réservations
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_ID));
                int voyageId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VOYAGE_ID_RES));
                int nbPlaces = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NB_PLACES_RES));
                String heureReservation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HEURE_RESERVATION));
                String from = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FROM));
                String to = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TO));

                // Création d'une nouvelle réservation et ajout à la liste
                Reservation reservation = new Reservation(id, voyageId, utilisateurId, nbPlaces, heureReservation, from, to);
                reservations.add(reservation);
            } while (cursor.moveToNext());
        }

        // Fermeture du curseur et de la base de données
        cursor.close();
        db.close();

        return reservations;
    }

    public boolean modifyChauffeurById(int id, String firstName, String lastName, String phone, String email, String matricule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOM_CHAUF, firstName);
        values.put(COLUMN_PRENOM_CHAUF, lastName);
        values.put(COLUMN_TELEPHONE_CHAUF, phone);
        values.put(COLUMN_EMAIL_CHAUF, email);
        values.put(COLUMN_MATRICULE_CHAUF, matricule);

        // Mise à jour de la base de données, on spécifie l'ID du chauffeur pour identifier l'enregistrement
        int rows = db.update(TABLE_CHAUFFEURS, values, COLUMN_CHAUFFEUR_ID + "=?", new String[]{String.valueOf(id)});

        // Retourner true si au moins une ligne a été mise à jour
        return rows > 0;
    }

    public boolean modifyUtilisateurById(int id, String firstName, String lastName, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOM_UTILISATEUR, firstName);
        values.put(COLUMN_PRENOM_UTILISATEUR, lastName);
        values.put(COLUMN_TELEPHONE_UTILISATEUR, phone);
        values.put(COLUMN_EMAIL_UTILISATEUR, email);


        // Mise à jour de la base de données, on spécifie l'ID du chauffeur pour identifier l'enregistrement
        int rows = db.update(TABLE_UTILISATEUR, values, COLUMN_ID_UTILISATEUR + "=?", new String[]{String.valueOf(id)});

        // Retourner true si au moins une ligne a été mise à jour
        return rows > 0;
    }

    // Méthode pour annuler la réservation
    public boolean cancelReservation(int reservationId, int voyageId, int nbPlacesToCancel) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Commencer une transaction pour s'assurer que les deux opérations se produisent atomiquement
        db.beginTransaction();
        try {
            // Supprimer la réservation de la table reservations
            int rowsDeleted = db.delete(TABLE_RESERVATIONS, COLUMN_RESERVATION_ID + " = ?", new String[]{String.valueOf(reservationId)});

            if (rowsDeleted > 0) {
                // Calculer le nouveau nombre de réservations et de places disponibles
                // Récupérer le nombre actuel de réservations et de places disponibles
                String query = "SELECT " + COLUMN_NB_RESERVATION + ", " + COLUMN_NB_PLACES_DISPO + " FROM " + TABLE_VOYAGE + " WHERE " + COLUMN_VOYAGE_ID + " = ?";
                Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(voyageId)});
                if (cursor != null && cursor.moveToFirst()) {
                    int currentNbReservations = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NB_RESERVATION));
                    int currentNbPlacesDispo = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NB_PLACES_DISPO));

                    // Mettre à jour les valeurs
                    int newNbReservations = currentNbReservations - 1; // Une réservation annulée
                    int newNbPlacesDispo = currentNbPlacesDispo + nbPlacesToCancel;

                    // Préparer les nouvelles valeurs pour la table voyage
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_NB_RESERVATION, newNbReservations);
                    values.put(COLUMN_NB_PLACES_DISPO, newNbPlacesDispo);

                    // Mettre à jour la table voyage
                    int rowsUpdated = db.update(TABLE_VOYAGE, values, COLUMN_VOYAGE_ID + " = ?", new String[]{String.valueOf(voyageId)});

                    if (rowsUpdated > 0) {
                        // Si les deux mises à jour ont réussi, valider la transaction
                        db.setTransactionSuccessful();
                        return true; // Annulation et mise à jour réussies
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fin de la transaction, elle est validée uniquement si la méthode setTransactionSuccessful() a été appelée
            db.endTransaction();
        }
        return false; // Si l'une des opérations échoue
    }

    // Dans DatabaseHelper.java

    public String getChauffeurPhoneNumber(int voyageId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String phoneNumber = null;

        // Requête SQL pour récupérer le numéro de téléphone du chauffeur à partir de la table 'voyage'
        String query = "SELECT " + COLUMN_TELEPHONE_CHAUF + " FROM " + TABLE_CHAUFFEURS +
                " WHERE " + COLUMN_CHAUFFEUR_ID + " = (SELECT " + COLUMN_CHAUFFEUR_ID +
                " FROM " + TABLE_VOYAGE + " WHERE " + COLUMN_VOYAGE_ID + " = ?)";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(voyageId)});

        if (cursor != null && cursor.moveToFirst()) {
            // Récupérer le numéro de téléphone du chauffeur
            phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEPHONE_CHAUF));
        }

        // Fermer le curseur après utilisation
        if (cursor != null) {
            cursor.close();
        }

        return phoneNumber != null ? phoneNumber : "Numéro non disponible"; // Retourner un message si aucun numéro n'est trouvé
    }







}