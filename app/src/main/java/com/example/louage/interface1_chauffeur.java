

package com.example.louage;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;




public class interface1_chauffeur extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Spinner fromSpinner, toSpinner;

    // Liste des 24 gouvernorats
    private static final String[] GOVERNORATS = {
            "Tunis", "Ariana", "Ben Arous", "Manouba", "Bizerte", "Beja", "Jendouba", "Kef",
            "Siliana", "Zaghouan", "Nabeul", "Sousse", "Monastir", "Mahdia", "Kairouan",
            "Kasserine", "Sidi Bouzid", "Gabes", "Mednine", "Tataouine", "Gafsa", "Tozeur",
            "Kebili", "Sfax"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface1_chauffeur);

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
            } else if (item.getItemId() == R.id.nav_profile) {
                Toast.makeText(this, "Profil sélectionné", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.nav_settings) {
                Toast.makeText(this, "Paramètres sélectionnés", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.nav_logout) {
                Toast.makeText(this, "Déconnexion sélectionnée", Toast.LENGTH_SHORT).show();
            }

            drawerLayout.closeDrawers(); // Fermer le menu après sélection
            return true;
        });


        dbHelper = new DatabaseHelper(this);

        // Initialisation des composants
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        NumberPicker placesNumberPicker = findViewById(R.id.placesNumberPicker);
        Button submitButton = findViewById(R.id.submitButton);

        // Configurer le NumberPicker pour les places
        placesNumberPicker.setMinValue(1);
        placesNumberPicker.setMaxValue(8);

        // Créer un adaptateur pour les listes déroulantes (destinations)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, GOVERNORATS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Associer l'adaptateur aux Spinners de destinations
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Gérer le clic sur le bouton "Valider"
        submitButton.setOnClickListener(v -> {
            // Récupérer les destinations
            String from = fromSpinner.getSelectedItem().toString();
            String to = toSpinner.getSelectedItem().toString();
            int selectedPlaces = placesNumberPicker.getValue();

            // Vérifier si les destinations sont différentes
            if (from.equals(to)) {
                Toast.makeText(interface1_chauffeur.this, "Veuillez choisir des destinations différentes.", Toast.LENGTH_SHORT).show();
            } else {
                // Récupérer l'ID du chauffeur
                int currentChauffeurId = GlobalState.getInstance().getChauffeurId();
                Log.e("CURRENT_ID", "ID du chauffeur actuel : " + currentChauffeurId);

                // Insérer le voyage dans la base de données
                long result = dbHelper.insertVoyage(currentChauffeurId, from, to,0, selectedPlaces);

                // Vérifier si l'insertion a réussi
                if (result != -1) {
                    Toast.makeText(interface1_chauffeur.this, "Voyage enregistré avec succès.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(interface1_chauffeur.this, "Erreur lors de l'enregistrement.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_LONG).show();
    }
}


