package com.cps_assignment.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * This class is used to collect data from the api even if application has been offline for some time.
 *
 * @author Baremikkel, Tiomann99
 */
public class ReadLastTimestamp {
    private static ReadLastTimestamp instance;
    private final String API_KEY = "6a923f87ddbd476b8889e32091ad40ee";
    private final int ONE_DAY_IN_MILLIS = 86400000;

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

    long convertMillisToUTC(long timestampNow) {
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

    private void SendGetRequestToApi(LocalDate startDate, LocalDate endDate) {
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

    void updateFile(String filename, long lastTimestamp) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(String.valueOf(lastTimestamp)); //should be a string
            myWriter.close();
            System.out.println("Successfully wrote timestamp: " + lastTimestamp + " to last_timestamp.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ReadLastTimestamp getInstance() {
        if (instance == null) {
            instance = new ReadLastTimestamp();
        }
        return instance;
    }

}