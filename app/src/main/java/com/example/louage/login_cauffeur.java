package com.example.louage;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class login_cauffeur extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private TextView signupText;
    private Button loginButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cauffeur);

        // Initialisation des vues
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signupText = findViewById(R.id.signupText);
        loginButton = findViewById(R.id.loginButton);

        dbHelper = new DatabaseHelper(this);

        // Gestion du clic sur le bouton de connexion
        loginButton.setOnClickListener(v -> handleLogin());

        // Gestion du clic sur le texte pour l'inscription
        signupText.setOnClickListener(v -> startActivity(new Intent(login_cauffeur.this, register_driver.class)));
    }

    // Fonction de gestion du login
    private void handleLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        Log.d("LOGIN_ID", "Utilisateur ID récupéré : " +email );

        // Validation des champs
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un email et un mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Format de l'email invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérification des informations de connexion
        int userId = checkLogin(email, password);
        if (userId != -1) {
            Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();
            GlobalState.getInstance().setChauffeurId(userId);
            Log.d("LOGIN_ID", "Chauffeur ID récupéré : " + userId);
            startActivity(new Intent(login_cauffeur.this, interface1_chauffeur.class));
            finish();  // Terminer l'activité actuelle
        } else {
            Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    // Fonction pour vérifier les informations de connexion et retourner l'ID chauffeur
    @SuppressLint("Range")
    private int checkLogin(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        int userId = -1;  // Retourner -1 si la connexion échoue

        try {
            cursor = db.query(
                    DatabaseHelper.TABLE_CHAUFFEURS,
                    new String[]{DatabaseHelper.COLUMN_CHAUFFEUR_ID},
                    DatabaseHelper.COLUMN_EMAIL_CHAUF + "=? AND " + DatabaseHelper.COLUMN_MOT_DE_PASSE_CHAUF + "=?",
                    new String[]{email, password},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHAUFFEUR_ID));
            }
        } catch (Exception e) {
            Log.e("LOGIN_ERROR", "Erreur lors de la vérification du login chauffeur", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userId;
    }
}
