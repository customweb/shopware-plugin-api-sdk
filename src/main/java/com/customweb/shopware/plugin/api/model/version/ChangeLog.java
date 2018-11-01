package com.customweb.shopware.plugin.api.model.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeLog {

	private final long id;
	private final ChangeLog.Locale locale;
	private String text;

	@JsonCreator
	public ChangeLog(@JsonProperty("id") long id, @JsonProperty("locale") Locale locale, @JsonProperty("text") String text) {
		this.id = id;
		this.locale = locale;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public ChangeLog.Locale getLocale() {
		return locale;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public static class Locale {
		private final long id;
		private final String name;

		@JsonCreator
		public Locale(@JsonProperty("id") long id, @JsonProperty("name") String name) {
			this.id = id;
			this.name = name;
		}

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}
}
