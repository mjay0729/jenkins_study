package com.lineplus.featurestore.statistics.repository;

import com.lineplus.featurestore.statistics.domain.ContinuousAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContinuousAttributeRepository extends JpaRepository<ContinuousAttribute,Long> {

    List<ContinuousAttribute> findByBaseFeatureGroupAndTargetFeatureSet(String featureGroupID, String targetFeatureSetID);
}
