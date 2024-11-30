package com.example.louage;

public class GlobalState {
    private static GlobalState instance; // Instance unique
    private int chauffeurId; // Variable pour stocker l'ID du chauffeur

    private GlobalState() {
        // Constructeur privé
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
        return chauffeurId;
    }

    public void setChauffeurId(int chauffeurId) {
        this.chauffeurId = chauffeurId;
    }
}
