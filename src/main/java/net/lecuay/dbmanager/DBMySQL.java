package net.lecuay.dbmanager;

import java.net.URI;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class created as instance of {@link DBManager} created to manage MySQL
 * connections.
 * 
 * @author LeCuay
 * @version 0.1 - Alpha
 */
public class DBMySQL extends DBManager {

    /**
     * Creates a connection with the parameters given.
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
        JDBC = "jdbc:mysql://" + host + "/" + this.DBName;

        // If SSL is required JDBC will be updated.
        if(sslmode)
            JDBC += "?verifyServerCertificate=true&useSSL=true&requireSSL=true";
    }

    /**
     * Creates a connection based on a given <b>JDBC</b>.
     * @param user The user used for the connection.
     * @param password The password used for the connection.
     * @param JDBC The customized JDBC given.
     */
    public DBMySQL(String user, String password, String JDBC)
    {
        super(user, password, JDBC);
    }

    /**
     * Creates a connection based on a given URI.
     * @param uri The object {@link java.net.URI} used for the connection. 
     */
    public DBMySQL(URI uri)
    {
        super(uri);
    }

    /**
     * Creates a connection with the following <i>default values</i>:
     * <ul>
     * <li>Scheme = null</li>
     * <li>host = localhost</li>
     * <li>port = 3306</li>
     * <li>ssl = non-required</li>
     * </ul>
     * It also creates the JDBC with the given parameters.<br>
     * This methods requires {@code setDBName(String DBName)} for later
     * connection to a Database.<br>
     * <pre>
     * DBMySQL conex = DBMySQL("username", "password");
     * conex.setDBName("sampleDatabase");
     * </pre>
     * @param user The user used for the connection.
     * @param password The password used for the connection.
     */
    public DBMySQL(String user, String password)
    {
        super(user, password, "localhost", "", DBType.MYSQL, 3306, false);
        JDBC = "jdbc:mysql://" + host + "/";
    }

    @Override
    protected void doConnect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(JDBC, properties);
    }
    
}