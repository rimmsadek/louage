package com.example.louage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<Reservation> reservations;

    public ReservationAdapter(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        holder.dateTextView.setText(reservation.getHeureReservation());
        holder.placesTextView.setText("Places réservées: " + reservation.getNbPlaces());
        // Autres champs à afficher selon votre modèle
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView placesTextView;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            placesTextView = itemView.findViewById(R.id.textViewPlaces);
        }
    }
}
