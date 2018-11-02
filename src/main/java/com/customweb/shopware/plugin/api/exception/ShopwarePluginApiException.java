package com.customweb.shopware.plugin.api.exception;

/**
 * Exception is thrown when there is an error while processing Shopware plugins.
 */
public class ShopwarePluginApiException extends RuntimeException {

	private static final long serialVersionUID = 6017966158891141431L;

	/**
	 * Constructor.
	 */
	public ShopwarePluginApiException() {
		super();
	}

	/**
	 * Inherited constructor.
	 *
	 * @param message
	 *            the detailed exception message
	 */
	public ShopwarePluginApiException(String message) {
		super(message);
	}

	/**
	 * Inherited constructor.
	 *
	 * @param message
	 *            the detailed exception message
	 * @param cause
	 *            underlying cause exception
	 */
	public ShopwarePluginApiException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Inherited constructor.
	 *
	 * @param cause
	 *            underlying cause exception
	 */
	public ShopwarePluginApiException(Throwable cause) {
		super(cause);
	}
}
