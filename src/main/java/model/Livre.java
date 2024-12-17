package model;

import java.util.List;

public class Livre {
    private int id;
    private String titre;
    private String resume;
    private int annee;
    private String format;
    private int categorieId;
    private List<Auteur> auteurs; // List of authors associated with this book
    private String imageUrl;
    // Default constructor
    public Livre() {
    }

    // Constructor with all fields
    public Livre(int id, String titre, String resume, int annee, String format, int categorieId, List<Auteur> auteurs) {
        this.id = id;
        this.titre = titre;
        this.resume = resume;
        this.annee = annee;
        this.format = format;
        this.categorieId = categorieId;
        this.auteurs = auteurs;
    }

    public Livre(int id, String titre, String resume, int annee, String format, String imageUrl) {
        this.id = id;
        this.titre = titre;
        this.resume = resume;
        this.annee = annee;
        this.format = format;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    public List<Auteur> getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(List<Auteur> auteurs) {
        this.auteurs = auteurs;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    // toString method for debugging and logging
    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", resume='" + resume + '\'' +
                ", annee=" + annee +
                ", format='" + format + '\'' +
                ", categorieId=" + categorieId +
                ", auteurs=" + auteurs +
                '}';
    }
}
