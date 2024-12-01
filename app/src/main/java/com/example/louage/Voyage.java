package com.example.louage;

public class Voyage {
    private int id;
    private String from;
    private String to;
    private int nbReservation; // Notez que c'est "nbReservation" sans "s"
    private int placesDisponibles;
    private int ChauffeurId;
    private String chauffeurNom;
    private String chauffeurPrenom;

    public Voyage(int id, String from, String to, int nbReservation, int placesDisponibles, int ChauffeurId, String chauffeurNom, String chauffeurPrenom) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.nbReservation = nbReservation;
        this.placesDisponibles = placesDisponibles;
        this.ChauffeurId = ChauffeurId;
        this.chauffeurNom = chauffeurNom;
        this.chauffeurPrenom = chauffeurPrenom;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getNbReservation() { // Utilisez cette méthode
        return nbReservation;
    }

    public int getPlacesDisponibles() {
        return placesDisponibles;
    }

    public int getChauffeurId() {
        return ChauffeurId;
    }

    public String getChauffeurNom() {
        return chauffeurNom;
    }

    public String getChauffeurPrenom() {
        return chauffeurPrenom;
    }

}