package com.example.louage;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.content.Intent;

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
        EditText placesEditText = findViewById(R.id.editTextPlaces);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);

        buttonMinus.setOnClickListener(v -> {
            int currentValue = Integer.parseInt(placesEditText.getText().toString());
            if (currentValue > 1) {
                placesEditText.setText(String.valueOf(currentValue - 1));
            }
        });

        buttonPlus.setOnClickListener(v -> {
            int currentValue = Integer.parseInt(placesEditText.getText().toString());
            if (currentValue < 8) {
                placesEditText.setText(String.valueOf(currentValue + 1));
            }
        });

        reserver.setOnClickListener(v -> {
            int selectedPlaces = Integer.parseInt(placesEditText.getText().toString());
            boolean success = dbHelper.addReservation(voyageId, utilisateurId, selectedPlaces);
            if (success) {
                Toast.makeText(this, "Réservation réussie!", Toast.LENGTH_SHORT).show();
                // Créer un Intent pour démarrer ReservationActivity
                Intent intent = new Intent(DetailsVoyageActivity.this, ReservationsActivity.class);

                // Passer des informations à l'activité (par exemple l'ID du voyage)
                intent.putExtra("voyageId", voyageId);  // Vous pouvez ajouter d'autres informations si nécessaire

                // Démarrer l'activité
                startActivity(intent);
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
            ((TextView) findViewById(R.id.textViewPlaces)).setText("Places disponibles: " + voyage.getPlacesDisponibles());
        }
    }
}
