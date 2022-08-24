package com.lineplus.featurestore.statistics.domain;


import com.lineplus.featurestore.feature.domain.BaseFeature;
import com.lineplus.featurestore.feature.domain.BaseFeatureGroup;
import com.lineplus.featurestore.feature.domain.TargetFeature;
import com.lineplus.featurestore.feature.domain.TargetFeatureSet;
import com.lineplus.featurestore.global.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AbstractStatistics extends BaseEntity {

    @ManyToOne(targetEntity = BaseFeatureGroup.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "base_feature_group_id")
    protected BaseFeatureGroup baseFeatureGroup;

    @ManyToOne(targetEntity = BaseFeature.class, fetch = FetchType.LAZY)
    @JoinColumn(name ="base_feature_id")
    protected BaseFeature baseFeature;

    @ManyToOne(targetEntity = TargetFeatureSet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "target_feature_set_id")
    protected TargetFeatureSet targetFeatureSet;

    @ManyToOne(targetEntity = TargetFeature.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "target_feature_id")
    protected TargetFeature targetFeature;

    public static Set<Long> getBaseFeatureGroupIdSet(List<? extends AbstractStatistics> abstractStatistics) {
        Set<Long> results = new HashSet<>();
        for(AbstractStatistics target : abstractStatistics){
            results.add(target.getBaseFeatureGroup().getId());
        }
        return results;
    }
}
