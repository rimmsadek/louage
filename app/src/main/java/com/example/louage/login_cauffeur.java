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



public class login_cauffeur extends AppCompatActivity {

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
                Toast.makeText(login_cauffeur.this, "Enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(login_cauffeur.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérification des informations de connexion
            if (checkLogin(email, password, selectedRole)) {
                Toast.makeText(login_cauffeur.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login_cauffeur.this, interface1_chauffeur.class) ;
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(login_cauffeur.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion du clic pour signup
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(login_cauffeur.this, register_driver.class) ;
            startActivity(intent);
        });
    }

    @SuppressLint("Range")
    private boolean checkLogin(String email, String password, String role) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String hashedPassword = password;
        try {
            db = dbHelper.getReadableDatabase();

            String table = DatabaseHelper.TABLE_CHAUFFEURS;
            String emailColumn = DatabaseHelper.COLUMN_EMAIL_CHAUF;
            String passwordColumn = DatabaseHelper.COLUMN_MOT_DE_PASSE_CHAUF;
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

                // Récupérer l'index de la colonne ID du chauffeur
                int chauffeurIdColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CHAUFFEUR_ID);
                if (chauffeurIdColumnIndex != -1) {
                    // Récupérer l'ID du chauffeur
                    int chauffeurId = cursor.getInt(chauffeurIdColumnIndex);
                    Log.d("LOGIN_ID", "Chauffeur ID récupéré : " + chauffeurId);

                    // Stocker l'ID dans la classe GlobalState
                    GlobalState.getInstance().setChauffeurId(chauffeurId);

                    return true; // Login réussi
                } else {
                    Log.d("LOGIN_ERROR", "La colonne COLUMN_CHAUFFEUR_ID n'existe pas");
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











