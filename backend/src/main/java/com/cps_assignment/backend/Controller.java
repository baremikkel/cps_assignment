package com.cps_assignment.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Baremikkel, tiomann99
 */
@SpringBootApplication
@Import(CorsConfig.class)
public class Controller {


    public static void main(String[] args) throws Exception { // Pilot
        SpringApplication.run(Controller.class, args);
        ReadLastTimestamp.getInstance();
        //seedDataToDB(); //only run collectData when sqlseeding, will result in duplicated data.
    }
    private static void seedDataToDB(){
        try {
            DBCommunicator db = DBCommunicator.getDatabase();
            File myObj = new File("backend/src/main/java/com/cps_assignment/backend/assets/sql/db_exchangerates_table.sql");
            Scanner myReader = new Scanner(myObj);
            int alterCounter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                db.UpdateTable(data);
                alterCounter++;
            }
            db.UpdateTable("ALTER SEQUENCE exchangerates_exchangeid_seq RESTART WITH "+alterCounter);
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
