package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.BackHome;
import models.Inventaire;

import static models.Inventaire.getInventaire;

public class InventaireController extends Application {

    @Controller
    private static final String VIEW = "/views/Inventaire.fxml";
    @Controller
    private static final String STYLE = "/assets/css/Inventaire.css";
    @Controller
    private static final Inventaire MODELE = getInventaire();

    /**
     * Retourne la vue associée au controller
     * @return chemin de la vue
     */
    @Controller
    public static String getView(){
        return VIEW;
    }

    /**
     * Génère l'interface d'accueil
     * @param stage primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(VIEW));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(STYLE);
        stage.setScene(scene);
    }
}
