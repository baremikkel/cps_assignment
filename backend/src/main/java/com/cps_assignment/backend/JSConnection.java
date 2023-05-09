package com.cps_assignment.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
/**
 * @author Baremikkel, Tiomann99
 * Creates a connection between the frontend JS and backend Java.
 */
@RestController
public class JSConnection { //Prøver at skabe en connection
    private String choice = "";

   // @CrossOrigin(origins = "localhost:5500")
    @GetMapping("/exchangedates")
    public SendData getDates() throws SQLException {
        SendData data = new SendData();
        DBCommunicator db = DBCommunicator.getDatabase();
        StringBuilder message = new StringBuilder();
        try {
                ResultSet values = db.SelectFromTableWithCondition("lastexchange", "exchangerates", " INNER JOIN currencies c ON c.currencyid = exchangerates.currencyid WHERE currencysymbol = '", choice);
                while (values.next()) {
                    message.append(values.getString("lastexchange"));
                    message.append(" ");
                }
                data.setMessage(message.toString());
        } catch (SQLException e) {
            e.getMessage();
        }

        return data;
    }

    @GetMapping("/exchangevalues")
    public SendData getExchangevalues() throws SQLException {
        SendData data = new SendData();
        DBCommunicator db = DBCommunicator.getDatabase();
        StringBuilder message = new StringBuilder();
        try {
            if (!Objects.equals(choice, "")) {
                ResultSet values = db.SelectFromTableWithCondition("exchangevalue", "exchangerates", " INNER JOIN currencies c ON c.currencyid = exchangerates.currencyid WHERE currencysymbol = '", choice);
                while (values.next()) {
                    message.append(values.getString("exchangevalue"));
                    message.append(" ");
            }
                data.setMessage(message.toString());
            } else
                data.setMessage("0");
        } catch (SQLException ignored) { }
        return data;
    }

    @GetMapping("/symbols")
    public SendData getCurrencySymbols() throws SQLException {
        SendData data = new SendData();
        DBCommunicator db = DBCommunicator.getDatabase();
        StringBuilder message = new StringBuilder();
        try {
            ResultSet symbols = db.SelectFromTable("currencysymbol", "currencies ORDER BY currencysymbol");
            while (symbols.next()) {
                message.append(symbols.getString("currencysymbol"));
                message.append(" ");
            }
        } catch (SQLException ignored) { }
        data.setMessage(message.toString());
        return data;
    }

    @PostMapping("/wantedSymbol")
    public String retrieveChosenOption(@RequestBody String data) {
        choice = data;
        ReadLastTimestamp.getInstance();
     return "success";
    }


    public static class SendData {
        private String message;

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() { //although it appears unused, this is vital for the front end
            return message;
        };
    }

}
