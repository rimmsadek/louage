package com.example.louage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ListeVoyagesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayList<String> voyagesList;
    private ArrayList<String> placesList;
    private ArrayList<Integer> voyageIds;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_voyages);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerViewVoyages);
        voyagesList = new ArrayList<>();
        placesList = new ArrayList<>();
        voyageIds = new ArrayList<>();

        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");

        loadVoyages(from, to);

        VoyageAdapter adapter = new VoyageAdapter(this, voyagesList, placesList ,voyageIds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadVoyages(String from, String to) {
        Cursor cursor = dbHelper.getVoyagesByDestination(from, to);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VOYAGE_ID);
                int chauffeurIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CHAUFFEUR_ID);
                int nbPlaceDispoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NB_PLACES_DISPO);

                if (idIndex != -1 && chauffeurIdIndex != -1 && nbPlaceDispoIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    int chauffeurId = cursor.getInt(chauffeurIdIndex);  // Chauffeur ID
                    int nbPlaceDispo = cursor.getInt(nbPlaceDispoIndex);

                    // Récupérer les détails du chauffeur
                    String chauffeurNom = dbHelper.getChauffeurNom(chauffeurId);
                    String chauffeurPrenom = dbHelper.getChauffeurPrenom(chauffeurId);

                    // Ajouter les détails du voyage à la liste
                    voyagesList.add("Chauffeur: " + chauffeurNom + " " + chauffeurPrenom );
                    placesList.add("Places disponibles: " + nbPlaceDispo);
                    voyageIds.add(id);
                } else {
                    showToast("Erreur lors de la récupération des données.");
                }
            } while (cursor.moveToNext());
        } else {
            showToast("Aucun voyage disponible.");
        }
        if (cursor != null) cursor.close();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
