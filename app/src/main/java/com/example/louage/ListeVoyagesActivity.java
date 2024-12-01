package com.example.louage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListeVoyagesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayList<String> voyagesList;
    private ArrayList<Integer> voyageIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_voyages);

        dbHelper = new DatabaseHelper(this);

        ListView listView = findViewById(R.id.listViewVoyages);
        voyagesList = new ArrayList<>();
        voyageIds = new ArrayList<>();

        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");

        loadVoyages(from, to);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, voyagesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            int voyageId = voyageIds.get(position);
            Intent intent = new Intent(this, DetailsVoyageActivity.class);
            intent.putExtra("voyageId", voyageId);
            startActivity(intent);
        });
    }

    private void loadVoyages(String from, String to) {
        Cursor cursor = dbHelper.getVoyagesByDestination(from, to);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VOYAGE_ID);
                int chauffeurIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CHAUFFEUR_ID);
                int nbReservationsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NB_RESERVATION);

                if (idIndex != -1 && chauffeurIdIndex != -1 && nbReservationsIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    int chauffeurId = cursor.getInt(chauffeurIdIndex);  // Chauffeur ID
                    int nbReservations = cursor.getInt(nbReservationsIndex);

                    // Récupérer les détails du chauffeur
                    String chauffeurNom = dbHelper.getChauffeurNom(chauffeurId);
                    String chauffeurPrenom = dbHelper.getChauffeurPrenom(chauffeurId);

                    // Ajouter les détails du voyage à la liste
                    voyagesList.add("Chauffeur: " + chauffeurNom + " " + chauffeurPrenom +
                            " | Places disponibles: " + (8 - nbReservations));
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
