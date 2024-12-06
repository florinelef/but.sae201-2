/** La classe CsvToGraphe permet de transformer des données d'un fichier CSV sous forme d'un tableau
 * @author CHARLERY Klara, DARQUES Charlie, LEFEBVRE Florine
 * <a href=mailto:klara.charlery.etu@univ-lille.fr>K. Charlery</a>
 * <a href=mailto:charlie.darques.etu@univ-lille.fr>C. Darques</a>
 * <a href=mailto:florine.lefebvre.etu@univ-lille.fr>F. Lefebvre</a>
*/
package poo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import fr.ulille.but.sae_s2_2024.Lieu;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;

public class CsvToCorrespondance {
    private static ArrayList<Correspondance> correspondances = new ArrayList<Correspondance>();

    /**@param chemin chemin depuis le répertoire où le programme a été lancé jusqu'au fichier .csv des correspondances
     * @return une ArrayList des correspondances enregistrées depuis le fichier CSV
     */
    public static ArrayList<Correspondance> transformer(String chemin){

        try (BufferedReader br = new BufferedReader(new FileReader(new File(chemin)))){

            String line = br.readLine();
            Scanner sc = new Scanner(line);

            Correspondance c;
            Lieu ville;
            ModaliteTransport m1;
            ModaliteTransport m2;
            double prix;
            double co2;
            double temps;

            while(line != null){
                sc.useDelimiter(";");
                
                ville = new Sommet(sc.next());
                m1 = ModaliteTransport.valueOf(sc.next());
                m2 = ModaliteTransport.valueOf(sc.next());
                prix = Double.parseDouble(sc.next());
                co2 = Double.parseDouble(sc.next());
                temps = Double.parseDouble(sc.next());
                
                c = new Correspondance(ville, m1, m2, prix, co2, temps);
                CsvToCorrespondance.correspondances.add(c);
                line = br.readLine();

                if(line != null) sc = new Scanner(line);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("Veuillez vérifier la présence du fichier souhaité, ou la validité de son chemin.");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("Error :" + e);
            System.out.println("Problème de lecture ou d'écriture");
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CsvToCorrespondance.correspondances;
    }
}
