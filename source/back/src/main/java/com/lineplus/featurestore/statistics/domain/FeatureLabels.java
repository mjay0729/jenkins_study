package com.lineplus.featurestore.statistics.domain;


import com.lineplus.featurestore.feature.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="feature_labels")
public class FeatureLabels extends AbstractStatistics {

    @OneToOne(targetEntity = Label.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id")
    private Label labels;

    @Column(name = "count")
    private Integer count;

    public static Map<Long, ArrayList<FeatureLabels>> groupFeatureLabelsByBaseFeature(List<FeatureLabels> featureLabels) {
        Map<Long, ArrayList<FeatureLabels>> results = new HashMap<>();
        for(FeatureLabels target : featureLabels){
            if(results.containsKey(target.targetFeature.getId())){
                results.get(target.targetFeature.getId()).add(target);
            }else{
                ArrayList<FeatureLabels> labelStatistics = new ArrayList<>();
                labelStatistics.add(target);
                results.put(target.targetFeature.getId(), labelStatistics);
            }
        }
        return results;
    }

}

