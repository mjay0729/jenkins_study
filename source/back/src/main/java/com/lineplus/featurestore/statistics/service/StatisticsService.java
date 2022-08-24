package com.lineplus.featurestore.statistics.service;

import com.lineplus.featurestore.statistics.domain.dto.request.FeatureStatisticsRequestDto;
import com.lineplus.featurestore.statistics.domain.dto.response.StatisticsResponseDto;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface StatisticsService {
    List<StatisticsResponseDto> getFeatureStatisticsList(FeatureStatisticsRequestDto featureStatisticsRequestDTO, Pageable pageable);
}
