package com.lineplus.featurestore.feature.domain;


import com.lineplus.featurestore.feature.domain.enums.OperatedSetType;
import com.lineplus.featurestore.feature.domain.enums.Operator;
import com.lineplus.featurestore.global.common.domain.BaseEntity;
import com.lineplus.featurestore.feature.domain.enums.UpdateInterval;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="target_feature_set")
public class TargetFeatureSet extends BaseEntity {

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "description", length = 256)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="update_interval", length = 1)
    private UpdateInterval updateInterval;

    @Column(name = "is_batch")
    private boolean isBatch;

    @Setter
    @Column(name = "cassandra_table_name", length = 256)
    private String cassandraTableName;

    @Setter
    @ManyToOne(targetEntity =TargetFeatureSet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "target_feature_set_a_id")
    private TargetFeatureSet targetFeatureSetA;

    @Setter
    @ManyToOne(targetEntity =TargetFeatureSet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "target_feature_set_b_id")
    private TargetFeatureSet targetFeatureSetB;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator")
    private Operator operator;

    /**
     * 카산드라 MID List의 크기
     */
    @Column(name="total_count")
    private Integer totalCount;

    @Column(name="estimation_count")
    private Integer estimationCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "operated_set_type")
    private OperatedSetType operatedSetType;
}
