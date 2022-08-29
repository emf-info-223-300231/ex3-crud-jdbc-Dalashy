package app.workers;

import app.beans.Personne;
import app.exceptions.MyDBException;
import java.util.List;

public interface DbWorkerItf {

  void connecterBdMySQL( String nomDB ) throws MyDBException;
  void connecterBdHSQLDB( String nomDB ) throws MyDBException;
  void connecterBdAccess( String nomDB ) throws MyDBException;
  void creer(Personne o) throws MyDBException; 
  void deconnecter() throws MyDBException; 
  void effacer(Personne o) throws MyDBException;
  Personne lire(int i) throws MyDBException;
  List<Personne> lirePersonnes() throws MyDBException;
  void modifier(Personne o) throws MyDBException;  
}
