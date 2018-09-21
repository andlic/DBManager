package net.lecuay.dbmanager;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * This abstract class declares a DB connection that will make easier
 * database managing.
 * 
 * @author LeCuay
 * @version 0.1 - Alpha
 */
public abstract class DBManager {

    /** The user used in the connection. */
    protected String user;

    /** The password used in the connection. */
    protected String password;

    /** The host used for the connection. */
    protected String host;

    /** The name of the Database we want to connect to. */
    protected String DBName;

    /** The enum {@link DBType} declared to create a connection. */
    protected DBType conexType;
    
    /** The port used in the connection. */
    protected int port;

    /** Declares if SSLMODE is required. */
    protected boolean sslmode;

    /** The <b>JDBC</b> used for the connection. */
    protected String JDBC;

    /** The object {@link Connection} used for queries. */
    protected Connection connection;

    /** List of <b>Propeties</b> required for the connection. */
    protected final Properties properties = new Properties();

    /**
     * Simple Enum used for setting allowed connections.<br>
     * <h3>Supported Connections:</h3>
     * <ul>
     * <li>MYSQL - MySQL connection.</li>
     * <li>POSTGRESQL - PostgreSQL connection.</li>
     * <li>SQLSERVER - SQLServer connection.</li>
     * <li>SQLITE3 - SQLite 3 connection.</li>
     * </ul>
     */
    public enum DBType {
        MYSQL,
        POSTGRESQL,
        SQLSERVER,
        SQLITE3
    }

    /**
     * Declares parameters used for a connection.
     * @param user The user used for the connection.
     * @param password The password used for the connecion.
     * @param host The host where our Database is hosted.
     * @param DBName The database name we want access to.
     * @param conexType The enum {@link DBType} declared for the connection.
     * @param port The port used for the connection.
     * @param sslmode Declares if SSL is required.
     */
    protected DBManager(String user, String password, String host, String DBName, DBType conexType, int port, boolean sslmode)
    {
        this.user = user;
        this.password = password;
        this.host = host;
        this.DBName = DBName;
        this.conexType = conexType;
        this.port = port;
        this.sslmode = sslmode;

        properties.setProperty("user", user);
        properties.setProperty("password", password);
    }

    /**
     * Creates a connection based on a given <b>JDBC</b>.
     * @param user The user used for the connection.
     * @param password The password used for the connection.
     * @param JDBC The customized JDBC given.
     */
    protected DBManager(String user, String password, String JDBC)
    {
        this.user = user;
        this.password = password;
        this.JDBC = JDBC;

        properties.setProperty("user", user);
        properties.setProperty("password", password);
    }

    /**
     * Creates a connection based on a given URI.
     * @param uri The object {@link java.net.URI} used for the connection. 
     */
    protected DBManager(URI uri)
    {
        // Since UserInfo works by user:password we are getting each
        user = uri.getUserInfo().split(":")[0];
        password = uri.getUserInfo().split(":")[1];
        host = uri.getHost();
        port = uri.getPort();

        // getSchemeSpecificPart will return a full path, but we only need Database, which is
        // followed by host:port/
        DBName = uri.getSchemeSpecificPart().split(port + "/")[1];

        properties.setProperty("user", user);
        properties.setProperty("password", password);
    }

    /**
     * This method will make a connection for our Database.
     * @throws SQLException If database is null or access error is raised.
     * @throws ClassNotFoundException In case driver isn't found.
     */
    protected abstract void doConnect() throws SQLException, ClassNotFoundException;

    /**
     * Closes an open connection.
     * @throws SQLException If access error is raised.
     */
    protected void doClose() throws SQLException{connection.close();}

    /**
     * Closes connection itself and every object in params.
     * @param oCloseables Objects that implements {@link java.lang.AutoCloseable}.
     * @throws SQLException In case {@link java.sql.Connection} couldn't be closed.
     */
    protected void doClose(Object... oCloseables) throws SQLException
    {
        ArrayList<Object> arrayCloseables = new ArrayList<>(Arrays.asList(oCloseables));
        arrayCloseables.forEach(closeable -> {
            if(!(closeable instanceof java.lang.AutoCloseable))
                try {
                    throw new Exception("The object " + closeable.toString() + " cannot be closed!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
        connection.close();
    }

    // GETTERS AND SETTERS

    /**
     * Changes the user used in the Database connection.
     * @param user The new user.
     */
    public void setUser(String user){this.user = user;}

    /**
     * Changes the password used for the connection.
     * @param password The new password.
     */
    public void setPassword(String password){this.password = password;}

    /**
     * Changes the Database we want connect to.
     * @param DBName The new Database.
     */
    public void setDBName(String DBName){this.DBName = DBName;}

    /**
     * Changes the enum {@link DBType} used for the connection.
     * @param conexType The new {@link DBType}.
     */
    public void setConexType(DBType conexType){this.conexType = conexType;}

    /**
     * Changes the port used for the connection.
     * @param port The new port.
     */
    public void setPort(int port){this.port = port;}

    /**
     * Sets if SSL is required.
     * @param sslmode {@code True} if is required {@code False} otherwise.
     */
    public void setSSLMode(boolean sslmode){this.sslmode = sslmode;}
    
    /**
     * Returns the user used for the connection.
     * @return User as {@link java.lang.String}.
     */
    public String getUser(){return this.user;}

    /**
     * Returns the name of the Database we are connected to.
     * @return Database's name as {@link java.lang.String}.
     */
    public String getDBName(){return this.DBName;}

    /**
     * Returns the Enum used in the actual connection.
     * @return Enum {@link DBType}.
     */
    public DBType getConexType(){return this.conexType;}

    /**
     * Returns the name of the enum {@link DBType} used in the actual connection.
     * @return {@link DBType} as {@link java.lang.String}.
     */
    public String getConexTypeName(){return this.conexType.name();}

    /**
     * Returns the port used.
     * @return The port used as Integer.
     */
    public int getPort(){return this.port;}

    /**
     * Checks if SSL is required.
     * @return {@code True} or {@code False}.
     */
    public boolean isSSLMode(){return this.sslmode;}
    
}