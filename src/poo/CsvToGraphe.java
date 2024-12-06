/** La classe CsvToGraphe permet de transformer des données sous forme d'un csv
* en une représentation de type Plateforme
* @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
* <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
* <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
* <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/
package poo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;

public class CsvToGraphe {
    
    private static Plateforme p = new Plateforme();
    private static HashMap<String, Sommet> map = new HashMap<String, Sommet>(); 
    
    /** méthode statique qui permet de transformer les données sous forme de String[] en Plateforme
    * @param chemin est le chemin vers le csv de données d'origine
    * @param cheminCorrespondance est le chemin vers le csv de données des correspondances
    * @param p est la plateforme créée à partir de ces données
    */
    public static Plateforme transformer(String chemin, String cheminCorrespondance, Voyageur voyageur){
        
        p.setVoyageur(voyageur);
        
        try(BufferedReader br = new BufferedReader(new FileReader(chemin))){
            String line = br.readLine();
            Scanner sc = new Scanner(line);
            sc.useDelimiter(";");
            
            String nomVilleA;
            String nomVilleB;
            String nomVille;
            Sommet villeA;
            Sommet villeB;
            ModaliteTransport modalite;
            ModaliteTransport mod1;
            ModaliteTransport mod2;
            double prix;
            double co2;
            double temps;
            CoutTroncon couts;
            
            int cptLigne = 1;
            
            
            //ajout des trajets entre les villes
            while(line != null){
                try{
                    nomVilleA = sc.next(); //ex: Lille
                    nomVilleB = sc.next(); //ex: Orchies
                    modalite = ModaliteTransport.valueOf(sc.next().toUpperCase()); //ex: BUS
                    villeA = addSommet(nomVilleA + ":" + modalite.toString()); //ex: Lille:BUS
                    villeB = addSommet(nomVilleB + ":" + modalite.toString()); //ex: Orchies:BUS
                    prix = Double.parseDouble(sc.next());
                    co2 = Double.parseDouble(sc.next());
                    temps = Double.parseDouble(sc.next());
                    couts = new CoutTroncon(prix, co2, temps);
                    CsvToGraphe.p.addArete(new Arete(villeA, villeB, modalite, couts));
                    CsvToGraphe.p.addArete(new Arete(villeB, villeA, modalite, couts));
                } catch(IllegalArgumentException e){
                    //entête ignorée
                } catch(Exception e){
                    System.err.println("Ligne " + cptLigne + " : Données invalides - veuillez donner les données sous la forme : villeA;villeB;modalitéTransport;prix (double);co2 (double);temps (double)");
                }
                
                line = br.readLine();
                if(line != null){
                    sc = new Scanner(line);
                    sc.useDelimiter(";");
                }
                cptLigne++;
            }
            
            cptLigne = 1;
            
            //ajout des correspondances inter-ville
            BufferedReader br2 = new BufferedReader(new FileReader(cheminCorrespondance));
            line = br2.readLine();
            sc = new Scanner(line);
            sc.useDelimiter(";");
            
            
            while(line != null){
                try{
                    nomVille = sc.next();
                    mod1 = ModaliteTransport.valueOf(sc.next().toUpperCase());
                    mod2 = ModaliteTransport.valueOf(sc.next().toUpperCase());
                    villeA = addSommet(nomVille + ":" + mod1.toString());
                    villeB = addSommet(nomVille + ":" + mod2.toString());
                    prix = sc.nextDouble();
                    co2 = sc.nextDouble();
                    temps = sc.nextDouble();
                    couts = new CoutTroncon(prix, co2, temps);
                    CsvToGraphe.p.addArete(new Arete(villeA, villeB, null, couts));
                    CsvToGraphe.p.addArete(new Arete(villeB, villeA, null, couts));
                    
                } catch(IllegalArgumentException e){
                    //entête ignorée
                } catch(Exception e){
                    System.err.println("Ligne " + cptLigne + " : Données invalides - veuillez donner les données sous la forme : ville;modalitéTransport1;modalitéTransport2;prix (double);co2 (double);temps (double)");
                    e.printStackTrace();
                }
                
                line = br2.readLine();
                if(line != null){
                    sc = new Scanner(line);
                    sc.useDelimiter(";");
                }
                
                cptLigne++;
            }
            
            br2.close();
            
        } catch (FileNotFoundException e){
            System.err.println("Fichier introuvable.");
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return CsvToGraphe.p;
    }
    
    
    
    
    /** @return méthode statique qui ajoute un sommet à la plateforme (s'il n'existe pas deja) et le renvoie pour l'utilisation dans la méthode transformée
    */
    public static Sommet addSommet(String s){
        /** parcours des clés et valeurs
        * */
        for(Map.Entry<String,Sommet> sommet: CsvToGraphe.map.entrySet()){
            if(sommet.getKey().equals(s)){
                return sommet.getValue();
            }
        }
        Sommet res = new Sommet(s);
        CsvToGraphe.map.put(s, res);
        CsvToGraphe.p.addSommet(res);
        return res;
    }
}
