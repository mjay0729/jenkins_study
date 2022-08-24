package com.lineplus.featurestore.statistics.domain.dao;

import com.lineplus.featurestore.statistics.domain.ContinuousAttribute;
import com.lineplus.featurestore.statistics.domain.FeatureRanges;
import lombok.Data;
import java.util.List;

@Data
public class FeaturesDao {
    private long featureID;
    private String featureName;
    private long featureGroupID;
    private String featureType;
    private String description;
    private String attributeClass;
    private List<LabelDao> labels;
    private List<FeatureRangesDao> featureRanges;
    private List<ContinuousAttributeDao> continuousFeatures;
}
