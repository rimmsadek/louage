package com.example.louage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VoituresActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface1_chauffeur);

        dbHelper = new DatabaseHelper(this);

        EditText matriculeInput = findViewById(R.id.matricule);
        EditText fromInput = findViewById(R.id.from_destination);
        EditText toInput = findViewById(R.id.to_destination);
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricule = matriculeInput.getText().toString();
                String from = fromInput.getText().toString();
                String to = toInput.getText().toString();

                if (matricule.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    Toast.makeText(VoituresActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.insertVoiture(matricule, from, to);
                    Toast.makeText(VoituresActivity.this, "Voiture ajoutée avec succès", Toast.LENGTH_SHORT).show();
                    matriculeInput.setText("");
                    fromInput.setText("");
                    toInput.setText("");
                }
            }
        });
    }
}
