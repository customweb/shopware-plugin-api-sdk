package com.customweb.shopware.plugin.api.model.review;

import java.time.LocalDateTime;

import com.customweb.shopware.plugin.api.model.common.Status;

/**
 * Response object received when a plugin is submitted for review.
 */
public class ReviewResponse {

	private final long id;
	private final String comment;
	private final LocalDateTime creationDate;
	private final Status status;

	public ReviewResponse(long id, String comment, LocalDateTime creationDate, Status status) {
		this.id = id;
		this.comment = comment;
		this.creationDate = creationDate;
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
