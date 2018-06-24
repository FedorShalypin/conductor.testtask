package conductor.testtask;

import java.util.TreeMap;

import conductor.testtask.helpers.Configuration;
import conductor.testtask.helpers.Util;

public class Program {

	public static void main(String[] args) throws Exception {
		final String value = Util.getOutputValue(Configuration.endpoint, Configuration.outputValuePath);
		Util.validateOutputValue(value, Configuration.outputValueLength, Configuration.outputValuePattern);

		final TreeMap<String, Long> chars = Util.groupChars(value);
		chars.forEach((k, v) -> System.out.printf(Configuration.outputPrintFormat, k, v));
	}
}