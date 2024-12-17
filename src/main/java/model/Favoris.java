package model;

import java.sql.Date;

public class Favoris {
    private int id;
    private int utilisateurId;
    private int livreId;
    private String titre;
    private String imageUrl;  // Added imageUrl
    private Date dateAjout;

    public Favoris(int id, int utilisateurId, int livreId, String titre, String imageUrl, Date dateAjout) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.livreId = livreId;
        this.titre = titre;
        this.imageUrl = imageUrl;  // Initialize imageUrl
        this.dateAjout = dateAjout;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getLivreId() {
        return livreId;
    }

    public void setLivreId(int livreId) {
        this.livreId = livreId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }
}
