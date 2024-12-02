package com.example.louage;

public class Reservation {

    private int id;
    private int voyageId;
    private int utilisateurId;
    private int nbPlaces;
    private String heureReservation;
    private String from ;
    private String to ;

    public Reservation(int id, int voyageId, int utilisateurId, int nbPlaces, String heureReservation, String from, String to) {
        this.id = id;
        this.voyageId = voyageId;
        this.utilisateurId = utilisateurId;
        this.nbPlaces = nbPlaces;
        this.heureReservation = heureReservation;
        this.from = from;
        this.to = to;
    }

    // Getters et setters pour les nouveaux champs
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
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
