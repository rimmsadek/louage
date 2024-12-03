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

public class profil_chauffeur extends AppCompatActivity {



    private TextView tvName, tvPhone, tvEmail, tvMatricule;
    private ImageView imageView, editProfileIcon;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_chauffeur);


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

        // Initialisation des vues
        imageView = findViewById(R.id.profile_image);
        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        tvMatricule = findViewById(R.id.tv_matricule);
        editProfileIcon = findViewById(R.id.editProfileIcon);

        databaseHelper = new DatabaseHelper(this);

        // Charger l'ID du chauffeur à partir de GlobalState
        int chauffeurId = GlobalState.getInstance().getChauffeurId();

        // Charger le profil
        loadDriverProfile(chauffeurId);

        // Ajouter l'événement pour rediriger vers la page de modification
        editProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profil_chauffeur.this, edit_profil_chauffeur.class);
                intent.putExtra("chauffeurId", chauffeurId);
                startActivity(intent);
            }
        });
    }

    private void loadDriverProfile(int chauffeurId) {
        Cursor cursor = databaseHelper.getChauffeurById(chauffeurId);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOM_CHAUF)) + " " +
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRENOM_CHAUF));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEPHONE_CHAUF));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL_CHAUF));
            String matricule = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MATRICULE_CHAUF));

            tvName.setText(name);
            tvPhone.setText(phone);
            tvEmail.setText(email);
            tvMatricule.setText(matricule);

            cursor.close();
        } else {
            Toast.makeText(this, "Aucun profil trouvé", Toast.LENGTH_SHORT).show();
        }
    }
}
