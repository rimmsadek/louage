package com.example.louage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button loginButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        // Initialisation de la base de données
        dbHelper = new DatabaseHelper(this);

        // Gestion du clic sur le bouton de connexion
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Validation des champs
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(login.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Vérification des informations de connexion dans la base de données
                    boolean isValidUser = checkLogin(username, password);
                    if (isValidUser) {
                        Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Rediriger vers une autre activité (par exemple Dashboard)
                        // Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        // startActivity(intent);
                    } else {
                        Toast.makeText(login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Fonction pour vérifier si l'utilisateur existe dans la base de données
    private boolean checkLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { DatabaseHelper.COLUMN_ID_UTILISATEUR, DatabaseHelper.COLUMN_NOM_UTILISATEUR, DatabaseHelper.COLUMN_PRENOM_UTILISATEUR };

        // Effectuer la requête de sélection
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_UTILISATEUR,   // Table à interroger
                projection,                         // Colonnes à retourner
                DatabaseHelper.COLUMN_EMAIL_UTILISATEUR + "=? AND " + DatabaseHelper.COLUMN_TELEPHONE_UTILISATEUR + "=?",  // Sélection
                new String[]{username, password},  // Valeurs des paramètres
                null,                               // Grouper les résultats
                null,                               // Trier les résultats
                null                                // Ordre
        );

        // Si un utilisateur est trouvé avec ces informations de connexion
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }
}
