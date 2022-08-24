package com.lineplus.featurestore.statistics.repository.custom;

import com.lineplus.featurestore.statistics.domain.dao.FeatureStatisticsDao;
import com.lineplus.featurestore.statistics.domain.dto.request.FeatureStatisticsRequestDto;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface StatisticsCustomRepository {
    List<FeatureStatisticsDao> findByBaseFeatureGroupAndTargetFeatureSet(FeatureStatisticsRequestDto featureStatisticsRequestDTO, Pageable pageable);

    List<FeatureStatisticsDao> findByTargetFeatureSet(FeatureStatisticsRequestDto featureStatisticsRequestDTO, Pageable pageable);
}
