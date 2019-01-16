package com.customweb.shopware.plugin.api.util;

import java.io.IOException;

import com.customweb.shopware.plugin.api.model.version.Assessment;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Custom serialiser for {@link Assessment}. This is needed, because assessment
 * can be of different type depending on the situation, so we have to fake it on
 * our side.
 */
public class AssessmentSerializer extends StdSerializer<Assessment> {

	private static final long serialVersionUID = -3079983542141527505L;

	public AssessmentSerializer() {
		super(Assessment.class);
	}

	@Override
	public void serialize(Assessment value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (value.isAssessed()) {
			gen.writeStartObject();
			gen.writeNumberField("id", value.getId());
			gen.writeNumberField("baseValue", value.getBaseValue());
			gen.writeNumberField("resultValue", value.getResultValue());
			gen.writeStringField("comments", value.getComment());
			gen.writeStringField("assessmentDate", DateUtil.formatDate(value.getAssessmentDate()));
			gen.writeObjectField("factors", value.getFactors());
			gen.writeEndObject();
		} else {
			gen.writeBoolean(false);
		}
	}
}
