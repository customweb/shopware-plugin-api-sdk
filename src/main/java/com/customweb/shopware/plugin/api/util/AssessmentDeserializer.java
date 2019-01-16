/**
 * 
 */
package com.customweb.shopware.plugin.api.util;

import java.io.IOException;
import java.util.List;

import com.customweb.shopware.plugin.api.model.version.Assessment;
import com.customweb.shopware.plugin.api.model.version.AssessmentFactor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Custom deserialiser for {@link Assessment}. This is needed, because
 * assessment can be of different type depending on the situation, so we have to
 * fake it on our side.
 */
public class AssessmentDeserializer extends StdDeserializer<Assessment> {

	private static final long serialVersionUID = 3135681576456825417L;

	public AssessmentDeserializer() {
		super(Assessment.class);
	}

	@Override
	public Assessment deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode root = jp.getCodec().readTree(jp);

		if (root.size() == 0) {
			// It's not an object, let's try to parse it as boolean.
			String textVal = root.asText();
			if (textVal != null && textVal.equalsIgnoreCase("false")) {
				return new Assessment();
			} else {
				throw new IllegalArgumentException(
						"When the value for 'assessment' is not an object it's only allowed to be a boolean equal to 'false'.");
			}
		} else {
			// The value is an object, we can get full data.
			long id = root.get("id").asLong();
			long baseValue = root.get("baseValue").asLong();
			long resultValue = root.get("resultValue").asLong();
			String comment = root.get("comment").asText();
			String assessmentDate = root.get("assessmentDate").asText();
			List<AssessmentFactor> factors = mapToObject(root.get("factors"), new TypeReference<List<AssessmentFactor>>() {
			});

			return new Assessment(id, baseValue, resultValue, comment, assessmentDate, factors);
		}
	}

	private <T> T mapToObject(JsonNode node, TypeReference<T> valueType) throws IOException, JsonProcessingException {
		final JsonParser parser = node.traverse();
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(parser, valueType);
	}
}
