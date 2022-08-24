package com.lineplus.featurestore.feature.domain.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TargetFeaturesRequestDto {

    private long baseFeatureGroupId;
    private long baseFeatureId;
    private BaseFeaturesAttribute baseFeaturesAttribute;
    List<AttributeRequestDto> attributeList;
}
