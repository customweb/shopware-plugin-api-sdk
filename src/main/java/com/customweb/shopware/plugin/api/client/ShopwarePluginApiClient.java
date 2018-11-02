package com.customweb.shopware.plugin.api.client;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Objects;

import com.customweb.shopware.plugin.api.model.ShopwareEndpoints;
import com.customweb.shopware.plugin.api.model.cred.ShopwareCredentials;
import com.customweb.shopware.plugin.api.model.plugin.PluginInformation;
import com.customweb.shopware.plugin.api.model.review.ReviewResponse;
import com.customweb.shopware.plugin.api.model.token.AccessToken;
import com.customweb.shopware.plugin.api.model.token.AccessTokenRequest;
import com.customweb.shopware.plugin.api.model.version.PluginVersion;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Client for Shopware plugin management API.
 */
public class ShopwarePluginApiClient {

	private ShopwareCredentials credentials;

	private AccessToken accessToken = null;

	private final static TemporalAmount EXPIRY_BUFFER = Duration.ofMinutes(5);

	/**
	 * Constructor.
	 * 
	 * @param username
	 *            Shopware username
	 * @param password
	 *            Shopware password
	 * @param producerId
	 *            Shopware producer id
	 */
	public ShopwarePluginApiClient(ShopwareCredentials credentials) {
		Objects.requireNonNull(credentials);
		this.credentials = credentials;
	}

	/**
	 * List all the uploaded plugins. Because querying a lot of plugins can be slow,
	 * this method allows to make use of pagination with limit and offset
	 * parameters.
	 * 
	 * @param includeInactive
	 *            true, if inactive plugins should be included
	 * @param limit
	 *            how many results to return
	 * @param offset
	 *            which result to start on
	 * @return list of plugins
	 */
	public List<PluginInformation> listPlugins(boolean includeInactive, int limit, int offset) {
		return executeRequest(ShopwareEndpoints.PLUGINS + "?producerId=" + credentials.getProducerId() + "&includeInactive="
				+ includeInactive + "&limit=" + limit + "&offset=" + offset, new TypeReference<List<PluginInformation>>() {
				});
	}

	/**
	 * Retrieve details for the specified plugin.
	 * 
	 * @param pluginId
	 *            plugin id
	 * @return plugin detais
	 */
	public PluginInformation pluginDetails(long pluginId) {
		return executeRequest(ShopwareEndpoints.PLUGINS + "/" + pluginId, null, PluginInformation.class);
	}

	/**
	 * Upload a ZIP file containing the code for a new plugin version.
	 * 
	 * @param pluginId
	 *            plugin id
	 * @param fileContent
	 *            the ZIP file contents
	 * @return the new plugin version information
	 */
	public PluginVersion uploadPluginVersion(long pluginId, byte[] fileContent) {
		String url = ShopwareEndpoints.PLUGINS + "/" + pluginId + ShopwareEndpoints.BINARIES;
		List<PluginVersion> versions = ShopwareHttpUtil.executeMultipartRequest(url, new TypeReference<List<PluginVersion>>() {
		}, fileContent, obtainToken());
		return versions.get(0);
	}

	/**
	 * Update plugin version informaiton.
	 * 
	 * @param pluginId
	 *            plugin id
	 * @param version
	 *            plugin version information
	 * @return updated information
	 */
	public PluginVersion updatePluginVersion(long pluginId, PluginVersion version) {
		String url = ShopwareEndpoints.PLUGINS + "/" + pluginId + ShopwareEndpoints.BINARIES + "/" + version.getId();
		return executePutRequest(url, version, PluginVersion.class);
	}

	/**
	 * Retrieve plugin version information.
	 * 
	 * @param pluginId
	 *            plugin id
	 * @param versionId
	 *            plugin version id
	 * @return plugin version information.
	 */
	public PluginVersion pluginVersionStatus(long pluginId, long versionId) {
		String url = ShopwareEndpoints.PLUGINS + "/" + pluginId + ShopwareEndpoints.BINARIES + "/" + versionId;
		return executeRequest(url, null, PluginVersion.class);
	}

	/**
	 * Request plugin version review.
	 * 
	 * @param pluginId
	 *            plugin id
	 */
	public ReviewResponse requestReview(long pluginId) {
		String url = ShopwareEndpoints.PLUGINS + "/" + pluginId + ShopwareEndpoints.REVIEWS;
		return ShopwareHttpUtil.executeRequest(url, null, ReviewResponse.class, obtainToken(), "POST");
	}

	private String obtainToken() {
		if (accessToken == null || ZonedDateTime.now().plus(EXPIRY_BUFFER).isAfter(accessToken.getExpiryDate())) {
			AccessTokenRequest request = new AccessTokenRequest(credentials.getUsername(), credentials.getPassword());
			AccessToken response = ShopwareHttpUtil.executeRequest(ShopwareEndpoints.ACCESS_TOKENS, request, AccessToken.class,
					null, "POST");
			this.accessToken = response;
		}
		return accessToken.getToken();
	}

	private <U, T> U executeRequest(String url, T sourceData, Class<U> outputType) {
		String token = obtainToken();
		if (sourceData == null) {
			return ShopwareHttpUtil.executeRequest(url, outputType, token);
		} else {
			return ShopwareHttpUtil.executeRequest(url, sourceData, outputType, token, "POST");
		}
	}

	private <U, T> U executePutRequest(String url, T sourceData, Class<U> outputType) {
		String token = obtainToken();
		return ShopwareHttpUtil.executeRequest(url, sourceData, outputType, token, "PUT");
	}

	private <U, T> U executeRequest(String url, TypeReference<U> outputType) {
		String token = obtainToken();
		return ShopwareHttpUtil.executeRequest(url, outputType, token);
	}

}
