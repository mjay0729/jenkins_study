package com.lineplus.featurestore.setoperation.repository;

import com.lineplus.featurestore.setoperation.domain.OperatedSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatedSetRepository extends JpaRepository<OperatedSet, Long>, QuerydslPredicateExecutor<OperatedSet> {
}
