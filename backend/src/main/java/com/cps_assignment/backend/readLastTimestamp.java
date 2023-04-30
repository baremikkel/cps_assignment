package com.cps_assignment.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class readLastTimestamp {
    private String API_KEY = "2f68cc286ef546b2aa09fbac10e33503";

    public readLastTimestamp() {
        checkLastTimestamp("backend/src/main/java/com/cps_assignment/backend/assets/last_timestamp.txt");
    }

    public boolean checkLastTimestamp(String filename) {
        long timestamp = convertToUTC(System.currentTimeMillis())/1000;
        long lastTimestamp = Long.parseLong(readFromFile(filename));
        if (timestamp > lastTimestamp) {
            System.out.println("Updates file");
            updateFile(filename, timestamp);
            //Calls API
            HTTPSniffer sniffer = new HTTPSniffer();
            try {
                sniffer.sendGETRequest("https://openexchangerates.org/api/latest.json?app_id="+API_KEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            System.out.println("Not update file");
        return true;
    }

    private long convertToUTC(long timestamp){
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.ofHours(2))
                .withOffsetSameInstant(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.HOURS);
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
            myWriter.write(lastTimestamp+"");
            myWriter.close();
            System.out.println("Successfully wrote to the file. "+lastTimestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
