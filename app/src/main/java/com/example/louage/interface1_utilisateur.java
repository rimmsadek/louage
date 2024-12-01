package com.example.louage;
import com.example.louage.R;
import android.os.Bundle;
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


public class interface1_utilisateur extends AppCompatActivity {

    private static final String[] GOVERNORATS = {
            "Tunis", "Ariana", "Ben Arous", "Manouba", "Bizerte", "Beja", "Jendouba", "Kef",
            "Siliana", "Zaghouan", "Nabeul", "Sousse", "Monastir", "Mahdia", "Kairouan",
            "Kasserine", "Sidi Bouzid", "Gabes", "Mednine", "Tataouine", "Gafsa", "Tozeur",
            "Kebili", "Sfax"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface1_utilisateur);

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



        // Trouver les Spinners et le bouton
        Spinner fromSpinner = findViewById(R.id.fromSpinner);
        Spinner toSpinner = findViewById(R.id.toSpinner);
        Button submitButton = findViewById(R.id.submitButton);

        // Créer un adaptateur pour les listes déroulantes
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, GOVERNORATS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Associer l'adaptateur aux Spinners
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Gérer le clic sur le bouton "Valider"
        submitButton.setOnClickListener(v -> {
            String from = fromSpinner.getSelectedItem() != null ? fromSpinner.getSelectedItem().toString() : null;
            String to = toSpinner.getSelectedItem() != null ? toSpinner.getSelectedItem().toString() : null;

            if (from == null || to == null) {
                showError("Erreur lors de la sélection des destinations !");
            } else if (from.equals(to)) {
                showToast("Veuillez choisir des destinations différentes.");
            } else {
                // Passer les destinations choisies à l'activité suivante
                Intent intent = new Intent(this, ListeVoyagesActivity.class);
                intent.putExtra("from", from);
                intent.putExtra("to", to);
                startActivity(intent);
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