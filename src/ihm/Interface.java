package ihm;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import poo.CsvToGraphe;
import poo.Plateforme;
import poo.TypeCout;
import poo.Voyageur;

public class Interface extends Application{

    public static Plateforme reseau = new Plateforme();
    public static Voyageur voyageur = new Voyageur();
    public static String depart = null;
    public static String arrivee = null;
    
    class DepartChangeListener implements ListChangeListener<String>{

        public void onChanged(Change<? extends String> arg0) {
            depart = arg0.getList().toString().substring(1, arg0.getList().toString().length()-1); 
        }
    }
    class ArriveChangeListener implements ListChangeListener<String>{

        public void onChanged(Change<? extends String> arg0) {
            arrivee = arg0.getList().toString().substring(1, arg0.getList().toString().length()-1);
        }
    }

    public void start(Stage stage){

        BorderPane pane = new BorderPane();
        HBox menu = new HBox();

        //Entête avec le titre
        VBox vTitre = new VBox();
        vTitre.setAlignment(Pos.CENTER);
        vTitre.setStyle("-fx-background-color: #BDD7FF;");
        Label titre = new Label("TrueV votre voyage !");
        titre.setTextFill(Color.WHITE);
        titre.setStyle("-fx-font: 50 arial");
        vTitre.setMaxHeight(70);
        vTitre.setPrefHeight(70);
        vTitre.getChildren().add(titre);

        //Création d'une liste contenant toute les villes du fichier CSV
        ObservableList<String> villes = FXCollections.observableArrayList();
        villes.addAll(reseau.getVillesSansModalite());
        
        //Créations des listes des villes de départ et d'arrivée
        HBox treeHbox = new HBox();
        ListView<String> ListViewDepart = new ListView<>();
        ListViewDepart.getItems().addAll(villes);
        ListView<String> ListViewArrive = new ListView<>();

        ListViewDepart.getSelectionModel().getSelectedItems().addListener(new DepartChangeListener());
        ListViewArrive.getItems().addAll(villes);
        ListViewArrive.getSelectionModel().getSelectedItems().addListener(new ArriveChangeListener());

        treeHbox.setMaxHeight(25);
        treeHbox.setPrefHeight(25);
        treeHbox.setSpacing(60);
        treeHbox.getChildren().addAll(ListViewDepart,ListViewArrive);

        //CheckBox pour les modélités de transport
        // Ajouter des images ??
        VBox critere = new VBox();
        CheckBox avion = new CheckBox("Prix");
        avion.setOnMouseClicked(e -> {
            if(avion.selectedProperty().get()){
                voyageur.getCriteres().add(TypeCout.PRIX);
            }
            else{
                voyageur.getCriteres().remove(TypeCout.PRIX);
            }
        }); 

        CheckBox bus = new CheckBox("CO2");
        bus.setOnMouseClicked(e -> {
            if(bus.selectedProperty().get()){
                voyageur.getCriteres().add(TypeCout.CO2);
            }
            else{
                voyageur.getCriteres().remove(TypeCout.CO2);
            }
        }); 

        CheckBox train = new CheckBox("Temps");
        train.setOnMouseClicked(e -> {
            if(train.selectedProperty().get()){
                voyageur.getCriteres().add(TypeCout.TEMPS);
            }
            else{
                voyageur.getCriteres().remove(TypeCout.TEMPS);
            }
        }); 

        critere.setSpacing(25);
        critere.setAlignment(Pos.CENTER_LEFT);
        critere.getChildren().addAll(avion,bus,train);

        VBox vButton = new VBox();
        Button recherche = new Button("Recherche");
        recherche.setOnMouseClicked(e -> {
            new Voyage().start(stage, Interface.voyageur, Interface.reseau, Interface.depart, Interface.arrivee);

        });

        recherche.setMaxWidth(100);
        recherche.setPrefWidth(100);
        recherche.setMaxHeight(40);
        recherche.setPrefHeight(40);
        vButton.getChildren().add(recherche);
        vButton.setAlignment(Pos.CENTER_RIGHT);
        vButton.setMaxWidth(300);
        vButton.setPrefWidth(300);

        menu.setSpacing(50);
        menu.setStyle("-fx-background-color: #A4C7FF;");
        menu.setMaxHeight(120);
        menu.setPrefHeight(120);
        menu.getChildren().addAll(treeHbox, critere,vButton);
        menu.setAlignment(Pos.CENTER);

        pane.setTop(vTitre);
        pane.setCenter(menu);
        pane.setStyle("-fx-background-color: #D6E6FF;");

        Scene scene = new Scene(pane, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("TrueV");
        stage.show();
    }
    
    public static void main(String[] args){

        String chemin = System.getProperty("user.dir") + "/res/Data.csv";
        String cheminCorrespondance = System.getProperty("user.dir") + "/res/Correspondance.csv";

        List<TypeCout> couts = voyageur.getCriteres();
        //création de l'utilisateur.ice de test pour avoir le nom des villes
        Voyageur test = new Voyageur(couts, -1, -1, -1);
        //création du réseau de test
        reseau = CsvToGraphe.transformer(chemin, cheminCorrespondance, test);

        Application.launch(args);
    }
}
