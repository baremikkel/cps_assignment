package com.cps_assignment.backend;

import java.sql.*;

/**
 * @author Baremikkel, Tiomann99
 * Connects to database and allows the system to write/read from said.
 */

public class DBCommunicator {
    private Connection conn = null;
    private String dbname = "cps";
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String user = "postgres";
    private String password = "Olsen2001";

    protected static DBCommunicator db;

    public DBCommunicator() throws SQLException { //When initializing the communicator it connects to the db.
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }
    public void updateTable(String cmd){ //Crud command Update via parameter.
        try{
            PreparedStatement insert = conn.prepareStatement(cmd);
            insert.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public ResultSet selectFromTable(String column, String table) { //Collects data from chosen table.
        ResultSet set = null;
        try {
            set = conn.createStatement().executeQuery("SELECT "+column+" FROM " + table);
        } catch (SQLException e) {
            System.out.println("Here is why you can't get data: " + e.getMessage() + " Good luck you need it!");
        }
        return set;
    }

    public ResultSet selectFromTableWithCondition(String column, String table, String condition, String currency) {
        //Same as the above function but allows for WHERE statement.
        ResultSet set = null;
        try {
            set = conn.createStatement().executeQuery("SELECT "+column+" FROM " + table + condition + currency +"'");

        } catch (SQLException e) {
            System.out.println("Here is why you can't get data: " + e.getMessage() + " Good luck you need it!");
        }
        return set;
    }

    protected static DBCommunicator getDatabase() throws SQLException {
        //Singleton as we only want to connect to the db once.
        if (db == null) {
            db = new DBCommunicator();
        }
        return db;
    }
    //The following method was intended to automate the process of setting up the database and seeding it.
    protected void integrateDataBase() {
        boolean dbExists;
        String seedPath = "backend/src/main/java/com/cps_assignment/backend/assets/sql/";
        String createFunction = "CREATE OR REPLACE FUNCTION checkExistence(dbname text)\n" +
                "RETURNS boolean AS $$\n" +
                "BEGIN\n" +
                "RETURN EXISTS (SELECT FROM pg_catalog.pg_database\n" +
                "WHERE datname = "+ dbname +");\n" +
                "END;\n" +
                "$$ LANGUAGE plpgsql;";

        String queryFunction = "{ ? = call checkExistence(?) }";

        try { //Tries to create a sql function that lets us check for already existing databases.
            Statement stmt = conn.createStatement();
            stmt.executeQuery(createFunction);
        } catch (SQLException e) {
            throw new RuntimeException("Could not create PL/pgSQL function. >:(\n" + e.getMessage());
        }

        try { //tries to find a database named the same as dbName
            CallableStatement stmt = conn.prepareCall(queryFunction);
            stmt.setString(1, dbname);
            stmt.registerOutParameter(2, Types.BOOLEAN);
            stmt.execute();
            dbExists = stmt.getBoolean(2);
        } catch (SQLException e) {
            throw new RuntimeException("Could not call PL/pgSQL function. >:(\n" + e.getMessage());
        }

        if (dbExists == false) { //If there was no database with that name, we create one.
            String createDbQuery = "CREATE DATABASE " + dbname;

            try {
                Class.forName("org.postgresql.Driver"); //Loads database connection driver.
                conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(createDbQuery);
                System.out.println("Database named "+ dbname +" was created successfully!");

            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Could not load db connection drivvers. >:(\n" + e.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException("Could not connect. >:(\n" + e.getMessage());
            }
        } else {
            System.out.println("You already have a database named " + dbname +"!");
        }
        //Now we have the database created, we need to create the tables, pog champ almost there!
        try {
            conn.createStatement().executeQuery("CREATE TABLE currencies (currencyId SERIAL PRIMARY KEY, currencyName VARCHAR(255) UNIQUE NOT NULL, currencySymbol VARCHAR(50) UNIQUE NOT NULL);");
            conn.createStatement().executeQuery("CREATE TABLE exchangeRates (exchangeId SERIAL PRIMARY KEY, currencyId INTEGER NOT NULL REFERENCES currencies(currencyId), exchangeValue DECIMAL NOT NULL, lastExchange VARCHAR(50) NOT NULL\n);");
            System.out.println("Both tables have been created!");
        } catch (SQLException e) {
            throw new RuntimeException("Could not create tables. >:(\n" + e.getMessage());
        }
        //Still missing actual seeding methods. Could be done in the future.
    }
}
