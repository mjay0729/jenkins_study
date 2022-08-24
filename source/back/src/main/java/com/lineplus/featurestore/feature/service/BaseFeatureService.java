package com.lineplus.featurestore.feature.service;

import com.lineplus.featurestore.feature.domain.dto.response.BaseFeatureGroupResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.AttributeResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface BaseFeatureService {
    BaseFeatureGroupResponseDto getBaseFeatureGroupDto();
    AttributeResponseDto getAttribute(long featuresId);
}
