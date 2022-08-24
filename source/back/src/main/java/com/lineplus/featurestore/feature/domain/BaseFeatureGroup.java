package com.lineplus.featurestore.feature.domain;

import javax.persistence.*;

import com.lineplus.featurestore.global.common.domain.BaseEntity;
import com.lineplus.featurestore.feature.domain.enums.UpdateInterval;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "base_feature_group")
public class BaseFeatureGroup extends BaseEntity {

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name="depth_path", length = 256)
    private String depthPath;

    @Enumerated(EnumType.STRING)
    @Column(name="updated_interval", length = 1)
    private UpdateInterval updatedInterval;

    @Column(name="total_count")
    private Integer totalCount;
}
