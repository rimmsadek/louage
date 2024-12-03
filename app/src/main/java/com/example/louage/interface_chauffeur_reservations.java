package com.example.louage;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class interface_chauffeur_reservations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_chauffeur_reservations);

        //**********************************************

        // Configurer la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurer le DrawerLayout et NavigationView
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Ajouter ActionBarDrawerToggle pour gérer l'ouverture/fermeture du drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); // Synchroniser l'état du DrawerToggle

        // Gérer les éléments du menu latéral
        navigationView.setNavigationItemSelectedListener(item -> {


            if (item.getItemId() == R.id.acceuil) {
                Intent intent = new Intent(this, interface1_chauffeur.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_Voyages) {
                Intent intent = new Intent(this, interface_chauffeur_reservations.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.profile) {
                Intent intent = new Intent(this, profil_chauffeur.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_logout) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }



            drawerLayout.closeDrawers(); // Fermer le menu après sélection
            return true;
        });

        //******************************************************************************


        // Conteneur pour les CardView
        LinearLayout container = findViewById(R.id.layout_reservations);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Récupération de l'ID du chauffeur depuis le GlobalState
        int chauffeurId = GlobalState.getInstance().getChauffeurId();

        // Requête pour récupérer les réservations liées à ce chauffeur
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT u.nom, u.prenom, u.telephone " +
                        "FROM reservation r " +
                        "JOIN voyage v ON r.voyage_id = v.voyage_id " +
                        "JOIN utilisateur u ON r.utilisateur_id = u.utilisateur_id " +
                        "WHERE v.chauffeur_id = ?",
                new String[]{String.valueOf(chauffeurId)}
        );

        // Afficher les réservations sous forme de CardView
        if (cursor.moveToFirst()) {
            do {
                String nom = cursor.getString(0);
                String prenom = cursor.getString(1);
                String telephone = cursor.getString(2);

                // Ajouter une CardView pour chaque réservation
                setupCardView(container, nom, prenom, telephone);
            } while (cursor.moveToNext());
        } else {
            // Afficher un message si aucune réservation n'est trouvée
            setupEmptyMessage(container);
        }
        cursor.close();
    }

    private void setupCardView(LinearLayout container, String nom, String prenom, String telephone) {
        // Création d'une CardView pour afficher les détails d'une réservation
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 20, 0, 16);
        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(16);
        cardView.setPadding(16, 16, 16, 16);
        cardView.setCardElevation(8);

        // Conteneur vertical pour le texte et l'icône
        LinearLayout cardContent = new LinearLayout(this);
        cardContent.setOrientation(LinearLayout.HORIZONTAL);
        cardContent.setPadding(8, 8, 8, 8);

        // Ajout d'un TextView avec les détails
        TextView textView = new TextView(this);
        textView.setText("Nom: " + nom + "\n" +
                "Prénom: " + prenom + "\n" +
                "Téléphone: " + telephone);
        textView.setTextSize(16);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        // Ajouter une icône pour l'appel téléphonique
        ImageView callIcon = new ImageView(this);
        callIcon.setImageResource(R.drawable.baseline_local_phone_24); // Assurez-vous d'avoir une icône nommée `ic_call` dans le dossier drawable
        callIcon.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        callIcon.setPadding(16, 0, 16, 0);

        // Ajouter un clic pour passer un appel
        callIcon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + telephone));
            startActivity(intent);
        });

        // Ajouter le texte et l'icône au conteneur
        cardContent.addView(textView);
        cardContent.addView(callIcon);

        // Ajouter le conteneur à la CardView
        cardView.addView(cardContent);

        // Ajouter la CardView au conteneur principal
        container.addView(cardView);
    }

    private void setupEmptyMessage(LinearLayout container) {
        // Afficher un message si aucune réservation n'est trouvée
        TextView textView = new TextView(this);
        textView.setText("Aucune réservation trouvée pour ce chauffeur.");
        textView.setTextSize(18);
        textView.setPadding(16, 16, 16, 16);
        container.addView(textView);
    }
}
