package net.lecuay.dbmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * This abstract class declares a DB connection that will make easier
 * database managing.
 * 
 * @author LeCuay
 * @version 0.1 - Beta
 */
public abstract class DBManager {

    /** The author of the API */
    private static String author = "LeCuay";

    /** The version of the API */
    private static String version = "0.1 - Beta";

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

    // <editor-fold defaultstate="collapsed" desc="Constructors">

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
    protected DBManager(java.net.URI uri)
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

    // </editor-fold>

    /**
     * This method will make a connection for our Database.
     * @throws SQLException If database is null or access error is raised.
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
     */
    protected void doClose(AutoCloseable... oCloseables)
    throws SQLException {
        for (AutoCloseable closeable: oCloseables)
        {
            if(!(closeable instanceof java.lang.AutoCloseable))
            {
                throw new SQLException("The object " + closeable + " cannot be closed!");
            } else {
                try {
                    closeable.close();
                } catch (Exception e) {
                    throw new SQLException("The object " + closeable + " cannot be closed!");
                }
            }
        }
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
            executeFile(file, commit);
        } else {
            executeQuery(commit, sql);
        }
    }

    /**
     * Executes either SQL code or a File SQL <i>(archive.sql)</i>
     * and returns result as {@code ArrayList<LinkedHashMap<String, String>>}.<br>
     * 
     * <b>Example of use:</b><br>
     * <i>Assuming we have the following table 'users':</i>
     * <table>
     *  <tr>
     *   <th>id</th>
     *   <th>name</th>
     *   <th>money</th>
     *   <th>email</th>
     *  </tr>
     *  <tr>
     *   <td>1</td>
     *   <td>LeCuay</td>
     *   <td>125.43</td>
     *   <td>thisisnotmyemail&#64;live.com</td>
     *  </tr>
     *  <tr>
     *   <td>2</td>
     *   <td>John Smith</td>
     *   <td>332.21</td>
     *   <td>testemail&#64hotmail.com</td>
     *  </tr>
     * </table>
     * 
     * <pre>
     * // We already have an object DBManager connection.
     * String sql = "SELECT name FROM users; SELECT COUNT(id) FROM users;";
     * connection.doExecuteWithReturn(sql).forEach(query -> {
     *  // The first one will be the select with name
     *  // The second one will be the count of id
     * 
     *  query.forEach(entry -> {
     *      // Now we read every entry got by our select
     *      System.out.println(entry);
     *  });
     * });
     * </pre>
     * @param sql SQL code or path to the File SQL.
     * @return An array with every {@link java.util.ArrayList} with each entry in our table. Also each {@link java.util.ArrayList}
     * has a {@link java.util.LinkedHashMap} with <i>Column</i> as {@code Key} and <i>Value of column</i> as {@code Value}.
     * @throws FileNotFoundException If file cannot be accessed.
     * @throws SQLException If SQL syntax error or Connection error is raised.
     * 
     * @see DBManager#executeQueryWithReturn(String...)
     */
    public ArrayList<ArrayList<LinkedHashMap<String, String>>> doExecuteWithReturn(String sql)
    throws FileNotFoundException, SQLException {
        File file = new File(sql);
        // Check if SQL exists and is a file
        if (file.exists() && file.isFile())
        {
            return executeFileWithReturn(file);
        } else {
            return executeQueryWithReturn(sql);
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
        java.util.Scanner reader = new java.util.Scanner(fileSQL);

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
     * Executes a SQL file query-by-query and returns result.
     * @param fileSQL The SQL file to execute.
     * @return An array with the result format.
     * @throws FileNotFoundException In case file cannot be found.
     * @throws SQLException If SQL syntax error or connection error raises.
     * 
     * @see DBManager#executeQueryWithReturn(String...)
     */
    protected ArrayList<ArrayList<LinkedHashMap<String, String>>> executeFileWithReturn(File fileSQL)
    throws FileNotFoundException, SQLException {
        String sql = "";
        java.util.Scanner reader = new java.util.Scanner(fileSQL);

        while(reader.hasNext())
        {
            sql += reader.next() + " ";
        }
        reader.close();

        // Getting each query by ';' character
        String[] queries = sql.split(";");
        return executeQueryWithReturn(queries);
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
            if (!(query.isEmpty() || query.trim().isEmpty()))
            {
                // Adding delimiter
                if (!query.contains(";"))
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
     * Executes SQL code and returns result as {@code ArrayList<LinkedHasMap>}.
     * @param codeSQL Array of SQL code to execute.
     * @return An array with every {@link java.util.ArrayList} with each entry in our table. Also each {@link java.util.ArrayList}
     * has a {@link java.util.LinkedHashMap} with <i>Column</i> as {@code Key} and <i>Value of column</i> as {@code Value}.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    protected ArrayList<ArrayList<LinkedHashMap<String, String>>> executeQueryWithReturn(String... codeSQL)
    throws SQLException {
        doConnect();
        ArrayList<ArrayList<LinkedHashMap<String, String>>> selectResult = new ArrayList<>();
        Statement stm = connection.createStatement();
        ResultSet result = stm.getResultSet();

        for (int i = 0; i < codeSQL.length; i++) {
            // Creating ArrayList for each query
            selectResult.add(new ArrayList<>());
            result = stm.executeQuery(codeSQL[i]);

            while (result.next())
            {
                selectResult.get(i).add(new LinkedHashMap<>());
                for (int j = 0; j < result.getMetaData().getColumnCount(); j++) {
                    // selectResult.get(i).get(selectResult.get(i).size() - 1) will get the very last item
                    selectResult.get(i).get(selectResult.get(i).size() - 1).put(result.getMetaData().getColumnName(j+1), result.getString(j+1));
                }
            }

            result.close();
        }

        doClose(stm, result);
        return selectResult;
    }

    /**
     * This method will return an <code>{@literal ArrayList<Map<Column, Value of Column>>}</code>
     * for example:<br>
     * 
     * <b>Assuming that we have 3 entries in our table <i>sample</i> with the following
     * columns: <i>id</i>, <i>name</i>, <i>country</i> we must use:</b>
     * 
     * <pre>
     * // Create object connection
     * {@literal ArrayList<LinkedHashMap<String, String>>} select;
     * select = connection.doSelect("sample", "", "id", "name", "country");
     * </pre>
     * Our <i>select</i> object now is filled with 3 {@link java.util.Map}, each map has
     * <i>id</i>, <i>name</i>, <i>country</i> as {@code Keys} and its respective values.
     * 
     * @param table The table we want to select from.
     * @param condition The condition if its needed (otherwise just "").
     * @param columns The columns we want to select.
     * @return An {@link java.util.ArrayList} with each entry in our table. Also each {@link java.util.ArrayList}
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
        try {
            resultSelect.get(0).keySet().forEach(column -> System.out.print(column + " | "));
            System.out.println();
    
            resultSelect.forEach(select -> {
                select.values().forEach(value -> System.out.print(value + " | "));
                System.out.println();
            });
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Empty set.");
        }
    }

    /**
     * Does a insert into a table.<br>
     * Parameters must follow the next syntax {@code columnName=value}.<br>
     * For example, assuming that we want to insert <i>name</i>, <i>coins</i> and <i>id</i> into
     * <i>sample</i> table we have to use the following lines:
     * <pre>
     * // DBManager instace connection
     * connection.doInsert("sample", "name='LeCuay'", "coins=12.5", "id=12")
     * </pre>
     * <b>Is very important to follow the correct syntax: values between <i>''</i> for Strings
     * and plain for numbers.</b>
     * 
     * @param table The table where values will be inserted.
     * @param inserts The values to insert following the syntax: {@code column=value}.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    public abstract void doInsert(String table, String... inserts) throws SQLException;

    /**
     * Does an update into row or rows.<br>
     * Parameters must follow the next syntax {@code columnName=newValue}.<br>
     * For example, assuming we want to change every <i>name</i> value starting by
     * "Jr." for "Mr." we will use the following code:
     * <pre>
     * // DBManager instance connection
     * connection.doUpdate("sample", "name LIKE 'Jr.%'", "name='Mr. ' + name");
     * </pre>
     * <b>Is very important to follow the correct syntax: values between <i>''</i> for Strings
     * and plain for numbers.</b>
     * 
     * @param table The Table where values will be updated.
     * @param condition The condition for updating rows.
     * @param updates The values to update following the syntax: {@code column=newValue}.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    public abstract void doUpdate(String table, String condition, String... updates) throws SQLException;

    /**
     * Deletes a row or rows where condition is true.
     * @param table The table we want to remove the rows.
     * @param condition The condition for the rows to be deleted.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    public abstract void doDelete(String table, String condition) throws SQLException;

    /**
     * Creates a table with the columns given in paramaters.<br>
     * Parameters must follow the next syntax {@code columnName Type Value}.<br>
     * For exaple, assuming we want to create the table 'persons' with <i>id (integer primary key)</i>,
     * <i>name (varchar(255))</i> and <i>age (integer(4))</i> we will use the follow code:
     * <pre>
     * // DBManager instance connection
     * connection.createTable("persons", "id INTEGER PRIMARY KEY",
     *                              "name VARCHAR(255)",
     *                              "age INTEGER(4)");
     * </pre>
     * <b>Keep in mind each SQL Connection has its own syntax.</b>
     * 
     * @param table Name of the table we want to create.
     * @param columns The columns to create following the syntax: {@code colunmName Type Value}.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    public abstract void createTable(String table, String... columns) throws SQLException;

    /**
     * Creates a database with the given name.
     * @param database The name of the database to create.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    public abstract void createDatabase(String database) throws SQLException;

    /**
     * Deletes a table or tables.
     * @param tables The tables to delete.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    public abstract void dropTable(String... tables) throws SQLException;

    /**
     * Deletes a database or databases.
     * @param databases The databases to delete.
     * @throws SQLException If SQL syntax error or connection error raises.
     */
    public abstract void dropDatabase(String... databases) throws SQLException;

    // <editor-fold defaultstate="collapsed" desc="Setter and Getters">
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
     * Adds a property to the JDBC.
     * @param key The key of the property.
     * @param value The value of the property.
     */
    public void addProperty(String key, String value)
    {
        properties.setProperty(key, value);
    }

    /**
     * Gets every schema within a connection and returns them as an Array.
     * @return An array with all the schemas.
     * @throws SQLException If connection fails.
     */
    public String[] getDatabases()
    throws SQLException {
        doConnect();
        
        ArrayList<String> databases = new ArrayList<>();
        ResultSet result = connection.getMetaData().getCatalogs();

        while(result.next())
        {
            databases.add(result.getString(1));
        }

        doClose();
        return databases.toArray(new String[]{});
    }

    /**
     * Gets every table within a connection and returns them as an Array.
     * @return An array with tables in that database
     * @throws SQLException If connection fails.
     */
    public String[] getTables()
    throws SQLException {
        doConnect();

        ArrayList<String> tables = new ArrayList<>();
        ResultSet result;
        DBName = connection.getCatalog();
        if (DBName != null)
        {
            // Getting all tables or just those in database
            if (DBName.equals(""))
                result = connection.getMetaData().getTables(null, null, "%", null);
            else
                result = connection.getMetaData().getTables(DBName, null, "%", null);
            
            while(result.next())
            {
                // 3 is the index for tables name
                tables.add(result.getString(3));
            }
        } else {
            throw new SQLException("Database is not selected.");
        }
        
        doClose(result);
        return tables.toArray(new String[]{});
    }

    /**
     * Gets every column within a table and returns them as an array.
     * @param table The table we want columns from.
     * @return An array with all columns within a Table.
     * @throws SQLException If connection fails.
     */
    public String[] getColumns(String table)
    throws SQLException {
        doConnect();

        ArrayList<String> columns = new ArrayList<>();
        ResultSet result;
        DBName = connection.getCatalog();

        // Getting all columns or just those in database
        if (DBName.equals(""))
            result = connection.getMetaData().getColumns(null, null, "%" + table + "%", null);
        else
            result = connection.getMetaData().getColumns(DBName, null, "%" + table + "%", null);
        
        while(result.next())
        {
            // 4 is the index for columns name
            columns.add(result.getString(4));
        }

        doClose();
        return columns.toArray(new String[]{});
    }

    /**
     * Returns an array of Strings with columns information.
     * @param table The table used to extract columns information.
     * @return An array with all columns and detailed information.
     * @throws SQLException If connection fails.
     */
    public String[] getColumnsInfo(String table)
    throws SQLException {
        doConnect();

        ResultSet result, rstPrimaryKeys;
        ArrayList<String> columnInfo = new ArrayList<>();
        ArrayList<String> primaryKeys = new ArrayList<>();

        // Getting all columns or just those in database
        if (DBName.equals(""))
        {
            result = connection.getMetaData().getColumns(null, null, "%" + table + "%", null);
            rstPrimaryKeys = connection.getMetaData().getPrimaryKeys(null, "", "%" + table + "%");
        } else {
            result = connection.getMetaData().getColumns(DBName, null, "%" + table + "%", null);
            rstPrimaryKeys = connection.getMetaData().getPrimaryKeys(DBName, "", "%" + table + "%");
        }

        while(rstPrimaryKeys.next())
        {
            primaryKeys.add(rstPrimaryKeys.getString(4));
        }

        while(result.next())
        {
            columnInfo.add(String.format("%s %s(%s%s)%s%s",
                                        result.getString(4),
                                        result.getString(6),
                                        result.getString(7),
                                        result.getString(9)!=null?", "+result.getString(9):"",
                                        primaryKeys.contains(result.getString(4))?" PRIMARY KEY":"",
                                        result.getString(11).equals("0")?" NOT NULL":""));
        }
        
        doClose(result, rstPrimaryKeys);
        return columnInfo.toArray(new String[]{});
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

    @Override
    public String toString()
    {
        return String.format("- - - - - DBManager %s - - - - -\n"+
                             "Connection type: %s\n"+
                             "User: %s\n"+
                             "Host: %s\n"+
                             "Schema: %s\n"+
                             "Port: %d\n"+
                             "SSLMode: %s\n"+
                             "- - - - - Created by %s - - - - -",
                             DBManager.version, conexType.name(),
                             user, host, DBName, port, sslmode,
                             DBManager.author);
    }
    
    // </editor-fold>
    
}