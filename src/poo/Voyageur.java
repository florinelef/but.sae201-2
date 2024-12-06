/**La classe Voyageur permet de modéliser un utilisateur de l'application
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/

package poo;

import java.util.ArrayList;
import java.util.List;

public class Voyageur {
    private List<TypeCout> criteres;
    private double bornePrix;
    private double borneCo2;
    private double borneTemps;

    /** @return Constructeur d'un Voyageur 
     * @param critere le critère que le voyageur cherche à limiter
     * @param bornePrix le prix maximum souhaité
     * @param borneCo2 l'émission de CO2 maximum souhaitée
     * @param borneTemps le temps maximum souhaité
     */

    public Voyageur(List<TypeCout> criteres, double bornePrix, double borneCo2, double borneTemps) {
        this.criteres = criteres;
        this.bornePrix = bornePrix;
        this.borneCo2 = borneCo2;
        this.borneTemps = borneTemps;
    }

     
    public Voyageur(List<TypeCout> critere){
        this(critere, -1, -1, -1);
    }

    public Voyageur(){
        this(new ArrayList<TypeCout>());
    }
    
    /** @return Renvoie les critères sélectionnés par le Voyageur */
    public List<TypeCout> getCriteres() {
        return criteres;
    }

    /**@return double La borne de prix de l'utilisateur */
    public double getBornePrix() {
        return bornePrix;
    }

    /**@return double La borne de cO2 de l'utilisateur */
    public double getBorneCo2() {
        return borneCo2;
    }
    /**@return double La borne de temps de l'utilisateur */
    public double getBorneTemps() {
        return borneTemps;
    }

    /**
     * @param criteres modifie les critères de l'utilisateur
     */
    public void setCriteres(List<TypeCout> criteres) {
        this.criteres = criteres;
    }

    /**
     * @param bornePrix change la borne de prix de l'utilisateur
     */
    public void setBornePrix(double bornePrix) {
        this.bornePrix = bornePrix;
    }

    /**
     * @param borneCo2 change la borne de c02 de l'utilisateur
     */
    public void setBorneCo2(double borneCo2) {
        this.borneCo2 = borneCo2;
    }

    /**
     * @param borneTemps change la borne de temps de l'utilisateur
     */
    public void setBorneTemps(double borneTemps) {
        this.borneTemps = borneTemps;
    }
}
