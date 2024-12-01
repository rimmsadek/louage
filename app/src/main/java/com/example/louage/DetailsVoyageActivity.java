package com.example.louage;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class DetailsVoyageActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_voyage);

        dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID de l'utilisateur courant depuis GlobalState
        int utilisateurId = GlobalState.getInstance().getUtilisateurId();

        // Récupérer l'ID du voyage depuis l'intent
        int voyageId = getIntent().getIntExtra("voyageId", -1);

        if (voyageId != -1) {
            displayVoyageDetails(voyageId);
        }

        Button reserver = findViewById(R.id.buttonReserve);
        NumberPicker placesNumberPicker = findViewById(R.id.numberPickerPlaces);

        placesNumberPicker.setMinValue(1); // Minimum 1 place pour la réservation
        placesNumberPicker.setMaxValue(8);

        reserver.setOnClickListener((View v) -> {
            int selectedPlaces = placesNumberPicker.getValue();

            // Utilisation de l'ID de l'utilisateur courant pour la réservation
            boolean success = dbHelper.addReservation(voyageId, utilisateurId, selectedPlaces);
            if (success) {
                Toast.makeText(this, "Réservation réussie!", Toast.LENGTH_SHORT).show();
                finish(); // Retour à l'écran précédent.
            } else {
                Toast.makeText(this, "Échec de la réservation.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayVoyageDetails(int voyageId) {
        Voyage voyage = dbHelper.getVoyageDetails(voyageId);

        if (voyage != null) {
            ((TextView) findViewById(R.id.textViewChauffeur)).setText("Chauffeur: " + voyage.getChauffeurNom() + " " + voyage.getChauffeurPrenom());
            ((TextView) findViewById(R.id.textViewFrom)).setText("De: " + voyage.getFrom());
            ((TextView) findViewById(R.id.textViewTo)).setText("À: " + voyage.getTo());
            ((TextView) findViewById(R.id.textViewPlaces)).setText("Places disponibles: " + (8 - voyage.getNbReservation()));
        }
    }
}
