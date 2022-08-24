package com.lineplus.featurestore.statistics.domain.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class FeatureStatisticsRequestDto implements Serializable {
    private Long featureGroupID;
    private Long targetFeatureSetID;

    public boolean isNullTargetFeatureSetID() {
        if(this.targetFeatureSetID == null){
            return true;
        }
        return false;
    }
}
