package com.example.louage;

import android.content.Intent;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    private boolean checkLogin(String email, String password, String role) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        if (role.equals("driver")) {
            // Vérifier dans la table chauffeur
            cursor = db.query(
                    DatabaseHelper.TABLE_CHAUFFEURS,  // Table à interroger
                    new String[]{DatabaseHelper.COLUMN_CHAUFFEUR_ID, DatabaseHelper.COLUMN_NOM_CHAUF},
                    DatabaseHelper.COLUMN_EMAIL_CHAUF + "=? AND " + DatabaseHelper.COLUMN_MOT_DE_PASSE_CHAUF + "=?",
                    new String[]{email, password},
                    null, null, null);
        } else if (role.equals("user")) {
            // Vérifier dans la table utilisateur
            cursor = db.query(
                    DatabaseHelper.TABLE_UTILISATEUR,  // Table à interroger
                    new String[]{DatabaseHelper.COLUMN_ID_UTILISATEUR, DatabaseHelper.COLUMN_NOM_UTILISATEUR},
                    DatabaseHelper.COLUMN_EMAIL_UTILISATEUR + "=? AND " + DatabaseHelper.COLUMN_MOT_DE_PASSE_UTILISATEUR + "=?",
                    new String[]{email, password},
                    null, null, null);
        }

        // Vérification de l'existence du compte dans la table appropriée
        boolean loginValid = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return loginValid;
    }
}