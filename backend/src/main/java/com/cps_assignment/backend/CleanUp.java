package com.cps_assignment.backend;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tioman99
 * This is a cheap solution for removing duplicates in the exchangerates table
 */

public class CleanUp {
    public static void main(String[] args) {
        try {
            DBCommunicator db = new DBCommunicator();
            ResultSet set1 = db.SelectFromTable("*","exchangerates");
            ResultSet set2 = db.SelectFromTable("*","exchangerates");
            int deletedCount = 0;
            while (set1.next()) {
                int id1 = set1.getInt("exchangeid");
                int value1 = set1.getInt("exchangevalue");
                while (set2.next()) {
                    int id2 = set1.getInt("exchangeid");
                    int value2 = set1.getInt("exchangevalue");
                    if (id1 != id2 && value1 == value2) {
                        db.UpdateTable("DELETE FROM postgres WHERE id = " + id1);
                        System.out.println("Deleted duplicate row w. exchangeid: " + id1);
                        deletedCount++;
                    }
                }
                set2.beforeFirst(); //resets the inner loop
            }
            if (deletedCount == 0) {
                System.out.println("No duplicates where found :)");
            } else {
                System.out.println("Deleted a total of " + deletedCount + " rows.");
            }
        } catch (SQLException e) {
            System.out.println("Problems with database setup :(");
            throw new RuntimeException(e);
        }
    }
}
