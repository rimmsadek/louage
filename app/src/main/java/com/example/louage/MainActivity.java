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

        // Lancer l'activité LoginActivity avec le rôle passé en paramètre
        Intent intent = new Intent(MainActivity.this, login.class); // Corrected class name
        intent.putExtra(KEY_ROLE, role);  // Passer le rôle à LoginActivity
        startActivity(intent);
    }
}

