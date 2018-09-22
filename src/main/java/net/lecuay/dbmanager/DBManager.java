package net.lecuay.dbmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Scanner;

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
    protected boolean sslmode = false;

    /** The <b>JDBC</b> used for the connection. */
    protected String JDBC = ""; 

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
    protected abstract void doConnect() throws SQLException;

    /**
     * Closes an open connection.
     * @throws SQLException If access error is raised.
     */
    protected void doClose() throws SQLException{connection.close();}

    /**
     * Closes connection itself and every object in params.
     * @param oCloseables Objects that implements {@link java.lang.AutoCloseable}.
     * @throws SQLException In case {@link java.sql.Connection} couldn't be closed.
     * @throws Exception If object cannot be closed.
     */
    protected void doClose(AutoCloseable... oCloseables)
    throws SQLException {
        ArrayList<AutoCloseable> arrayCloseables = new ArrayList<>(Arrays.asList(oCloseables));
        arrayCloseables.forEach(closeable -> {
            if(!(closeable instanceof java.lang.AutoCloseable))
            {
                try {
                    throw new Exception("The object " + closeable.toString() + " cannot be closed!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    closeable.close();
                } catch (Exception e) {
                    System.err.println("The object " + closeable.toString() + " cannot be closed!");
                }
            }
        });
        connection.close();
    }

    /**
     * Executes either SQL code or a File SQL <i>(archive.sql)</i>
     * and commits at the end.
     * @param sql SQL code or path to the File SQL.
     * @throws FileNotFoundException If file cannot be accessed.
     * @throws SQLException If SQL syntax error or Connection error is raised.
     */
    public void doExecute(String sql)
    throws FileNotFoundException, SQLException {
        doExecute(sql, true);
    }

    /**
     * Executes either SQL code or a File SQL <i>(archive.sql)</i>
     * and commits if is needed.
     * @param sql SQL code or path to the File SQL.
     * @param commit {@code true} for committing, {@code false} otherwise.
     * @throws FileNotFoundException If file cannot be accessed.
     * @throws SQLException If SQL syntax error or Connection error is raised.
     */
    public void doExecute(String sql, boolean commit)
    throws FileNotFoundException, SQLException {
        File file = new File(sql);
        // Check if SQL exists and is a file
        if (file.exists() && file.isFile())
        {
            executeFile(file, true);
        } else {
            executeQuery(true, sql);
        }
    }

    /**
     * Executes a SQL file query-by-query.
     * @param fileSQL The SQL file to execute.
     * @param commit {@code true} for committing, {@code false} otherwise.
     * @throws FileNotFoundException In case file cannot be found.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    protected void executeFile(File fileSQL, boolean commit)
    throws FileNotFoundException, SQLException {
        String sql = "";
        Scanner reader = new Scanner(fileSQL);

        while(reader.hasNext())
        {
            sql += reader.next() + " ";
        }
        reader.close();

        // Getting each query by ';' character
        String[] queries = sql.split(";");
        executeQuery(commit, queries);
    }

    /**
     * Executes SQL code and commits if needed.
     * @param commit {@code true} for committing, {@code false} otherwise.
     * @param codeSQL Array of SQL code to execute.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    protected void executeQuery(boolean commit, String... codeSQL)
    throws SQLException {
        doConnect();
        Statement stm = connection.createStatement();

        for(String query: codeSQL)
        {
            // Looking for empty Strings
            if (!(query.isEmpty() || query.trim().isEmpty() || query == null))
            {
                // Adding delimiter
                if (query.indexOf(";") == -1)
                {
                    query += ";";
                }
                stm.executeUpdate(query);
            } else {
                throw new SQLException("Query was empty.");
            }
        }

        if (commit && !connection.getAutoCommit())
            connection.commit();
        
        doClose(stm);
    }

    /**
     * This method will return an <code>{@literal ArrayList<Map<Column, Value of Column>>}</code>
     * for example:<br>
     * 
     * <b>Assuming that we have 3 entries in our table <i>sample</i> with the following
     * columns: <i>id</i>, <i>name</i>, <i>country</i> we must use:</b>
     * 
     * <pre>
     * // Create object conex
     * {@literal ArrayList<LinkedHashMap<String, String>>} select;
     * select = conex.doSelect("sample", "", "id", "name", "country");
     * </pre>
     * Our <i>select</i> object now is filled with 3 {@link java.util.Map}, each map has
     * <i>id</i>, <i>name</i>, <i>country</i> as {@code Keys} and its respective values.
     * 
     * @param table The table we want to select from.
     * @param condition The condition if its needed (otherwise just "").
     * @param columns The columns we want to select.
     * @return An {@java.util.ArrayList} with each entry in our table. Also each {@link java.util.ArrayList}
     * has a {@link java.util.LinkedHashMap} with <i>Column</i> as {@code Key} and <i>Value of column</i> as {@code Value}.
     * @throws SQLException In case SQL syntax error or Connection error.
     */
    public abstract ArrayList<LinkedHashMap<String, String>> doSelect(String table, String condition, String... columns) throws SQLException;

    /**
     * It prints our select.
     * 
     * @param table The table we want to select from.
     * @param condition The condition if its needed (otherwise just "").
     * @param columns The columns we want to select.
     * @throws SQLException In case SQL syntax error or Connection error.
     */
    public void showSelect(String table, String condition, String... columns)
    throws SQLException {
        ArrayList<LinkedHashMap<String, String>> resultSelect = doSelect(table, condition, columns);
        // Getting literally any entry to get columns size
        resultSelect.get(0).keySet().forEach(column -> {
            System.out.print(column + " | ");
        });
        System.out.println();

        resultSelect.forEach(select -> {
            select.values().forEach(value -> {
                System.out.print(value + " | ");
            });
            System.out.println();
        });
    }

    /**
     * Do a insert into a table.<br>
     * Parameters must follow the next syntax {@code columnName=value}.<br>
     * For example, assuming that we want to insert <i>name</i>, <i>coins</i> and <i>id</i> into
     * <i>sample</i> table we have to use the following lines:
     * <pre>
     * // DBManager instace conex
     * conex.doInsert("sample", "name='LeCuay'", "coins=12.5", "id=12")
     * </pre>
     * <b>Is very important to follow the correct syntax: values between <i>''</i> for Strings
     * and plain for numbers.
     * 
     * @param table The table where values will be inserted.
     * @param inserts The values to insert following the syntax: {@code column=value}.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    public abstract void doInsert(String table, String... inserts) throws SQLException;

    // GETTERS AND SETTERS

    /*
    ** ---- Every time we reset something by setValue() method JDBC must be reseted ---- **
    */

    /**
     * Changes the user used in the Database connection.
     * @param user The new user.
     */
    public void setUser(String user){
        JDBC = "";
        this.user = user;
    }

    /**
     * Changes the password used for the connection.
     * @param password The new password.
     */
    public void setPassword(String password){
        JDBC = "";
        this.password = password;
    }

    /**
     * Changes the Database we want connect to.
     * @param DBName The new Database.
     */
    public void setDBName(String DBName){
        JDBC = "";
        this.DBName = DBName;
    }

    /**
     * Changes the enum {@link DBType} used for the connection.
     * @param conexType The new {@link DBType}.
     */
    public void setConexType(DBType conexType){this.conexType = conexType;}

    /**
     * Changes the port used for the connection.
     * @param port The new port.
     */
    public void setPort(int port){
        JDBC = "";
        this.port = port;
    }

    /**
     * Sets if SSL is required.
     * @param sslmode {@code True} if is required {@code False} otherwise.
     */
    public void setSSLMode(boolean sslmode){
        JDBC = "";
        this.sslmode = sslmode;
    }
    
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