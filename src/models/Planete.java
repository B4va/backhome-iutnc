package models;

import java.util.ArrayList;

/**
 * Modélisation d'une planète
 */
public class Planete {

    private String nom;
    private int niveau;
    private ArrayList<Planete> planetesVoisines = new ArrayList<Planete>();

    /**
     * Constructeur
     * @param nom nom de la planète
     * @param niveau niveau d'accès requis
     */
    public Planete(String nom, int niveau) {
        this.nom = nom;
        this.niveau = niveau;
    }

    /**
     * Getter
     * @return nom de la planète
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter
     * @return niveau d'accès requis
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * Getter
     * @return planètes accessible depuis la planète créée
     */
    public ArrayList<Planete> getPlanetesVoisines() {
        return planetesVoisines;
    }

    /**
     * Ajoute une planète voisine
     * @param p Planète à ajouter aux planètes voisines
     */
    public void nouvellePlaneteVoisine(Planete p) {
        this.planetesVoisines.add(p);
    }
}
