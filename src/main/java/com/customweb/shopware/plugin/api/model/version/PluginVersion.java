package com.customweb.shopware.plugin.api.model.version;

import java.time.LocalDateTime;
import java.util.List;

import com.customweb.shopware.plugin.api.model.common.Status;
import com.customweb.shopware.plugin.api.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a plugin version. This entity is used for both receiving and
 * sending data, therefore some of the properties are not immutable as they need
 * to be updated and sent after the initial upload.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PluginVersion {

	private final long id;
	private final String name;
	private final String remoteLink;
	private String version;
	private final Status status;
	private List<SoftwareVersion> compatibleSoftwareVersions;
	private final List<ChangeLog> changeLogs;
	private final LocalDateTime creationDate;
	private final LocalDateTime lastChangeDate;
	private final List<Archives> archives;
	private final Assessment assessment;
	private final boolean ionCubeEncrypted;
	private final boolean licenseCheckRequired;

	@JsonCreator
	public PluginVersion(@JsonProperty("id") long id, @JsonProperty("name") String name,
			@JsonProperty("remoteLink") String remoteLink, @JsonProperty("version") String version,
			@JsonProperty("status") Status status,
			@JsonProperty("compatibleSoftwareVersions") List<SoftwareVersion> compatibleSoftwareVersions,
			@JsonProperty("changelogs") List<ChangeLog> changeLogs, @JsonProperty("creationDate") String creationDate,
			@JsonProperty("lastChangeDate") String lastChangeDate, @JsonProperty("archives") List<Archives> archives,
			@JsonProperty("assessment") Assessment assessment, @JsonProperty("ionCubeEncrypted") boolean ionCubeEncrypted,
			@JsonProperty("licenseCheckRequired") boolean licenseCheckRequired) {
		this.id = id;
		this.name = name;
		this.remoteLink = remoteLink;
		this.version = version;
		this.status = status;
		this.compatibleSoftwareVersions = compatibleSoftwareVersions;
		this.changeLogs = changeLogs;
		this.creationDate = DateUtil.parseDate(creationDate);
		this.lastChangeDate = DateUtil.parseDate(lastChangeDate);
		this.archives = archives;
		this.assessment = assessment;
		this.ionCubeEncrypted = ionCubeEncrypted;
		this.licenseCheckRequired = licenseCheckRequired;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRemoteLink() {
		return remoteLink;
	}

	public String getVersion() {
		return version;
	}

	public Status getStatus() {
		return status;
	}

	public List<SoftwareVersion> getCompatibleSoftwareVersions() {
		return compatibleSoftwareVersions;
	}

	@JsonProperty("changelogs")
	public List<ChangeLog> getChangeLogs() {
		return changeLogs;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	public LocalDateTime getLastChangeDate() {
		return lastChangeDate;
	}

	public List<Archives> getArchives() {
		return archives;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public boolean isIonCubeEncrypted() {
		return ionCubeEncrypted;
	}

	public boolean isLicenseCheckRequired() {
		return licenseCheckRequired;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setCompatibleSoftwareVersions(List<SoftwareVersion> compatibleSoftwareVersions) {
		this.compatibleSoftwareVersions = compatibleSoftwareVersions;
	}

}
