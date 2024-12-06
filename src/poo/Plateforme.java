/**La classe Plateforme permet de créer des graphes avec plusieurs critères (multigraphes)
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/
package poo;

import java.util.HashSet;
import java.util.Set;

import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;

public class Plateforme{
    private Set<Lieu> sommets;
    private Set<Trancon> aretes;
    private Voyageur voyageur;
    
    /** @return Constructeur qui prend en paramètre <em>deux Set</em> */
    public Plateforme(Set<Lieu> sommets, Set<Trancon> aretes, Voyageur voyageur) {
        this.sommets = sommets;
        this.aretes = aretes; //-1 pour la valeur nulle
        this.voyageur = voyageur;
    }
    /** @return Constructeur sans paramètre qui crée une plateforme vide */
    public Plateforme(){
        this.sommets = new HashSet<Lieu>();
        this.aretes = new HashSet<Trancon>();
    }

    /** @return Méthode qui retourne la liste de sommets */
    public Set<Lieu> getSommets(){
        return this.sommets;
    }
    /** @return Méthode qui retourne la liste des arêtes */
    public Set<Trancon> getAretes(){
        return this.aretes;
    }

    public void setVoyageur(Voyageur voyageur) {
        this.voyageur = voyageur;
    }

    public Voyageur getVoyageur(){
        return this.voyageur;
    }

    /** @return Méthode de recherche d'une ville en fonction de son nom*/
    public Lieu getVille(String s){
        Sommet ville = null;
        for(Lieu l: this.sommets){
            if(((Sommet) l).toString().equals(s)){
                ville = (Sommet) l;
            } else if(((Sommet) l).toString().length() >= s.length()){
                if(s.equals(((Sommet) l).toString().substring(0, s.length()))){
                    ville = (Sommet) l;
                }
            }
        }
        return ville;
    }


    /** @return Méthode qui permet d'ajouter un sommet */
    public void addSommet(Lieu s){
        this.sommets.add(s);
    }
 
    /** @return Méthode qui permet d'ajouter une arête */
    public boolean addArete(Trancon t){
        return this.aretes.add(t);
    }

    /** @return Méthode qui retourne un graphe orienté, en fonction du critère et de la modalité*/
    public MultiGrapheOrienteValue getGraphe(){
        if(this.voyageur.getCriteres() != null) return this.getGrapheAvecCritere(this.voyageur.getCriteres().get(0));
        return null;
    }

    public MultiGrapheOrienteValue getGrapheAvecCritere(TypeCout critere){
        MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

        for(Lieu l : this.sommets){
            graphe.ajouterSommet(l);
        }

        for(Trancon t: this.aretes){
            double poids = ((Arete)t).getCout().getCouts().get(critere);
            graphe.ajouterArete(t, poids);
        }
        return graphe;
    }

    public Set<String> getVillesSansModalite(){
        HashSet<String> villes = new HashSet<String>();
        for(Lieu l : this.sommets){
            villes.add(l.toString().split(":")[0]);
        }
        return villes;
    }

    /** @return Retourne un graphe sous forme d'une chaîne de caractère */
    public String toString(){
        return this.getGraphe().toString();
    }
}
