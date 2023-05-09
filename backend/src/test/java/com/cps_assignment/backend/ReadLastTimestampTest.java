package com.cps_assignment.backend;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class ReadLastTimestampTest {
    private static final String FILENAME = "testfile.txt";
    private static final String CONTENT = "123456789";
    private static final long CURRENT_TIME = System.currentTimeMillis();
    private static final long ONE_DAY_IN_MILLIS = 86400000;

    @BeforeAll
    static void createTestFile() throws IOException {
        Files.write(Paths.get(FILENAME), CONTENT.getBytes());
    }

    @Test
    void testConvertMillisToUTC() {
        ReadLastTimestamp readLastTimestamp = ReadLastTimestamp.getInstance();
        long utc = readLastTimestamp.convertMillisToUTC(CURRENT_TIME);
        Assertions.assertEquals(Instant.ofEpochMilli(CURRENT_TIME)
                .atOffset(ZoneOffset.ofHours(2)).withOffsetSameInstant(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.DAYS).toInstant().toEpochMilli(), utc);
    }


    @Test
    void testReadFromFile() {
        ReadLastTimestamp readLastTimestamp = ReadLastTimestamp.getInstance();
        String data = readLastTimestamp.readFromFile(FILENAME);
        Assertions.assertEquals(CONTENT, data);
    }

    @Test
    void testUpdateFile() throws IOException {
        ReadLastTimestamp readLastTimestamp = ReadLastTimestamp.getInstance();
        long newTimestamp = CURRENT_TIME + ONE_DAY_IN_MILLIS;
        readLastTimestamp.updateFile(FILENAME, newTimestamp);
        File file = new File(FILENAME);
        String content = new String(Files.readAllBytes(file.toPath()));
        Assertions.assertEquals(Long.toString(newTimestamp), content);
    }

    @AfterAll
    static void removeTestFile() throws IOException {
        Files.deleteIfExists(Paths.get(FILENAME));
    }
}
