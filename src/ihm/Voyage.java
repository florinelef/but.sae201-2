package ihm;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import poo.Algorithme;
import poo.Plateforme;
import poo.TypeCout;
import poo.Voyageur;


public class Voyage extends Application{

    public static Plateforme reseau = new Plateforme();
    public static Voyageur voyageur = new Voyageur(null);
    public static String depart = null ;
    public static String arrive = null ;
    public static Stage stage = null;
    public static int index = 0;

    Slider prix = new Slider();
    Slider temps = new Slider();
    Slider c02 = new Slider(0, 5, 0.1);
    class CheminChangeListener implements ListChangeListener<String>{
    
        public void onChanged(Change<? extends String> arg0) {
         new Trajet().start(stage, arg0.getList().toString(), Voyage.depart, Voyage.arrive, Voyage.voyageur, Voyage.reseau, Voyage.index);   
        }
        
    }
        class DepartChangeListener implements ListChangeListener<String>{

        public void onChanged(Change<? extends String> arg0) {
            depart = arg0.getList().toString().substring(1, arg0.getList().toString().length()-1); 
        }
    }
    class ArriveChangeListener implements ListChangeListener<String>{

        public void onChanged(Change<? extends String> arg0) {
            arrive = arg0.getList().toString().substring(1, arg0.getList().toString().length()-1);
        }
    }

    public void start(Stage s, Voyageur v, Plateforme p, String depart, String arrivee){
        Voyage.stage = s;
        Voyage.voyageur = v;
        Voyage.reseau = p ;
        Voyage.depart = depart;
        Voyage.arrive = arrivee;
        this.start(s);
    }

