/** La classe UsePlateforme permet d'utiliser concrètement la classe Plateforme (@see #Plateforme)
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/
package poo;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;

public class UsePlateforme {
    public static void main(String[] args) {

        // création de la plateforme (avec sommets et arêtes) 
    
        Plateforme p = new Plateforme();
        for(int i=0; i<5; i++){
            p.addSommet(new Sommet("Ville " + i));
        }
        Sommet[] sommets = p.getSommets().toArray(new Sommet[0]);
        for(int i=0; i<4; i++){
            p.addArete(new Arete(sommets[i], sommets[i+1], ModaliteTransport.TRAIN, new CoutTroncon(70.0, 1.7, 40.0)));
            p.addArete(new Arete(sommets[i+1], sommets[i], ModaliteTransport.BUS, new CoutTroncon(40.0, 5.8, 60.0)));
        }

        System.out.println(p);
    }
}
