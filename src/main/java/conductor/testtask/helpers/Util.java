package conductor.testtask.helpers;

import static com.jayway.restassured.RestAssured.given;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.jayway.restassured.response.Response;

public class Util {

	public static String getOutputValue(String URL, String BeaconPath) throws Exception {
		final Response resp = given().get(URL);
		final int status = resp.statusCode();
		if (status != 200)
			throw new Exception(
					"Response code: " + status + System.lineSeparator() + "Response body: " + resp.body().asString());
		return resp.path(BeaconPath);
	}

	public static void validateOutputValue(String value, int length, String allowedCharacters) throws Exception {
		if (StringUtils.isBlank(value))
			throw new Exception("Beacon couldn't be blank");
		if (value.length() != length)
			throw new UnsupportedOperationException("Unexpected Output Value length: " + value.length());
		final Matcher m = Pattern.compile(allowedCharacters).matcher(value);
		if (m.find())
			throw new UnsupportedOperationException("Unexpected character: " + m.group());
	}

	public static TreeMap<String, Long> groupChars(final String value) {
		return new TreeMap<>(Stream.of(value.split("")).collect(Collectors.groupingBy(c -> c, Collectors.counting())));
	}
}