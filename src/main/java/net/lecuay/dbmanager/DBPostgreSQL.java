package net.lecuay.dbmanager;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class created as instance of {@link DBManager} to manage PostgreSQL
 * connections.
 *
 * @author LeCuay
 * @version 0.7.1
 * @see DBManager
 */
public class DBPostgreSQL extends DBManager {

    /**
     * Creates a connection with the parameters given.
     *
     * @param user     The user used for the connection.
     * @param password The password used for the connecion.
     * @param host     The host where our Database is hosted.
     * @param DBName   The database name we want access to.
     * @param port     The port used for the connection.
     * @param sslmode  Declares if SSL is required.
     */
    public DBPostgreSQL(String user, String password, String host, String DBName, int port, boolean sslmode) {
        super(user, password, host, DBName, DBType.POSTGRESQL, port, sslmode);
    }

    /**
     * Creates a connection based on a given <b>JDBC</b>.
     *
     * @param user     The user used for the connection.
     * @param password The password used for the connection.
     * @param JDBC     The customized JDBC given.
     * @throws URISyntaxException
     */
    public DBPostgreSQL(String user, String password, String JDBC) throws URISyntaxException {
        super(user, password, JDBC);
        this.conexType = DBType.POSTGRESQL;
    }

    /**
     * Creates a connection based on a given URI.
     *
     * @param uri The object {@link java.net.URI} used for the connection.
     */
    public DBPostgreSQL(URI uri) {
        super(uri);
        this.conexType = DBType.POSTGRESQL;
    }

    /**
     * Creates a connection with the following <i>default values</i>:
     * <ul>
     * <li>Scheme = null</li>
     * <li>host = localhost</li>
     * <li>port = 5432</li>
     * <li>ssl = non-required</li>
     * </ul>
     * It also creates the JDBC with the given parameters.<br>
     * This methods requires {@code setDBName(String DBName)} for later connection
     * to a Database.<br>
     * 
     * <pre>
     * DBPostgreSQL conex = DBPostgreSQL("username", "password");
     * conex.setDBName("sampleDatabase");
     * </pre>
     *
     * @param user     The user used for the connection.
     * @param password The password used for the connection.
     */
    public DBPostgreSQL(String user, String password) {
        super(user, password, "localhost", "", DBType.POSTGRESQL, 5432, false);
    }

    @Override
    protected void doConnect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found! -> " + e.getMessage());
        }

        if (JDBC.equals("")) {
            JDBC = "jdbc:postgresql://" + host + ":" + port + "/" + DBName;
            // If SSL is required JDBC will be updated.
            if (sslmode) {
                properties.setProperty("ssl", "true");
                properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
            } else {
                properties.setProperty("ssl", "false");
            }
        }

        connection = DriverManager.getConnection(JDBC, properties);
    }

    @Override
    public ArrayList<LinkedHashMap<String, String>> doSelect(String table, String condition, String... columns)
            throws SQLException {
        StringBuilder codeSQL = new StringBuilder("SELECT ");

        codeSQL.append(String.join(", ", columns));
        codeSQL.append(" FROM ").append("\"").append(table).append("\"");

        if (!(condition.isEmpty() || condition.trim().isEmpty())) {
            codeSQL.append(" WHERE ").append(condition);
        }

        return executeQueryWithReturn(codeSQL.toString()).get(0);
    }

    @Override
    public void doInsert(String table, String... inserts) throws SQLException {
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
        String sql = "INSERT INTO \"" + table + "\"(\""; // Also we need to get case-sensitive so we use '"'
        sql += String.join("\", \"", parsedInserts.keySet().toArray(new String[] {})) + "\")";
        sql += " VALUES (" + String.join(", ", parsedInserts.values().toArray(new String[] {})) + ")";

        executeQuery(true, sql);
    }

    @Override
    public void doUpdate(String table, String condition, String... updates) throws SQLException {
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
        String sql = "UPDATE \"" + table + "\" SET ";
        StringBuilder dummy = new StringBuilder();
        // Since with lamda we need to scope a finally, we just create another variable
        Set<Entry<String, String>> entrySet = parsedUpdates.entrySet();

        entrySet.forEach((entry) -> {
            dummy.append("\"").append(entry.getKey()).append("\" = ").append(entry.getValue()).append(", ");
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
        String sql = "DELETE FROM \"" + table + "\" WHERE " + condition;
        executeQuery(true, sql);
    }

    @Override
    public void createTable(String table, String... columns) throws SQLException {
        String sql = "CREATE TABLE \"" + table + "\" (";
        sql += String.join(", ", columns) + ")";
        executeQuery(true, sql);
    }

    @Override
    public void createDatabase(String database) throws SQLException {
        String sql = "CREATE DATABASE \"" + database + "\"";
        executeQuery(true, sql);
    }

    @Override
    public void dropTable(String... tables) throws SQLException {
        String sql = "DROP TABLE IF EXISTS \"" + String.join("\", \"", tables) + "\"";
        executeQuery(true, sql);
    }

    @Override
    public void dropDatabase(String... databases) throws SQLException {
        String sql;
        for (String db : databases) {
            sql = "DROP DATABASE IF EXISTS \"" + db + "\"";
            executeQuery(true, sql);
        }
    }

    @Override
    public String[] getColumnsInfo(String table) throws SQLException {
        doConnect();

        ResultSet result;
        ArrayList<String> columnInfo = new ArrayList<>();
        ArrayList<String> primaryKeys = new ArrayList<>();

        // Getting all columns or just those in database
        if (DBName.equals("")) {
            result = connection.getMetaData().getColumns(null, null, "%" + table + "%", null);
        } else {
            result = connection.getMetaData().getColumns(DBName, null, "%" + table + "%", null);
        }

        executeQueryWithReturn("select column_name, constraint_name " + "from information_schema.key_column_usage "
                + "where table_catalog='aed' and table_name='clientes_premium';").get(0).forEach(entry -> {
                    primaryKeys.add(entry.get("column_name"));
                });

        while (result.next()) {
            columnInfo.add(String.format("%s %s(%s%s)%s%s", result.getString(4),
                    result.getString(6).contains("bpchar") ? "character" : result.getString(6), result.getString(7),
                    result.getString(9) != null ? ", " + result.getString(9) : "",
                    primaryKeys.contains(result.getString(4)) ? " PRIMARY KEY" : "",
                    result.getString(11).equals("0") ? " NOT NULL" : ""));
        }

        doClose(result);
        return columnInfo.toArray(new String[] {});
    }

    @Override
    public void addColumn(String table, String columnName, String columnDefinition) throws SQLException {
        String codeSQL = String.format("ALTER TABLE \"%s\" ADD COLUMN \"%s\" %s", table, columnName, columnDefinition);
        executeQuery(true, codeSQL);
    }

}
