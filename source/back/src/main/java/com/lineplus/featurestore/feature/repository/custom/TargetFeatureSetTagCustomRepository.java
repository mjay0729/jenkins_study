package com.lineplus.featurestore.feature.repository.custom;


import com.lineplus.featurestore.feature.domain.TargetFeatureSet;
import com.lineplus.featurestore.feature.domain.dto.request.EditTargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetRequestDto;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetFeatureSetTagCustomRepository {
    void saveTargetFeatureSetTag(TargetFeatureSet targetFeatureSet, TargetFeatureSetRequestDto requestDto);
    void updateTargetFeatureSetTag(long targetFeatureSetId, EditTargetFeatureSetRequestDto editTargetFeatureSetRequestDto);
}
