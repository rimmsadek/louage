package com.example.louage;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class interface1_chauffeur extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Spinner matriculeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface1_chauffeur);

        dbHelper = new DatabaseHelper(this);

        matriculeSpinner = findViewById(R.id.matricule_spinner);
        EditText fromInput = findViewById(R.id.from_destination);
        EditText toInput = findViewById(R.id.to_destination);
        Button saveButton = findViewById(R.id.saveButton);

        // Charger les matricules dans le Spinner
        loadMatricules();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricule = matriculeSpinner.getSelectedItem().toString();
                String from = fromInput.getText().toString();
                String to = toInput.getText().toString();

                if (from.isEmpty() || to.isEmpty()) {
                    Toast.makeText(interface1_chauffeur.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.insertVoiture(matricule, from, to);
                    Toast.makeText(interface1_chauffeur.this, "Voiture ajoutée avec succès", Toast.LENGTH_SHORT).show();
                    fromInput.setText("");
                    toInput.setText("");
                }
            }
        });
    }

    private void loadMatricules() {
        ArrayList<String> matricules = dbHelper.getAllMatricules();
        if (matricules.isEmpty()) {
            matricules.add("Aucune matricule disponible");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, matricules);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        matriculeSpinner.setAdapter(adapter);
    }
}
