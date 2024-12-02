package com.example.louage;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID de l'utilisateur courant
        int utilisateurId = GlobalState.getInstance().getUtilisateurId();

        // Récupérer la liste des réservations de l'utilisateur
        List<Reservation> reservations = dbHelper.getReservationsByUserId(utilisateurId);

        // Si aucune réservation n'est trouvée
        if (reservations.isEmpty()) {
            Toast.makeText(this, "Aucune réservation trouvée.", Toast.LENGTH_SHORT).show();
        }

        // Initialiser le RecyclerView et son adaptateur
        recyclerView = findViewById(R.id.recyclerViewReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Créer un adaptateur et le lier au RecyclerView
        reservationAdapter = new ReservationAdapter(reservations);
        recyclerView.setAdapter(reservationAdapter);
    }
}
