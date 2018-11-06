package com.customweb.shopware.plugin.api.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;

import com.customweb.shopware.plugin.api.exception.ShopwarePluginApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Utility that helps with sending requests of HTTP connnections.
 */
class ShopwareHttpUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.registerModule(new JavaTimeModule());
	}

	private static final String TOKEN_HEADER_NAME = "X-Shopware-Token";

	static <T, U> U executeRequest(String url, T sourceData, Class<U> outputType, String token, String method) {
		try (InputStream is = executeRequestInternal(buildUrl(url), method, sourceData, token)) {
			return objectMapper.readValue(is, outputType);
		} catch (IOException exc) {
			exc.printStackTrace();
			return null;
		}
	}

	static <T, U> U executeMultipartRequest(String url, TypeReference<U> outputType, byte[] fileContent, String token) {
		HttpsURLConnection conn;
		try {
			conn = (HttpsURLConnection) buildUrl(url).openConnection();
		} catch (IOException e) {
			throw new ShopwarePluginApiException(e);
		}

		addRequestHeaders(conn, token, 0, "POST");

		InputStream is = null;
		try {
			MultiPartUtil multiPart = new MultiPartUtil(conn);
			multiPart.addFilePart("file", fileContent);
			multiPart.finish();
			is = getHttpResponseInputStream(conn);
			return objectMapper.readValue(is, outputType);
		} catch (IOException exc) {
			throw new ShopwarePluginApiException(exc);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// ignore, not much we can do here
				}
			}
		}
	}

	static <U> U executeRequest(String url, Class<U> outputType, String token) {
		try (InputStream is = executeRequestInternal(buildUrl(url), "GET", null, token)) {
			return objectMapper.readValue(is, outputType);
		} catch (IOException exc) {
			exc.printStackTrace();
			return null;
		}
	}

	static <U> U executeRequest(String url, TypeReference<U> outputType, String token) {
		try (InputStream is = executeRequestInternal(buildUrl(url), "GET", null, token)) {
			return objectMapper.readValue(is, outputType);
		} catch (IOException exc) {
			exc.printStackTrace();
			return null;
		}
	}

	private static <T> InputStream executeRequestInternal(URL url, String httpMethod, T sourceData, String token) {
		byte[] data = null;
		int dataLength = 0;

		if (sourceData != null) {
			try {
				data = objectMapper.writeValueAsString(sourceData).getBytes(StandardCharsets.UTF_8);
				dataLength = data.length;
			} catch (Exception exc) {
				throw new ShopwarePluginApiException(exc);
			}
		}

		HttpsURLConnection conn;
		try {
			conn = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			throw new ShopwarePluginApiException(e);
		}

		addRequestHeaders(conn, token, dataLength, httpMethod);

		if (data != null) {
			postData(conn, data);
		}

		InputStream is = getHttpResponseInputStream(conn);

		return is;
	}

	private static void addRequestHeaders(HttpsURLConnection conn, String token, int contentLength, String requestMethod) {
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setInstanceFollowRedirects(false);
		try {
			conn.setRequestMethod(requestMethod);
		} catch (ProtocolException exc) {
			throw new ShopwarePluginApiException(exc);
		}

		if (token != null) {
			conn.setRequestProperty(TOKEN_HEADER_NAME, token);
		}

		if (contentLength > 0) {
			conn.setFixedLengthStreamingMode(contentLength);
		}
	}

	private static void postData(HttpsURLConnection conn, byte[] data) {
		try (final DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(data);
		} catch (Exception e) {
			throw new ShopwarePluginApiException(e);
		}
	}

	private static InputStream getHttpResponseInputStream(HttpsURLConnection conn) {
		try {
			int code = conn.getResponseCode();
			if (code > 199 && code < 300) {
				return conn.getInputStream();
			} else {
				throw new ShopwarePluginApiException(
						"Response code was " + code + "; error text: " + IOUtils.toString(conn.getErrorStream()));
			}
		} catch (IOException e) {
			throw new ShopwarePluginApiException("Cannot read response from connection.", e);
		}
	}

	private static URL buildUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new ShopwarePluginApiException(e);
		}
	}
}
