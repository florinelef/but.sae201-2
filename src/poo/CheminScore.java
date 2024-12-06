package poo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;

public class CheminScore implements Comparable<CheminScore>{
    private Chemin chemin;
    private Double score;

    public CheminScore(Chemin chemin, Double score) {
        this.chemin = chemin;
        this.score = score;
    }

    public Chemin getChemin() {
        return this.chemin;
    }

    public Double getScore() {
        return this.score;
    }

    public int compareTo(CheminScore other){
        return this.score.compareTo(other.score);
    }

    public static List<Chemin> sort(List<CheminScore> chemins){
        List<Chemin> list = new ArrayList<Chemin>();
        Collections.sort(chemins);
        for(CheminScore c : chemins){
            list.add(c.chemin);
        }
        return list;
    }
}
