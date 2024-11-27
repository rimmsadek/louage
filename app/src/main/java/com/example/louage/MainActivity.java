package com.example.louage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPreferences";
    private static final String KEY_ROLE = "role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Charger et appliquer le thème précédemment choisi
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String selectedRole = sharedPreferences.getString(KEY_ROLE, "");

        if (!selectedRole.isEmpty()) {
            // Lancer l'activité correspondante en fonction du rôle
            redirectUserBasedOnRole(selectedRole);
        }

        // Initialisation des vues à partir de leurs ID
        View userCard = findViewById(R.id.userCard);
        View driverCard = findViewById(R.id.driverCard);

        // Définir des événements de clic
        userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRoleSelected(view);
            }
        });

        driverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRoleSelected(view);
            }
        });
    }

    public void onRoleSelected(View view) {
        String role = "";

        if (view.getId() == R.id.userCard) {
            role = "user";
        } else if (view.getId() == R.id.driverCard) {
            role = "driver";
        } else {
            Toast.makeText(this, "Invalid selection", Toast.LENGTH_SHORT).show();
            return;
        }

        // Enregistrer le rôle choisi dans SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ROLE, role);
        editor.apply();

        // Lancer l'activité appropriée en fonction du rôle choisi
        redirectUserBasedOnRole(role);
    }


    private void redirectUserBasedOnRole(String role) {
        if (role.equals("user")) {
            // Rediriger vers l'activité utilisateur (par exemple, utilisateur_dashboard)
            Intent intent = new Intent(MainActivity.this, UserDashboardActivity.class);
            startActivity(intent);
        } else if (role.equals("driver")) {
            // Rediriger vers l'activité chauffeur (par exemple, driver_dashboard)
            Intent intent = new Intent(MainActivity.this, DriverDashboardActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
        }
    }
}
