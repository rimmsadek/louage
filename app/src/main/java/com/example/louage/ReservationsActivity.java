package com.example.louage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;
    private List<Reservation> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
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

        recyclerView = findViewById(R.id.recyclerViewReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Exemple de données (vous pouvez les charger depuis la base de données)
        reservations = loadReservationsFromDatabase();

        reservationAdapter = new ReservationAdapter(reservations, this);
        recyclerView.setAdapter(reservationAdapter);
    }

    // Méthode pour charger les réservations depuis la base de données
    private List<Reservation> loadReservationsFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        return dbHelper.getReservationsByUserId(1); // Exemple avec l'ID utilisateur 1
    }
    private void showError(String errorMessage) {
        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_LONG).show();
    }
}
