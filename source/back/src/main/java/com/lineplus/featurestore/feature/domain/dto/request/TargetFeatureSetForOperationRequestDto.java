package com.lineplus.featurestore.feature.domain.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lineplus.featurestore.feature.domain.enums.OperatedSetType;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TargetFeatureSetForOperationRequestDto {

    private OperatedSetType operatedSetType;
    private List<TargetFeaturesRequestDto> targetFeaturesList;

}
