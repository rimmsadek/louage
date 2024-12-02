package com.example.louage;

public class Reservation {

    private int id;
    private int voyageId;
    private int utilisateurId;
    private int nbPlaces;
    private String heureReservation;

    // Constructeur
    public Reservation(int id, int voyageId, int utilisateurId, int nbPlaces, String heureReservation) {
        this.id = id;
        this.voyageId = voyageId;
        this.utilisateurId = utilisateurId;
        this.nbPlaces = nbPlaces;
        this.heureReservation = heureReservation;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(int voyageId) {
        this.voyageId = voyageId;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getHeureReservation() {
        return heureReservation;
    }

    public void setHeureReservation(String heureReservation) {
        this.heureReservation = heureReservation;
    }
}
