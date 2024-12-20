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

        // Charger le rôle sauvegardé (si nécessaire)
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String selectedRole = sharedPreferences.getString(KEY_ROLE, "");

        // Initialisation des vues à partir de leurs ID
        View userCard = findViewById(R.id.userCard);
        View driverCard = findViewById(R.id.driverCard);

        // Définir des événements de clic
        userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLogin("user");
            }
        });

        driverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLogin("driver");
            }
        });
    }

    private void navigateToLogin(String role) {
        // Enregistrer le rôle choisi dans SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ROLE, role);
        editor.apply();

        // Lancer l'activité correspondante en fonction du rôle
        Intent intent;
        if ("user".equals(role)) {
            intent = new Intent(MainActivity.this, login.class);  // Pour les utilisateurs
        } else if ("driver".equals(role)) {
            intent = new Intent(MainActivity.this, login_cauffeur.class);  // Pour les chauffeurs
        } else {
            Toast.makeText(this, "Invalid role selected", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(KEY_ROLE, role);  // Passer le rôle
        startActivity(intent);
    }
}
