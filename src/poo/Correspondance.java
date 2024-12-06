/** La classe DataToGraphe permet de transformer des données sous forme d'un tableau de chaînes de caractère
 * en une représentation de type Plateforme
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/
package poo;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.Lieu;


public class Correspondance {
    private Lieu ville;
    private ModaliteTransport m1;
    private ModaliteTransport m2;
    private CoutTroncon couts;

    /**
     * @param ville de type Lieu qui correspond à la ville à laquelle correspond les coûts
     * @param m1 ModalitéTransport de départ 
     * @param m2 ModalitéTransport d'arrivée
     */
    public Correspondance(Lieu ville, ModaliteTransport m1, ModaliteTransport m2, double prix, double co2, double temps){
        this.ville= ville;
        this.m1=m1;
        this.m2=m2;
        this.couts = new CoutTroncon(prix, co2, temps);
    }

    public Correspondance(String ville, String m1, String m2, String prix, String co2, String temps){
        this(new Sommet(ville), ModaliteTransport.valueOf(m1),  ModaliteTransport.valueOf(m2), Double.valueOf(prix), Double.valueOf(co2), Double.valueOf(temps));
    }

    /**
     * @return Lieu ville à laquelle correspond les coûts
     */
    public Lieu getVille() {
        return this.ville;
    }

    /**
     * @param ville permet de modifier la ville de la correspondance
     */
    public void setVille(Lieu ville) {
        this.ville = ville;
    }

    /**
     * @return ModaliteTransport de départ
     */
    public ModaliteTransport getM1() {
        return this.m1;
    }

    /**
     * @param m1 permet de modifier la Modalité de Transport de départ 
     */
    public void setM1(ModaliteTransport m1) {
        this.m1 = m1;
    }

    /**
     * @return ModaliteTransport d'arrivée
     */
    public ModaliteTransport getM2() {
        return this.m2;
    }

    /**
     * @param m2 permet de modififer la Modalité de Transprot d'arrivée
     */
    public void setM2(ModaliteTransport m2) {
        this.m2 = m2;
    }

    /**
     * @return double le cout nécessaire à la correspondance
     */
    public CoutTroncon getCouts() {
        return this.couts;
    }

    /** 
     * @return Retourne une chaîne de caractère qui permet l'affichage des correspondances sous la forme <em>"<Ville> : <modalité1> vers <modalité2> ; Temps = <temps> ; CO2 = <co2> ; Prix = <prix>"
     */
    public String toString() {
        return this.ville + " : " + this.m1 + " vers " + this.m2 + "; Prix = " + this.couts.getCoutByType(TypeCout.PRIX) + "€; CO2 = " + this.couts.getCoutByType(TypeCout.CO2) + "kg CO2; Temps = " + this.couts.getCoutByType(TypeCout.TEMPS) + "min";
    }
}
