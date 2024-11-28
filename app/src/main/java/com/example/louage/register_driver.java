package com.example.louage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register_driver extends AppCompatActivity {
    private EditText editTextNom, editTextPrenom, editTextMatricule, editTextTelephone, editTextEmail;
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
        editTextTelephone= findViewById(R.id.telephoneC);
        editTextEmail = findViewById(R.id.emailC);
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
                String matricule = editTextMatricule.getText().toString();
                String telephone = editTextTelephone.getText().toString();
                String email = editTextEmail.getText().toString();

                // Validation des champs
                if (nom.isEmpty() || prenom.isEmpty() || matricule.isEmpty() || email.isEmpty()) {
                    Toast.makeText(register_driver.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Enregistrer les données dans la base de données
                    boolean result = insertUserData(nom, prenom, matricule,telephone, email);
                    if (result) {
                        Toast.makeText(register_driver.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(register_driver.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Fonction pour insérer les données dans la base de données
    private boolean insertUserData(String nom, String prenom, String matricule,String telephone, String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Ajouter les données aux ContentValues
        values.put(DatabaseHelper.COLUMN_NOM_CHAUF , nom);
        values.put(DatabaseHelper.COLUMN_PRENOM_CHAUF , prenom);
        values.put(DatabaseHelper.COLUMN_MATRICULE_CHAUF , matricule);
        values.put(DatabaseHelper.COLUMN_TELEPHONE_CHAUF , telephone);
        values.put(DatabaseHelper.COLUMN_EMAIL_CHAUF , email);



        // Insertion dans la base de données
        long result = db.insert(DatabaseHelper.TABLE_CHAUFFEURS, null, values);

        // Si l'insertion échoue (result == -1), retour false
        return result != -1;
    }
}