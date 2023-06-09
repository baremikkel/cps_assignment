package com.cps_assignment.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Baremikkel, Tiomann99
 * Controls system
 */

@SpringBootApplication
@Import(CorsConfig.class)
public class Controller {


    public static void main(String[] args) throws Exception { //Pilot
        SpringApplication.run(Controller.class, args);
        ReadLastTimestamp.getInstance().checkLastTimestamp("backend/src/main/java/com/cps_assignment/backend/assets/last_timestamp.txt"); //Start program
        //seedDataToDB(); //Only run collectData when sqlseeding, can result in duplicated data.
    }
    private static void seedDataToDB(){
        try {
            DBCommunicator db = DBCommunicator.getDatabase();
            File myObj = new File("backend/src/main/java/com/cps_assignment/backend/assets/sql/db_exchangerates_table.sql");
            Scanner myReader = new Scanner(myObj);
            System.out.println("Seeding...");
            int alterCounter = 1;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                db.updateTable(data);
                alterCounter++;
            }
            db.updateTable("ALTER SEQUENCE exchangerates_exchangeid_seq RESTART WITH "+alterCounter);
            System.out.println("Done with seeding");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Seeding file db_exchangerates_table.sql not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to db. \n" + e);
        }
    }
}
