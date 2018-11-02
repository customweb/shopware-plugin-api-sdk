package com.customweb.shopware.plugin.api.model.token;

/**
 * Request for the access token for Shopware system.
 */
public class AccessTokenRequest {

	private final String shopwareId;
	private final String password;

	public AccessTokenRequest(String shopwareId, String password) {
		this.shopwareId = shopwareId;
		this.password = password;
	}

	public String getShopwareId() {
		return shopwareId;
	}

	public String getPassword() {
		return password;
	}

}
