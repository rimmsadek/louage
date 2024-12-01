package com.example.louage;

import android.content.Intent;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private TextView signupText;
    private Button loginButton;
    private DatabaseHelper dbHelper;
    private String selectedRole;
    private int currentUserId; // ID de l'utilisateur connecté


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Récupérer le rôle passé depuis MainActivity
        selectedRole = getIntent().getStringExtra("role");

        // Initialisation des vues
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);


        // Initialisation de la base de données
        dbHelper = new DatabaseHelper(this);

        // Gestion du clic sur le bouton de connexion
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // Validation des champs
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(login.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Vérification des informations de connexion dans la base de données selon le rôle
                    boolean isValidLogin = checkLogin(email, password, selectedRole);
                    if (isValidLogin) {
                        Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Rediriger vers le Dashboard correspondant
                        if (selectedRole.equals("driver")) {
                            Intent intent = new Intent(login.this, interface1_chauffeur.class);
                            startActivity(intent);
                        } else if (selectedRole.equals("user")) {
                            Intent intent = new Intent(login.this, interface1_utilisateur.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Gestion du clic sur le TextView pour l'inscription
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité d'inscription selon le rôle sélectionné
                if (selectedRole.equals("driver")) {
                    Intent intent = new Intent(login.this, register_driver.class);
                    startActivity(intent);
                } else if (selectedRole.equals("user")) {
                    Intent intent = new Intent(login.this, register_utilisateur.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Fonction pour vérifier les informations de connexion
//    private boolean checkLogin(String email, String password, String role) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = null;
//
//        if (role.equals("driver")) {
//            // Vérifier dans la table chauffeur
//            cursor = db.query(
//                    DatabaseHelper.TABLE_CHAUFFEURS,  // Table à interroger
//                    new String[]{DatabaseHelper.COLUMN_CHAUFFEUR_ID, DatabaseHelper.COLUMN_NOM_CHAUF},
//                    DatabaseHelper.COLUMN_EMAIL_CHAUF + "=? AND " + DatabaseHelper.COLUMN_MOT_DE_PASSE_CHAUF + "=?",
//                    new String[]{email, password},
//                    null, null, null);
//        } else if (role.equals("user")) {
//            // Vérifier dans la table utilisateur
//            cursor = db.query(
//                    DatabaseHelper.TABLE_UTILISATEUR,  // Table à interroger
//                    new String[]{DatabaseHelper.COLUMN_ID_UTILISATEUR, DatabaseHelper.COLUMN_NOM_UTILISATEUR},
//                    DatabaseHelper.COLUMN_EMAIL_UTILISATEUR + "=? AND " + DatabaseHelper.COLUMN_MOT_DE_PASSE_UTILISATEUR + "=?",
//                    new String[]{email, password},
//                    null, null, null);
//        }
//
//        // Vérification de l'existence du compte dans la table appropriée
//        boolean loginValid = cursor != null && cursor.getCount() > 0;
//        if (cursor != null) {
//            cursor.close();
//        }
//        return loginValid;
//    }


    private boolean checkLogin(String email, String password, String role) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String hashedPassword = password; // Appliquez un hachage ici si nécessaire
        try {
            db = dbHelper.getReadableDatabase();

            // Définir les valeurs selon le rôle (utilisateur ou chauffeur)
            String table;
            String emailColumn;
            String passwordColumn;
            String idColumn;

            if ("driver".equals(role)) {
                table = DatabaseHelper.TABLE_CHAUFFEURS;
                emailColumn = DatabaseHelper.COLUMN_EMAIL_CHAUF;
                passwordColumn = DatabaseHelper.COLUMN_MOT_DE_PASSE_CHAUF;
                idColumn = DatabaseHelper.COLUMN_CHAUFFEUR_ID;
            } else if ("user".equals(role)) {
                table = DatabaseHelper.TABLE_UTILISATEUR;
                emailColumn = DatabaseHelper.COLUMN_EMAIL_UTILISATEUR;
                passwordColumn = DatabaseHelper.COLUMN_MOT_DE_PASSE_UTILISATEUR;
                idColumn = DatabaseHelper.COLUMN_ID_UTILISATEUR;
            } else {
                Log.e("LOGIN_ERROR", "Rôle invalide");
                return false;
            }

            // Exécuter la requête pour vérifier les informations
            cursor = db.query(
                    table,
                    null,
                    emailColumn + "=? AND " + passwordColumn + "=?",
                    new String[]{email, hashedPassword},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                // Récupérer l'index de la colonne ID
                int userIdColumnIndex = cursor.getColumnIndex(idColumn);
                if (userIdColumnIndex != -1) {
                    // Récupérer l'ID de l'utilisateur
                    int userId = cursor.getInt(userIdColumnIndex);
                    Log.d("LOGIN_ID", "Utilisateur ID récupéré : " + userId);

                    // Stocker l'ID dans la classe GlobalState
                    if ("driver".equals(role)) {
                        GlobalState.getInstance().setChauffeurId(userId);
                    } else if ("user".equals(role)) {
                        GlobalState.getInstance().setUtilisateurId(userId);
                    }

                    return true; // Login réussi
                } else {
                    Log.d("LOGIN_ERROR", "La colonne " + idColumn + " n'existe pas");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DB_ERROR", "Error while checking login", e); // Log d'erreur
            return false;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return false;
    }





    //
}