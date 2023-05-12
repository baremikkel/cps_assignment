package com.cps_assignment.backend;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Tioman99
 * This is a cheap solution for removing duplicates in the exchangerates table
 */

public class CleanUp {
    public static void main(String[] args) {
        try {
            DBCommunicator db = new DBCommunicator();
            String currenciesQuery = "SELECT currencyid FROM currencies ORDER by currencyid";
            String exchangeQuery = "SELECT exchangeid, lastexchange, exchangevalue FROM exchangerates ";
            String exchangeOrder = " ORDER by exchangeid DESC";
            ResultSet set1 = db.ScrollableQuery(currenciesQuery);
            int deletedCount = 0;
            System.out.println("Commencing cleanup...");
            while (set1.next()) {
                int currencyid = set1.getInt("currencyid");
                ResultSet set2 = db.ScrollableQuery(exchangeQuery + "WHERE currencyid=" + currencyid + exchangeOrder);
                ResultSet set3 = db.ScrollableQuery(exchangeQuery + "WHERE currencyid=" + currencyid + exchangeOrder);
                while (set2.next()) {
                    int id1 = set2.getInt("exchangeid");
                    String timestamp1 = set2.getString("lastexchange");
                    double value1 = set2.getDouble("exchangevalue");
                    while (set3.next()) {
                        int id2 = set3.getInt("exchangeid");
                        String timestamp2 = set3.getString("lastexchange");
                        double value2 = set3.getDouble("exchangevalue");
                        if (id1 != id2 && timestamp1.equals(timestamp2) && value1 == value2) {
                            System.out.println("Deleted data point w. id: " + id2);
                            deletedCount++;
                            break;
                        }
                    }
                    set3.beforeFirst();
                }
                set2.beforeFirst();
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
