package com.cps_assignment.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
@Import(CorsConfig.class)
public class Controller {


    public static void main(String[] args) throws Exception { // Pilot
        SpringApplication.run(Controller.class, args);
        ReadLastTimestamp r = new ReadLastTimestamp();
        //collectData(); //do not run collectData, can result in duplicated data.
    }
    private static void collectData (){
        try {
            DBCommunicator db = DBCommunicator.getDatabase();
            File myObj = new File("backend/src/main/java/com/cps_assignment/backend/assets/sql/db_exchangerates_table.sql");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                db.UpdateTable(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Seeding file db_exchangerates_table.sql not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to db. \n" + e);
        }
    }
}
