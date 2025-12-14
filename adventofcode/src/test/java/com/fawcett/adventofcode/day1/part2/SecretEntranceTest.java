package com.fawcett.adventofcode.day1.part2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;

public class SecretEntranceTest {
    @ParameterizedTest
    @CsvSource({
            "R10, 0, 10, 0",
            "R110, 0, 10, 1",
            "L10, 0, 90, 0",
            "L110, 0, 90, 1",
            "R10, 95, 5, 1",
            "R110, 95, 5, 2",
            "L10, 5, 95, 1",
            "L110, 5, 95, 2",
            "R10, 90, 0, 1",
            "L10, 10, 0, 1",
    })
    public void move_returnsExpected(String instruction, int start, int expectedCurrentPosition,
            int expectedClicksAtZero) {
        SecretEntrance secretEntrance = new SecretEntrance(start);
        int clicksAtZero = secretEntrance.move(instruction);

        int currentPosition = secretEntrance.getCurrentPosition();

        assertEquals(expectedCurrentPosition, currentPosition);
        assertEquals(expectedClicksAtZero, clicksAtZero);
    }

    @Test
    public void findPassword_passingThroughZero_countsZeros() {
        SecretEntrance secretEntrance = new SecretEntrance(10);

        int result = secretEntrance.findPassword(
                Arrays.asList("L20", "R20", "L20", "R20", "L20"));

        assertEquals(5, result);
    }

    @Test
    public void run_readsSampleFile_andPrintsExpected() throws Exception {
        URL resource = SecretEntranceTest.class.getResource("testdata/sample.txt");
        Path path = Path.of(resource.toURI());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        new CommandLine(new SecretEntrance(50)).execute("--file", path.toString());

        String output = outContent.toString().trim();
        assertEquals("6", output);
    }
}