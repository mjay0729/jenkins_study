package com.lineplus.featurestore.statistics.repository;

import com.lineplus.featurestore.statistics.domain.FeatureLabels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureLabelsRepository  extends JpaRepository<FeatureLabels, Long> {

    List<FeatureLabels> findByBaseFeatureGroupAndTargetFeatureSet(String featureGroupID, String targetFeatureSetID);
}
