package com.cps_assignment.backend;

import java.sql.*;

public class DBCommunicator {
    private Connection conn = null;

    protected static DBCommunicator db;

    public DBCommunicator() throws SQLException { //Når man initiere communicatoren forbinder den til db.
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Olsen2001");
            System.out.println("Connected");
        } catch (SQLException exception) {
            System.out.println("fuck you: " + exception.getMessage());
        }

    }
    public void UpdateTable(String cmd){ //Crud command Update via parameter
        try{
            PreparedStatement insert = conn.prepareStatement(cmd);
            insert.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public ResultSet SelectFromTable(String column, String table) { // Henter det data som man ønsker fra hvilken tabel
        ResultSet set = null;
        try {
            set = conn.createStatement().executeQuery("SELECT "+column+" FROM " + table);
        } catch (SQLException e) {
            System.out.println("Here is why you can't get data: " + e.getMessage() + " Good luck you need it!");
        }
        return set;
    }

    public ResultSet SelectFromTableWithCondition(String column, String table, String condition, String currency) {
        // Virker på samme måde som metoden over, men har en WHERE condition i tilfældet man har brug for det
        ResultSet set = null;
        try {
            set = conn.createStatement().executeQuery("SELECT "+column+" FROM " + table + condition + currency +"'");

        } catch (SQLException e) {
            System.out.println("Here is why you can't get data: " + e.getMessage() + " Good luck you need it!");
        }
        return set;
    }

    protected static DBCommunicator getDatabase() throws SQLException { //Singleton da vi ikke har brug for at forbinde mere end en gang til db
        if (db == null) {
            db = new DBCommunicator();
        }
        return db;
    }
}
