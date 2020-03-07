package models;

import static models.Inventaire.getInventaire;

/**
 * Modélisation d'une arme à distance (utilisée pour le héros)
 */
public class ArmeDistance extends Arme {

	/**
	 * Constructeur
	 * 
	 * @param id l'id de l'arme
	 */
	public ArmeDistance(int id) {
		super(id);
	}

	/**
	 * Lance une attaque sur un personnage
	 * 
	 * @param cible victime de l'attaque
	 */
	@Override
	public String attaquer(Personnage cible) {
		if (cible instanceof Brute) {
			cible.subirAttaque(this.getDegats() * 1.5);
			return("degat :" + ((int)this.getDegats() * 1.5)+"\n");
		} else {
			if (cible instanceof Tireur) {
				cible.subirAttaque(this.getDegats() * 0.75);
				return("degat :" + ((int)this.getDegats() * 0.75)+"\n");
			} else {
				if (cible instanceof Heros) {
					cible.subirAttaque(this.getDegats());
					return("degat : " + (this.getDegats() - getInventaire().getArmure().getResistance())+"\n");
				} else {
					cible.subirAttaque(this.getDegats());
					return("degat :" + this.getDegats()+"\n");
				}
			}
		}
	}
}
