package com.lineplus.featurestore.feature.domain;


import com.lineplus.featurestore.global.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="target_feature")
public class TargetFeature extends BaseEntity {

    @ManyToOne(targetEntity =BaseFeatureGroup.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "base_feature_group_id")
    private BaseFeatureGroup baseFeatureGroup;

    @ManyToOne(targetEntity = BaseFeature.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "base_feature_id")
    private BaseFeature baseFeature;

    @ManyToOne(targetEntity =TargetFeatureSet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "target_feature_set_id")
    private TargetFeatureSet targetFeatureSet;

    @ManyToOne(targetEntity = Label.class, fetch = FetchType.LAZY)
    @JoinColumn(name="label_id")
    private Label label;

    @Column(name = "feature_condition", length = 256)
    private String featureCondition;
}


