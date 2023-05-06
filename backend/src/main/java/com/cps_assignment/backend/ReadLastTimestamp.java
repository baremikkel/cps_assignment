package com.cps_assignment.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * This method is used to collect data from the api even if application has been offline for some time.
 * @author bare.mikkel, tiomann99
 */
public class ReadLastTimestamp {
    private String API_KEY = "6a923f87ddbd476b8889e32091ad40ee"; //2f68cc286ef546b2aa09fbac10e33503
    private int ONE_DAY_IN_MILLIS = 86400000;

    public ReadLastTimestamp() {
        //When a new Object has been initialized, the program starts by checking when was the last the api has called
        checkLastTimestamp("backend/src/main/java/com/cps_assignment/backend/assets/last_timestamp.txt");
    }

    public void checkLastTimestamp(String filename) {
        //It then checks if the current time and the time from the textfile is the same and then desides if the api should be called or not
        long timestampNow = convertMillisToUTC(System.currentTimeMillis());
        long lastTimestamp = Long.parseLong(readFromFile(filename));
        if (timestampNow > lastTimestamp) {
            convertMillisToUTCDate(timestampNow, lastTimestamp);
            updateFile(filename, timestampNow);
        } else
            System.out.println("Last_timestamp.txt is already up to date!");
    }

    private long convertMillisToUTC(long timestampNow) {
        //This method take the millis and converts it into the timezone for Coordinated Universal Time (UTC) and returns the present day in millis
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestampNow), ZoneOffset.ofHours(2))
                .withOffsetSameInstant(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.DAYS); //We then converts the millis to only include days since it was decided that the exchange rate don't change a lot over a day, and it would ease the amount the api had to be called
        return offsetDateTime.toInstant().toEpochMilli();
    }
    private void convertMillisToUTCDate(long timestampNow, long lastTimestamp) {
        //Converts the newly converted millis-time to LocalDate and sends them to the SendGetRequestToApi
        LocalDate startDate = Instant.ofEpochMilli(lastTimestamp).atZone(ZoneId.of("UTC")).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(timestampNow + ONE_DAY_IN_MILLIS).atZone(ZoneId.of("UTC")).toLocalDate();
        SendGetRequestToApi(startDate, endDate);
    }

    private void SendGetRequestToApi(LocalDate startDate, LocalDate endDate){
        //makes sure to get the rate for all the missing dates
        HTTPSniffer sniffer = new HTTPSniffer();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            try {
                sniffer.sendGETRequest("https://openexchangerates.org/api/historical/" + date + ".json?app_id=" + API_KEY, String.valueOf(date));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public String readFromFile(String filename) {
        String data = "";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
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
            myWriter.write(String.valueOf(lastTimestamp)); //should be a string
            myWriter.close();
            System.out.println("Successfully wrote timestamp: " + lastTimestamp + " to last_timestamp.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
