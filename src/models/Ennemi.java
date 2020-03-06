package models;

public abstract class Ennemi extends Personnage implements Configurable {

    private String description;
    private Arme arme;
    private int id;

    /**
     * Constructeur
     * @param id l'id de l'ennemi
     */
    public Ennemi(int id){
        this.id = id;
        this.initConfiguration();
    }

    /**
     * Getter
     * @return la description de l'ennemi
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter
     * @return l'arme de l'ennemi
     */
    public Arme getArme() {
        return arme;
    }

    @Override
    public void initConfiguration() {

    }

}
