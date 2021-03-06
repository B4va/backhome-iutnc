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
public class Carte implements Configurable {

    @Singleton
    private static Carte CARTE = new Carte();

    private ArrayList<Planete> planetes;

    /**
     * Constructeur privé
     */
    private Carte(){
        initConfiguration();
    }

    /**
     * Getter de l'instance de la carte (singleton)
     * @return l'instance de la carte
     */
    @Singleton
    public static Carte getCarte(){
        if(CARTE == null) {
            CARTE = new Carte();
        }
        return CARTE;
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
     * Récupère les données de configuration de la carte
     */
    public void initConfiguration() {
        String cheminConf = "/data/carte.json";
        String cle = "planetes";
        JSONObject[] pl = null;
        try {
            new JsonParser();
            pl = JsonParser.parseObjects(cheminConf, cle);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        if (pl != null){
            initialiserPlanetes(pl);
            initialiserPlanetesVoisines(pl);
        }
    }

    /**
     * Initialise les planètes de la carte
     */
    private void initialiserPlanetes(JSONObject[] planetes) {
        this.planetes = new ArrayList<>();
        for (JSONObject p : planetes){
            Object pe = p.get("idPremierEvenement");
            int peInt = pe == null ? -1 : Integer.parseInt(p.get("idPremierEvenement").toString());
            Object rcac = p.get("idRecompenseArmeCac");
            int rcacInt = rcac == null ? -1 : Integer.parseInt(p.get("idRecompenseArmeCac").toString());
            Object rdist = p.get("idRecompenseDistance");
            int rdistInt = rdist == null ? -1 : Integer.parseInt(p.get("idRecompenseDistance").toString());
            Object rarm = p.get("idRecompenseArmure");
            int rarmInt = rarm == null ? -1 : Integer.parseInt(p.get("idRecompenseArmure").toString());

            this.nouvellePlanete(new Planete(
                    p.get("nom").toString(),
                    Integer.parseInt(p.get("niveau").toString()),
                    p.get("description").toString(),
                    peInt,
                    rcacInt,
                    rdistInt,
                    rarmInt)
            );
        }
    }

    /**
     * Initialise les planètes voisines de chaque planète de la carte
     */
    private void initialiserPlanetesVoisines(JSONObject[] planetes) {
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

    public static void reset() {
        CARTE = new Carte();
    }

    /**
     * Getter
     * @return liste des planètes
     */
    public ArrayList<Planete> getPlanetes() {
        return planetes;
    }

    public boolean[] getAllStatus(){
        boolean[] arr = new boolean[planetes.size()];
        for (int i = 0 ; i < planetes.size() ; i++){
            arr[i] = planetes.get(i).getVisitee();
        }
        return arr;
    }

    public void setAllStatus(boolean[] status){
        for (int i = 0 ; i < planetes.size() ; i++){
            if (status[i]) planetes.get(i).setVisitee();
        }
    }
}
