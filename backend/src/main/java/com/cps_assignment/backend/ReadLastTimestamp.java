package com.cps_assignment.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class ReadLastTimestamp {
    private String API_KEY = "2f68cc286ef546b2aa09fbac10e33503";

    public ReadLastTimestamp() {
        checkLastTimestamp("backend/src/main/java/com/cps_assignment/backend/assets/last_timestamp.txt");
    }

    public boolean checkLastTimestamp(String filename) {
        long timestampNow = convertToUTC(System.currentTimeMillis());
        long lastTimestamp = Long.parseLong(readFromFile(filename));
        if (timestampNow > lastTimestamp) {
            System.out.println("Updates file");
            compareDateToMillis(timestampNow, lastTimestamp);
            updateFile(filename, timestampNow);
            //Calls API
        } else

            System.out.println("Not update file");
        return true;
    }

    private void compareDateToMillis(long timestampNow, long lastTimestamp) {
        LocalDate startDate = Instant.ofEpochMilli(lastTimestamp).atZone(ZoneId.of("UTC")).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(timestampNow + 86400000).atZone(ZoneId.of("UTC")).toLocalDate();
        HTTPSniffer sniffer = new HTTPSniffer();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            try {
                sniffer.sendGETRequest("https://openexchangerates.org/api/historical/" + date + ".json?app_id=" + API_KEY, String.valueOf(date));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private long convertToUTC(long timestampNow) {
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestampNow), ZoneOffset.ofHours(2))
                .withOffsetSameInstant(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.DAYS);
        System.out.println(offsetDateTime.toInstant().toEpochMilli());
        return offsetDateTime.toInstant().toEpochMilli(); //nuverende utc tid i dage
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
            myWriter.write(lastTimestamp + "");
            myWriter.close();
            System.out.println("Successfully wrote to the file. " + lastTimestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
