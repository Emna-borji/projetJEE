package model;

import java.io.Serializable;

public class StatistiqueMensuelle implements Serializable {

    private int annee;
    private int mois;
    private int totalEmprunts;
    private int totalTelechargements;
    private int totalAjouts;

    public StatistiqueMensuelle(int annee, int mois, int totalEmprunts, int totalTelechargements, int totalAjouts) {
        this.annee = annee;
        this.mois = mois;
        this.totalEmprunts = totalEmprunts;
        this.totalTelechargements = totalTelechargements;
        this.totalAjouts = totalAjouts;
    }

    public int getAnnee() {
        return annee;
    }

    public int getMois() {
        return mois;
    }

    public int getTotalEmprunts() {
        return totalEmprunts;
    }

    public int getTotalTelechargements() {
        return totalTelechargements;
    }

    public int getTotalAjouts() {
        return totalAjouts;
    }
}
