package model;

public class Utilisateur {
	private int id;
    private String nom;
    private String email;
    private int nbActions;

    public Utilisateur(int id, String nom, String email, int nbActions) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.nbActions = nbActions;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public int getNbActions() {
        return nbActions;
    }
}
