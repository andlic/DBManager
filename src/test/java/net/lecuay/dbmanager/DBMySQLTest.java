package net.lecuay.dbmanager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import net.lecuay.dbmanager.DBManager.DBType;

/**
 * TestDBManager
 */
public class DBMySQLTest {

    // User, password and database will be System Enviorments for better security
    String username = System.getenv("DB_USERNAME");
    String password = System.getenv("DB_PASSWORD");
    String database = System.getenv("DB_NAME");
    String host = System.getenv("DB_HOST");
    String port = System.getenv("DB_PORT");

    @Test
    public void checkEnvTest() {
        // Looking for for null values
        assertAll(
            () -> assertNotNull(username, "Username is null."),
            () -> assertNotNull(password, "Password is null."),
            () -> assertNotNull(database, "Database Name is null."),
            () -> assertNotNull(host, "Host is null."),
            () -> assertNotNull(port, "Port is null.")
        );
    }

    @Test
    public void constructorsWithoutSSLTest() {
        // Full constructor
        DBManager conn = new DBMySQL(username, password, host, database, Integer.parseInt(port), false);
        assertAll(
            () -> assertEquals(username, conn.getUser(), "Username has incorrect value. -> " + conn.getUser()),
            () -> assertEquals(password, conn.password, "Password has incorrect value. -> " + conn.password),
            () -> assertEquals(host, conn.getHost(), "Host has incorrect value. -> " + conn.getHost()),
            () -> assertEquals(database, conn.getDBName(), "Database has incorrect value. -> " + conn.getDBName()),
            () -> assertEquals(Integer.parseInt(port), conn.getPort(), "Port has incorrect value. -> " + conn.getPort()),
            () -> assertEquals(false, conn.isSSLMode(), "SSL has incorrect value. -> " + conn.isSSLMode()),
            () -> assertEquals(DBType.MYSQL, conn.getConexType(), "DBType has incorrect value -> " + conn.getConexType().name())
        );

        // Default constructor
        DBManager conn2 = new DBMySQL(username, password);
        assertAll(
            () -> assertEquals(username, conn2.getUser(), "Username has incorrect value. -> " + conn2.getUser()),
            () -> assertEquals(password, conn2.password, "Password has incorrect value. -> " + conn2.password),
            () -> assertEquals("localhost", conn2.getHost(), "Host has incorrect value. -> " + conn2.getHost()),
            () -> assertEquals("", conn2.getDBName(), "Database has incorrect value. -> " + conn2.getDBName()),
            () -> assertEquals(3306, conn2.getPort(), "Port has incorrect value. -> " + conn2.getPort()),
            () -> assertEquals(false, conn2.isSSLMode(), "SSL has incorrect value. -> " + conn2.isSSLMode()),
            () -> assertEquals(DBType.MYSQL, conn2.getConexType(), "DBType has incorrect value -> " + conn.getConexType().name())
        );

        // Constructor with JDBC
        try {
            DBManager conn3 = new DBMySQL(username, password, "jdbc:mysql://localhost:3306/" + database);
            assertAll(
                () -> assertEquals(username, conn3.getUser(), "Username has incorrect value. -> " + conn3.getUser()),
                () -> assertEquals(password, conn3.password, "Password has incorrect value. -> " + conn3.password),
                () -> assertEquals("localhost", conn3.getHost(), "Host has incorrect value. -> " + conn3.getHost()),
                () -> assertEquals(database, conn3.getDBName(), "Database has incorrect value. -> " + conn3.getDBName()),
                () -> assertEquals(3306, conn3.getPort(), "Port has incorrect value. -> " + conn3.getPort()),
                () -> assertEquals(false, conn3.isSSLMode(), "SSL has incorrect value. -> " + conn3.isSSLMode()),
                () -> assertEquals(DBType.MYSQL, conn3.getConexType(), "DBType has incorrect value -> " + conn.getConexType().name())
            );
        } catch (URISyntaxException e) {
			fail("Exception caugth! -> " + e.getMessage());
        }
        
        // Constructor with URI
        try {
            DBManager conn4 = new DBMySQL(new URI("mysql://"+username+":"+password+"@"+"localhost:3306/" + database));
            assertAll(
                () -> assertEquals(username, conn4.getUser(), "Username has incorrect value. -> " + conn4.getUser()),
                () -> assertEquals(password, conn4.password, "Password has incorrect value. -> " + conn4.password),
                () -> assertEquals("localhost", conn4.getHost(), "Host has incorrect value. -> " + conn4.getHost()),
                () -> assertEquals(database, conn4.getDBName(), "Database has incorrect value. -> " + conn4.getDBName()),
                () -> assertEquals(3306, conn4.getPort(), "Port has incorrect value. -> " + conn4.getPort()),
                () -> assertEquals(false, conn4.isSSLMode(), "SSL has incorrect value. -> " + conn4.isSSLMode()),
                () -> assertEquals(DBType.MYSQL, conn4.getConexType(), "DBType has incorrect value -> " + conn.getConexType().name())
            );
        } catch (URISyntaxException e) {
            fail("Exception caugth! -> " + e.getMessage());
        }

        // Getting exceptions
        Exception e = assertThrows(URISyntaxException.class, () -> {
            new DBMySQL(username, password, "jdbc:://localhost:3306/proyecto");
        });
        assertEquals(URISyntaxException.class, e.getClass());
    }

