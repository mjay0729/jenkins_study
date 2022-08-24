package com.lineplus.featurestore.statistics.domain;

import com.lineplus.featurestore.global.common.domain.BaseEntity;
import com.lineplus.featurestore.feature.domain.BaseFeature;
import com.lineplus.featurestore.feature.domain.BaseFeatureGroup;
import com.lineplus.featurestore.feature.domain.TargetFeature;
import com.lineplus.featurestore.feature.domain.TargetFeatureSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="feature_ranges")
public class FeatureRanges extends AbstractStatistics{

    @Column(name = "count")
    private Integer count;

    public static Map<Long, ArrayList<FeatureRanges>> groupFeatureRangesByBaseFeature(List<FeatureRanges> featureRanges) {
        Map<Long, ArrayList<FeatureRanges>> results = new HashMap<>();
        for(FeatureRanges target : featureRanges){
            if(results.containsKey(target.baseFeature.getId())){
                results.get(target.baseFeature.getId()).add(target);
            }else{
                ArrayList<FeatureRanges> rangeStatistics = new ArrayList<>();
                rangeStatistics.add(target);
                results.put(target.baseFeature.getId(), rangeStatistics);
            }
        }
        return results;
    }
}


