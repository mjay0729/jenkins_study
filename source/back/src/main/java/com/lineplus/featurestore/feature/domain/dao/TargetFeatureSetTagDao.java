package com.lineplus.featurestore.feature.domain.dao;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TargetFeatureSetTagDao {
    private long targetFeatureSetTagId;
    private String targetFeatureSetTagName;
}
