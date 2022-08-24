package com.lineplus.featurestore.feature.repository.custom;

import com.lineplus.featurestore.feature.domain.TargetFeatureSet;
import com.lineplus.featurestore.feature.domain.dto.request.EditTargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetForOperationRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetOperationRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetDetailResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
public interface TargetFeatureSetCustomRepository {
    TargetFeatureSet saveTargetFeatureSet(TargetFeatureSetRequestDto targetFeatureSetRequestDto) throws NoSuchAlgorithmException;
    TargetFeatureSet saveTargetFeatureSet(TargetFeatureSetOperationRequestDto targetFeatureSetOperationRequestDto) throws NoSuchAlgorithmException;
    TargetFeatureSet saveTargetFeatureSetForOperation(TargetFeatureSet parentTargetFeatureSet,TargetFeatureSetForOperationRequestDto targetFeatureSetOperationRequestDto) throws NoSuchAlgorithmException;
    List<TargetFeatureSetResponseDto> findTargetFeatureSetList(String name, List<String> tagList, Pageable pageable);
    TargetFeatureSetDetailResponseDto findTargetFeatureSetDetail(long targetFeatureSetId);
    void updateTargetFeatureSet(long targetFeatureSetId, EditTargetFeatureSetRequestDto editTargetFeatureSetRequestDto);
    void updateOperatedTargetFeatureSet(TargetFeatureSet targetFeatureSet, List<TargetFeatureSet> operatedTargetFeatureSet);
    void deleteTargetFeatureSetDetail(long targetFeatureSetId);
}
