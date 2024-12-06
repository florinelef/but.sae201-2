import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poo.*;

public class CoutTronconTest {

    public double prix,temps,co2;
    public HashMap<TypeCout,Double> h1;
    public CoutTroncon c1,c2,c3;
    

    @BeforeEach 
    void initialisation(){

        prix= 15;
        co2 = 0.23;
        temps = 60;
        h1 = new HashMap<TypeCout,Double>();
        c3 = new CoutTroncon(new HashMap<TypeCout,Double>());
        h1.put(TypeCout.PRIX, prix);
        h1.put(TypeCout.TEMPS, temps);
        h1.put(TypeCout.CO2, co2);

        c1 = new CoutTroncon(h1);
        c2 = new CoutTroncon(prix, co2, temps);
    }

    @Test 
    void testGetCouts(){
        assertEquals(new HashMap<TypeCout,Double>(), c3.getCouts());
        assertEquals(new CoutTroncon(15,0.23,60).getCouts(), c1.getCouts());
    }

    @Test 
    void testToString(){
        assertEquals("{}", c3.toString());

        assertEquals(15.0, c2.getCouts().get(TypeCout.PRIX));
        assertEquals(0.23, c2.getCouts().get(TypeCout.CO2));
        assertEquals(60.0, c2.getCouts().get(TypeCout.TEMPS));
    }
}