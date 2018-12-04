# DBManager for Java

[![JDKVersion](https://img.shields.io/badge/JDK-8+-red.svg)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[![JREVersion](https://img.shields.io/badge/JRE-8+-red.svg)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

A library for Database Management that makes our development easier and a GUI that makes our experience more comfy.

## DBManager 1.x

- [DBManagement Systems Supported](#compatibility)
- [Examples](#creating-dbmanager-object)

## Compatibility

DBManager has compatibility with the following drivers' version:

- **MySQL**:
  - Driver: `com.mysql.cj.jdbc.Driver`
  - Version: `Connector/J 8.0`. Tested with `8.0.12` for Maven repositories.

---

>SQL Server support has been disabled for now due to problems passing JUnit test sucessfully.

- **SQL Server**:
  - Driver: `com.microsoft.sqlserver.jdbc.SQLServerDriver`
  - Version: `Microsoft JDBC Driver for SQL Server 7.1.0`. Tested with `7.1.0.jre10-preview` and `7.1.0.jre8` for Maven repositories.

---
  
- **PostgreSQL**:
  - Driver: `org.postgres.Driver`.
  - Version: `PostgreSQL JDBC Driver JDBC 4.2`. Tested with `42.2.5` for Maven repositories.

## Features

This library manages everything by itself opening and closing connections, commits, cursors...  
But it also allows the user to make specific queries.

Principal functions are:

- `doExecute(String sql)`: Allows you to execute either a `file.sql` or SQL code itself.
- `doSelect(String table, String condition, String... columns)`: It makes a select based on parameters and return the result as an `ArrayList`.
- `doInsert(String table, String... inserts)`: Inserts values into a table by the following syntax -> `doInsert("table", "column=value", "column2=value2", ...)`.
- `doUpdate(String table, String condition, String... updates)`: The syntax is the same as `doInsert`, but in this case is updating columns from rows.
- `doDelete(String table, String condition)`: Deletes a row or rows where *condition* is true.
- `createTable(String table, String columns)`: Creates a table with the given columns (id INTEGER PRIMARY KEY, ...).
- `createDatabase(String database)`: Creates a Database.
- `dropTable(String tables...)`: Drops tables in parameters.
- `dropDatabase(String databases...)`: Drop databases in parameters.

## Creating DBManager Object

Creating a DBManager object can be done by any of the [constructors](###constructors) featuring our Management Systems suppoted.

**Example with MySQL:**

>**Note:** For this example we are using DBManager(user, password).  
>This constructor creates an empty connection that has to be related to a Database by `.setDBName(String database)`

```java
import java.sql.SQLException;
import net.lecuay.DBManager;
import net.lecuay.DBMySQL;

public class Whatever {

    public static void main(String[] args)
    throws SQLException {
        DBManager connection = new DBMySQL("username", "password");
        connection.setDBName("test_database");

        // .showSelect(table, condition, columns...);
        connection.showSelect("test_table", "", "*");
    }

}
```

**Example with PostgreSQL:**

>**Note:** For this example we are using DBManager with specific paremeters (user, password, host, DBName, port, sslmode).

```java
import java.sql.SQLException;
import net.lecuay.DBManager;
import net.lecuay.DBPostgreSQL;

public class WhateverPostgres {

    public static void main(String[] args)
    throws SQLException {
        DBManager connection = new DBPostgreSQL("username",
                                                "password",
                                                "localhost",
                                                "test_database",
                                                5432,
                                                false);
        // You can also add propeties to the JDBC by .addProperty
        connection.addProperty("autosave", "always");
        // .showSelect(table, condition, columns...);
        connection.showSelect("test_table", "", "*");
    }

}
```

**Using doSelect:**

```java
public class Example {

    public static void main(String[] args)
    throws SQLException {
        DBManager connection = new DBPostgreSQL("root", "root");
        // Connecting to the database
        connection.setDBName("test_database");

        /*
        We have this table
        CREATE TABLE test (
            id INT PRIMARY KEY,
            name VARCHAR(150) NOT NULL,
            username NVARCHAR(50),
            registered DATE
        );
        */
        // .doSelect(String table, String condition, String... columns)
        connection.doSelect("test", "name LIKE '%Cuay%'", "id", "username", "registered").forEach(entry -> {
            System.out.println(String.format("ID: %s\nUser: %s\n", entry.get("id"), entry.get("username")));
        });
    }

}
```

**Using doInsert:**

```java
public class Example {

    public static void main(String[] args)
    throws SQLException {
        DBManager connection = new DBMySQL("root", "root");
        // Connecting to the database
        connection.setDBName("test_database");
        // Adding properties
        connection.addProperty("allowPublicKeyRetrieval", "true");

        /*
        We have this table
        CREATE TABLE test (
            id INT PRIMARY KEY,
            name VARCHAR(150) NOT NULL,
            username NVARCHAR(50),
            registered DATE
        );
        */
        connection.doInsert("test", "id=1",
                                     "name='Cuayteron'",
                                     "username='LeCuay'",
                                     "registered='1998-07-14'");
    }

}
```

### Constructors

- `DBManager(user, password, host, DBName, port, sslmode)`: Creates a specific connection with the given parameters.
- `DBManager(user, password, JDBC)`: Creates a connection with a custom JDBC.
- `DBManager(URI uri)`: Creates a connection with the given URI.

## To-Do List

- [ ] Add more functions.
- [x] Add compatibility with MySQL.
- [x] Add compatibility with SQL Server.
- [x] Add compatibility with PostgreSQL.
- [ ] Add compatibility with SQLite3.
- [ ] Create User Interface.
- [ ] Test complex files SQL.