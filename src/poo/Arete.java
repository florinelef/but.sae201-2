/**La classe Arete implémente l'interface Trancon pour créer une arête entre deux Sommets.
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/

package poo;

import java.io.Serializable;

import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.Trancon;

/*Classe Arete qui permet de relier deux sommets entre eux avec la modalité de transport et le coût du trajet */
public class Arete implements Trancon, Serializable {
    /**sDep représente la ville de départ */
    private Lieu sDep;
    /** sArr représente la ville d'arrivée */
    private Lieu sArr;
    /** modalite représente le mode de transport utilisé sur l'arête */
    private ModaliteTransport modalite;
    /** cout représente le coût du type qu'on aura choisi plus tôt
     * (temps, prix, CO2) en fonction du critère choisi par le voyageur
     * @see #CoutTroncon
     * @see #TypeCout
     */
    private CoutTroncon cout;
    
    /** Constructeur qui prend en paramètre 
     * @param sDep sommet de départ
     * @param sArr sommet d'arrivée
     * @param modalite la modalité de transport
     * @param cout le coût 
    */
    public Arete(Lieu sDep, Lieu sArr, ModaliteTransport modalite, CoutTroncon cout) {
        this.sDep = sDep;
        this.sArr = sArr;
        this.modalite = modalite;
        this.cout = cout;
    }
    
    /** @return Permet de <em>récupérer le sommet de départ</em> */
    public Lieu getDepart() {
        return this.sDep;
    }
    
    /** @return Permet de <em>récupérer le sommet d'arrivée</em> */
    public Lieu getArrivee() {
        return this.sArr;
    }
    
    /** @return Permet de <em>récupérer la modalité de transport</em> */
    public ModaliteTransport getModalite() {
        return this.modalite;
    }

    /** @return Permet de <em>récupérer le coût du trajet</em> */
    public CoutTroncon getCout() {
        return this.cout;
    }

    /*Méthode toString */
    public String toString(){
        if(this.modalite == null) return this.sDep.toString() + " -> " + this.sArr.toString();
        return this.sDep.toString() + " -> " + this.sArr.toString() + " (" + this.modalite.toString() + ')';
    }
}
