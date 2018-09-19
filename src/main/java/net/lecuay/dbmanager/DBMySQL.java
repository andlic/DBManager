package net.lecuay.dbmanager;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Extension from {@link DBManager} used for MySQL Connections.
 * 
 * @author LeCuay
 * @version 0.1
 * 
 * @see DBManager
 */
public class DBMySQL extends DBManager {

    /**
     * Declares parameters used for a MySQL connection.
     * @param user The user used for the connection.
     * @param password The password used for the connecion.
     * @param host The host where our Database is hosted.
     * @param DBName The database name we want access to.
     * @param port The port used for the connection.
     * @param sslmode Declares if SSL is required.
     */
    public DBMySQL(String user, String password, String host, String DBName, int port, boolean sslmode)
    {
        super(user, password, host, DBName, DBType.MYSQL, port, sslmode);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found!\n" + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    protected void doConnect() throws SQLException {
        String jdbc = "jdbc://mysql//" + host + ":" + port + "/" + DBName;
        if(sslmode) {
            jdbc += "?verifyServerCertificate=true&useSSL=true&requireSSL=true";
        }
        this.connection = DriverManager.getConnection(jdbc, user, password);
    }
    
}