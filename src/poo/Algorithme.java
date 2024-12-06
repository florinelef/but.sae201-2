/**La classe Algorithme permet de récupérer la liste de chemins en fontion du critère choisit par le voyageur et ne dépassant pas les bornes mises en place par l'utilisateur .
* @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
* <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
* <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
* <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/

package poo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ulille.but.sae_s2_2024.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.Trancon;

public class Algorithme{

    private static HashMap<Chemin, CoutChemin> coutsChemins = new HashMap<Chemin, CoutChemin>();

    /**
    * @param reseau @see #Plateforme
    * @param villeA une chaine de caractère qui correspond à la ville de départ de l'utilisateur
    * @param villeB une chaine de caractère qui correspond à la ville de d'arrivée de l'utilisateur
    * @param k un nombre choisit par l'utilisateur en fonction du nombre d'option qu'il souhaite afficher
    * @return une liste de Chemin @see #Chemin en fonction du critère choisit par l'utilisateur ne dépassant les bornes de l'utilisateur
    */
    public static List<Chemin> plusCourtsChemins(Plateforme reseau, String villeA, String villeB, int k){
        List<Chemin> listPrix = new ArrayList<Chemin>();
        List<Chemin> listCo2 = new ArrayList<Chemin>();
        List<Chemin> listTemps = new ArrayList<Chemin>();
        
        try{
            listPrix = AlgorithmeKPCC.kpcc(reseau.getGrapheAvecCritere(TypeCout.PRIX), reseau.getVille(villeA), reseau.getVille(villeB), k);
            listCo2 = AlgorithmeKPCC.kpcc(reseau.getGrapheAvecCritere(TypeCout.CO2), reseau.getVille(villeA), reseau.getVille(villeB), k);
            listTemps = AlgorithmeKPCC.kpcc(reseau.getGrapheAvecCritere(TypeCout.TEMPS), reseau.getVille(villeA), reseau.getVille(villeB), k);
        } catch(IllegalArgumentException e){
            System.err.println("Problème dans le jeu de données ou dans les villes données en paramètre.");
            e.printStackTrace();
        }
        
        for(int i=listPrix.size()-1; i>=0; i--){

            if((reseau.getVoyageur().getBornePrix() != -1 && listPrix.get(i).poids() > reseau.getVoyageur().getBornePrix()) ||
            (reseau.getVoyageur().getBorneCo2() != -1 && listCo2.get(i).poids() > reseau.getVoyageur().getBorneCo2()) ||
            (reseau.getVoyageur().getBorneTemps() != -1 && listTemps.get(i).poids() > reseau.getVoyageur().getBorneTemps())){
                listPrix.remove(i);
                listCo2.remove(i);
                listTemps.remove(i);
            }
        }
        
        List<Chemin> list = Algorithme.gestionCriteres(reseau, listPrix, listCo2, listTemps);

        
        // ajout des 3 couts pour chaque chemin
        for(Chemin c1 : list){
            for(Chemin c2 : listPrix){
                if(c1.aretes().equals(c2.aretes())){
                    if(!Algorithme.coutsChemins.containsKey(c1)){
                        Algorithme.coutsChemins.put(c1, new CoutChemin(TypeCout.PRIX, c2.poids()));
                    } else {
                        Algorithme.coutsChemins.put(c1, Algorithme.coutsChemins.get(c1).addCout(TypeCout.PRIX, c2.poids()));
                    }
                }
            }
            for(Chemin c2 : listCo2){
                if(c1.aretes().equals(c2.aretes())){
                    if(!Algorithme.coutsChemins.containsKey(c1)){
                        Algorithme.coutsChemins.put(c1, new CoutChemin(TypeCout.CO2, c2.poids()));
                    } else {
                        Algorithme.coutsChemins.put(c1, Algorithme.coutsChemins.get(c1).addCout(TypeCout.CO2, c2.poids()));
                    }
                }
            }
            for(Chemin c2 : listTemps){
                if(c1.aretes().equals(c2.aretes())){
                    if(!Algorithme.coutsChemins.containsKey(c1)){
                        Algorithme.coutsChemins.put(c1, new CoutChemin(TypeCout.TEMPS, c2.poids()));
                    } else {
                        Algorithme.coutsChemins.put(c1, Algorithme.coutsChemins.get(c1).addCout(TypeCout.TEMPS, c2.poids()));
                    }
                }
            }
        }
        
        try{
            list.get(0);
        } catch (Exception e){
            System.out.println("Il n'y a pas de chemin qui satisfait vos critères !");
        }
        return list;
    }

    private static List<Chemin> gestionCriteres(Plateforme reseau, List<Chemin> listPrix, List<Chemin> listCo2, List<Chemin> listTemps){
        List<Chemin> list = null;
        List<TypeCout> criteres = reseau.getVoyageur().getCriteres();
        if(criteres.size() == 1){
            TypeCout critere = criteres.get(0);
            if(critere == TypeCout.PRIX) list = listPrix;
            if(critere == TypeCout.CO2) list = listCo2;
            if(critere == TypeCout.TEMPS) list = listTemps;

        } else {
            Map<List<Trancon>,Double> chemins = new HashMap<List<Trancon>,Double>();
            Double score;
            List<Chemin> listCritere;

            for(TypeCout crit : criteres){
                listCritere = listPrix;
                if(crit == TypeCout.CO2) listCritere = listCo2;
                if(crit == TypeCout.TEMPS) listCritere = listTemps;
                for(Chemin c : listCritere){
                    if(chemins.containsKey(c.aretes())){
                        score = chemins.get(c.aretes()) + c.poids();
                    } else {
                        score = c.poids();
                    }
                    chemins.put(c.aretes(), score);
                }
            }

            List<CheminScore> cheminScores = new ArrayList<CheminScore>();
            for(List<Trancon> lt : chemins.keySet()){
                cheminScores.add(new CheminScore(new Voyage(lt), chemins.get(lt)));
            }
            list = CheminScore.sort(cheminScores);
        }
        return list;
    }

    public static Chemin getCheminPlusEcologique(Plateforme reseau, String villeA, String villeB){
        List<Chemin> listPrix = new ArrayList<Chemin>();
        List<Chemin> listCo2 = new ArrayList<Chemin>();
        List<Chemin> listTemps = new ArrayList<Chemin>();
        
        try{
            listPrix = AlgorithmeKPCC.kpcc(reseau.getGrapheAvecCritere(TypeCout.PRIX), reseau.getVille(villeA), reseau.getVille(villeB), 999);
            listCo2 = AlgorithmeKPCC.kpcc(reseau.getGrapheAvecCritere(TypeCout.CO2), reseau.getVille(villeA), reseau.getVille(villeB), 999);
            listTemps = AlgorithmeKPCC.kpcc(reseau.getGrapheAvecCritere(TypeCout.TEMPS), reseau.getVille(villeA), reseau.getVille(villeB), 999);
        } catch(IllegalArgumentException e){
            System.err.println("Problème dans le jeu de données ou dans les villes données en paramètre.");
            e.printStackTrace();
        }
        
        Chemin c1 = listCo2.get(0);
        for(Chemin c2 : listPrix){
            if(c1.aretes().equals(c2.aretes())){
                if(!Algorithme.coutsChemins.containsKey(c1)){
                    Algorithme.coutsChemins.put(c1, new CoutChemin(TypeCout.PRIX, c2.poids()));
                } else {
                    Algorithme.coutsChemins.put(c1, Algorithme.coutsChemins.get(c1).addCout(TypeCout.PRIX, c2.poids()));
                }
            }
        }
        if(!Algorithme.coutsChemins.containsKey(c1)){
            Algorithme.coutsChemins.put(c1, new CoutChemin(TypeCout.CO2, c1.poids()));
        } else {
            Algorithme.coutsChemins.put(c1, Algorithme.coutsChemins.get(c1).addCout(TypeCout.CO2, c1.poids()));
        }
        for(Chemin c2 : listTemps){
            if(c1.aretes().equals(c2.aretes())){
                if(!Algorithme.coutsChemins.containsKey(c1)){
                    Algorithme.coutsChemins.put(c1, new CoutChemin(TypeCout.TEMPS, c2.poids()));
                } else {
                    Algorithme.coutsChemins.put(c1, Algorithme.coutsChemins.get(c1).addCout(TypeCout.TEMPS, c2.poids()));
                }
            }
        }
        return listCo2.get(0);
    }

    public static CoutChemin getCouts(Chemin c){
        return Algorithme.coutsChemins.get(c);
    }

    public static double maxOf(List<Chemin> chemins, TypeCout typeBorne){
        ArrayList<Double> couts = new ArrayList<Double>();
        for(Chemin c1 : chemins){
            for(Chemin c2 : Algorithme.coutsChemins.keySet()){
                if(c1.aretes().equals(c2.aretes())){
                    couts.add(Double.valueOf(Algorithme.coutsChemins.get(c2).getCoutByType(typeBorne)));
                }
            }
        }
        return Collections.max(couts);
    }
    
    public static void afficher(List<Chemin> list){
        int cpt = 1;
        for(Chemin c : list){
            System.out.print("Chemin " + cpt + " : ");
            List<Trancon> aretes = c.aretes();
            
            Lieu debut = null;
            Lieu fin = null;
            ModaliteTransport mod = null;
            
            for(int i=0; i<c.aretes().size(); i++){
                if(aretes.get(i).getModalite() == null){
                    System.out.print("[" + aretes.get(i) + "]   ");
                    mod = null;
                    debut = null;
                    fin = null;
                } else {
                    if(mod == null){
                        mod = aretes.get(i).getModalite();
                        debut = aretes.get(i).getDepart();
                    }
                    if(i == c.aretes().size()-1 || aretes.get(i+1).getModalite() == null){
                        fin = aretes.get(i).getArrivee();
                        System.out.print(((Sommet)debut).toStringSansModalite() + " -> " + ((Sommet)fin).toStringSansModalite() + " (" + mod + ")   ");
                    }
                }
            }
            System.out.println("("+Algorithme.getCouts(c).toString()+")");
            cpt++;
        }
        System.out.println();        
    }
}