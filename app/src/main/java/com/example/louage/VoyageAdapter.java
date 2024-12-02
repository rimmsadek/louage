package com.example.louage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import android.content.Intent;

public class VoyageAdapter extends RecyclerView.Adapter<VoyageAdapter.VoyageViewHolder> {

    private Context context;
    private ArrayList<String> voyagesList;
    private ArrayList<String> placesList;
    private ArrayList<Integer> voyageIds;

    public VoyageAdapter(Context context, ArrayList<String> voyagesList,ArrayList<String> placesList, ArrayList<Integer> voyageIds) {
        this.context = context;
        this.voyagesList = voyagesList;
        this.placesList = placesList;
        this.voyageIds = voyageIds;
    }

    @Override
    public VoyageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_item_voyage, parent, false);
        return new VoyageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VoyageViewHolder holder, int position) {

        holder.textChauffeurNom.setText(voyagesList.get(position));
        holder.textPlacesDispo.setText(placesList.get(position));

        holder.cardView.setOnClickListener(v -> {
            // Passer à l'activité DetailsVoyageActivity
            Intent intent = new Intent(context, DetailsVoyageActivity.class);
            intent.putExtra("voyageId", voyageIds.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return voyagesList.size();
    }

    public static class VoyageViewHolder extends RecyclerView.ViewHolder {
        TextView textChauffeurNom, textPlacesDispo;
        CardView cardView;

        public VoyageViewHolder(View itemView) {
            super(itemView);
            textChauffeurNom = itemView.findViewById(R.id.textChauffeurNom);
            textPlacesDispo = itemView.findViewById(R.id.textPlacesDispo);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
