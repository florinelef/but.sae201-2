import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.Lieu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poo.*;


public class CorrepondanceTest {

    Lieu l,o;
    double m,c,p;
    Correspondance correspondance;

    @BeforeEach
    void initialisation(){
        m=10;
        c=0.1;
        p=5;
        l=new Sommet("Lille");
        o= new Sommet("Orchies");
        correspondance = new Correspondance(l, ModaliteTransport.TRAIN, ModaliteTransport.AVION, p, c, m);    }

    @Test
    void testGetteur(){
        assertEquals(l, correspondance.getVille());
        assertEquals("Lille", correspondance.getVille().toString());

        assertEquals(ModaliteTransport.TRAIN, correspondance.getM1());
        assertEquals("TRAIN", correspondance.getM1().toString());

        assertEquals(ModaliteTransport.AVION, correspondance.getM2());
        assertEquals("AVION", correspondance.getM2().toString());

        assertEquals(m, correspondance.getCouts().getCoutByType(TypeCout.TEMPS));
        assertEquals(10, correspondance.getCouts().getCoutByType(TypeCout.TEMPS));

        assertEquals(c, correspondance.getCouts().getCoutByType(TypeCout.CO2));
        assertEquals(0.1, correspondance.getCouts().getCoutByType(TypeCout.CO2));

        assertEquals(p, correspondance.getCouts().getCoutByType(TypeCout.PRIX));
        assertEquals(5, correspondance.getCouts().getCoutByType(TypeCout.PRIX));

        //assertEquals(false, assertEquals(10, correspondance.getCouts().getCoutByType(TypeCout.CO2)));
    }

    @Test 
    void testSetteur(){
        correspondance.setVille(o);
        assertEquals(o, correspondance.getVille());
        assertEquals("Orchies", correspondance.getVille().toString());

        correspondance.setM1(ModaliteTransport.AVION);
        assertEquals(ModaliteTransport.AVION, correspondance.getM1());
        assertEquals("AVION", correspondance.getM1().toString());

        correspondance.setM2(ModaliteTransport.TRAIN);
        assertEquals(ModaliteTransport.TRAIN, correspondance.getM2());
        assertEquals("TRAIN", correspondance.getM2().toString());
    }

    @Test
    void testConstructeurString() {
        Correspondance corresp = new Correspondance("Ville", "TRAIN", "BUS", "20", "0.3", "30");
        assertEquals("Ville : TRAIN vers BUS; Prix = 20.0€; CO2 = 0.3kg CO2; Temps = 30.0min", corresp.toString());
    }
}
