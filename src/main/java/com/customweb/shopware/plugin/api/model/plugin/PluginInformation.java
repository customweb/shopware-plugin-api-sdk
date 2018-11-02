package com.customweb.shopware.plugin.api.model.plugin;

import java.time.LocalDateTime;

import com.customweb.shopware.plugin.api.model.common.Status;
import com.customweb.shopware.plugin.api.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains the general information about a plugin.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class PluginInformation {

	private final long id;
	private final String name;
	private final String code;
	private final String moduleKey;
	private final Status lifecycleStatus;
	private final Status activationStatus;
	private final Status approvalStatus;
	private final PluginLicense license;
	private final LocalDateTime lastChange;
	private final LocalDateTime creationDate;
	private final Status status;
	private final String iconUrl;

	@JsonCreator
	public PluginInformation(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("code") String code,
			@JsonProperty("moduleKey") String moduleKey, @JsonProperty("lifecycleStatus") Status lifecycleStatus,
			@JsonProperty("activationStatus") Status activationStatus, @JsonProperty("approvalStatus") Status approvalStatus,
			@JsonProperty("license") PluginLicense license, @JsonProperty("lastChange") String lastChange,
			@JsonProperty("creationDate") String creationDate, @JsonProperty("status") Status status,
			@JsonProperty("iconUrl") String iconUrl) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.moduleKey = moduleKey;
		this.lifecycleStatus = lifecycleStatus;
		this.activationStatus = activationStatus;
		this.approvalStatus = approvalStatus;
		this.license = license;
		this.lastChange = DateUtil.parseDate(lastChange);
		this.creationDate = DateUtil.parseDate(creationDate);
		this.status = status;
		this.iconUrl = iconUrl;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getModuleKey() {
		return moduleKey;
	}

	public Status getLifecycleStatus() {
		return lifecycleStatus;
	}

	public Status getActivationStatus() {
		return activationStatus;
	}

	public Status getApprovalStatus() {
		return approvalStatus;
	}

	public PluginLicense getLicense() {
		return license;
	}

	public LocalDateTime getLastChange() {
		return lastChange;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public Status getStatus() {
		return status;
	}

	public String getIconUrl() {
		return iconUrl;
	}

}
