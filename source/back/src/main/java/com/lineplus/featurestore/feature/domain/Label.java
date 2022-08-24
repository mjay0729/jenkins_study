package com.lineplus.featurestore.feature.domain;


import com.lineplus.featurestore.global.common.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="label")
public class Label extends BaseEntity {

    @Column(name = "name", length = 20)
    private String name;

    @ManyToOne(targetEntity = BaseFeatureGroup.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "base_feature_group_id", nullable = false)
    private BaseFeatureGroup baseFeatureGroupId;


    @ManyToOne(targetEntity = BaseFeature.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "base_feature_id", nullable = false)
    private BaseFeature baseFeatureId;

    @ManyToOne(targetEntity = TargetFeatureSet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "target_feature_set_id", nullable = true)
    private TargetFeatureSet targetFeatureSetId;

    @ManyToOne(targetEntity = TargetFeature.class, fetch = FetchType.LAZY)
    @JoinColumn(name="target_feature_id", nullable = true)
    private TargetFeature targetFeatureId;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "label_condition", length = 256)
    private String labelCondition;

}


