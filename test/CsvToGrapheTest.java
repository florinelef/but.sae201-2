import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.Lieu;
import poo.*;


public class CsvToGrapheTest{

    Plateforme p;
    Sommet s1;
    Sommet s2;
    Voyageur v;
    Voyageur v2;
    Voyageur v3;
    String chemin = System.getProperty("user.dir") + "/res/Data.csv";
    String cheminCorres = System.getProperty("user.dir" + "/res/Correspondance.csv");
    
    @BeforeEach
    void initialisation(){        
        List<TypeCout> couts = new ArrayList<>();
        couts.add(TypeCout.PRIX);
        couts.add(TypeCout.CO2);

        v= new Voyageur(couts);
        v2 = new Voyageur(couts);
        v3 = new Voyageur(couts);

        p= new Plateforme();
        s1 = new Sommet("Lille");
        s2 = new Sommet("Orchies");
    }

    @Test 
    void testAddSommetVide(){
        assertEquals("[]",p.getSommets().toString());
    }

    @Test
    void testAddSommet() {
        p.addSommet(s1);
        assertEquals("[Lille]",p.getSommets().toString());
        p.addSommet(s1); //Vérification qu'il n'ajoute pas deux fois le même sommet
        assertEquals("[Lille]",p.getSommets().toString());
        p.addSommet(s2);
        ArrayList<String> result = new ArrayList<String>();
        for(Lieu l : p.getSommets()){
            result.add(l.toString());
        }
        Collections.sort(result);
        assertEquals("[Lille, Orchies]", result.toString());
    }

    @Test
    void testTransformerVide(){
        assertEquals("[]",p.getSommets().toString());
    }

    @Test
    void testTransformer() {
        p= CsvToGraphe.transformer(chemin, cheminCorres,v);
        ArrayList<String> result = new ArrayList<String>();
        for(Lieu l : p.getSommets()){
            result.add(l.toString());
        }
        Collections.sort(result);
        assertEquals("[4 Cantons:BUS, 4 Cantons:TRAIN, IUT:BUS, IUT:TRAIN, Lille:TRAIN, Orchies:TRAIN]", result.toString());
    }
}