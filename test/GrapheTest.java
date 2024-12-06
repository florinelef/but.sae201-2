/**
 * Cette classe est un test d'un scénario permettant de vérifier l'affichage des graphes créés. 
 * @see #Plateforme 
 * @see #Voyageur 
 * @see #Chemin
 */

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poo.*;


public class GrapheTest {
    
    Plateforme reseaux;
    Voyageur marie;
    List<Chemin> resultats;

    @BeforeEach
    void initialisation(){
        String chemin = System.getProperty("user.dir") + "/res/Data.csv";
        String cheminCorrespondance = System.getProperty("user.dir") + "/res/Correspondance.csv";
        
        List<TypeCout> couts = new ArrayList<>();
        couts.add(TypeCout.PRIX);
        couts.add(TypeCout.CO2);
        marie = new Voyageur(couts, -1, -1, -1);
        
        reseaux = CsvToGraphe.transformer(chemin, cheminCorrespondance, marie);
    }

    @Test 
    void test(){
        //Les k plus court chemins en fonction des préférences de Marie
        assertEquals("[Chemin(Arêtes: [Orchies:BUS -> Orchies:TRAIN, Orchies:TRAIN -> IUT:TRAIN (TRAIN), IUT:TRAIN -> IUT:BUS], Poids: 1,400000), Chemin(Arêtes: [Orchies:BUS -> Orchies:TRAIN, Orchies:TRAIN -> Lille:TRAIN (TRAIN), Lille:TRAIN -> IUT:TRAIN (TRAIN), IUT:TRAIN -> IUT:BUS], Poids: 3,100000), Chemin(Arêtes: [Orchies:BUS -> Orchies:TRAIN, Orchies:TRAIN -> IUT:TRAIN (TRAIN), IUT:TRAIN -> IUT:BUS], Poids: 26,000000), Chemin(Arêtes: [Orchies:BUS -> Orchies:TRAIN, Orchies:TRAIN -> 4 Cantons:TRAIN (TRAIN), 4 Cantons:TRAIN -> 4 Cantons:BUS, 4 Cantons:BUS -> IUT:BUS (BUS)], Poids: 40,000000)]",Algorithme.plusCourtsChemins(reseaux, "Orchies", "IUT", 2).toString());
    }

    @Test
    void testGraphe(){
          //Contenu du graphe, seul les arêtes des trajets contenant la préférence de l'utilisateur s'affiche
        assertEquals("Sommets: [4 Cantons:TRAIN, Orchies:TRAIN, Orchies:BUS, IUT:TRAIN, Lille:BUS, IUT:BUS, 4 Cantons:BUS, Lille:TRAIN]  Aretes: [(null Lille:TRAIN -> Lille:BUS : 10,000), (TRAIN 4 Cantons:TRAIN -> Orchies:TRAIN : 22,000), (TRAIN IUT:TRAIN -> Orchies:TRAIN : 14,000), (null Orchies:BUS -> Orchies:TRAIN : 2,000), (TRAIN Orchies:TRAIN -> Lille:TRAIN : 60,000), (BUS IUT:BUS -> 4 Cantons:BUS : 11,000), (null IUT:BUS -> IUT:TRAIN : 10,000), (TRAIN Orchies:TRAIN -> IUT:TRAIN : 14,000), (TRAIN Lille:TRAIN -> IUT:TRAIN : 42,000), (null IUT:TRAIN -> IUT:BUS : 10,000), (TRAIN Lille:TRAIN -> Orchies:TRAIN : 60,000), (TRAIN Orchies:TRAIN -> 4 Cantons:TRAIN : 22,000), (null Lille:BUS -> Lille:TRAIN : 10,000), (TRAIN 4 Cantons:TRAIN -> IUT:TRAIN : 65,000), (BUS 4 Cantons:BUS -> IUT:BUS : 11,000), (TRAIN IUT:TRAIN -> Lille:TRAIN : 42,000), (null Orchies:TRAIN -> Orchies:BUS : 2,000), (TRAIN IUT:TRAIN -> 4 Cantons:TRAIN : 65,000), (null 4 Cantons:BUS -> 4 Cantons:TRAIN : 5,000), (null 4 Cantons:TRAIN -> 4 Cantons:BUS : 5,000)]", reseaux.toString());
    }
}


