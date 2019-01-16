package com.customweb.shopware.plugin.api.model.version;

import java.time.LocalDateTime;
import java.util.List;

import com.customweb.shopware.plugin.api.util.AssessmentDeserializer;
import com.customweb.shopware.plugin.api.util.AssessmentSerializer;
import com.customweb.shopware.plugin.api.util.DateUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = AssessmentDeserializer.class)
@JsonSerialize(using = AssessmentSerializer.class)
public final class Assessment {

	private final Long id;
	private final Long baseValue;
	private final Long resultValue;
	private final String comment;
	private final LocalDateTime assessmentDate;
	private final boolean assessed;
	private final List<AssessmentFactor> factors;

	// This constructor is private, so that the value for 'assessed' cannot be set
	// directly.
	private Assessment(Long id, Long baseValue, Long resultValue, String comment, String assessmentDate, boolean assessed,
			List<AssessmentFactor> factors) {
		this.id = id;
		this.baseValue = baseValue;
		this.resultValue = resultValue;
		this.comment = comment;
		if (assessmentDate != null) {
			this.assessmentDate = DateUtil.parseDate(assessmentDate);
		} else {
			this.assessmentDate = null;
		}
		this.factors = factors;
		this.assessed = assessed;
	}

	public Assessment(Long id, Long baseValue, Long resultValue, String comment, String assessmentDate,
			List<AssessmentFactor> factors) {
		this(id, baseValue, resultValue, comment, assessmentDate, true, factors);
	}

	public Assessment() {
		this(null, null, null, null, null, false, null);
	}

	public Long getId() {
		return id;
	}

	public Long getBaseValue() {
		return baseValue;
	}

	public Long getResultValue() {
		return resultValue;
	}

	public String getComment() {
		return comment;
	}

	public LocalDateTime getAssessmentDate() {
		return assessmentDate;
	}

	public boolean isAssessed() {
		return assessed;
	}

	public List<AssessmentFactor> getFactors() {
		return factors;
	}

}
