package com.lineplus.featurestore.feature.domain;

import com.lineplus.featurestore.global.common.domain.BaseEntity;
import com.lineplus.featurestore.setoperation.domain.OperatedSet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="operated_set_venn_diagram")
public class OperatedSetVennDiagram extends BaseEntity {

    @ManyToOne(targetEntity = OperatedSet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "target_feature_set_id")
    private TargetFeatureSet targetFeatureSet;

    @JoinColumn(name = "first_set_count")
    private Integer firstSetCount;

    @JoinColumn(name = "second_set_count")
    private Integer secondSetCount;

    @JoinColumn(name = "joined_set_count")
    private Integer joinedSetCount;

}
