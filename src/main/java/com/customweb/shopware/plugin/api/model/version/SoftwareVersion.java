package com.customweb.shopware.plugin.api.model.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the Shopware product version that a particular plugin version is
 * compatible with.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoftwareVersion {

	private final String name;

	@JsonCreator
	public SoftwareVersion(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
