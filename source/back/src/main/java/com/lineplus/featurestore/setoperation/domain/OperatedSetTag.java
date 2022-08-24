package com.lineplus.featurestore.setoperation.domain;

import com.lineplus.featurestore.global.common.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="operated_set_tag")
public class OperatedSetTag  extends BaseEntity {

    @ManyToOne(targetEntity =OperatedSet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "operated_set_id")
    private OperatedSet operatedSet;

    @Column(name = "name", length = 20)
    private String name;

}
