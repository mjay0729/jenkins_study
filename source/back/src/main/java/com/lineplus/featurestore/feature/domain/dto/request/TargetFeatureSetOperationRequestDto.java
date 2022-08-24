package com.lineplus.featurestore.feature.domain.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lineplus.featurestore.feature.domain.enums.Operator;
import com.lineplus.featurestore.feature.domain.enums.UpdateInterval;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TargetFeatureSetOperationRequestDto {

    private String featureSetName;
    private String description;
    private List<String> tagList;
    private List<TargetFeatureSetForOperationRequestDto> targetFeatureSet;
    private Integer estimationCount;
    private boolean isBatch;
    private UpdateInterval updateInterval;
    private Operator operator;
}
