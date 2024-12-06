//Exemple d'utilisation de Plateforme et Algorithme
package poo;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;

public class Main {
    public static void main(String[] args) {

        String chemin = System.getProperty("user.dir") + "/res/Data.csv";
        String cheminCorrespondance = System.getProperty("user.dir") + "/res/Correspondance.csv";
        
        //création de l'utilisatrice
        List<TypeCout> criteres = new ArrayList<TypeCout>();
        criteres.add(TypeCout.PRIX);
        Voyageur voyageuse = new Voyageur(criteres, -1, -1, -1);
        //création du réseau
        Plateforme reseau = CsvToGraphe.transformer(chemin, cheminCorrespondance, voyageuse);
        
        Sauvegarde saving = new Sauvegarde(reseau);
        //System.out.println(reseau);
        //On veut prendre les 4 chemins les moins chers de la villeC à la villeD selon les critères de l'utilisatrice
        List<Chemin> resultats = Algorithme.plusCourtsChemins(reseau, "Lille:BUS", "Orchies:BUS", 3);
        saving.add(Voyage.cheminToVoyage(resultats));
        Algorithme.afficher(resultats);


        voyageuse.setBornePrix(150);
        criteres.clear();
        criteres.add(TypeCout.CO2);
        voyageuse.setCriteres(criteres);
        reseau.setVoyageur(voyageuse);
        resultats = Algorithme.plusCourtsChemins(reseau, "Orchies", "Lille", 5);
        saving.add(Voyage.cheminToVoyage(resultats));
        Algorithme.afficher(resultats);

        reseau.getVoyageur().getCriteres().add(TypeCout.TEMPS);
        resultats = Algorithme.plusCourtsChemins(reseau, "Lille:BUS", "Orchies:BUS", 3);
        saving.add(Voyage.cheminToVoyage(resultats));
        Algorithme.afficher(resultats);

        resultats = Algorithme.plusCourtsChemins(reseau, "IUT:BUS", "4 Cantons:BUS", 4);
        saving.add(Voyage.cheminToVoyage(resultats));
        Algorithme.afficher(resultats);

        // Sauvegarde des trajets 
        saving.saveInFile();
        saving.displayAllSauvegarde();

        // voir les statistiques des voyages
        saving.seeStats(TypeCout.PRIX);

    }
}
