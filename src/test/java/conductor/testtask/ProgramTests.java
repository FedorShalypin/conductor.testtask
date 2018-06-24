package conductor.testtask;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import conductor.testtask.helpers.Util;

class ProgramTests {
	private static final String CorrectOutputValue = "BD06640A77680AC9871AD663A8DD7DC547957EC5474E890BF334A7BDA80920058439FBAD71FBF75CDCEE4748AAD4E552D50EA39B955E7A75C654802517EF0687";
	public static final String endpoint = "https://beacon.nist.gov/rest/record/last";
	public static final String outputValuePath = "record.outputValue";
	public static final int outputValueLength = 128;
	public static final String outputPrintFormat = "%s,%s%n";
	public static final String outputValuePattern = "[^A-F\\d]";

	@Test
	void getOutputValueHappyPass() throws Exception {
		final String value = Util.getOutputValue(endpoint, outputValuePath);
		assertNotEquals(0, value.length());
	}

	@Test
	void getOutputValueWrongEndpoint() {
		Throwable exception = assertThrows(Exception.class, () -> Util.getOutputValue("", outputValuePath));
		assertEquals("Connection refused: connect", exception.getMessage());
	}

	@Test
	void getOutputValueWrongEndpointPort() {
		Throwable exception = assertThrows(Exception.class,
				() -> Util.getOutputValue("", "https://beacon.nist.gov:9090/rest/record/last"));
		assertEquals("Connection refused: connect", exception.getMessage());
	}

	@Test
	void getOutputValueWrongEndpointPath() {
		Throwable exception = assertThrows(Exception.class, () -> Util.getOutputValue(endpoint + "2", ""));
		assertEquals("Response code: 404\r\nResponse body: Invalid Method or Argument", exception.getMessage());
	}

	@Test
	void getOutputValueWrongBeaconPath() {
		Throwable exception = assertThrows(ClassCastException.class, () -> Util.getOutputValue(endpoint, ""));
		assertEquals("com.jayway.restassured.internal.path.xml.NodeImpl cannot be cast to java.lang.String",
				exception.getMessage());
	}

	@Test
	void validateOutputValueHappyPass() throws Exception {
		Util.validateOutputValue(CorrectOutputValue, outputValueLength, outputValuePattern);
	}

	@Test
	void validateOutputValueBlank() {
		final String value = "   ";
		Throwable exception = assertThrows(Exception.class,
				() -> Util.validateOutputValue(value, outputValueLength, outputValuePattern));
		assertEquals("Beacon couldn't be blank", exception.getMessage());
	}

	@Test
	void validateOutputValueLessLength() {
		Throwable exception = assertThrows(UnsupportedOperationException.class,
				() -> Util.validateOutputValue(CorrectOutputValue, outputValueLength - 1, outputValuePattern));
		assertEquals("Unexpected Output Value length: 128", exception.getMessage());
	}

	@Test
	void validateOutputValueGreaterLength() {
		Throwable exception = assertThrows(UnsupportedOperationException.class,
				() -> Util.validateOutputValue(CorrectOutputValue, outputValueLength + 1, outputValuePattern));
		assertEquals("Unexpected Output Value length: 128", exception.getMessage());
	}

	@Test
	void validateOutputValueWrongCharacters() {
		final String value = CorrectOutputValue.replaceAll("\\d", "Z");
		Throwable exception = assertThrows(UnsupportedOperationException.class,
				() -> Util.validateOutputValue(value, outputValueLength, outputValuePattern));
		assertEquals("Unexpected character: Z", exception.getMessage());
	}

	@Test
	void validateOutputValueWrongPattern() {
		Throwable exception = assertThrows(UnsupportedOperationException.class,
				() -> Util.validateOutputValue(CorrectOutputValue, outputValueLength, "[^A-F]"));
		assertEquals("Unexpected character: 0", exception.getMessage());
	}

	@Test
	void groupCharsHappyPass() {
		final TreeMap<String, Long> collection = Util.groupChars(CorrectOutputValue);
		assertAll(() -> {
			assertLinesMatch(collection.values().stream().map(Object::toString).collect(Collectors.toList()),
					Arrays.asList(new String[] { "10", "3", "3", "5", "10", "13", "7", "15", "9", "7", "11", "6", "6",
							"10", "8", "5" }));
			assertLinesMatch(new ArrayList<String>(collection.keySet()), Arrays.asList(
					new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" }));
		});
	}

	@Test
	void groupCharsAllZero() {
		final TreeMap<String, Long> collection = Util.groupChars(CorrectOutputValue.replaceAll("[A-F\\d]", "0"));
		assertAll(() -> {
			assertLinesMatch(collection.values().stream().map(Object::toString).collect(Collectors.toList()),
					Arrays.asList(new String[] { "128" }));
			assertLinesMatch(new ArrayList<String>(collection.keySet()), Arrays.asList(new String[] { "0" }));
		});
	}
}