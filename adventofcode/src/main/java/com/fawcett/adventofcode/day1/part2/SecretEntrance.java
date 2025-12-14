package com.fawcett.adventofcode.day1.part2;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/** A class to handle secret entrance logic for day 1 part 2 of the Advent of Code. */
@Command(name = "SecretEntrance", mixinStandardHelpOptions = true, version = "1.0", description = "Finds the secret entrance password.")
public class SecretEntrance implements Runnable {

    @Option(names = { "-f", "--file" }, description = "Path to input file")
    private Path inputFile;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new SecretEntrance(50)).execute(args);
        System.exit(exitCode);
    }

    private int currentPosition;

    SecretEntrance(int startingPosition) {
        this.currentPosition = startingPosition;
    }

    /** Returns the current position of the dial. */
    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void run() {
        if (inputFile == null) {
            throw new IllegalArgumentException("Input file must be provided");
        }
        List<String> input = readFile(inputFile);
        int result = findPassword(input);
        System.out.println(result);
    }

    /**
     * Finds the password from the input list.
     * 
     * The password is defined as the number of times the position clicked at 0.
     * 
     * @param input A list of movement instructions.
     * @return The number of times the position clicked at zero.
     */
    public int findPassword(List<String> input) {
        int clicksAtZero = 0;
        for (String instruction : input) {
            clicksAtZero += move(instruction);
        }
        return clicksAtZero;
    }

    /** Returns the number of clicks at that occur at zero. 
     * 
     * @param input The movement instruction (e.g., "R10", "L20").
     * @return The number of clicks at zero.
    */
    int move(String input) {
        int clicksAtZero = 0;
        char direction = input.charAt(0);
        int steps = Integer.parseInt(input.substring(1));
        switch (direction) {
            case 'R':
                currentPosition += steps;
                while (currentPosition >= 100) {
                    if (currentPosition != 100) {
                        clicksAtZero++;
                    }
                    currentPosition -= 100;
                }
                break;
            case 'L':
                currentPosition -= steps;
                while (currentPosition < 0) {
                    if (currentPosition != -steps) {
                        clicksAtZero++;
                    }
                    currentPosition += 100;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        if(currentPosition == 0 && steps != 0) {
            clicksAtZero++;
        }
        return clicksAtZero;
    }

    private static List<String> readFile(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + path, e);
        }
    }
}
