package com.example.louage;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
}
