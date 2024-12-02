package com.example.louage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.louage.DatabaseHelper;
import com.example.louage.GlobalState;
import com.example.louage.profil_chauffeur;
import com.google.android.material.textfield.TextInputEditText;

public class edit_profil_chauffeur extends AppCompatActivity {

    private TextInputEditText etFirstName, etLastName, etPhone, etEmail, etMatricule;
    private Button btnSaveChanges;
    private DatabaseHelper databaseHelper;
    private int chauffeurId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_chauffeur);

        // Initialisation des vues avec les ID correspondants
        etFirstName = findViewById(R.id.editTextFirstName);
        etLastName = findViewById(R.id.editTextLastName);
        etPhone = findViewById(R.id.editTextPhone);
        etEmail = findViewById(R.id.editTextEmail);
        etMatricule = findViewById(R.id.editTextMatricule);
        btnSaveChanges = findViewById(R.id.buttonSaveChanges);

        databaseHelper = new DatabaseHelper(this);

        // 'ID du chauffeur à partir de GlobalState
        chauffeurId = GlobalState.getInstance().getChauffeurId();

        //appel de la fonction loadDriverProfile
        loadDriverProfile(chauffeurId);

        // sauvegarder bel methode saveChanges
        btnSaveChanges.setOnClickListener(view -> saveChanges());
    }

    // Charger les informations du chauffeur pour les afficher
    private void loadDriverProfile(int chauffeurId) {
        Cursor cursor = databaseHelper.getChauffeurById(chauffeurId);

        if (cursor != null && cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOM_CHAUF));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRENOM_CHAUF));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEPHONE_CHAUF));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL_CHAUF));
            String matricule = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MATRICULE_CHAUF));

            // Afficher les informations du chauffeur dans les champs de texte
            etFirstName.setText(firstName);
            etLastName.setText(lastName);
            etPhone.setText(phone);
            etEmail.setText(email);
            etMatricule.setText(matricule);

            cursor.close();
        } else {
            Toast.makeText(this, "Aucun profil trouvé", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveChanges() {
        // Récupérer les valeurs des champs de texte
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String matricule = etMatricule.getText().toString();

        // Mettre à jour les informations du chauffeur dans la base de données
        boolean isUpdated = databaseHelper.modifyChauffeurById(chauffeurId, firstName, lastName, phone, email, matricule);
        if (isUpdated) {
            Toast.makeText(this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show();

            // Naviguer vers la page de profil après la mise à jour
            Intent intent = new Intent(this, profil_chauffeur.class);
            startActivity(intent);  // Lancez l'activité
            finish();  // Ferme l'activité actuelle pour ne pas revenir en arrière
        } else {
            Toast.makeText(this, "Erreur lors de la mise à jour du profil", Toast.LENGTH_SHORT).show();
        }
    }
}
