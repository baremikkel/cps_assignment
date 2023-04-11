package com.cps_assignment.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCommunicator {
    private Connection conn = null;
    protected static DBCommunicator db;

    public DBCommunicator() throws SQLException {
        //tried to make this: https://www.odbms.org/2018/12/how-to-create-a-database-using-jdbc-2/
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Olsen2001");
            System.out.println("Connected");
        } catch (SQLException exception) {
            System.out.println("fuck you: " + exception.getMessage());
        } /*finally {
            conn.close();
        }*/

    }
    public void UpdateTable(String cmd){
        try{
            PreparedStatement insert = conn.prepareStatement(cmd);
            insert.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    protected static DBCommunicator getDatabase() throws SQLException {
        if (db == null) {
            db = new DBCommunicator();
        }
        return db;
    }
}
