package com.customweb.shopware.plugin.api.model.token;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Map;

import com.customweb.shopware.plugin.api.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessToken {

	private final Locale locale;
	private final String token;
	private final String userId;
	private final ZonedDateTime expiryDate;

	@JsonCreator
	public AccessToken(@JsonProperty("expire") Map<String, String> expiration, @JsonProperty("locale") Locale locale,
			@JsonProperty("token") String token, @JsonProperty("userId") String userId) {
		expiryDate = DateUtil.parseDate(expiration.get("date")).atZone(ZoneId.of(expiration.get("timezone")));
		this.locale = locale;
		this.token = token;
		this.userId = userId;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getToken() {
		return token;
	}

	public String getUserId() {
		return userId;
	}

	public ZonedDateTime getExpiryDate() {
		return expiryDate;
	}

}
