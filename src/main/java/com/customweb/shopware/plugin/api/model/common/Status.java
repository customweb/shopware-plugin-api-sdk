package com.customweb.shopware.plugin.api.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A generic status field that has a name and description.
 */
public final class Status {

	private final long id;
	private final String name;
	private final String description;

	@JsonCreator
	public Status(@JsonProperty("id") long id, @JsonProperty("name") String name,
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
