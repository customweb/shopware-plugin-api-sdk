package com.customweb.shopware.plugin.api.model.version;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.customweb.shopware.plugin.api.model.common.Status;
import com.customweb.shopware.plugin.api.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PluginVersion {

	private final long id;
	private final String name;
	private final String remoteLink;
	private final String version;
	private final Status status;
	private final List<SoftwareVersion> compatibleSoftwareVersions;
	private final List<ChangeLog> changeLogs;
	private final LocalDateTime creationDate;
	private final LocalDateTime lastChangeDate;
	private final List<Archives> archives;
	private final boolean assessment;
	private final boolean ionCubeEncrypted;
	private final boolean licenseCheckRequired;

	@JsonCreator
	public PluginVersion(@JsonProperty("id") long id, @JsonProperty("name") String name,
			@JsonProperty("remoteLink") String remoteLink, @JsonProperty("version") String version,
			@JsonProperty("status") Status status,
			@JsonProperty("compatibleSoftwareVersions") List<SoftwareVersion> compatibleSoftwareVersions,
			@JsonProperty("changelogs") List<ChangeLog> changeLogs, @JsonProperty("creationDate") String creationDate,
			@JsonProperty("lastChangeDate") String lastChangeDate, @JsonProperty("archives") List<Archives> archives,
			@JsonProperty("assessment") boolean assessment, @JsonProperty("ionCubeEncrypted") boolean ionCubeEncrypted,
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
		return "https://sbp-plugin-binaries.s3.eu-west-1.amazonaws.com/7788_1541080924_dfd7492db3158348fbee8cd9fefd6dda.zip";
		// return remoteLink;
	}

	public String getVersion() {
		return "1.0.1";
		// return version;
	}

	public Status getStatus() {
		return status;
	}

	public List<SoftwareVersion> getCompatibleSoftwareVersions() {
		// return compatibleSoftwareVersions;
		return Arrays.asList(new SoftwareVersion("5.3.3"));
	}

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

	public boolean isAssessment() {
		return assessment;
	}

	public boolean isIonCubeEncrypted() {
		return ionCubeEncrypted;
	}

	public boolean isLicenseCheckRequired() {
		return licenseCheckRequired;
	}

}
