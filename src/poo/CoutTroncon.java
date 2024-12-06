/**La classe CoutTroncon permet d'associer le type de coût (prix, temps, co2) avec la valeur 
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/

package poo;

import java.io.Serializable;
import java.util.HashMap;

public class CoutTroncon implements Serializable{
    private HashMap <TypeCout, Double> couts;

    /** Constructeur qui prend en paramètre une HashMap 
     * @param couts la liste associative des valeurs pour chaque type de coût des arêtes
    */
    public CoutTroncon(HashMap <TypeCout, Double> couts) {
        this.couts = couts;
    }

    /**Constructeur qui prend en paramètre les valeurs des <em>trois types de coût</em> */
    public CoutTroncon(double prix, double co2, double temps){
        this.couts = new HashMap<TypeCout, Double>();
        this.couts.put(TypeCout.PRIX, prix);
        this.couts.put(TypeCout.CO2, co2);
        this.couts.put(TypeCout.TEMPS, temps);
    }

    /** @return Getter de la HashMap */
    public HashMap<TypeCout, Double> getCouts(){
        return this.couts;
    }

    /** @return affiche la liste associative sous forme de chaîne de caractère */
    public String toString() {
        return this.couts.toString();
    }

    public double getCoutByType(TypeCout type){
        return this.couts.get(type);
    }
}
