package net.lecuay.dbmanager;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static java.lang.System.lineSeparator;

/**
 * Class created as instance of {@link DBManager} to manage SQLite3 connections.
 *
 * @author LeCuay
 * @version 0.1 - Beta
 */
public class DBSQLite extends DBManager {

    /**
     * The path to the SQLite database.
     */
    private String databasePath;

    /**
     * Creates a connection to the SQLite file declared in path.
     * 
     * @param path The path to the SQLite file.
     */
    public DBSQLite(String databasePath) {
        // Since we just one the actual name of the file, we get the very last item from
        // a path (/usr/path/etc.db)
        super("", "", null, databasePath.split(lineSeparator())[databasePath.split(lineSeparator()).length - 1],
                DBType.SQLITE3, Integer.MIN_VALUE, false);
        this.databasePath = databasePath;
    }

    @Override
    protected void doConnect() throws SQLException {
        File dblite = new File(databasePath);
        
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.err.println("Driver not found! -> " + e.getMessage());
        }

        if (!dblite.exists() && !dblite.isFile())
            throw new SQLException("Database doesn't exists.");

        if (JDBC.equals("")) {
            JDBC = "jdbc:sqlite:" + DBName;
        }

        connection = DriverManager.getConnection(JDBC);
    }

    @Override
    public ArrayList<LinkedHashMap<String, String>> doSelect(String table, String condition, String... columns)
            throws SQLException {
        return null;
    }

    @Override
    public void doInsert(String table, String... inserts) throws SQLException {

    }

    @Override
    public void doUpdate(String table, String condition, String... updates) throws SQLException {

    }

    @Override
    public void doDelete(String table, String condition) throws SQLException {

    }

    @Override
    public void createTable(String table, String... columns) throws SQLException {

    }

    @Override
    public void createDatabase(String database) throws SQLException {
        File dblite = new File(database);

        if (dblite.isFile() && dblite.exists()) {
            throw new SQLException("The file SQLite already exists.");
        } else {
            try {
                dblite.createNewFile();
            } catch (IOException e) {
                System.err.println("Unable to create the file -> " + e.getMessage());
            }
        }
    }

    @Override
    public void dropTable(String... tables) throws SQLException {

    }

    @Override
    public void dropDatabase(String... databases) throws SQLException {
        File dblite;
        for (String database : databases) {
            dblite = new File(database);
            if (dblite.isFile() && dblite.exists()) {
                dblite.delete();
            }
        }
    }

    /**
     * 
     * 
     * @param parentPath
     * @param fileIdentifier
     * @throws SQLException
     */
    public void dropDatabase(String parentPath, String fileIdentifier) throws SQLException {
        File parent = new File(parentPath);

        if (parent.exists() && parent.isDirectory()) {
            for (File dblite : parent.listFiles()) {
                if (dblite.exists() && dblite.isFile()) {
                    if (dblite.getAbsolutePath().endsWith(fileIdentifier)) {
                        dblite.delete();
                    }
                }
            }
        } else {
            throw new SQLException("Parent path is not a folder.");
        }
    }

}