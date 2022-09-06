package app.workers;

import app.beans.Personne;
import app.exceptions.MyDBException;
import app.helpers.DateTimeLib;
import app.helpers.SystemLib;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        final String url_remote = "jdbc:mysql://172.23.85.187:3306/" + nomDB;
        final String user = "root";
        final String password = "emf123";

        System.out.println("url:" + url_local);
        try {
            dbConnexion = DriverManager.getConnection(url_local, user, password);
        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
    }

    public void creer(Personne o) throws MyDBException {
        if (o != null) {
            String prep = "INSERT INTO t_personne (Nom, Prenom, Date_naissance, No_rue, "
                    + "Rue, NPA, Salaire, Ville, Actif, date_modif) VALUES (?,?,?,?,?,?,?,?,?,?);";

            try ( PreparedStatement ps = dbConnexion.prepareStatement(prep)) {

                ps.setString(1, o.getNom());
                ps.setString(2, o.getPrenom());
                ps.setDate(3, new java.sql.Date(o.getDateNaissance().getTime()));
                ps.setInt(4, o.getNoRue());
                ps.setString(5, o.getRue());
                ps.setInt(6, o.getNpa());
                ps.setDouble(7, o.getSalaire());
                ps.setString(8, o.getLocalite());
                ps.setBoolean(9, o.isActif());
                ps.setDate(10, new java.sql.Date(o.getDateModif().getTime()));

                int nb = ps.executeUpdate();

                if (nb != 1) {
                    System.out.println("Erreur de mise à jour !!!");
                }

            } catch (SQLException ex) {
                throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
            }
        }
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

    @Override
    public void effacer(Personne o) throws MyDBException {
        if (o != null) {
            String prep = "DELETE FROM t_personne where PK_PERS=?";

            try ( PreparedStatement ps = dbConnexion.prepareStatement(prep)) {
                ps.setInt(1, o.getPkPers());

                int nb = ps.executeUpdate();

                if (nb != 1) {
                    System.out.println("Erreur de mise à jour !!!");
                }

            } catch (SQLException ex) {
                throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
            }
        }
    }

    @Override
    public Personne lire(int i) throws MyDBException {
        return lirePersonnes().get(i);
    }

    @Override
    public List<Personne> lirePersonnes() throws MyDBException {
        ArrayList<Personne> listePersonnes = new ArrayList<Personne>();
        Statement st;
        ResultSet rs;
        try {
            st = dbConnexion.createStatement();
            rs = st.executeQuery("select PK_PERS, Nom, Prenom, Date_naissance, "
                    + "No_rue, Rue, NPA, Salaire, Ville, Actif, date_modif from t_personne");

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

        } catch (SQLException ex) {
            throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
        }
        return listePersonnes;
    }

    @Override
    public void modifier(Personne o) throws MyDBException {
        if (o != null) {
            String prep = "update t_personne set Nom=?, Prenom=?, Date_naissance=?, No_rue=?, "
                    + "Rue=?, NPA=?, Salaire=?, Ville=?, Actif=?, date_modif=? where PK_PERS=?";

            try ( PreparedStatement ps = dbConnexion.prepareStatement(prep)) {

                ps.setString(1, o.getNom());
                ps.setString(2, o.getPrenom());
                ps.setDate(3, new java.sql.Date(o.getDateNaissance().getTime()));
                ps.setInt(4, o.getNoRue());
                ps.setString(5, o.getRue());
                ps.setInt(6, o.getNpa());
                ps.setDouble(7, o.getSalaire());
                ps.setString(8, o.getLocalite());
                ps.setBoolean(9, o.isActif());
                ps.setDate(10, new java.sql.Date(o.getDateModif().getTime()));
                ps.setInt(11, o.getPkPers());

                int nb = ps.executeUpdate();

                if (nb != 1) {
                    System.out.println("Erreur de mise à jour !!!");
                }

            } catch (SQLException ex) {
                throw new MyDBException(SystemLib.getFullMethodName(), ex.getMessage());
            }
        }
    }

}
