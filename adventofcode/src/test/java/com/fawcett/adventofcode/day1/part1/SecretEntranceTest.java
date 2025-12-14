package com.fawcett.adventofcode.day1.part1;

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
        "R10, 0, 10",
        "L10, 0, 90",
        "R10, 95, 5",
        "R110, 95, 5",
        "L10, 5, 95",
        "L110, 5, 95"
    })
    public void move_returnsExpected(String instruction, int start, int expected) {
        assertEquals(expected, SecretEntrance.move(instruction, start));
    }

    public void findPassword_countsZeros() {
        int result = SecretEntrance.findPassword(
            Arrays.asList("R10", "L20", "R10", "L80", "R100", "L10"), 
            50
        );
        assertEquals(2, result);
    }

    @Test
    public void run_readsSampleFile_andPrintsExpected() throws Exception {
        URL resource = SecretEntranceTest.class.getResource("testdata/sample.txt");
        Path path = Path.of(resource.toURI());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        new CommandLine(new SecretEntrance()).execute("--file", path.toString());

        String output = outContent.toString().trim();
        assertEquals("3", output);
    }
}