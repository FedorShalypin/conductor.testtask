package conductor.testtask.helpers;

public class Configuration {
	public static final String endpoint = System.getProperty("beacon.endpoint");
	public static final String outputValuePath = System.getProperty("beacon.outputValuePath");
	public static final int outputValueLength = Integer.parseInt(System.getProperty("beacon.outputValueLength"));
	public static final String outputPrintFormat = System.getProperty("app.outputPrintFormat");
	public static final String outputValuePattern = System.getProperty("beacon.outputAlowedCharactersPattern");
}