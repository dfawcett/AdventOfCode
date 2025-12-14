package com.fawcett.adventofcode.day2.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * A class to find invalid product ids in the gift shop database for day 2 part
 * 1 of the Advent of Code.
 */
@Command(name = "GiftShop", mixinStandardHelpOptions = true, version = "1.0", description = "Finds the invalid product ids in the gift shop database.")
public class GiftShop implements Runnable {

    @Option(names = { "-f", "--file" }, description = "Path to input file")
    private Path inputFile;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new GiftShop()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (inputFile == null) {
            throw new IllegalArgumentException("Input file must be provided");
        }
        String input = readFile(inputFile);
        long result = addInvalidIdsInRanges(input);
        System.out.println(result);
    }

    public static long addInvalidIdsInRanges(String rangesString) {
        String[] parts = rangesString.split(",");
        return addInvalidIdsInRanges(parts);
    }

    public static long addInvalidIdsInRanges(String[] rangeStrings) {
        long[][] ranges = new long[rangeStrings.length][2];
        for (int i = 0; i < rangeStrings.length; i++) {
            String[] parts = rangeStrings[i].split("-");
            ranges[i][0] = Long.parseLong(parts[0]);
            ranges[i][1] = Long.parseLong(parts[1]);
        }
        return addInvalidIdsInRanges(ranges);
    }

    public static long addInvalidIdsInRanges(long[][] ranges) {
        long invalidSum = 0;
        for (long[] range : ranges) {
            invalidSum += addInvalidIdsInRange(range[0], range[1]);
        }
        return invalidSum;
    }

    /**
     * Adds up all invalid product ids in the given range.
     * 
     * @param start The starting product id (inclusive).
     * @param end   The ending product id (inclusive).
     * @return The sum of all invalid product ids in the range.
     */
    public static long addInvalidIdsInRange(long start, long end) {
        long invalidSum = 0;
        for (long i = start; i <= end; i++) {
            String productId = Long.toString(i);
            if (!isValidProductId(productId)) {
                invalidSum += i;
            }
        }
        return invalidSum;
    }

    /**
     * Determines if a product id is valid.
     * 
     * A product id is valid if not made up of a repeated substring.
     * 
     * @param productId The product id to check.
     * @return True if the product id is valid, false otherwise.
     */
    public static boolean isValidProductId(String productId) {
        int len = productId.length();
        for(int i = 1; i <= len / 2; i++) {
            if (len % i == 0) {
                String compareString = productId.substring(0, i).repeat(len / i);
                if (compareString.equals(productId)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String readFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + path, e);
        }
    }
}
