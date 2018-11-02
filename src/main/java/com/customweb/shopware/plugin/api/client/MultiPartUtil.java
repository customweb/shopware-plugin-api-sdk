package com.customweb.shopware.plugin.api.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import com.customweb.shopware.plugin.api.exception.ShopwarePluginApiException;

/**
 * Utility that heps with uploading multipart content.
 */
class MultiPartUtil {
	private final String boundary;
	private static final String LINE_FEED = "\r\n";
	private OutputStream outputStream;
	private PrintWriter writer;

	MultiPartUtil(HttpURLConnection httpConn) {
		this.boundary = "===" + System.currentTimeMillis() + "===";

		httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		try {
			outputStream = httpConn.getOutputStream();
			writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
		} catch (IOException e) {
			throw new ShopwarePluginApiException(e);
		}

	}

	void addFilePart(String fieldName, byte[] uploadFile) throws IOException {
		writer.append("--" + boundary).append(LINE_FEED);
		writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"plugin.zip\"").append(LINE_FEED);
		writer.append("Content-Type: application/zip").append(LINE_FEED);
		writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
		writer.append(LINE_FEED);
		writer.flush();

		outputStream.write(uploadFile, 0, uploadFile.length);
		outputStream.flush();

		writer.append(LINE_FEED);
		writer.flush();
	}

	void finish() throws IOException {
		writer.append(LINE_FEED).flush();
		writer.append("--" + boundary + "--").append(LINE_FEED);
		writer.close();
	}
}
