package com.example.louage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register_driver extends AppCompatActivity {
    private EditText editTextNom, editTextPrenom, editTextMatricule, editTextTelephone, editTextEmail, editTextPassword;
    private Button registerButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        // Initialisation des vues
        editTextNom = findViewById(R.id.nomC);
        editTextPrenom = findViewById(R.id.prenomC);
        editTextMatricule = findViewById(R.id.matriculeC);
        editTextTelephone = findViewById(R.id.telephoneC);
        editTextEmail = findViewById(R.id.emailC);
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
                String matricule = editTextMatricule.getText().toString().trim();
                String telephone = editTextTelephone.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Validation des champs
                if (nom.isEmpty() || prenom.isEmpty() || matricule.isEmpty() || telephone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(register_driver.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(register_driver.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else if (!telephone.matches("^\\+?[0-9]{8,15}$")) { // Format simple pour numéro de téléphone
                    Toast.makeText(register_driver.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    // Enregistrer les données dans la base de données en utilisant la méthode insertChauffeur de DatabaseHelper
                    long result = dbHelper.insertChauffeur(nom, prenom,telephone, email, matricule, password);
                    if (result != -1) {
                        Toast.makeText(register_driver.this, "Driver Registered Successfully", Toast.LENGTH_SHORT).show();
                        // Rediriger vers la page de connexion après l'inscription
                        Intent intent = new Intent(register_driver.this, login_cauffeur.class);
                        startActivity(intent);
                        finish(); // Fermer cette activité
                    } else {
                        Toast.makeText(register_driver.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}