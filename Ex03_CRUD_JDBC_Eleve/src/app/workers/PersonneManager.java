/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.workers;

import app.beans.Personne;
import java.util.List;

/**
 *
 * @author Pythond02
 */
public class PersonneManager {

    private int index = 0;
    private List<Personne> listePersonnes;

    /**
     * Constructeur du worker
     */
    public PersonneManager() {
    }

    public Personne courantPersonne() {

        return null;
    }

    public Personne debutPersonne() {

        return null;
    }

    public Personne finPersonne() {

        return null;
    }

    public Personne precedentPersonne() {
        int ind = index;

        if (ind > 0) {
            index--;
        }

        return listePersonnes.get(ind);
    }

    public Personne setPersonnes(List<Personne> o) {
        this.listePersonnes = o;
        return listePersonnes.get(0);
    }

    public Personne suivantPersonne() {

        if (index < (listePersonnes.size() - 1)) {
            index++;
        }
        return listePersonnes.get(index);
    }
}
