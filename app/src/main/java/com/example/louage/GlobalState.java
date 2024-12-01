package com.example.louage;

import android.util.Log;

public class GlobalState {
    private static GlobalState instance; // Instance unique
    private int chauffeurId; // Variable pour stocker l'ID du chauffeur
    private int utilisateurId; // Variable pour stocker l'ID de l'utilisateur

    private GlobalState() {
        // Initialisation des valeurs par défaut
        chauffeurId = 1;
        utilisateurId = 1;
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
        Log.d("GlobalState", "ID du chauffeur : " + chauffeurId);  // Ajouter un log pour vérifier l'ID du chauffeur
        return chauffeurId;
    }

    public void setChauffeurId(int chauffeurId) {
        Log.d("GlobalState", "Changement ID chauffeur : " + chauffeurId); // Log pour vérifier l'ID du chauffeur
        this.chauffeurId = chauffeurId;
    }

    // Getter et Setter pour l'ID de l'utilisateur
    public int getUtilisateurId() {
        Log.d("GlobalState", "ID de l'utilisateur : " + utilisateurId);  // Ajouter un log pour vérifier l'ID de l'utilisateur
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        Log.d("GlobalState", "Changement ID utilisateur : " + utilisateurId); // Log pour vérifier l'ID de l'utilisateur
        this.utilisateurId = utilisateurId;
    }
}
