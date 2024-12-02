package com.example.louage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.net.Uri;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<Reservation> reservations;
    private Context context;

    public ReservationAdapter(List<Reservation> reservations, Context context) {
        this.reservations = reservations;
        this.context = context;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);

        holder.dateTextView.setText(reservation.getHeureReservation());
        holder.placesTextView.setText("Places réservées: " + reservation.getNbPlaces());
        holder.fromTextView.setText("De: " + reservation.getFrom());
        holder.toTextView.setText("À: " + reservation.getTo());

        // Gestion de l'icône d'appel
        holder.callImageView.setOnClickListener(v -> {
            // Logique pour appeler le chauffeur
            String chauffeurPhoneNumber = getChauffeurPhoneNumber(reservation.getVoyageId());
            callChauffeur(chauffeurPhoneNumber);
        });

        // Gestion de l'icône d'annulation
        holder.cancelImageView.setOnClickListener(v -> {
            // Logique pour annuler la réservation
            cancelReservation(reservation.getId(), reservation.getVoyageId(), reservation.getNbPlaces());
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView, placesTextView, fromTextView, toTextView;
        ImageView callImageView, cancelImageView;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            placesTextView = itemView.findViewById(R.id.textViewPlaces);
            fromTextView = itemView.findViewById(R.id.textViewFrom);
            toTextView = itemView.findViewById(R.id.textViewTo);
            callImageView = itemView.findViewById(R.id.imageViewCall);
            cancelImageView = itemView.findViewById(R.id.imageViewCancel);
        }
    }

    private String getChauffeurPhoneNumber(int voyageId) {
        // Utiliser DatabaseHelper pour récupérer le numéro du chauffeur
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        return dbHelper.getChauffeurPhoneNumber(voyageId);
    }

    // Méthode pour appeler le chauffeur
    private void callChauffeur(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    // Méthode pour annuler la réservation
    private void cancelReservation(int reservationId, int voyageId, int nbPlacesToCancel) {
        // Implémenter la logique pour annuler la réservation dans la base de données
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        boolean success = dbHelper.cancelReservation(reservationId, voyageId, nbPlacesToCancel);

        if (success) {
            // Si la réservation est annulée avec succès, supprimer la réservation de la liste
            reservations.removeIf(reservation -> reservation.getId() == reservationId);
            notifyDataSetChanged(); // Actualiser la liste après suppression
        }
    }
}
