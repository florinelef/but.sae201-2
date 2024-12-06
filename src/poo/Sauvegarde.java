/**La classe Sauvegarde permet d'enregistrer l'historique de voyage de l'utilisateur, afin de lui montrer l'évolution de ses trajets en terme d'écologie, d'économie...
* @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
* <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
* <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
* <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/

package poo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Sauvegarde implements Serializable {
    private List<Voyage> trajets;
    private CoutTroncon couts;// type de coût associé à un total 
    private Plateforme plateforme;
    private int id;
    private static int cpt = 0;

    private static String path = System.getProperty("user.dir") + "/res/Sauvegarde.csv";

    /** Constructeur de Sauvegarde avec paramètre
     * 
     * @param trajets la liste des trajets que l'utilisateur a effectué 
     * @return une sauvegarde des trajets effectués
     */
    public Sauvegarde(Plateforme plateforme) {
        this.trajets = new ArrayList<Voyage>();
        this.plateforme = plateforme;
    }

    /** Constructeur de Sauvegarde sans paramètre
     * @return une sauvegarde vide 
     */
    public Sauvegarde() {
        this.trajets = new ArrayList<Voyage>();
    }

    /** @param trajet le trajet à ajouter à la liste
     * 
     */
    public void add(Voyage trajet) {
        this.trajets.add(trajet);
    }

    /** @param trajets la liste de trajets à ajouter à la liste
     * 
     */
    public void add (List<Voyage> trajets) {
        for (int i = 0 ; i < trajets.size() ; i++) {
            this.trajets.add(trajets.get(i));
        }
    }

    /** 
    * Sérialise les trajets enregistrés dans un fichier csv
    */ 
    public void saveInFile() {
        for (int i= 0; i < this.trajets.size(); i++) {
            try (FileWriter writer = new FileWriter(path, true)) {
                this.id = Sauvegarde.cpt++;

                HashMap<TypeCout, Double> map = new HashMap<>();

                if (this.plateforme.getVoyageur().getCriteres().contains(TypeCout.PRIX)) {
                    this.trajets.get(i).setCritere(TypeCout.PRIX);
                    map.put(TypeCout.PRIX, this.trajets.get(i).poids(this.trajets.get(i).getCritere()));
                }
                else {
                    map.put(TypeCout.PRIX, null);
                }
                if (this.plateforme.getVoyageur().getCriteres().contains(TypeCout.CO2)) {
                    this.trajets.get(i).setCritere(TypeCout.CO2);
                    map.put(TypeCout.CO2, this.trajets.get(i).poids(this.trajets.get(i).getCritere()));
                }
                else {
                    map.put(TypeCout.CO2, null);
                }
                if (this.plateforme.getVoyageur().getCriteres().contains(TypeCout.TEMPS)) {
                    this.trajets.get(i).setCritere(TypeCout.TEMPS);
                    map.put(TypeCout.TEMPS, this.trajets.get(i).poids(this.trajets.get(i).getCritere()));
                }
                else {
                    map.put(TypeCout.TEMPS, null);
                }

                this.couts = new CoutTroncon(map);

                String texte = "\n" + this.id +";";
                for (int cpt = 0; cpt < this.plateforme.getVoyageur().getCriteres().size(); cpt++) {
                    String res = this.couts.getCouts().entrySet().toString();
                    res = res.substring(1, res.length()-1);
                    res = res.replace(",", ";");
                    res = res.replace(" ", "");
                    res = res.replace("=", ";");
                    texte += res;
                }
                writer.write(texte);
                writer.close();
            }
            catch (NotSerializableException e) {System.out.println("L'objet n'est pas sérialisable");}
            catch (Exception e) {
                e.printStackTrace();
                e.getCause();
                System.out.println("Le trajet n'a pas été enregistré.");
            }   
        }
    }

    /*
     * Affiche chaque voyage enregistré dans le fichier de sauvegarde
     */
    public void displayAllSauvegarde() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line = br.readLine();
            Scanner sc = new Scanner(line);
            while (line != null) {
                sc.useDelimiter(";");
                String id;
                String co2;
                String temps;
                String prix;
                id = sc.next();
                sc.next();
                co2 = sc.next();
                sc.next();
                temps = sc.next();
                sc.next();
                prix = sc.next();
                System.out.println("Voyage n°" + id + " : \nCO2 = " + co2 + "\nTEMPS = " + temps + "\nPRIX = " + prix +"\n");
                line = br.readLine();
                if (line != null) sc = new Scanner(line);
            }
            sc.close();
            br.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Fichier non trouvé, vérifiez qu'il existe");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problème de lecture du fichier");
        }
        System.out.println();
    }

    /*
     * Affiche l'évolution des voyages de l'utilisateur selon le critère choisi
     */

    public void seeStats(TypeCout critere) {
        List<Double> listePoids = new ArrayList<>();
        double somme =0;
        for (int i = 0; i <this.trajets.size(); i++) {
            listePoids.add(this.trajets.get(i).poids(critere));
            somme += this.trajets.get(i).poids(critere);
        }
        double moyenne = somme/listePoids.size();
        String unit = "";
        if (critere == TypeCout.PRIX) unit = "€";
        else if (critere == TypeCout.CO2) unit = "kg de CO2";
        else if (critere == TypeCout.TEMPS) unit = "minutes";
        System.out.println("La moyenne de " + critere.toString().toLowerCase() + " de vos trajets est : " + moyenne + unit);
    }
    
    // méthode sérialisation en binaire
    // public void saveInFile() {
    //     for (int i= 0; i < this.trajets.size(); i++) {
    //         try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path, true))) {
    //             oos.writeObject(this.trajets.get(i));
    //             HashMap<TypeCout, Double> map = new HashMap<>();

    //             if (this.plateforme.getVoyageur().getCriteres().contains(TypeCout.PRIX)) {
    //                 this.trajets.get(i).setCritere(TypeCout.PRIX);
    //                 map.put(TypeCout.PRIX, this.trajets.get(i).poids(this.trajets.get(i).getCritere()));
    //             }
    //             else {
    //                 map.put(TypeCout.PRIX, null);
    //             }
    //             if (this.plateforme.getVoyageur().getCriteres().contains(TypeCout.CO2)) {
    //                 this.trajets.get(i).setCritere(TypeCout.CO2);
    //                 map.put(TypeCout.CO2, this.trajets.get(i).poids(this.trajets.get(i).getCritere()));
    //             }
    //             else {
    //                 map.put(TypeCout.CO2, null);
    //             }
    //             if (this.plateforme.getVoyageur().getCriteres().contains(TypeCout.TEMPS)) {
    //                 this.trajets.get(i).setCritere(TypeCout.TEMPS);
    //                 map.put(TypeCout.TEMPS, this.trajets.get(i).poids(this.trajets.get(i).getCritere()));
    //             }
    //             else {
    //                 map.put(TypeCout.TEMPS, null);
    //             }

    //             this.couts = new CoutTroncon(map);
    //             oos.close();
    //         }
    //         catch (NotSerializableException e) {
    //             e.printStackTrace();
    //             System.out.println("L'objet n'est pas sérialisable");
    //         }
    //         catch (Exception e) {
    //             e.printStackTrace();
    //             e.getCause();
    //             System.out.println("Le trajet n'a pas été enregistré.");
    //         }   
    //     }
    // }
}
