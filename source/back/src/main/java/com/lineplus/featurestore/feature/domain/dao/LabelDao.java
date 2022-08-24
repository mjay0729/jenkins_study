package com.lineplus.featurestore.feature.domain.dao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LabelDao {
    private long baseFeatureGroupId;
    private long baseFeatureId;
    private long labelId;
    private String labelName;
    private String description;
    private String labelCondition;
}
