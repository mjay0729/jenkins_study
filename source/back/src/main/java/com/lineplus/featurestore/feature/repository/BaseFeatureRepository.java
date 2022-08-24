package com.lineplus.featurestore.feature.repository;

import com.lineplus.featurestore.feature.domain.BaseFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseFeatureRepository extends JpaRepository<BaseFeature,Long>, QuerydslPredicateExecutor<BaseFeature> {
}
