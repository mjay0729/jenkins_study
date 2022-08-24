package com.lineplus.featurestore.feature.repository.custom;

import com.lineplus.featurestore.feature.domain.BaseFeature;
import com.lineplus.featurestore.feature.domain.BaseFeatureGroup;
import com.lineplus.featurestore.feature.domain.TargetFeatureSet;
import com.lineplus.featurestore.feature.domain.dao.LabelDao;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeaturesRequestDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TargetFeatureCustomRepository {

    void saveLabelTargetFeature(
            TargetFeaturesRequestDto targetFeaturesRequestDto
            , TargetFeatureSet targetFeatureSet
            , BaseFeatureGroup baseFeatureGroup
            , BaseFeature baseFeature);

    void saveRangeTargetFeature(
            TargetFeaturesRequestDto targetFeaturesRequestDto
            , TargetFeatureSet targetFeatureSet
            , BaseFeatureGroup baseFeatureGroup
            , BaseFeature baseFeature
            , List<LabelDao> labelDaoList);

    void saveDateTargetFeature(
            TargetFeaturesRequestDto targetFeaturesRequestDto
            , TargetFeatureSet targetFeatureSet
            , BaseFeatureGroup baseFeatureGroup
            , BaseFeature baseFeature);
}
