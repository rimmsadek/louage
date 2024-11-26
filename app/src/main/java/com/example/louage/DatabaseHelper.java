package com.example.louage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "louage.db";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_UTILISATEUR = "utilisateur";
    public static final String COLUMN_UTILISATEUR_ID = "utilisateur_id";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_PRENOM = "prenom";
    public static final String COLUMN_TELEPHONE = "telephone";
    public static final String COLUMN_EMAIL = "email";




    // SQL query to create the "utilisateur" table
    private static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE " + TABLE_UTILISATEUR + " (" +
                    COLUMN_UTILISATEUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOM + " TEXT NOT NULL, " +
                    COLUMN_PRENOM + " TEXT NOT NULL, " +
                    COLUMN_TELEPHONE + " TEXT NOT NULL, " +
                    COLUMN_EMAIL  + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables

        db.execSQL(CREATE_TABLE_UTILISATEUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        // Recreate the tables
        onCreate(db);
    }
}

