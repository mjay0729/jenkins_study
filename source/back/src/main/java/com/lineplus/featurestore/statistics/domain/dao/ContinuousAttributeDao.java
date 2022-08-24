package com.lineplus.featurestore.statistics.domain.dao;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
public class ContinuousAttributeDao {
    private long statisticsID;
    private long featureID;
    private long featureGroupID;
    private long targetFeatureSetID;
    private long targetFeatureID;
    private float min;
    private float max;
    private float average;
    private float numRanges;
    private LocalDateTime creationDate;
}
