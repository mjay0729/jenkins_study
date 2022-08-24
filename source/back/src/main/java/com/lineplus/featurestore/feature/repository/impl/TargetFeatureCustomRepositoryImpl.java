package com.lineplus.featurestore.feature.repository.impl;

import com.lineplus.featurestore.feature.domain.BaseFeature;
import com.lineplus.featurestore.feature.domain.BaseFeatureGroup;
import com.lineplus.featurestore.feature.domain.TargetFeature;
import com.lineplus.featurestore.feature.domain.TargetFeatureSet;
import com.lineplus.featurestore.feature.domain.dao.LabelDao;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeaturesRequestDto;
import com.lineplus.featurestore.feature.repository.TargetFeatureRepository;
import com.lineplus.featurestore.feature.repository.custom.TargetFeatureCustomRepository;
import com.lineplus.featurestore.global.utils.cassandra.CassandraConditionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class TargetFeatureCustomRepositoryImpl implements TargetFeatureCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    private final TargetFeatureRepository targetFeatureRepository;


    @Override
    public void saveLabelTargetFeature(
            TargetFeaturesRequestDto targetFeaturesRequestDto
            , TargetFeatureSet targetFeatureSet
            , BaseFeatureGroup baseFeatureGroup
            , BaseFeature baseFeature
            ) {

        TargetFeature targetFeature = TargetFeature.builder()
                .targetFeatureSet(targetFeatureSet)
                .baseFeatureGroup(baseFeatureGroup)
                .baseFeature(baseFeature)
                .featureCondition(
                        CassandraConditionUtils.getBetweenCondition(
                                baseFeature.getName()
                                , targetFeaturesRequestDto.getAttributeList().get(0).getMin()
                                , targetFeaturesRequestDto.getAttributeList().get(0).getMax()))
                .build();

        targetFeatureRepository.save(targetFeature);
    }

    @Override
    public void saveRangeTargetFeature(
            TargetFeaturesRequestDto targetFeaturesRequestDto
            , TargetFeatureSet targetFeatureSet
            , BaseFeatureGroup baseFeatureGroup
            , BaseFeature baseFeature
            , List<LabelDao> labelDaoList) {

        List<String> labelConditionList = labelDaoList.stream().map(label->label.getLabelCondition()).collect(Collectors.toList());

        TargetFeature targetFeature = TargetFeature.builder()
                .targetFeatureSet(targetFeatureSet)
                .baseFeatureGroup(baseFeatureGroup)
                .baseFeature(baseFeature)
                .featureCondition(CassandraConditionUtils.getOrCondition(labelConditionList))
                .build();

        targetFeatureRepository.save(targetFeature);
    }

    @Override
    public void saveDateTargetFeature(
            TargetFeaturesRequestDto targetFeaturesRequestDto
            , TargetFeatureSet targetFeatureSet
            , BaseFeatureGroup baseFeatureGroup
            , BaseFeature baseFeature) {

        TargetFeature targetFeature = TargetFeature.builder()
                .targetFeatureSet(targetFeatureSet)
                .baseFeatureGroup(baseFeatureGroup)
                .baseFeature(baseFeature)
                .featureCondition(
                        CassandraConditionUtils.getBetweenCondition(
                                baseFeature.getName()
                                , targetFeaturesRequestDto.getAttributeList().get(0).getStartDate()
                                , targetFeaturesRequestDto.getAttributeList().get(0).getEndDate()))
                .build();

        targetFeatureRepository.save(targetFeature);
    }
}
