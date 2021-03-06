package controllers;

import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import models.BackHome;
import models.Carte;
import models.Planete;
import models.Situation;
import utils.EffetsJavaFx;
import views.View;

import static models.Carte.getCarte;
import static models.Heros.getHeros;
import static models.Inventaire.getInventaire;

/**
 * Controller de la carte
 */
public class CarteController {

    @Controller
    private static Carte MODELE;

    @FXML
    private ImageView hud;
    @FXML
    private FlowPane flow;
    @FXML
    private Label loc, dest;

    /**
     * Initialisation de la vue et du modèle
     */
    @FXML
    private void initialize(){

        // initialise le modèle
        MODELE = getCarte();

        // enregistre le début de partie
        BackHome.setStarted();

        // met à jour la situation du héros
        getHeros().getLocalisation().setVisitee();
        getHeros().soin();
        getHeros().setSituation(Situation.VAISSEAU);

        // initialise la vue
        chargeElementsInterface();
        chargeLocalisation();
        chargePlanetesDisponibles();
    }

    /**
     * Charge l'apparition des éléments de l'interface
     */
    @FXML
    private void chargeElementsInterface(){
        EffetsJavaFx.fadeIn(hud, 2, 0);
    }

    /**
     * Charge la localisation du héros
     */
    @FXML
    private void chargeLocalisation(){
        loc.setText("LOCALISATION : " + getHeros().getLocalisation().getNom());
        EffetsJavaFx.fadeIn(dest, 2, 0);
        EffetsJavaFx.fadeIn(loc, 2, 0);
    }

    /**
     * Charge les planètes disponibles pour le héros
     */
    @FXML
    private void chargePlanetesDisponibles(){
        for (Planete p : getHeros().getLocalisation().getPlanetesVoisines()){
            HBox box = new HBox();
            box.setOnMouseClicked(this::allerPlanete);
            box.setUserData(p.getNom());
            box.setOpacity(0);
            box.getStyleClass().add("planete");
            stylePlanete(box, p);
            Label nom = new Label();
            nom.getStyleClass().add("nomPlanete");
            nom.setText(p.getNom());
            Label desc = new Label();
            desc.getStyleClass().add("descriptionPlanete");
            desc.setText(p.getDescription());
            box.getChildren().add(nom);
            box.getChildren().add(desc);
            flow.getChildren().add(box);
            EffetsJavaFx.fadeIn(box, 2, 0);
        }
    }

    /**
     * Fait naviguer le héros vers l'une des planètes disponibles
     * @param e clic sur l'une des options
     */
    @FXML
    private void allerPlanete(MouseEvent e){
        HBox box = (HBox) e.getSource();
        Planete nouvellePlanete = MODELE.getPlaneteParNom((String) box.getUserData());

        // redirige vers l'interface de quête si la planète est disponible
        if (nouvellePlanete.estAccessible()){
            getHeros().setLocalisation(nouvellePlanete);
            getInventaire().modifierCarburant(-100 * getHeros().getLocalisation().getNiveau());
            Transition p = new PauseTransition(Duration.seconds(1));
            p.setOnFinished((e2)->new View().queteView());
            p.play();

        // affiche les ressources nécessaires pour accéder à la planète si indisponible
        } else {
            Label l = (Label)(box.getChildren().get(1));
            l.setText("Carburant nécessaire : " + nouvellePlanete.getNiveau() * 100 + "L");
            box.setDisable(true);
        }
    }

    /**
     * Permet d'accéder à l'inventaire
     */
    @FXML
    private void allerInventaire(){
        new View().inventaireView();
    }

    /**
     * Génère le style du bouton en fonction du statut de la planète
     * @param box bouton
     * @param p planete
     */
    @FXML
    private void stylePlanete(HBox box, Planete p){
        if (p.estAccessible()){
            if (p.getVisitee()){
                box.getStyleClass().add("visitee");
                box.setDisable(true);
            } else {
                box.getStyleClass().add("dispo");
            }
        } else {
            box.getStyleClass().add("inaccessible");
        }
    }

    /**
     * Redirige vers la vue sauvegardes
     */
    @FXML
    private void sauvegarder(){
        new View().sauvegardeView();
    }
}
