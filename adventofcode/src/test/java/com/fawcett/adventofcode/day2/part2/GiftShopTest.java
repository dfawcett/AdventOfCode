package com.fawcett.adventofcode.day2.part2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import picocli.CommandLine;

public class GiftShopTest {

	@ParameterizedTest
	@CsvSource({
		"abc, true",    // odd length -> valid
		"a, true",      // odd length -> valid
		"abab, false",  // repeated substring -> invalid
		"aaaa, false",  // repeated substring -> invalid
		"abcd, true",   // even but not repeating -> valid
		"abca, true"    // even but not repeating -> valid
	})
	public void isValidProductId_returnsExpected(String productId, boolean expected) {
        assertEquals(GiftShop.isValidProductId(productId), expected);
	}

    @ParameterizedTest
	@CsvSource({
		"11-22, 33",
        "95-115, 210",
        "998-1012, 2009",
        "1188511880-1188511890, 1188511885",
        "222220-222224, 222222",
        "1698522-1698528, 0",
        "446443-446449, 446446",
        "38593856-38593862, 38593859",
        "565653-565659, 565656",
        "824824821-824824827, 824824824"
	})
	public void addInvalidIdsInRange(String range, int expected) {
        assertEquals(expected, GiftShop.addInvalidIdsInRanges(range));
	}


    @Test
    public void run_readsSampleFile_andPrintsExpected() throws Exception {
        URL resource = GiftShopTest.class.getResource("testdata/sample.txt");
        Path path = Path.of(resource.toURI());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        new CommandLine(new GiftShop()).execute("--file", path.toString());

        String output = outContent.toString().trim();
        assertEquals("4174379265", output);
    }
}
