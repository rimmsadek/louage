package com.example.louage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register_driver extends AppCompatActivity {
    public static final String KEY_ROLE = "role"; // Constante pour le rôle
    private String selectedRole;
    private EditText editTextNom, editTextPrenom, editTextMatricule, editTextTelephone, editTextEmail;
    private Button registerButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        // Récupérer le rôle


        // Initialisation des vues
        editTextNom = findViewById(R.id.nomC);
        editTextPrenom = findViewById(R.id.prenomC);
        editTextMatricule = findViewById(R.id.matriculeC);
        editTextTelephone = findViewById(R.id.telephoneC);
        editTextEmail = findViewById(R.id.emailC);
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

                // Validation des champs
                if (nom.isEmpty() || prenom.isEmpty() || matricule.isEmpty() || telephone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(register_driver.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(register_driver.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (telephone.length() != 8 || !telephone.matches("\\d+")) {
                    Toast.makeText(register_driver.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Enregistrer les données dans la base
                boolean result = insertUserData(nom, prenom, matricule, telephone, email);
                if (result) {
                    Toast.makeText(register_driver.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register_driver.this, login_cauffeur.class);
                    intent.putExtra(KEY_ROLE, selectedRole); // Passer le rôle
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(register_driver.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Fonction pour insérer les données dans la base de données
    private boolean insertUserData(String nom, String prenom, String matricule, String telephone, String email) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            // Ajouter les données
            values.put(DatabaseHelper.COLUMN_NOM_CHAUF, nom);
            values.put(DatabaseHelper.COLUMN_PRENOM_CHAUF, prenom);
            values.put(DatabaseHelper.COLUMN_MATRICULE_CHAUF, matricule);
            values.put(DatabaseHelper.COLUMN_TELEPHONE_CHAUF, telephone);
            values.put(DatabaseHelper.COLUMN_EMAIL_CHAUF, email);

            // Insérer dans la base
            long result = db.insert(DatabaseHelper.TABLE_CHAUFFEURS, null, values);
            return result != -1; // Retourne true si succès
        } catch (Exception e) {
            e.printStackTrace();
            return false; // En cas d'erreur
        } finally {
            if (db != null) db.close(); // Assure la fermeture
        }
    }
}
