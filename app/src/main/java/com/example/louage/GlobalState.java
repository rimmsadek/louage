//package com.example.louage;
//
//import android.util.Log;
//
//public class GlobalState {
//    private static GlobalState instance; // Instance unique
//    private int chauffeurId; // Variable pour stocker l'ID du chauffeur
//    private int utilisateurId;
//
//
//
//    private GlobalState() {
//        // Constructeur privé
//        chauffeurId = 1;
//        utilisateurId = 1;
//    }
//
//    // Récupérer l'instance unique
//    public static synchronized GlobalState getInstance() {
//        if (instance == null) {
//            instance = new GlobalState();
//        }
//        return instance;
//    }
//
//    // Getter et Setter pour l'ID du chauffeur
//
//    public int getChauffeurId() {
//        Log.d("GlobalState", "ID du chauffeur : " + chauffeurId);  // Ajouter un log ici
//        return chauffeurId;
//    }
//
//    public void setChauffeurId(int chauffeurId) {
//        this.chauffeurId = chauffeurId;
//    }
//
//    public int getUtilisateurId() {
//        return utilisateurId;
//    }
//
//    public void setUtilisateurId(int utilisateurId) {
//        this.utilisateurId = utilisateurId;
//    }
//}
//
//
//
//
//
//
//


package com.example.louage;

public class GlobalState {
    private static GlobalState instance;
    private int chauffeurId;
    private int utilisateurId;

    private GlobalState() {}

    public static GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }

    public int getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(int id) {
        this.chauffeurId = id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int id) {
        this.utilisateurId = id;
    }
}
