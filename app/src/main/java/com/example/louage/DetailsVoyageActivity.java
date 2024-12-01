package com.example.louage;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsVoyageActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_voyage);

        dbHelper = new DatabaseHelper(this);
        int voyageId = getIntent().getIntExtra("voyageId", -1);

        if (voyageId != -1) {
            displayVoyageDetails(voyageId);
        }
    }

    private void displayVoyageDetails(int voyageId) {
        Voyage voyage = dbHelper.getVoyageDetails(voyageId);

        if (voyage != null) {
            Chauffeur chauffeur = voyage.getChauffeur(); // Récupération de l'objet Chauffeur
            if (chauffeur != null) {
                ((TextView) findViewById(R.id.textViewChauffeur))
                        .setText("Chauffeur: " + chauffeur.getNom() + " " + chauffeur.getPrenom());
            } else {
                ((TextView) findViewById(R.id.textViewChauffeur)).setText("Chauffeur inconnu");
            }
            ((TextView) findViewById(R.id.textViewFrom)).setText("De: " + voyage.getFrom());
            ((TextView) findViewById(R.id.textViewTo)).setText("À: " + voyage.getTo());
            ((TextView) findViewById(R.id.textViewPlaces))
                    .setText("Places disponibles: " + (8 - voyage.getNbReservation())); // Utilisation de getNbReservation()
        } else {
            // Gérer le cas où le voyage est introuvable
            ((TextView) findViewById(R.id.textViewChauffeur)).setText("Détails du voyage introuvables.");
        }
    }


}
