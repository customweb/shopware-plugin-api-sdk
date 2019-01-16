package com.customweb.shopware.plugin.api.model.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class AssessmentFactor {

	private final long id;
	private final String name;
	private final String description;
	private final long impactValue;

	@JsonCreator
	public AssessmentFactor(@JsonProperty("id") long id, @JsonProperty("name") String name,
			@JsonProperty("description") String description, @JsonProperty("impactValue") long impactValue) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.impactValue = impactValue;
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

	public long getImpactValue() {
		return impactValue;
	}

}