    public void start (Stage stage){

        List<TypeCout> couts = new ArrayList<>();
        couts.add(TypeCout.PRIX);
        voyageur.setCriteres(couts);

        /// Main avec les différents chemins 
        VBox chemins = new VBox();
        ListView<String> list = new ListView<>(afficheChemins());
        chemins.getChildren().addAll(list);
        
        list.getSelectionModel().getSelectedItems().addListener(new CheminChangeListener());

        //entête
        HBox menu = new HBox();

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
                    reseau.setVoyageur(voyageur);
                }); 
                CheckBox bus = new CheckBox("CO2");
                bus.setOnMouseClicked(e -> {
                    if(bus.selectedProperty().get()){
                        voyageur.getCriteres().add(TypeCout.CO2);
                    }
                    else{
                        voyageur.getCriteres().remove(TypeCout.CO2);
                    }
                    reseau.setVoyageur(voyageur);
                }); 
                CheckBox train = new CheckBox("Temps");
                train.setOnMouseClicked(e -> {
                    if(train.selectedProperty().get()){
                        voyageur.getCriteres().add(TypeCout.TEMPS);
                    }
                    else{
                        voyageur.getCriteres().remove(TypeCout.TEMPS);
                    }
                    reseau.setVoyageur(voyageur);
                }); 

                critere.setSpacing(25);
                critere.setAlignment(Pos.CENTER_LEFT);
                critere.getChildren().addAll(avion,bus,train);

                VBox vButton = new VBox();
                Button recherche = new Button("Recherche");
                recherche.setOnMouseClicked(e -> {
                   list.setItems(afficheChemins());
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

        //Coté Sliders
        VBox sliders = new VBox();
        Label titre = new Label("Bornes : ");
        titre.setStyle("-fx-font: 20 arial");

        VBox vPrix = new VBox();
        Label lPrix = new Label("Limite de prix");
        lPrix.setStyle("-fx-font: 15 arial");
        prix = new Slider(0, Algorithme.maxOf(Algorithme.plusCourtsChemins(Voyage.reseau, Voyage.depart, Voyage.arrive, 999), TypeCout.PRIX), 1);
        TextField tPrix = new TextField(String.valueOf(prix.valueProperty().intValue()));
        prix.setOnMouseDragged(e->{
            tPrix.setText(String.valueOf(prix.valueProperty().intValue()));
            voyageur.setBornePrix(prix.valueProperty().doubleValue());
            reseau.setVoyageur(voyageur);
            list.setItems(afficheChemins());
        });
        prix.setOnMouseClicked(e->{
            tPrix.setText(String.valueOf(prix.valueProperty().intValue()));
            voyageur.setBornePrix(prix.valueProperty().doubleValue());
            reseau.setVoyageur(voyageur);
            list.setItems(afficheChemins());
        });
        tPrix.setOnAction(e-> {
            prix.setValue(Integer.valueOf(tPrix.getText()));
            voyageur.setBornePrix(prix.valueProperty().doubleValue());
            reseau.setVoyageur(voyageur);
            list.setItems(afficheChemins());
        });
        vPrix.getChildren().addAll(lPrix, prix, tPrix);
        vPrix.setAlignment(Pos.CENTER);
        vPrix.setSpacing(10);

        VBox vCO2 = new VBox();
        Label lC02 = new Label("Limite d'émission de CO2");
        lC02.setStyle("-fx-font: 15 arial");
        c02 = new Slider(0, Algorithme.maxOf(Algorithme.plusCourtsChemins(Voyage.reseau, Voyage.depart, Voyage.arrive, 999), TypeCout.CO2), 0.1);
        c02.setBlockIncrement(0.1);
        TextField tC02 = new TextField(String.valueOf(prix.valueProperty().doubleValue()));
        c02.setOnMouseDragged(e->{
            tC02.setText(String.format("%.2f", c02.valueProperty().doubleValue()));
            voyageur.setBorneCo2(c02.valueProperty().doubleValue());
            reseau.setVoyageur(voyageur);
            list.setItems(afficheChemins());
        });
        c02.setOnMouseClicked(e->{
            tC02.setText(String.format("%.2f", c02.valueProperty().doubleValue()));
            voyageur.setBorneCo2(c02.valueProperty().doubleValue());
            reseau.setVoyageur(voyageur);
            list.setItems(afficheChemins());
        });
        tC02.setOnAction(e-> {
            c02.setValue(Integer.valueOf(tC02.getText()));
            voyageur.setBorneCo2(c02.valueProperty().doubleValue());
            reseau.setVoyageur(voyageur);
            list.setItems(afficheChemins());
        });
        vCO2.getChildren().addAll(lC02, c02,tC02);
        vCO2.setAlignment(Pos.CENTER);
        vCO2.setSpacing(10);

        VBox vTemps = new VBox();
        Label lTemps = new Label("Limite de temps");
        Slider temps = new Slider(0, Algorithme.maxOf(Algorithme.plusCourtsChemins(Voyage.reseau, Voyage.depart, Voyage.arrive, 999), TypeCout.TEMPS), 1);
        TextField tTemps = new TextField(String.valueOf(prix.valueProperty().intValue()));
        temps.setOnMouseDragged(e->{
            tTemps.setText(String.valueOf(temps.valueProperty().intValue()));
            voyageur.setBorneTemps(temps.valueProperty().doubleValue());
            reseau.setVoyageur(voyageur);
            list.setItems(afficheChemins());
        });
        temps.setOnMouseClicked(e->{
            tTemps.setText(String.valueOf(temps.valueProperty().intValue()));
            voyageur.setBorneTemps(temps.valueProperty().doubleValue());
            reseau.setVoyageur(voyageur);
            list.setItems(afficheChemins());
        });

        tTemps.setOnAction(e-> {
            temps.setValue(Integer.valueOf(tTemps.getText()));
            voyageur.setBorneTemps(temps.valueProperty().doubleValue());
            reseau.setVoyageur(voyageur);
            list.setItems(afficheChemins());
        });


        lTemps.setStyle("-fx-font: 15 arial");
        vTemps.getChildren().addAll(lTemps, temps,tTemps);
        vTemps.setAlignment(Pos.CENTER);
        vTemps.setSpacing(10);

        sliders.setStyle("-fx-background-color: #C2DAFF;");
        sliders.setSpacing(50);
        sliders.setAlignment(Pos.CENTER_LEFT);
        sliders.getChildren().addAll(titre,vPrix,vCO2,vTemps);

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #D6E6FF;");
        pane.setTop(menu);
        pane.setLeft(sliders);
        pane.setCenter(chemins);

        Scene scene = new Scene(pane, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("TrueV");
        stage.show();


    }
    

    public ObservableList<String> afficheChemins(){
        ObservableList<String> ol = FXCollections.observableArrayList();
        List<Chemin> resultats = new ArrayList<>();
        resultats = Algorithme.plusCourtsChemins(Voyage.reseau, Voyage.depart, Voyage.arrive, 999);
        resultats.add(0, Algorithme.getCheminPlusEcologique(Voyage.reseau, Voyage.depart, Voyage.arrive));
        ol.add("Le plus écolo : " + Voyage.depart + " -> " + Voyage.arrive + '\n' + Algorithme.getCouts(resultats.get(0)));
        for(int i=1; i<resultats.size();i++){
            ol.add(Voyage.depart + " -> " + Voyage.arrive + '\n' + Algorithme.getCouts(resultats.get(i)));
        };
        // prix.setMax(Algorithme.maxOf(resultats, TypeCout.PRIX));
        // c02.setMax(Algorithme.maxOf(resultats, TypeCout.CO2));
        // temps.setMax(Algorithme.maxOf(resultats, TypeCout.TEMPS));
        return ol;
    }

    public static void main(String[] args){

        Application.launch(args);
    }
}
