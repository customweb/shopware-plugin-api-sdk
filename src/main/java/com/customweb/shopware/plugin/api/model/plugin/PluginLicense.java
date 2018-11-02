package com.customweb.shopware.plugin.api.model.plugin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Plugin license information.
 */
public final class PluginLicense {

	private final long id;
	private final String name;
	private final String description;

	@JsonCreator
	public PluginLicense(@JsonProperty("id") long id, @JsonProperty("name") String name,
			@JsonProperty("description") String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
