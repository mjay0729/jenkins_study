package com.lineplus.featurestore.statistics.domain.dao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeatureRangesDao {
    private long statisticsID;
    private long featureID;
    private long featureGroupID;
    private long targetFeatureSetID;
    private long targetFeatureID;
    private long count;
    private LocalDateTime creationDate;
}
