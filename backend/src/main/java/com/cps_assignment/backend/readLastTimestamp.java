package com.cps_assignment.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class readLastTimestamp {
    private String API_KEY = "2f68cc286ef546b2aa09fbac10e33503";

    public readLastTimestamp() {
        checkLastTimestamp("backend/src/main/java/com/cps_assignment/backend/assets/last_timestamp.txt");
    }

    public boolean checkLastTimestamp(String filename) {
        System.out.println(System.currentTimeMillis());
        long timestamp = convertToUTC(System.currentTimeMillis()) / 1000;
        long lastTimestamp = Long.parseLong(readFromFile(filename));
        //if (timestamp > lastTimestamp) {
        System.out.println("Updates file");
        // updateFile(filename, timestamp);
        //Calls API
        HTTPSniffer sniffer = new HTTPSniffer();
        try {
            //Update so that it uses latest if the date is the same but if they are different it uses historical
            sniffer.sendGETRequest("https://openexchangerates.org/api/historical/2023-04-30.json?app_id=" + API_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // } else
        System.out.println("Not update file");
        return true;
    }

    private void compareDates() {
        /*
        Vi tjekker om datoen passer overens med UTC hvis den datoen er den samme så sker der ikke noget,
        hvis der er x antal dage imellem datoerne så kalder vi historical endpoint på datoen og gemmer i databasen
        på den måde slipper vi for at 1 lave forskellige metoder.
 */
    }

    private long convertToUTC(long timestamp) {
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.ofHours(2))
                .withOffsetSameInstant(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.DAYS);
        System.out.println(offsetDateTime.toInstant().toEpochMilli());
        return offsetDateTime.toInstant().toEpochMilli();
    }

    public String readFromFile(String filename) {
        String data = "";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                //System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void updateFile(String filename, long lastTimestamp) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(lastTimestamp + "");
            myWriter.close();
            System.out.println("Successfully wrote to the file. " + lastTimestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
