package com.customweb.shopware.plugin.api.util;

import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * Date utilities for shopware.
 */
public class DateUtil {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * Convert a string into a local date. Shopware uses a non-standard date format
	 * so we can't parse it automatically during JSON serialisation/deserialisation.
	 * 
	 * @param date
	 *            the date string
	 * @return the local date
	 */
	public static LocalDateTime parseDate(String date) {
		TemporalAccessor parsed = DATE_FORMATTER.parse(date, new ParsePosition(0));
		return LocalDateTime.from(parsed);
	}

	public static String formatDate(LocalDateTime date) {
		return DATE_FORMATTER.format(date);
	}
}
