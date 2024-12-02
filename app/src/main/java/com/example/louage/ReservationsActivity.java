package com.example.louage;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;
    private List<Reservation> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        recyclerView = findViewById(R.id.recyclerViewReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Exemple de données (vous pouvez les charger depuis la base de données)
        reservations = loadReservationsFromDatabase();

        reservationAdapter = new ReservationAdapter(reservations, this);
        recyclerView.setAdapter(reservationAdapter);
    }

    // Méthode pour charger les réservations depuis la base de données
    private List<Reservation> loadReservationsFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        return dbHelper.getReservationsByUserId(1); // Exemple avec l'ID utilisateur 1
    }
}
