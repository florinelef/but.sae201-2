/**La classe Voyage implémente l'interface Chemin pour modéliser les trajets réalisés par les utilisateurs
 * d'une ville à une autre
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/
package poo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Trancon;

public class Voyage implements Chemin, Serializable {
    private List<Trancon> aretes;
    private TypeCout critere;

    /** @return Constructeur de Voyage à partir d'une @param aretes liste d'arêtes */
    public Voyage(List<Trancon> aretes){
        this.aretes = aretes;
    }

    public Voyage(List<Trancon> aretes, TypeCout critere) {
        this(aretes);
        this.critere = critere;
    }
    
    /** @return Renvoie la liste des @param aretes arêtes constituant le voyage */
    public List<Trancon> aretes(){
        return this.aretes;
    }

    /** @return Renvoie le @param poids poids total du voyage à partir de celui des @param aretes aretes */
    public double poids(){
        return this.poids(this.critere);
    }

    public double poids(TypeCout critere){
        double poids = 0;
        for(Trancon t : this.aretes){
            poids += ((Arete)t).getCout().getCouts().get(this.critere);
        }
        return poids;
    }
    
    /** @return Setter pour le @param critere critère du voyage, en dehors du constructeur (null par défaut) */
    public void setCritere(TypeCout critere){
        this.critere = critere;
    }
    /**
     * @param bornePrix double qui représente le prix max
     * @param borneCo2 double qui représente le C02 max
     * @param borneTemps double qui représente le temps max
     * @return boolean qui retourne true si une des valeurs est en dessous des bornes, false sinon
     */
    public boolean isUnderBornes(double bornePrix, double borneCo2, double borneTemps){
        boolean isUnder = true;
        if(bornePrix != -1 && this.poids(TypeCout.PRIX) > bornePrix) isUnder = false;
        if(borneCo2 != -1 && this.poids(TypeCout.CO2) > borneCo2) isUnder = false;
        if(borneTemps != -1 && this.poids(TypeCout.TEMPS) > borneTemps) isUnder = false;
        return isUnder;
    }

    public TypeCout getCritere() {
        return critere;
    }

    public static List<Voyage> cheminToVoyage(List<Chemin> chemins) {
        List<Voyage> list = new ArrayList<>();
        for (int i = 0; i < chemins.size(); i++) {
            list.add(new Voyage(chemins.get(i).aretes()));
        }
        return list;
    }
}