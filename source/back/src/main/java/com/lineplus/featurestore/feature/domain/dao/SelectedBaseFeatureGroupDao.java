package com.lineplus.featurestore.feature.domain.dao;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SelectedBaseFeatureGroupDao {
    private String baseFeatureGroupName;
    private List<SelectedTargetFeatureDao> selectedTargetFeatureList;
}
