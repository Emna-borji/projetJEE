package model;

import java.sql.Date;

public class LivresTelecharges {
    private int id;
    private int utilisateurId;
    private int livreId;
    private String titre;
    private String imageUrl;
    private Date dateTelechargement;

    // Constructor
    public LivresTelecharges(int id, int utilisateurId, int livreId, String titre, String imageUrl, Date dateTelechargement) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.livreId = livreId;
        this.titre = titre;
        this.imageUrl = imageUrl;
        this.dateTelechargement = dateTelechargement;
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

    public Date getDateTelechargement() {
        return dateTelechargement;
    }

    public void setDateTelechargement(Date dateTelechargement) {
        this.dateTelechargement = dateTelechargement;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "LivresTelecharges{" +
                "id=" + id +
                ", utilisateurId=" + utilisateurId +
                ", livreId=" + livreId +
                ", titre='" + titre + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", dateTelechargement=" + dateTelechargement +
                '}';
    }
}
