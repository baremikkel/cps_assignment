package com.cps_assignment.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class JSConnection { //Prøver at skabe en connection

   // @CrossOrigin(origins = "localhost:5500")
    @GetMapping("/data")
    public MyData getData() throws SQLException {
        MyData data = new MyData();
        DBCommunicator db = DBCommunicator.getDatabase();
        StringBuilder message = new StringBuilder();
        try {
            ResultSet currencies = db.SelectFromTable("*", "currencies");
            ResultSet exchangerates = db.SelectFromTable("*", "exchangerates");
            while (currencies.next() && exchangerates.next()) {
                message.append(currencies.getString("currencyname"));
                message.append(" ");
                message.append(exchangerates.getDouble("exchangevalue"));
                message.append(" "+exchangerates.getString("lastexchange"));
                message.append(System.getProperty("Line.separator"));
            }

        } catch (SQLException e) {

        }
        data.setMessage(message.toString());
        return data;
    }

    public static class MyData {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
