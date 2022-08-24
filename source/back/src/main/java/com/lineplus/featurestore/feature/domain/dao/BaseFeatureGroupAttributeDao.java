package com.lineplus.featurestore.feature.domain.dao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseFeatureGroupAttributeDao {
    private String baseFeatureGroupDepthPath;
    private BaseFeaturesAttribute baseFeaturesAttribute;

}
