package com.lineplus.featurestore.setoperation.domain;


import com.lineplus.featurestore.global.common.domain.BaseEntity;
import com.lineplus.featurestore.setoperation.domain.enums.OperatedSetType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="operated_set")
public class OperatedSet extends BaseEntity {

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "description", length = 256)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name= "type", length = 3)
    private OperatedSetType type;

    @Column(name = "count")
    private Integer count;


}
