package com.example.louage;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        Button reserver = findViewById(R.id.buttonReserve);
        NumberPicker placesNumberPicker = findViewById(R.id.numberPickerPlaces);

        placesNumberPicker.setMinValue(1); // Minimum 1 place pour la réservation
        placesNumberPicker.setMaxValue(8);

        reserver.setOnClickListener((View v) -> {
            int selectedPlaces = placesNumberPicker.getValue();

            boolean success = dbHelper.addReservation(voyageId, 1, selectedPlaces); // 1 est un utilisateur fictif pour le test.
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
