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
        return listePersonnes.get(index);
    }

    public Personne debutPersonne() {
        index = 0;
        return listePersonnes.get(index);
    }

    public Personne finPersonne() {
        index = (listePersonnes.size() - 1);
        return listePersonnes.get(index);
    }

    public Personne precedentPersonne() {
        if (index > 0) {
            index--;
        }

        return listePersonnes.get(index);
    }

    public Personne setPersonnes(List<Personne> e) {
        this.listePersonnes = e;
        return courantPersonne();
    }

    public Personne suivantPersonne() {

        if (index < (listePersonnes.size() - 1)) {
            index++;
        }
        return listePersonnes.get(index);
    }
}
