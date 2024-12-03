package com.example.louage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.louage.DatabaseHelper;
import com.example.louage.GlobalState;
import com.example.louage.profile_utilisateur;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;


public class edit_profil_utilisateur extends AppCompatActivity {

    private TextInputEditText etFirstName, etLastName, etPhone, etEmail;
    private Button btnSaveChanges;
    private DatabaseHelper databaseHelper;
    private int utilisateurId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_utilisateur);

        // Configurer la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurer le DrawerLayout et NavigationView
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Vérifier la configuration du menu latéral
        if (drawerLayout == null || navigationView == null) {
            showError("Erreur dans la configuration du menu latéral !");
            return;
        }

        // Ajouter ActionBarDrawerToggle pour gérer l'ouverture/fermeture du drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); // Synchroniser l'état du DrawerToggle


        // Gérer les éléments du menu latéral
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                Toast.makeText(this, "Accueil sélectionné", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ListeVoyagesActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_profile) {
                Toast.makeText(this, "Profil sélectionné", Toast.LENGTH_SHORT).show();
                // Démarrer l'activité Profil
                Intent intent = new Intent(this, profile_utilisateur.class); // Remplacez ProfilActivity par le nom de votre activité de profil
                startActivity(intent);
            } else if (item.getItemId() == R.id.choixreservation) {
                Toast.makeText(this, "Choix de réservation sélectionnés", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, interface1_utilisateur.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.mesreservation) {
                Toast.makeText(this, "Mes réservations sélectionnées", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ReservationsActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_logout) {
                Toast.makeText(this, "Déconnexion sélectionnée", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class); // Par exemple, revenir à l'écran de connexion
                startActivity(intent);
            }

            drawerLayout.closeDrawers();
            return true;
        });


        // Initialisation des vues avec les ID correspondants
        etFirstName = findViewById(R.id.editTextFirstName);
        etLastName = findViewById(R.id.editTextLastName);
        etPhone = findViewById(R.id.editTextPhone);
        etEmail = findViewById(R.id.editTextEmail);
        btnSaveChanges = findViewById(R.id.buttonSaveChanges);

        databaseHelper = new DatabaseHelper(this);

        // 'ID du utilisateur à partir de GlobalState
        utilisateurId = GlobalState.getInstance().getUtilisateurId();

        //appel de la fonction loadDriverProfile
        loadUserProfile(utilisateurId);

        // sauvegarder bel methode saveChanges
        btnSaveChanges.setOnClickListener(view -> saveChanges());
    }

    // Charger les informations du utilisateur pour les afficher
    private void loadUserProfile(int utilisateurId) {
        Cursor cursor = databaseHelper.getUtulisateurById(utilisateurId);

        if (cursor != null && cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOM_UTILISATEUR));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRENOM_UTILISATEUR));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEPHONE_UTILISATEUR));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL_UTILISATEUR));


            // Afficher les informations du utilisateur dans les champs de texte
            etFirstName.setText(firstName);
            etLastName.setText(lastName);
            etPhone.setText(phone);
            etEmail.setText(email);


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


        // Mettre à jour les informations du utilisateur dans la base de données
        boolean isUpdated = databaseHelper.modifyUtilisateurById(utilisateurId, firstName, lastName, phone, email);
        if (isUpdated) {
            Toast.makeText(this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show();

            // Naviguer vers la page de profil après la mise à jour
            Intent intent = new Intent(this, profile_utilisateur.class);
            startActivity(intent);  // Lancez l'activité
            finish();  // Ferme l'activité actuelle pour ne pas revenir en arrière
        } else {
            Toast.makeText(this, "Erreur lors de la mise à jour du profil", Toast.LENGTH_SHORT).show();
        }


    }
    public void showError(String errorMessage) {
        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_LONG).show();
    }
}
