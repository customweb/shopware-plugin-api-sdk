package com.customweb.shopware.plugin.api.util;

import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateUtil {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDateTime parseDate(String date) {
		TemporalAccessor parsed = DATE_FORMATTER.parse(date, new ParsePosition(0));
		return LocalDateTime.from(parsed);
	}
}
