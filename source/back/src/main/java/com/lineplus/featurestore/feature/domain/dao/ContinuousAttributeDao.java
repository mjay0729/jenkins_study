package com.lineplus.featurestore.feature.domain.dao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ContinuousAttributeDao {
    private long baseFeatureGroupId;
    private long baseFeatureId;
    private long ContinuousAttributeId;
    private float min;
    private float max;
    private float average;
}
