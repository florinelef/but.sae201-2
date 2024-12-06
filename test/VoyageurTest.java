import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poo.*;

public class VoyageurTest {

    Voyageur klara,random;

    @BeforeEach
    void initialisation(){
        List<TypeCout> couts = new ArrayList<>();
        couts.add(TypeCout.PRIX);
        couts.add(TypeCout.CO2);
        klara= new Voyageur(couts,-1,-1,-1);
        random = new Voyageur(null);
    }

    @Test
    void testGetteur(){
        List<TypeCout> res = new ArrayList<>();
        res.add(TypeCout.PRIX);
        res.add(TypeCout.CO2);
        assertEquals(res.toString(), klara.getCriteres().toString());
        
        assertEquals(-1, klara.getBornePrix());

        assertEquals(-1, klara.getBorneCo2());

        assertEquals(-1, klara.getBorneTemps());
    }

    @Test
    void testSetteur(){
        List<TypeCout> res = new ArrayList<>();
        res.add(TypeCout.PRIX);
        res.add(TypeCout.CO2);

        assertNull(random.getCriteres());
        random.setCriteres(res);
        assertNotNull(random.getCriteres());

        assertEquals(-1,random.getBornePrix());
        random.setBornePrix(20);
        assertEquals(20,random.getBornePrix());

        assertEquals(-1,random.getBorneCo2());
        random.setBorneCo2(0);
        assertEquals(0,random.getBorneCo2());

        assertEquals(-1,random.getBorneTemps());
        random.setBorneTemps(20);
        assertEquals(20,random.getBorneTemps());

        klara.setBorneTemps(50);
        assertEquals(50, klara.getBorneTemps());
    }
}
