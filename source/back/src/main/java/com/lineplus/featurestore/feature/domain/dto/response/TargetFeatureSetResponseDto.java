package com.lineplus.featurestore.feature.domain.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lineplus.featurestore.feature.domain.dao.TargetFeatureSetTagDao;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TargetFeatureSetResponseDto {
    private long targetFeatureSetId;
    private String targetFeatureSetName;
    private String targetFeatureSetDescription;
    private List<TargetFeatureSetTagDao> targetFeatureSetTagList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
