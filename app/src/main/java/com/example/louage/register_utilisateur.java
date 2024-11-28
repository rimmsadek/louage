package com.example.louage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class register_utilisateur extends AppCompatActivity {

private EditText editTextNom, editTextPrenom, editTextTelephone, editTextEmail;
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
    registerButton = findViewById(R.id.registerButton);

    // Initialisation de la base de données
    dbHelper = new DatabaseHelper(this);

    // Gestion du clic sur le bouton d'enregistrement
    registerButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Récupérer les valeurs des champs
            String nom = editTextNom.getText().toString();
            String prenom = editTextPrenom.getText().toString();
            String telephone = editTextTelephone.getText().toString();
            String email = editTextEmail.getText().toString();

            // Validation des champs
            if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty()) {
                Toast.makeText(register_utilisateur.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Enregistrer les données dans la base de données
                boolean result = insertUserData(nom, prenom, telephone, email);
                if (result) {
                    Toast.makeText(register_utilisateur.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(register_utilisateur.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });
}

// Fonction pour insérer les données dans la base de données
private boolean insertUserData(String nom, String prenom, String telephone, String email) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues values = new ContentValues();

    // Ajouter les données aux ContentValues
    values.put(DatabaseHelper.COLUMN_NOM_UTILISATEUR , nom);
    values.put(DatabaseHelper.COLUMN_PRENOM_UTILISATEUR , prenom);
    values.put(DatabaseHelper.COLUMN_TELEPHONE_UTILISATEUR , telephone);
    values.put(DatabaseHelper.COLUMN_EMAIL_UTILISATEUR , email);

    // Insertion dans la base de données
    long result = db.insert(DatabaseHelper.TABLE_UTILISATEUR, null, values);

    // Si l'insertion échoue (result == -1), retour false
    return result != -1;
    }
    }


