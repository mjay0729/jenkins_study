package com.lineplus.featurestore.statistics.repository;
import com.lineplus.featurestore.statistics.domain.FeatureRanges;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeatureRangesRepository extends JpaRepository<FeatureRanges, Long> {

    List<FeatureRanges> findByBaseFeatureGroupAndTargetFeatureSet(String featureGroupID, String targetFeatureSetID);
}
