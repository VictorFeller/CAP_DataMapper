package ch.hearc.ig.guideresto.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOracleDriverManager {

    public static final String URLDB = "jdbc:oracle:thin:@db.ig.he-arc.ch:1521:ens";
    public static final String USER = "victor_feller";
    public static final String PASSWORD = "victor_feller";
    public static Connection cnn;

    public Connection getConnection() throws SQLException {
        try{
            //Connection à la DB
            cnn = DriverManager.getConnection(URLDB, USER, PASSWORD);
            //Désactive le mode auto-commit
            cnn.setAutoCommit(false);

            return cnn;
        } catch(Exception e){
            throw new SQLException();
        }
    }
}
