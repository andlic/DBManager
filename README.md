# DBManager

[![JDKVersion](https://img.shields.io/badge/JDK-8+-red.svg)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[![JREVersion](https://img.shields.io/badge/JRE-8+-red.svg)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

A package for Database Management that makes our development easier.

It also has a graphical interface for UI and UX.

## Compatibility

DBManager has compatibility with the following drivers' version:

- **MySQL**:
  - Driver: `com.mysql.cj.jdbc.Driver`
  - Version: `Connector/J 8.0`. Tested with `8.0.12` for Maven repositories.
- **SQL Server**:
  - Driver: `com.microsoft.sqlserver.jdbc.SQLServerDriver`
  - Version: `Microsoft JDBC Driver for SQL Server 7.1.0`. Tested with `7.1.0.jre10-preview` for Maven repositories.
- **PostgreSQL**:
  - Driver: `org.postgres.Driver`.
  - Version: `PostgreSQL JDBC Driver JDBC 4.2`. Tested with `42.2.5` for Maven repositories.

## Features

This library manages everything by itself opening and closing connections, commits, cursors...  
But it also allows the user to make specific queries.

Some functions are:

- `doExecute(String sql)`: Allows you to execute either a `file.sql` or SQL code itself.
- `doSelect(String table, String condition, String... columns)`: It makes a select based on parameters and return the result as an `ArrayList`.
- `doInsert(String table, String inserts)`: Inserts values into a table by the following syntax -> `doInsert("table", "column=value", "column2=value2", ...)`.

## To-Do List

- [ ] Add more functions.
- [ ] Add compatibility with PostgreSQL.
- [ ] Add compatibility with SQLite3.
- [ ] Create User Interface.
- [ ] Test complex files SQL.