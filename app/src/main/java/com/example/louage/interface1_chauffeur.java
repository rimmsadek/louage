package com.example.louage;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    //private static final int CHAUFFEUR_ID = 1; // ID statique du chauffeur (à remplacer dynamiquement si nécessaire)

    int current_CHAUFFEUR_ID = GlobalState.getInstance().getChauffeurId();// Récupérer l'ID du chauffeur depuis GlobalState

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface1_chauffeur);

        dbHelper = new DatabaseHelper(this);

        // Initialisation des Spinners
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        Button submitButton = findViewById(R.id.submitButton);

        // Créer un adaptateur pour les listes déroulantes (destinations)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, GOVERNORATS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Associer l'adaptateur aux Spinners de destinations
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Gérer le clic sur le bouton "Valider"
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer la destination de départ et d'arrivée
                String from = fromSpinner.getSelectedItem().toString();
                String to = toSpinner.getSelectedItem().toString();

                if (from.equals(to)) {
                    Toast.makeText(interface1_chauffeur.this, "Veuillez choisir des destinations différentes.", Toast.LENGTH_SHORT).show();
                } else {
                    // Obtenir la matricule automatiquement à partir de l'ID du chauffeur
                    String matricule = dbHelper.getMatriculeFromChauffeurId(current_CHAUFFEUR_ID);

                    if (matricule != null) {
                        // Insérer les données dans la base de données
                        dbHelper.insertVoiture(matricule, from, to);
                        Toast.makeText(interface1_chauffeur.this, "Trajet enregistré : " + from + " → " + to, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(interface1_chauffeur.this, "Erreur : Matricule non trouvée", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
