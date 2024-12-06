/**La classe CoutChemin permet d'associer le type de coût (prix, temps, co2) avec la valeur 
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/

package poo;

import java.io.Serializable;
import java.util.HashMap;

public class CoutChemin implements Serializable{
    private HashMap <TypeCout, Double> couts;

    /** Constructeur qui prend en paramètre une HashMap 
     * @param couts la liste associative des valeurs pour chaque type de coût des chemins
    */
    public CoutChemin(HashMap <TypeCout, Double> couts) {
        this.couts = couts;
    }

    public CoutChemin(TypeCout type, double cout){
        this.couts = new HashMap<TypeCout, Double>();
        this.couts.put(type, Double.valueOf(cout));
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

    public CoutChemin addCout(TypeCout type, double cout){
        if(!this.couts.containsKey(type)){
            this.couts.put(type, Double.valueOf(cout));
        }
        return this;
    }
}
