package net.lecuay.dbmanager;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

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

    /** The object {@link Connection} used for queries. */
    protected Connection connection;

    /**
     * Simple Enum used for setting allowed connections.<br>
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
    }

    /**
     * This method will make a connection for our Database.
     * @throws SQLException If database is null or access error is raised.
     */
    protected abstract void doConnect() throws SQLException;

    /**
     * Closes an open connection.
     * @throws SQLException If access error is raised.
     */
    protected void doClose() throws SQLException
    {
        connection.close();
    }

    /**
     * Executes a file SQL or SQL code.
     * @param sql Path to the SQL file or SQL code itself.
     */
    public void doExecute(String sql)
    {
        File sqlFile = new File(sql);
        
        if (sqlFile.exists())
        {
            executeFile(sqlFile);
        } else {
            executeSQL(sql);
        }
    }

    /**
     * 
     * @param sql
     */
    protected void executeFile(File sql)
    {

    }

    /**
     * 
     * @param sql
     */
    protected void executeSQL(String sql)
    {
        
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
     * @return User as {@link String}.
     */
    public String getUser(){return this.user;}

    /**
     * Returns the name of the Database we are connected to.
     * @return Database's name as {@link String}.
     */
    public String getDBName(){return this.DBName;}

    /**
     * Returns the Enum used in the actual connection.
     * @return Enum {@link DBType}.
     */
    public DBType getConexType(){return this.conexType;}

    /**
     * Returns the name of the enum {@link DBType} used in the actual connection.
     * @return {@link DBType} as {@link String}.
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