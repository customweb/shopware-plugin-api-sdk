package com.customweb.shopware.plugin.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.customweb.shopware.plugin.api.model.cred.ShopwareCredentials;
import com.customweb.shopware.plugin.api.model.plugin.PluginInformation;
import com.customweb.shopware.plugin.api.model.version.PluginVersion;
import com.customweb.shopware.plugin.api.model.version.SoftwareVersion;

public class ShopwareClientTest {

	@Test
	@Ignore
	public void loginTest() {
		ShopwarePluginApiClient client = new ShopwarePluginApiClient(readCredentials());
		List<PluginInformation> infoList = client.listPlugins(false, 50, 0);
		System.out.println(infoList.size());
		// client.pluginDetails(5304);
	}

	@Test
	public void uploadFile() {
		ShopwarePluginApiClient client = new ShopwarePluginApiClient(readCredentials());
		PluginVersion v = client.uploadPluginVersion(7788, readFile());

		v.setVersion("1.1.1");
		v.setCompatibleSoftwareVersions(Arrays.asList(new SoftwareVersion("5.3.4"), new SoftwareVersion("5.3.5")));

		v.getChangeLogs().forEach(c -> c.setText("This is a change description"));

		client.updatePluginVersion(7788, v);
	}

	private byte[] readFile() {
		try {
			return IOUtils.toByteArray(
					getClass().getClassLoader().getResourceAsStream("com/customweb/shopware/plugin/api/client/test.zip"));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private ShopwareCredentials readCredentials() {
		if (System.getenv("CW_SHOPWARE_USERNAME") != null) {
			return new ShopwareCredentials(System.getenv("CW_SHOPWARE_USERNAME"), System.getenv("CW_SHOPWARE_PASSWORD"),
					Long.parseLong(System.getenv("CW_SHOPWARE_PRODUCER")));
		} else {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("credentials.properties");
			if (is == null) {
				throw new IllegalStateException("Cannot load credentials.");
			}
			Properties properties = new Properties();
			try {
				properties.load(is);
			} catch (IOException e) {
				throw new IllegalStateException("Cannot load credentials.", e);
			}
			return new ShopwareCredentials(properties.getProperty("username"), properties.getProperty("password"),
					Long.parseLong(properties.getProperty("producerId")));
		}
	}
}
