package com.example.louage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class profile_utilisateur extends AppCompatActivity {



    private TextView tvName, tvPhone, tvEmail, tvMatricule;
    private ImageView imageView, editProfileIcon;
    private DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_utilisateur);

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

        // Initialisation des vues
        imageView = findViewById(R.id.profile_image);
        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        editProfileIcon = findViewById(R.id.editProfileIcon);

        databaseHelper = new DatabaseHelper(this);

        // Charger l'ID du utilisateur à partir de GlobalState
        int utilisateurId = GlobalState.getInstance().getUtilisateurId();

        // Charger le profil
        loadDriverProfile(utilisateurId);

        // Ajouter l'événement pour rediriger vers la page de modification
        editProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_utilisateur.this, edit_profil_utilisateur.class);
                intent.putExtra("utilisateurId", utilisateurId);
                startActivity(intent);
            }
        });
    }

    private void loadDriverProfile(int utilisateurId) {
        Cursor cursor = databaseHelper.getUtulisateurById(utilisateurId);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOM_UTILISATEUR)) + " " +
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRENOM_UTILISATEUR));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEPHONE_UTILISATEUR));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL_UTILISATEUR));


            tvName.setText(name);
            tvPhone.setText(phone);
            tvEmail.setText(email);


            cursor.close();
        } else {
            Toast.makeText(this, "Aucun profil trouvé", Toast.LENGTH_SHORT).show();
        }
    }
    private void showError(String errorMessage) {
        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_LONG).show();
    }
}
