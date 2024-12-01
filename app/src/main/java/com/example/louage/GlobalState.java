package com.example.louage;

import android.util.Log;

public class GlobalState {
    private static GlobalState instance; // Instance unique
    private int chauffeurId; // Variable pour stocker l'ID du chauffeur

    private GlobalState() {
        // Constructeur privé
        chauffeurId = 1;
    }

    // Récupérer l'instance unique
    public static synchronized GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }

    // Getter et Setter pour l'ID du chauffeur

    public int getChauffeurId() {
        Log.d("GlobalState", "ID du chauffeur : " + chauffeurId);  // Ajouter un log ici
        return chauffeurId;
    }

    public void setChauffeurId(int chauffeurId) {
        this.chauffeurId = chauffeurId;
    }
}







