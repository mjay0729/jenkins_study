package com.lineplus.featurestore.feature.domain;

import com.lineplus.featurestore.global.common.domain.BaseEntity;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="base_feature")
public class BaseFeature extends BaseEntity {

    @ManyToOne(targetEntity =BaseFeatureGroup.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "base_feature_group_id")
    private BaseFeatureGroup baseFeatureGroup;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name= "type", length = 10)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name="attribute_class")
    private BaseFeaturesAttribute attributeClass;

}
