import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poo.*;

public class SommetTest{

    public Sommet s1,s2,s3,s4;
    
    @BeforeEach
    void initialisation(){
        s1 = new Sommet("Lille");
        s2 = new Sommet("Orchies");
        s3 = new Sommet("Villeneuve d'Ascq");
        s4 = new Sommet("IUT");
    }

    @Test
    void testToString(){
        assertEquals("Lille", s1.toString());
        assertEquals("Orchies", s2.toString());
        assertEquals("Villeneuve d'Ascq", s3.toString());
        assertEquals("IUT", s4.toString());
        }
}