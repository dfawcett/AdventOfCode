package com.fawcett.adventofcode.day1.part1;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/** A class to handle secret entrance logic for day 1 part 1 of the Advent of Code. */
@Command(
    name = "SecretEntrance", 
    mixinStandardHelpOptions = true, 
    version = "1.0",
    description = "Finds the secret entrance password."
)
public class SecretEntrance implements Runnable {

    @Option(names = { "-f", "--file" }, description = "Path to input file")
    private Path inputFile;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new SecretEntrance()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (inputFile == null) {
            throw new IllegalArgumentException("Input file must be provided");
        }
        List<String> input = readFile(inputFile);
        int result = findPassword(input, 50);
        System.out.println(result);
    }

    /**
     * Finds the password from the input list.
     * 
     * The password is defined as the number of times the position was 0.
     * 
     * @param input            A list of movement instructions.
     * @param startingPosition The starting position on the circular dial.
     * @return The number of times the position was 0.
     */
    public static int findPassword(List<String> input, int startingPosition) {
        int currentPosition = startingPosition;
        int zeroCount = currentPosition == 0 ? 1 : 0;
        for (String instruction : input) {
            currentPosition = move(instruction, currentPosition);
            if (currentPosition == 0) {
                zeroCount++;
            }
        }
        return zeroCount;
    }

    /**
     * Moves the current position based on the instruction.
     * 
     * @param input            The movement instruction (e.g., "R10", "L20").
     * @param startingPosition The starting position on the circular dial.
     * @return The new position after applying the movement instruction.
     */
    static int move(String input, int startingPosition) {
        int currentPosition = startingPosition;
        char direction = input.charAt(0);
        int steps = Integer.parseInt(input.substring(1));
        switch (direction) {
            case 'R':
                currentPosition += steps;
                currentPosition %= 100;
                break;
            case 'L':
                currentPosition -= steps;
                while (currentPosition < 0) {
                    currentPosition += 100;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        return currentPosition;
    }

    private static List<String> readFile(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + path, e);
        }
    }
}
