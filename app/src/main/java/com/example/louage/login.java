package com.example.louage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;



public class login extends AppCompatActivity {

    public static final String KEY_ROLE = "role";
    private EditText editTextEmail, editTextPassword;
    private TextView signupText;
    private Button loginButton;
    private DatabaseHelper dbHelper;
    private String selectedRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des vues
        signupText = findViewById(R.id.signupText);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        dbHelper = new DatabaseHelper(this);

        // Récupérer le rôle passé dans l'Intent
        selectedRole = getIntent().getStringExtra(KEY_ROLE);

        // Gestion du clic pour login
        loginButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Validation des champs
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(login.this, "Enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(login.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérification des informations de connexion
            if (checkLogin(email, password, selectedRole)) {
                Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, interface1_utilisateur.class) ;
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion du clic pour signup
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, register_utilisateur.class) ;
            startActivity(intent);
        });
    }

    @SuppressLint("Range")
    private boolean checkLogin(String email, String password, String role) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String hashedPassword = password;  // Appliquez un hachage ici si nécessaire
        try {
            db = dbHelper.getReadableDatabase();

            String table;
            String emailColumn;
            String passwordColumn;
            String idColumn;

            // Vérification selon le rôle
            if ("driver".equals(role)) {
                table = DatabaseHelper.TABLE_CHAUFFEURS;
                emailColumn = DatabaseHelper.COLUMN_EMAIL_CHAUF;
                passwordColumn = DatabaseHelper.COLUMN_MOT_DE_PASSE_CHAUF;
                idColumn = DatabaseHelper.COLUMN_CHAUFFEUR_ID;
            } else {
                table = DatabaseHelper.TABLE_UTILISATEUR;
                emailColumn = DatabaseHelper.COLUMN_EMAIL_UTILISATEUR;
                passwordColumn = DatabaseHelper.COLUMN_MOT_DE_PASSE_UTILISATEUR;
                idColumn = DatabaseHelper.COLUMN_ID_UTILISATEUR;
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
                    Log.d("LOGIN_ID", role + " ID récupéré : " + userId);

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

}











