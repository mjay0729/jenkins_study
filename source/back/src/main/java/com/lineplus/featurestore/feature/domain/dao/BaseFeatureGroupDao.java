package com.lineplus.featurestore.feature.domain.dao;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseFeatureGroupDao {

    private long baseFeatureGroupId;
    private String depthPath;
    private String baseFeatureGroupName;
    private String description;
    private Integer totalCount;

    private List<BaseFeatureDao> baseFeature = new ArrayList<>();
    private List<BaseFeatureGroupDao> children = new ArrayList<>();

}
