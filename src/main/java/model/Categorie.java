package model;

import java.util.List;

public class Categorie {
    private int id;
    private String nom;
    private List<Livre> livres; // List of books associated with this category

    // Default constructor
    public Categorie() {
    }

    // Constructor with all fields
    public Categorie(int id, String nom, List<Livre> livres) {
        this.id = id;
        this.nom = nom;
        this.livres = livres;
    }

    // Constructor without livres
    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", livres=" + livres +
                '}';
    }
}
