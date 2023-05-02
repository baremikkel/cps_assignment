package com.cps_assignment.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class JSConnection { //Prøver at skabe en connection

   // @CrossOrigin(origins = "localhost:5500")
    @GetMapping("/exchangedates")
    public SendData getDates() throws SQLException {
        SendData data = new SendData();
        DBCommunicator db = DBCommunicator.getDatabase();
        StringBuilder message = new StringBuilder();
        try {
            ResultSet values = db.SelectFromTableWithCondition("lastexchange", "exchangerates", " INNER JOIN currencies c ON c.currencyid = exchangerates.currencyid WHERE currencysymbol = '", "DKK");
            //ResultSet values = db.SelectFromTable("lastexchange", "exchangerates");
            while (values.next()) {
                message.append(values.getString("lastexchange"));
                message.append(" ");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        data.setMessage(message.toString());
        return data;
    }

    @GetMapping("/exchangevalues")
    public SendData getExchangevalues() throws SQLException {
        SendData data = new SendData();
        DBCommunicator db = DBCommunicator.getDatabase();
        StringBuilder message = new StringBuilder();
        try {
            ResultSet values = db.SelectFromTableWithCondition("exchangevalue", "exchangerates", " INNER JOIN currencies c ON c.currencyid = exchangerates.currencyid WHERE currencysymbol = '", "DKK");
            while (values.next()) {
                message.append(values.getString("exchangevalue"));
                message.append(" ");
            }
        } catch (SQLException ignored) { }
        data.setMessage(message.toString());
        return data;
    }

    @GetMapping("/symbols")
    public SendData getCurrencySymbols() throws SQLException {
        SendData data = new SendData();
        DBCommunicator db = DBCommunicator.getDatabase();
        StringBuilder message = new StringBuilder();
        try {
            ResultSet symbols = db.SelectFromTable("currencysymbol", "currencies");
            while (symbols.next()) {
                message.append(symbols.getString("currencysymbol"));
                message.append(" ");
            }
        } catch (SQLException ignored) { }
        data.setMessage(message.toString());
        return data;
    }


    public static class SendData {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
