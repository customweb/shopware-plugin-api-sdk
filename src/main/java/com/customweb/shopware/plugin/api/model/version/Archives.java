package com.customweb.shopware.plugin.api.model.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Archives {

	private final long id;
	private final String remoteLink;
	private final String shopwareMajorVersion;
	private final boolean ionCubeEncrypted;

	@JsonCreator
	public Archives(@JsonProperty("id") long id, @JsonProperty("remoteLink") String remoteLink,
			@JsonProperty("shopwareMajorVersion") String shopwareMajorVersion,
			@JsonProperty("ioncubeEncrypted") boolean ionCubeEncrypted) {
		this.id = id;
		this.remoteLink = remoteLink;
		this.shopwareMajorVersion = shopwareMajorVersion;
		this.ionCubeEncrypted = ionCubeEncrypted;
	}

	public long getId() {
		return id;
	}

	public String getRemoteLink() {
		return remoteLink;
	}

	public String getShopwareMajorVersion() {
		return shopwareMajorVersion;
	}

	@JsonProperty("ioncubeEncrypted")
	public boolean isIonCubeEncrypted() {
		return ionCubeEncrypted;
	}

}
