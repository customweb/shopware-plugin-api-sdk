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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author ivantodorovic
 */
class ShopwareHttpUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.registerModule(new JavaTimeModule());
	}

	private static final String TOKEN_HEADER_NAME = "X-Shopware-Token";

	static <T, U> U executeRequest(String url, T sourceData, Class<U> outputType, String token, String method) {
		try (InputStream is = executeRequestInternal(buildUrl(url), method, sourceData, token)) {
			String r = IOUtils.toString(is);
			System.out.println(r);
			return objectMapper.readValue(r, outputType);
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
			throw new IllegalStateException(e);
		}

		addRequestHeaders(conn, token, 0, "POST");

		InputStream is = null;
		try {
			MultiPartUtil multiPart = new MultiPartUtil(conn);
			multiPart.addFilePart("file", fileContent);
			multiPart.finish();
			is = getHttpResponseInputStream(conn);
			String r = IOUtils.toString(is);
			System.out.println(r);
			return objectMapper.readValue(r, outputType);
		} catch (IOException exc) {
			exc.printStackTrace();
			return null;
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
			String r = IOUtils.toString(is);
			System.out.println(r);
			return objectMapper.readValue(r, outputType);
		} catch (IOException exc) {
			exc.printStackTrace();
			return null;
		}
	}

	static <U> U executeRequest(String url, TypeReference<U> outputType, String token) {
		System.out.println(url);
		try (InputStream is = executeRequestInternal(buildUrl(url), "GET", null, token)) {
			String r = IOUtils.toString(is);
			System.out.println(r);
			return objectMapper.readValue(r, outputType);
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
				System.out.println("SENDING (" + httpMethod + ") " + objectMapper.writeValueAsString(sourceData));
				data = objectMapper.writeValueAsString(sourceData).getBytes(StandardCharsets.UTF_8);
				dataLength = data.length;
			} catch (Exception exc) {
				throw new RuntimeException(exc);
			}
		}

		HttpsURLConnection conn;
		try {
			conn = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			throw new IllegalStateException(e);
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
		if (token != null) {
			// add token header
		}
		try {
			conn.setRequestMethod(requestMethod);
		} catch (ProtocolException exc) {
			throw new IllegalStateException(exc);
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
			throw new IllegalStateException(e);
		}
	}

	private static InputStream getHttpResponseInputStream(HttpsURLConnection conn) {
		try {
			int code = conn.getResponseCode();
			if (code > 199 && code < 300) {
				return conn.getInputStream();
			} else {
				System.out.println(IOUtils.toString(conn.getErrorStream()));
				throw new IllegalStateException("response code was " + code);
			}
		} catch (IOException e) {
			throw new IllegalStateException("Cannot read response from connection.", e);
		}
	}

	private static URL buildUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
