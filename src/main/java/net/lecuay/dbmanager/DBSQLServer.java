package net.lecuay.dbmanager;

import java.net.URI;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Class created as instance of {@link DBManager} to manage SQL Server
 * connections.
 * 
 * @author LeCuay
 * @version 0.1 - Alpha
 * @see DBManager
 */
public class DBSQLServer extends DBManager {

    /**
     * Declares parameters used for a connection.
     * @param user The user used for the connection.
     * @param password The password used for the connecion.
     * @param host The host where our Database is hosted.
     * @param DBName The database name we want access to.
     * @param port The port used for the connection.
     * @param sslmode Declares if SSL is required.
     */
    public DBSQLServer(String user, String password, String host, String DBName, int port, boolean sslmode)
    {
        super(user, password, host, DBName, DBType.SQLSERVER, port, sslmode);

        properties.setProperty("databaseName", this.DBName);
    }

    /**
     * Creates a connection based on a given <b>JDBC</b>.
     * @param user The user used for the connection.
     * @param password The password used for the connection.
     * @param JDBC The customized JDBC given.
     */
    public DBSQLServer(String user, String password, String JDBC)
    {
        super(user, password, JDBC);
    }

    /**
     * Creates a connection based on a given URI.
     * @param uri The object {@link java.net.URI} used for the connection. 
     */
    public DBSQLServer(URI uri)
    {
        super(uri);
    }

    /**
     * Creates a connection with the following <i>default values</i>:
     * <ul>
     * <li>Scheme = null</li>
     * <li>host = localhost</li>
     * <li>port = 1433</li>
     * <li>ssl = non-required</li>
     * </ul>
     * It also creates the JDBC with the given parameters.<br>
     * This methods requires {@code setDBName(String DBName)} for later
     * connection to a Database.<br>
     * <pre>
     * DBSQLServer conex = DBSQLServer("username", "password");
     * conex.setDBName("sampleDatabase");
     * </pre>
     * @param user The user used for the connection.
     * @param password The password used for the connection.
     */
    public DBSQLServer(String user, String password)
    {
        super(user, password, "localhost", "", DBType.SQLSERVER, 1433, false);
    }

    @Override
    protected void doConnect() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch(ClassNotFoundException e) {
            System.out.println("Driver not found! -> " + e.getMessage());
        }

        if (JDBC.equals(""))
        {
            JDBC = "jdbc:sqlserver://"  + host + ":" + port + "/" + ";";
            // If SSL is required JDBC will be updated.
            if(sslmode)
                JDBC += "integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
        }
        
		connection = DriverManager.getConnection(JDBC, properties);
    }

    @Override
    public ArrayList<LinkedHashMap<String, String>> doSelect(String table, String condition, String... columns)
    throws SQLException {
        doConnect();

        ArrayList<LinkedHashMap<String, String>> selectResult = new ArrayList<>();
        StringBuilder codeSQL = new StringBuilder("SELECT ");
        
        codeSQL.append(String.join(", ", columns));
        codeSQL.append(" FROM ").append(table);
        
        if (!(condition.isEmpty() || condition.trim().isEmpty())) {
            codeSQL.append(" WHERE ").append(condition);
        }
        codeSQL.append(";");

        Statement stm = connection.createStatement();
        ResultSet result = stm.executeQuery(codeSQL.toString());
        ResultSetMetaData resultData = result.getMetaData();
        
        // Checking if user wants every data from a table
        if(columns[0].equals("*"))
        {
            while(result.next())
            {
                // Creating a new Map for each element in columns
                selectResult.add(new LinkedHashMap<>());
                for(int i = 1; i <= resultData.getColumnCount(); ++i)
                {
                    // Adding values to that element
                    selectResult.get(selectResult.size() - 1).put(resultData.getColumnName(i), result.getString(i));
                }
            }
        } else {
            while (result.next())
            {
                // Creating a new Map for each element in columns
                selectResult.add(new LinkedHashMap<>());
                for (int i = 0; i < columns.length; ++i)
                {
                    // Adding values to that element
                    selectResult.get(selectResult.size() - 1).put(columns[i], result.getString(i + 1));
                }
            }
        }
        
        doClose(stm, result);
        return selectResult;
    }

	@Override
    public void doInsert(String table, String... inserts)
    throws SQLException {
		doConnect();
        Statement stm = connection.createStatement();
        HashMap<String, String> parsedInserts = new HashMap<>();

        for (String insert: inserts)
        {
            // Checks if insert syntax is correct
            if(insert.indexOf("=") == -1)
            {
                throw new SQLException("Syntax error: Inserts have to follow the next syntax 'columnName=value'");
            }
            // Inserts follows column=value so we have to store each
            parsedInserts.put(insert.split("=")[0].trim(), insert.split("=")[1].trim());
        }

        // Creating sentence
        String sql = "INSERT INTO " + table + "(";
        sql += String.join(", ", parsedInserts.keySet().toArray(new String[]{})) + ")";
        sql += " VALUES (" + String.join(", ", parsedInserts.values().toArray(new String[]{})) + ")";
        sql += ";";

        executeQuery(true, sql);

        doClose(stm);
	}
    
}