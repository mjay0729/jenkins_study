package com.lineplus.featurestore.feature.domain.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EditTargetFeatureSetRequestDto {
    private String featureSetName;
    private String description;
    private List<String> tagList;
}
