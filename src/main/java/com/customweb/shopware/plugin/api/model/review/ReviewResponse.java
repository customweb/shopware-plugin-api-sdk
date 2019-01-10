package com.customweb.shopware.plugin.api.model.review;

import java.time.LocalDateTime;

import com.customweb.shopware.plugin.api.model.common.Status;
import com.customweb.shopware.plugin.api.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response object received when a plugin is submitted for review.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewResponse {

	private final long id;
	private final String comment;
	private final LocalDateTime creationDate;
	private final Status status;

	@JsonCreator
	public ReviewResponse(@JsonProperty("id") long id, @JsonProperty("comment") String comment,
			@JsonProperty("creationDate") String creationDate, @JsonProperty("status") Status status) {
		this.id = id;
		this.comment = comment;
		this.creationDate = DateUtil.parseDate(creationDate);
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public Status getStatus() {
		return status;
	}
}
