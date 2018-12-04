package net.lecuay.dbmanager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

/**
 * TestDBManager
 */
public class TestDBManager {

    // User, password and database will be System Enviorments for better security
    String username = System.getenv("DB_USERNAME");
    String password = System.getenv("DB_PASSWORD");
    String database = System.getenv("DB_NAME");
    String host = System.getenv("DB_HOST");
    String port = System.getenv("DB_PORT");

    @Test
    public void checkEnvVariables() {
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
            () -> assertEquals(false, conn.isSSLMode(), "SSL has incorrect value. -> " + conn.isSSLMode())
        );

        // Default constructor
        DBManager conn2 = new DBMySQL(username, password);
        assertAll(
            () -> assertEquals(username, conn2.getUser(), "Username has incorrect value. -> " + conn2.getUser()),
            () -> assertEquals(password, conn2.password, "Password has incorrect value. -> " + conn2.password),
            () -> assertEquals("localhost", conn2.getHost(), "Host has incorrect value. -> " + conn2.getHost()),
            () -> assertEquals("", conn2.getDBName(), "Database has incorrect value. -> " + conn2.getDBName()),
            () -> assertEquals(3306, conn2.getPort(), "Port has incorrect value. -> " + conn2.getPort()),
            () -> assertEquals(false, conn2.isSSLMode(), "SSL has incorrect value. -> " + conn2.isSSLMode())
        );

        // Constructor with JDBC
        try {
            DBManager conn3 = new DBMySQL(username, password, "jdbc:mysql://localhost:3306/proyecto");
            assertAll(
                () -> assertEquals(username, conn3.getUser(), "Username has incorrect value. -> " + conn3.getUser()),
                () -> assertEquals(password, conn3.password, "Password has incorrect value. -> " + conn3.password),
                () -> assertEquals("localhost", conn3.getHost(), "Host has incorrect value. -> " + conn3.getHost()),
                () -> assertEquals("proyecto", conn3.getDBName(), "Database has incorrect value. -> " + conn3.getDBName()),
                () -> assertEquals(3306, conn3.getPort(), "Port has incorrect value. -> " + conn3.getPort()),
                () -> assertEquals(false, conn3.isSSLMode(), "SSL has incorrect value. -> " + conn3.isSSLMode())
            );
        } catch (URISyntaxException e) {
			fail("Exception caugth! -> " + e.getMessage());
        }
        
        // Constructor with URI
        try {
            DBManager conn4 = new DBMySQL(new URI("mysql://"+username+":"+password+"@"+"localhost:3306/proyecto"));
            assertAll(
                () -> assertEquals(username, conn4.getUser(), "Username has incorrect value. -> " + conn4.getUser()),
                () -> assertEquals(password, conn4.password, "Password has incorrect value. -> " + conn4.password),
                () -> assertEquals("localhost", conn4.getHost(), "Host has incorrect value. -> " + conn4.getHost()),
                () -> assertEquals("proyecto", conn4.getDBName(), "Database has incorrect value. -> " + conn4.getDBName()),
                () -> assertEquals(3306, conn4.getPort(), "Port has incorrect value. -> " + conn4.getPort()),
                () -> assertEquals(false, conn4.isSSLMode(), "SSL has incorrect value. -> " + conn4.isSSLMode())
            );
        } catch (URISyntaxException e) {
            fail("Exception caugth! -> " + e.getMessage());
        }
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
            () -> assertEquals(true, conn.isSSLMode(), "SSL has incorrect value. -> " + conn.isSSLMode())
        );
    }

}