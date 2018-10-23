package net.lecuay.dbmanager;

import java.net.URI;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class created as instance of {@link DBManager} to manage SQL Server
 * connections.
 *
 * @author LeCuay
 * @version 0.1 - Beta
 * @see DBManager
 */
public class DBSQLServer extends DBManager {

    /**
     * Declares parameters used for a connection.
     *
     * @param user The user used for the connection.
     * @param password The password used for the connecion.
     * @param host The host where our Database is hosted.
     * @param DBName The database name we want access to.
     * @param port The port used for the connection.
     * @param sslmode Declares if SSL is required.
     */
    public DBSQLServer(String user, String password, String host, String DBName, int port, boolean sslmode) {
        super(user, password, host, DBName, DBType.SQLSERVER, port, sslmode);

        properties.setProperty("databaseName", this.DBName);
    }

    /**
     * Creates a connection based on a given <b>JDBC</b>.
     *
     * @param user The user used for the connection.
     * @param password The password used for the connection.
     * @param JDBC The customized JDBC given.
     */
    public DBSQLServer(String user, String password, String JDBC) {
        super(user, password, JDBC);
    }

    /**
     * Creates a connection based on a given URI.
     *
     * @param uri The object {@link java.net.URI} used for the connection.
     */
    public DBSQLServer(URI uri) {
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
     *
     * @param user The user used for the connection.
     * @param password The password used for the connection.
     */
    public DBSQLServer(String user, String password) {
        super(user, password, "localhost", "", DBType.SQLSERVER, 1433, false);
    }

    @Override
    protected void doConnect()
            throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found! -> " + e.getMessage());
        }

        if (JDBC.equals("")) {
            JDBC = "jdbc:sqlserver://" + host + ":" + port + "/" + ";";
            // If SSL is required JDBC will be updated.
            if (sslmode) {
                properties.setProperty("integratedSecurity", "true");
                properties.setProperty("encrypt", "true");
                properties.setProperty("trustServerCertificate", "true");
            } else {
                properties.setProperty("integratedSecurity", "false");
                properties.setProperty("encrypt", "false");
                properties.setProperty("trustServerCertificate", "false");
            }
        }

        connection = DriverManager.getConnection(JDBC, properties);
    }

    @Override
    public ArrayList<LinkedHashMap<String, String>> doSelect(String table, String condition, String... columns)
            throws SQLException {
        StringBuilder codeSQL = new StringBuilder("SELECT ");

        codeSQL.append(String.join(", ", columns));
        codeSQL.append(" FROM ").append(table);

        if (!(condition.isEmpty() || condition.trim().isEmpty())) {
            codeSQL.append(" WHERE ").append(condition);
        }

        return executeQueryWithReturn(codeSQL.toString()).get(0);
    }

    @Override
    public void doInsert(String table, String... inserts)
            throws SQLException {
        HashMap<String, String> parsedInserts = new HashMap<>();

        for (String insert : inserts) {
            // Checks if insert syntax is correct
            if (!insert.contains("=")) {
                throw new SQLException("Syntax error: Inserts have to follow the next syntax 'columnName=value'");
            }
            // Inserts follows column=value so we have to store each
            parsedInserts.put(insert.split("=")[0].trim(), insert.split("=")[1].trim());
        }

        // Creating sentence
        String sql = "INSERT INTO " + table + "(";
        sql += String.join(", ", parsedInserts.keySet().toArray(new String[]{})) + ")";
        sql += " VALUES (" + String.join(", ", parsedInserts.values().toArray(new String[]{})) + ")";

        executeQuery(true, sql);
    }

    @Override
    public void doUpdate(String table, String condition, String... updates)
            throws SQLException {
        // Our HashMap will store column and value
        HashMap<String, String> parsedUpdates = new HashMap<>();

        for (String update : updates) {
            // Checks if insert syntax is correct
            if (!update.contains("=")) {
                throw new SQLException("Syntax error: Inserts have to follow the next syntax 'columnName=value'");
            }
            // Inserts follows column=value so we have to store each
            parsedUpdates.put(update.split("=")[0].trim(), update.split("=")[1].trim());
        }

        // Creating sentence
        String sql = "UPDATE " + table + " SET ";
        StringBuilder dummy = new StringBuilder();
        // Since with lamda we need to scope a finally, we just create another variable
        Set<Entry<String, String>> entrySet = parsedUpdates.entrySet();

        entrySet.forEach((entry) -> {
            dummy.append(entry.getKey()).append(" = ").append(entry.getValue()).append(", ");
        });

        // Deleting ', '
        dummy.delete(dummy.length() - 2, dummy.length());
        sql += dummy.toString();

        if (!condition.equals("")) {
            sql += " WHERE " + condition;
        }

        executeQuery(true, sql);
    }

    @Override
    public void doDelete(String table, String condition) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE " + condition;
        executeQuery(true, sql);
    }

    @Override
    public void createTable(String table, String... columns) throws SQLException {
        String sql = "CREATE TABLE " + table + " (";
        sql += String.join(", ", columns) + ")";
        executeQuery(true, sql);
    }

    @Override
    public void createDatabase(String database) throws SQLException {
        String sql = "CREATE DATABASE " + database;
        executeQuery(true, sql);
    }

    @Override
    public void dropTable(String... tables) throws SQLException {
        String sql;
        for (String table : tables) {
            sql = "DROP TABLE " + table;
            executeQuery(true, sql);
        }
    }

    @Override
    public void dropDatabase(String... databases) throws SQLException {
        String sql;
        for (String database : databases) {
            sql = "DROP DATABASE " + database;
            executeQuery(true, sql);
        }
    }

}
