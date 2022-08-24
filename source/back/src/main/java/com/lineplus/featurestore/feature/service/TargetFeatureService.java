package com.lineplus.featurestore.feature.service;

import com.lineplus.featurestore.feature.domain.dto.request.EditTargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetForOperationRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetOperationRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.response.CreatedTargetFeatureSetResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetDetailResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetResponseDto;
import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface TargetFeatureService {

    CreatedTargetFeatureSetResponseDto saveTargetFeatureSet(TargetFeatureSetRequestDto requestDto) throws NoSuchAlgorithmException;
    CreatedTargetFeatureSetResponseDto saveTargetFeatureSetOperation(TargetFeatureSetOperationRequestDto requestDto) throws NoSuchAlgorithmException;
    List<TargetFeatureSetResponseDto> getTargetFeatureSetList(String name, List<String> tagList, Pageable pageable);
    TargetFeatureSetDetailResponseDto getTargetFeatureSetDetailList(long targetFeatureSetId);
    void editTargetFeatureSetDetail(long targetFeatureSetId, EditTargetFeatureSetRequestDto editTargetFeatureSetRequestDto);
    void deleteTargetFeatureSetDetail(long targetFeatureSetId);
}
