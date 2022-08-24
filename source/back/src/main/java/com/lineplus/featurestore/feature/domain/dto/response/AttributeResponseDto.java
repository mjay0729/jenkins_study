package com.lineplus.featurestore.feature.domain.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AttributeResponseDto {
    private String baseFeatureGroupDepthPath;
    private BaseFeaturesAttribute baseFeaturesAttribute;
    private List<?> attributeList;
}
