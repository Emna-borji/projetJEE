package model;
import java.sql.Date;
import java.util.List;

public class Auteur {
    private int id;
    private String nom;
    private Date dateDeNaissance;
    private List<Livre> livres; // List of books associated with this author

    // Default constructor
    public Auteur() {
    }

    // Constructor with all fields
    public Auteur(int id, String nom, List<Livre> livres) {
        this.id = id;
        this.nom = nom;
        this.livres = livres;
    }
    public Auteur(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    public Auteur(int id, String nom, Date dateDeNaissance) {
        this.id = id;
        this.nom = nom;
        this.dateDeNaissance = dateDeNaissance;
    }
    
    
    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
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
        return "Auteur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", livres=" + livres +
                '}';
    }
}
