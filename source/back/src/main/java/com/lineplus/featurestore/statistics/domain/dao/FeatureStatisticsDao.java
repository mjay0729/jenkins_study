package com.lineplus.featurestore.statistics.domain.dao;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class FeatureStatisticsDao {
    private long featureGroupID;
    private String featureGroupName;
    private long featureSetID;
    private String featureSetName;
    private List<TagsDao> tags;
    private String description;
    private String creator;
    private LocalDateTime creationDate;
    private LocalDateTime updateTime;
    private List<FeaturesDao> features;

}
