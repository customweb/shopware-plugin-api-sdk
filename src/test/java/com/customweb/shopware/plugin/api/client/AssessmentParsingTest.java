package com.customweb.shopware.plugin.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Test;

import com.customweb.shopware.plugin.api.model.version.Assessment;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for custom deserialisation of {@link Assessment} object.
 */
public class AssessmentParsingTest {

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Test when there is actually a full 'assessment' object.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testObject() throws Exception {
		InputStream input = getClass().getClassLoader()
				.getResourceAsStream("com/customweb/shopware/plugin/api/client/assessment-full-test.json");
		Assessment assessment = mapper.readValue(input, Assessment.class);
		assertTrue(assessment.isAssessed());
		assertEquals(1, assessment.getFactors().size());
	}

	/**
	 * Test when 'assessment' is just a simple boolean property and not an object.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testProperty() throws Exception {
		InputStream input = getClass().getClassLoader()
				.getResourceAsStream("com/customweb/shopware/plugin/api/client/assessment-empty-test.json");
		Assessment assessment = mapper.readValue(input, Assessment.class);
		assertFalse(assessment.isAssessed());
	}
}
