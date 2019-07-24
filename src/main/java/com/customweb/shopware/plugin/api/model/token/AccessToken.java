package com.customweb.shopware.plugin.api.model.token;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Map;

import com.customweb.shopware.plugin.api.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Access token received from the Shopware system.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken {

	private final Locale locale;
	private final String token;
	private final String userId;
	private final ZonedDateTime expiryDate;
	private final Boolean legacyLogin;

	@JsonCreator
	public AccessToken(@JsonProperty("expire") Map<String, String> expiration, @JsonProperty("locale") Locale locale,
			@JsonProperty("token") String token, @JsonProperty("userId") String userId, @JsonProperty(value = "legacyLogin", required= false) Boolean legacyLogin) {
		expiryDate = DateUtil.parseDate(expiration.get("date")).atZone(ZoneId.of(expiration.get("timezone")));
		this.locale = locale;
		this.token = token;
		this.userId = userId;
		this.legacyLogin = legacyLogin;
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

	public Boolean getLegacyLogin() {
		return legacyLogin;
	}
}
