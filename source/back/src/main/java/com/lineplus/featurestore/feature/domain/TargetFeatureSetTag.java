package com.lineplus.featurestore.feature.domain;

import com.lineplus.featurestore.global.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="target_feature_set_tag")
public class TargetFeatureSetTag extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = TargetFeatureSet.class)
    @JoinColumn(name = "target_feature_set_id")
    private TargetFeatureSet targetFeatureSet;
}
