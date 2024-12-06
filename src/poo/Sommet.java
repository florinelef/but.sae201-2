/** La classe Sommet implémente l'interface Lieu pour modéliser les villes des trajets
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/
package poo;

import java.io.Serializable;

import fr.ulille.but.sae_s2_2024.Lieu;

public class Sommet implements Lieu, Serializable{
    private String nom;

    /** @return Constructeur qui prend en paramètre une String
     * @param nom chaîne de caractère correspondant au nom de la ville*/
    public Sommet(String nom) {
        this.nom = nom;
    }

    /** @return Méthode toString qui retourne le nom du sommet*/
    public String toString() {
        return this.nom;
    }

    public String toStringSansModalite(){
        String[] res = this.nom.split(":");
        return res[0];
    }
}
