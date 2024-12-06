package ihm;

import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import poo.Algorithme;
import poo.Plateforme;
import poo.Voyageur;

public class Trajet extends Application {
    
    public static String voyage = "blablabla";
    public static Plateforme reseau = null;
    public static Voyageur voyageur = null;
    public static String depart = null;
    public static String arrive = null;
    public static int index = 0 ;
    public static Stage stage = null;

    public void start(Stage stage, String voyage, String depart, String arrive, Voyageur voyageur, Plateforme p, int index){
        Trajet.voyage = voyage ;
        Trajet.reseau = p;
        Trajet.voyageur = voyageur;
        Trajet.depart = depart;
        Trajet.arrive = arrive;
        Trajet.index = index;
        Trajet.stage = stage;
        this.start(stage);
    }

    public void start(Stage stage){
        List<Chemin> chemins = Algorithme.plusCourtsChemins(reseau, Trajet.depart, Trajet.arrive, 3);
        Label voyage = new Label(Trajet.voyage);
        voyage.setStyle("-fx-background-color: #FFFFFF;");
        voyage.setMaxWidth(300);
        voyage.setPrefWidth(300);
        voyage.setMaxHeight(35);
        voyage.setPrefHeight(35);
        VBox main = new VBox();
        main.setStyle("-fx-background-color: #BDD7FF;");
        main.setAlignment(Pos.CENTER);
        Label titre = new Label("Détails du trajet");
        titre.setStyle("-fx-font: 30 arial");

        VBox details = new VBox();
        Label d = new Label(Trajet.depart + " -> " +  chemins.get(index).aretes().get(0).getModalite());
        details.setMaxWidth(700);
        details.setPrefWidth(700);
        details.setMaxHeight(200);
        details.setPrefHeight(200);
        details.setStyle("-fx-background-color: #D6E6FF;");
        details.getChildren().addAll(d);

        for(int i = 0 ; i< chemins.get(Trajet.index).aretes().size();i++){
           Label ville = new Label("- " + chemins.get(Trajet.index).aretes().get(i).getArrivee().toString());
            details.getChildren().addAll(ville);
        }

        Label arrive = new Label(Trajet.arrive);
        details.getChildren().addAll(arrive);
        details.setAlignment(Pos.CENTER_LEFT);
        details.setSpacing(10);
        Button retour = new Button("Retour <-");
        retour.setOnMouseClicked(e -> {
            new Voyage().start(Trajet.stage, Trajet.voyageur, Trajet.reseau, Trajet.depart, Trajet.arrive);
        });
        main.getChildren().addAll(titre,voyage,details,retour);
        main.setMaxHeight(500);
        main.setPrefHeight(500);
        main.setMaxWidth(800);
        main.setPrefWidth(800);
        main.setSpacing(20);
        BorderPane pane = new BorderPane();
        pane.setCenter(main);
        pane.setStyle("-fx-background-color: #D6E6FF;");

        Scene scene = new Scene(pane, 1280, 720);
        stage.setScene(scene);
        scene.getStylesheets().add((System.getProperty("user.dir") + "/src/ihm/control.css"));
        stage.setTitle("TrueV");
        stage.show();
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}
