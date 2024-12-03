package com.example.louage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ListeVoyagesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayList<String> voyagesList;
    private ArrayList<String> placesList;
    private ArrayList<Integer> voyageIds;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_voyages);
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


        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerViewVoyages);
        voyagesList = new ArrayList<>();
        placesList = new ArrayList<>();
        voyageIds = new ArrayList<>();

        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");

        loadVoyages(from, to);

        VoyageAdapter adapter = new VoyageAdapter(this, voyagesList, placesList ,voyageIds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadVoyages(String from, String to) {
        Cursor cursor = dbHelper.getVoyagesByDestination(from, to);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VOYAGE_ID);
                int chauffeurIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CHAUFFEUR_ID);
                int nbPlaceDispoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NB_PLACES_DISPO);

                if (idIndex != -1 && chauffeurIdIndex != -1 && nbPlaceDispoIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    int chauffeurId = cursor.getInt(chauffeurIdIndex);  // Chauffeur ID
                    int nbPlaceDispo = cursor.getInt(nbPlaceDispoIndex);

                    // Récupérer les détails du chauffeur
                    String chauffeurNom = dbHelper.getChauffeurNom(chauffeurId);
                    String chauffeurPrenom = dbHelper.getChauffeurPrenom(chauffeurId);

                    // Ajouter les détails du voyage à la liste
                    voyagesList.add("Chauffeur: " + chauffeurNom + " " + chauffeurPrenom );
                    placesList.add("Places disponibles: " + nbPlaceDispo);
                    voyageIds.add(id);
                } else {
                    showToast("Erreur lors de la récupération des données.");
                }
            } while (cursor.moveToNext());
        } else {
            showToast("Aucun voyage disponible.");
        }
        if (cursor != null) cursor.close();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void showError(String errorMessage) {
        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_LONG).show();
    }
}
