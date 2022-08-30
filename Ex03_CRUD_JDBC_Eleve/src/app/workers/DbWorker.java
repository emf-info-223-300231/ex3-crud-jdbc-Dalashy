package app.workers;

import app.beans.Personne;
import app.exceptions.MyDBException;
import app.helpers.SystemLib;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbWorker implements DbWorkerItf {

    private Connection dbConnexion;

    @Override
    public void connecterBdHSQLDB(String nomDB) throws MyDBException {
        final String url = "jdbc:hsqldb:file:" + nomDB + ";shutdown=true";
        final String user = "SA";
        final String password = "";
        System.out.println("url:" + url);
        try {
            dbConnexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
    }

    @Override
    public void connecterBdAccess(String nomDB) throws MyDBException {
        final String url = "jdbc:ucanaccess://" + nomDB;
        System.out.println("url=" + url);
        try {
            dbConnexion = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
    }

    @Override
    public void connecterBdMySQL(String nomDB) throws MyDBException {
        final String url_local = "jdbc:mysql://localhost:3306/" + nomDB;
        final String url_remote = "jdbc:mysql://LAPEMFB37-21.edu.net.fr.ch:3306/" + nomDB;
        final String user = "root";
        final String password = "emf123";

        System.out.println("url:" + url_local);
        try {
            dbConnexion = DriverManager.getConnection(url_local, user, password);
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
    }

    public void creer(Personne o) {

    }

    private Personne creerPersonne(ResultSet rs) {

        return null;
    }

    /**
     * Constructeur du worker
     */
    public DbWorker() {

    }

    @Override
    public void deconnecter() throws MyDBException {
        try {
            if (dbConnexion != null) {
                dbConnexion.close();
            }
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
    }

    public void effacer(Personne o) {

    }

    public Personne lire(int i) {

        return null;
    }

    public List<Personne> lirePersonnes() {
        ArrayList<Personne> listePersonnes = new ArrayList<Personne>();
        Statement st;
        ResultSet rs;
        try {
            st = dbConnexion.createStatement();
            rs = st.executeQuery("select PK_PERS, Nom, Prenom, Date_naissance, No_rue, Rue, NPA, Salaire, Ville, Actif, date_modif from t_personne");

            while (rs.next()) {

                Personne test = new Personne(rs.getInt("PK_PERS"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getDate("Date_naissance"),
                        rs.getInt("No_rue"),
                        rs.getString("Rue"),
                        rs.getInt("NPA"),
                        rs.getString("Ville"),
                        rs.getBoolean("Actif"),
                        rs.getDouble("Salaire"),
                        rs.getDate("date_modif"));
                listePersonnes.add(test);
            }

        } catch (SQLException o) {

        }
        return listePersonnes;
    }

    public void modifier(Personne o) {

    }

}