    @Test
    public void constructorsWithSSLTest() {
        // Full constructor
        DBManager conn = new DBMySQL(username, password, host, database, Integer.parseInt(port), true);
        assertAll(
            () -> assertEquals(username, conn.getUser(), "Username has incorrect value. -> " + conn.getUser()),
            () -> assertEquals(password, conn.password, "Password has incorrect value. -> " + conn.password),
            () -> assertEquals(host, conn.getHost(), "Host has incorrect value. -> " + conn.getHost()),
            () -> assertEquals(database, conn.getDBName(), "Database has incorrect value. -> " + conn.getDBName()),
            () -> assertEquals(Integer.parseInt(port), conn.getPort(), "Port has incorrect value. -> " + conn.getPort()),
            () -> assertEquals(true, conn.isSSLMode(), "SSL has incorrect value. -> " + conn.isSSLMode()),
            () -> assertEquals(DBType.MYSQL, conn.getConexType(), "DBType has incorrect value -> " + conn.getConexType().name())
        );
    }

    @Test
    public void settersTest() {
        // Full empty constructor
        DBManager conn = new DBMySQL("", "", "", "", 0, true);
        conn.setUser(username);
        conn.setPassword(password);
        conn.setHost(host);
        conn.setDBName(database);
        conn.setPort(Integer.parseInt(port));
        conn.setSSLMode(false);
        assertAll(
            () -> assertEquals(username, conn.getUser(), "Username has incorrect value. -> " + conn.getUser()),
            () -> assertEquals(password, conn.password, "Password has incorrect value. -> " + conn.password),
            () -> assertEquals(host, conn.getHost(), "Host has incorrect value. -> " + conn.getHost()),
            () -> assertEquals(database, conn.getDBName(), "Database has incorrect value. -> " + conn.getDBName()),
            () -> assertEquals(Integer.parseInt(port), conn.getPort(), "Port has incorrect value. -> " + conn.getPort()),
            () -> assertEquals(false, conn.isSSLMode(), "SSL has incorrect value. -> " + conn.isSSLMode()),
            () -> assertEquals(DBType.MYSQL, conn.getConexType(), "DBType has incorrect value -> " + conn.getConexType().name())
        );
    }

    @Test
    public void connectionTest() {
        assertDoesNotThrow(() -> {
            DBManager conn = new DBMySQL(username, password, host, database, Integer.parseInt(port), false);
            // Resolving standar GMT Timezone problem
            conn.addProperty("serverTimezone", "UTC");
            // Executing problematic methods
            conn.doConnect();
            conn.doClose();
        });
        assertDoesNotThrow(() -> {
            DBManager conn = new DBMySQL(username, password, host, database, Integer.parseInt(port), false);
            // Resolving standar GMT Timezone problem
            conn.addProperty("serverTimezone", "UTC");
            conn.doConnect();
            conn.doClose();
            conn.doClose();
        });
    }
    
}