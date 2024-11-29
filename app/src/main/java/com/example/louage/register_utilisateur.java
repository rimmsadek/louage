package com.example.louage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register_utilisateur extends AppCompatActivity {

    private EditText editTextNom, editTextPrenom, editTextTelephone, editTextEmail,editTextPassword;
    private Button registerButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_utilisateur);

        // Initialisation des vues
        editTextNom = findViewById(R.id.nom);
        editTextPrenom = findViewById(R.id.prenom);
        editTextTelephone = findViewById(R.id.telephone);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        // Initialisation de la base de données
        dbHelper = new DatabaseHelper(this);

        // Gestion du clic sur le bouton d'enregistrement
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs
                String nom = editTextNom.getText().toString().trim();
                String prenom = editTextPrenom.getText().toString().trim();
                String telephone = editTextTelephone.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Validation des champs
                if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(register_utilisateur.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(register_utilisateur.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else if (!telephone.matches("^\\+?[0-9]{8,15}$")) { // Vérification simple du numéro de téléphone
                    Toast.makeText(register_utilisateur.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    // Enregistrer les données dans la base de données
                    boolean result = insertUserData(nom, prenom, telephone, email, password);
                    if (result) {
                        Toast.makeText(register_utilisateur.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                        // Redirection vers la page de connexion après l'enregistrement
                        Intent intent = new Intent(register_utilisateur.this, login.class);
                        intent.putExtra("role", "user"); // Passer le rôle pour la connexion
                        startActivity(intent);
                        finish(); // Fermer l'activité actuelle
                    } else {
                        Toast.makeText(register_utilisateur.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Fonction pour insérer les données dans la base de données
    private boolean insertUserData(String nom, String prenom, String telephone, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Ajouter les données aux ContentValues
        values.put(DatabaseHelper.COLUMN_NOM_UTILISATEUR, nom);
        values.put(DatabaseHelper.COLUMN_PRENOM_UTILISATEUR, prenom);
        values.put(DatabaseHelper.COLUMN_TELEPHONE_UTILISATEUR, telephone);
        values.put(DatabaseHelper.COLUMN_EMAIL_UTILISATEUR, email);
        values.put(DatabaseHelper.COLUMN_MOT_DE_PASSE_UTILISATEUR, password);

        // Insertion dans la base de données
        long result = -1;
        try {
            result = db.insert(DatabaseHelper.TABLE_UTILISATEUR, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(register_utilisateur.this, "Database Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return result != -1;
    }
}
