package com.customweb.shopware.plugin.api.model.cred;

import java.util.Objects;

/**
 * Shopware credentials.
 */
public class ShopwareCredentials {

	private final String username;
	private final String password;
	private final long producerId;

	public ShopwareCredentials(String username, String password, long producerId) {
		this.username = Objects.requireNonNull(username);
		this.password = Objects.requireNonNull(password);
		this.producerId = producerId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public long getProducerId() {
		return producerId;
	}

}
