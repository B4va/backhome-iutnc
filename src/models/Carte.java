package models;

import lib.org.json.simple.JSONArray;
import lib.org.json.simple.JSONObject;
import lib.org.json.simple.parser.ParseException;
import utils.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Singleton modélisant la carte du jeu
 */
public class Carte {

    private static Carte CARTE = new Carte();
    private ArrayList<Planete> planetes = new ArrayList<>();

    /**
     * Constructeur privé
     */
    private Carte(){
        try {
            this.initialiserPlanetes();
            this.initialiserPlanetesVoisines();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    };

    /**
     * Getter de l'instance de la carte (singleton)
     * @return l'instance de la carte
     */
    public static Carte getCarte(){
        if(CARTE == null) {
            CARTE = new Carte();
        }
        return CARTE;
    }

    /**
     * Getter
     * @return les planètes de la carte
     */
    public ArrayList<Planete> getPlanetes(){
        return this.planetes;
    }

    /**
     * Getter
     * @param nom nom de la planète
     * @return la planete de la carte portant le nom saisi
     */
    public Planete getPlaneteParNom(String nom){
        Planete res = null;
        for (Planete p : this.planetes){
            if (p.getNom().equalsIgnoreCase(nom)){
                res = p;
                break;
            }
        }
        return res;
    }

    /**
     * Initialise les planètes de la carte
     * @throws IOException
     * @throws ParseException
     */
    private void initialiserPlanetes() throws IOException, ParseException {
        String cheminConf = "/assets/config/planetes.json";
        String cle = "planetes";
        JSONObject[] planetes = new JsonParser().parseObjects(cheminConf, cle);
        for (JSONObject p : planetes){
            this.nouvellePlanete(new Planete(
                    p.get("nom").toString(),
                    Integer.parseInt(p.get("niveau").toString())
            ));
        }
    }

    /**
     * Initialise les planètes voisines de chaque planète de la carte
     * @throws IOException
     * @throws ParseException
     */
    private void initialiserPlanetesVoisines() throws IOException, ParseException {
        String cheminConf = "/assets/config/planetes.json";
        String cle = "planetes";
        JSONObject[] planetes = new JsonParser().parseObjects(cheminConf, cle);
        for (int i = 0 ; i < this.planetes.size() ; i++){
            JSONArray arr = (JSONArray) planetes[i].get("planetesVoisines");
            for (Object p : arr){
                Planete voisine = this.getPlaneteParNom(p.toString());
                this.planetes.get(i).nouvellePlaneteVoisine(voisine);
            }
        }
    }

    /**
     * Ajoute une planète à la carte
     * @param p planète à ajouter
     */
    private void nouvellePlanete(Planete p){
        this.planetes.add(p);
    }
}