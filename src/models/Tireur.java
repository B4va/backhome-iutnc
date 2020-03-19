package models;

/**
 * Modélisation d'un pnj étant de type "Tireur" au combat
 */
public class Tireur extends Ennemi {

    /**
     * Constructeur
     * @param id l'id du tireur
     */
    public Tireur(int id){
        super(id);
    }

    /**
     * Constructeur servant uniquement aux tests
     * @param arme l'arme du combattant
     */
    public Tireur(Arme arme) {
        super(arme);
    }
}
