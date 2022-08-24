package com.lineplus.featurestore.statistics.domain.dao;

import lombok.Data;

@Data
public class TagsDao {
    private long targetFeatureSetTagID;
    private String targetFeatureSetName;
    private long featureSetID;
}
