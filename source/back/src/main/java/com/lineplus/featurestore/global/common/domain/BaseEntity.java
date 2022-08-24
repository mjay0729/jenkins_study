package com.lineplus.featurestore.global.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected long id;

    @Column(name = "created_by", length = 20)
    protected String createdBy;

    @Column(name = "updated_by", length = 20)
    protected String updatedBy;

    @Column(name="created_at")
    @CreationTimestamp
    protected LocalDateTime createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    protected LocalDateTime updateAt;

}
