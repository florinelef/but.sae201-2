import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poo.*;

import fr.ulille.but.sae_s2_2024.Trancon;

public class PlateformeTest {
    Plateforme p1;
    Sommet s1,s2,s3,s4;
    Trancon t1,t2,t3,t4;
    Set<Lieu> sommets = new HashSet<Lieu>();
    Set<Trancon> trancons = new HashSet<Trancon>();
    CoutTroncon c1;
    Voyageur v,v2;
    @BeforeEach 
    void initialisation(){
        s1 = new Sommet("Lille");
        s2 = new Sommet("Orchies");
        s3 = new Sommet("Villeneuve d'Ascq");
        s4 = new Sommet("IUT");
        sommets.add(s1);
        sommets.add(s2);
        sommets.add(s3);
        sommets.add(s4);

        List<TypeCout> couts = new ArrayList<>();
        couts.add(TypeCout.PRIX);
        couts.add(TypeCout.CO2);

        v = new Voyageur(couts, 50,-1,-1);
        v2= new Voyageur(null);
        c1 = new CoutTroncon(15,0.23,60);
        t1 = new Arete(s1, s2,ModaliteTransport.TRAIN , c1);
        t2 = new Arete(s1, s3,ModaliteTransport.AVION , c1);

        trancons.add(t1);
        trancons.add(t2);
        p1 = new Plateforme(sommets,trancons,v);

    }

    @Test
    void testGetteur(){
        assertEquals(sommets, p1.getSommets());
        assertEquals(trancons, p1.getAretes());
        assertEquals(s1, p1.getVille("Lille"));
        assertEquals("Orchies", p1.getVille("Orchies").toString());
        assertEquals("Sommets: [Lille, Orchies, IUT, Villeneuve d'Ascq]  Aretes: [(TRAIN Lille -> Orchies : 15,000), (AVION Lille -> Villeneuve d'Ascq : 15,000)]", p1.toString());
        assertEquals("Sommets: [Lille, Orchies, IUT, Villeneuve d'Ascq]  Aretes: [(TRAIN Lille -> Orchies : 0,230), (AVION Lille -> Villeneuve d'Ascq : 0,230)]", p1.getGrapheAvecCritere(TypeCout.CO2).toString());
    }

    @Test 
    void testAddSommet(){
        assertEquals(4, p1.getSommets().size());
        p1.addSommet(new Sommet("Madeleine"));
        assertEquals(5, p1.getSommets().size());
    }

    @Test
    void testAddArete(){
        assertEquals(2, p1.getAretes().size());
        p1.addArete(new Arete(s3,s4,ModaliteTransport.AVION,c1));
        assertEquals(3, p1.getAretes().size());
    }

    @Test
    void testSetteur(){
        assertEquals(v, p1.getVoyageur());
        p1.setVoyageur(v2);
        assertEquals(v2, p1.getVoyageur());


    }
}