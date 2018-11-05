package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampFormatter {

	private static final DateTimeFormatter PRETTY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static String prettyPrint(LocalDateTime localDateTime) {
		return localDateTime.format(PRETTY_FORMATTER);
	}
}
