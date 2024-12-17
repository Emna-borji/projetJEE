package model;

import java.util.Date;

public class LivresEmpruntes {
    private int id;
    private int userId; // Foreign key referring to 'utilisateurs'
    private int bookId; // Foreign key referring to 'livres'
    private String titre; // Title of the book
    private String image; // Image of the book
    private Date dateEmprunt;
    private Date dateRetour;
    private String utilisateur;
    private String livre;

    // Default constructor
    public LivresEmpruntes() {}

    // Constructor with all fields
    public LivresEmpruntes(int id, int userId, int bookId, String titre, String image, Date dateEmprunt, Date dateRetour) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.titre = titre;
        this.image = image;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        
    }
    
    public LivresEmpruntes(int id, String utilisateur, String livre, Date dateEmprunt, Date dateRetour) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    // Getters and Setters
    public String getLivre() {
        return livre;
    }
    
    public String getUtilisateur() {
        return utilisateur;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    // toString method to print the details of an emprunté
    @Override
    public String toString() {
        return "LivresEmpruntes{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", titre='" + titre + '\'' +
                ", image='" + image + '\'' +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetour=" + dateRetour +
                '}';
    }
}
