import fr.ulille.but.sae_s2_2024.ModaliteTransport;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poo.*;

public class AreteTest{
    public Sommet s1,s2,s3,s4;
    public CoutTroncon c1;
    public Arete a1,a2,a3,a4;
    
    @BeforeEach
    void initialisation(){
    s1 = new Sommet("Lille");
    s2 = new Sommet("Orchies");
    s3 = new Sommet("IUT");
    s4 = new Sommet("Villeneuve d'Ascq");
    c1 = new CoutTroncon(15,20,0.26);

    a1 = new Arete(s1,s2, ModaliteTransport.AVION, c1);
    a2 = new Arete(s1,s3, ModaliteTransport.TRAIN, c1);
    a3 = new Arete(s4,s2, ModaliteTransport.TRAIN, c1);
    a4 = new Arete(null, null,null, null);
    }

    @Test
    void testGetDepartTest(){
        assertEquals(s1, a1.getDepart());
        assertEquals(s1, a2.getDepart());
        assertEquals(null, a4.getDepart());
        assertEquals("Villeneuve d'Ascq", a3.getDepart().toString());
    }

    @Test
    void testGetArrivee(){
        assertEquals(s2, a1.getArrivee());
        assertEquals(s3, a2.getArrivee());
        assertEquals(null, a4.getArrivee());
        assertEquals("Orchies", a3.getArrivee().toString());
    }

    @Test 
    void testGetModalite(){
        assertEquals(ModaliteTransport.AVION, a1.getModalite());
        assertEquals(ModaliteTransport.TRAIN, a2.getModalite());
        assertEquals(ModaliteTransport.TRAIN, a3.getModalite());
        assertEquals(null,a4.getModalite());
    }

    @Test
    void testGetCout(){
        assertEquals(c1, a1.getCout());
        assertEquals(c1, a2.getCout());
        assertEquals(c1, a3.getCout());
        assertEquals(null, a4.getCout());
    }
}
