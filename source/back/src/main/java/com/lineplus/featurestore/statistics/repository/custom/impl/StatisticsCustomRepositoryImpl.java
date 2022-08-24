package com.lineplus.featurestore.statistics.repository.custom.impl;

import com.lineplus.featurestore.statistics.domain.dao.*;
import com.lineplus.featurestore.statistics.domain.dto.request.FeatureStatisticsRequestDto;
import com.lineplus.featurestore.statistics.repository.custom.StatisticsCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

import static com.lineplus.featurestore.feature.domain.QBaseFeature.baseFeature;
import static com.lineplus.featurestore.feature.domain.QBaseFeatureGroup.baseFeatureGroup;
import static com.lineplus.featurestore.feature.domain.QTargetFeatureSet.targetFeatureSet;
import static com.lineplus.featurestore.statistics.domain.QContinuousAttribute.continuousAttribute;
import static com.lineplus.featurestore.statistics.domain.QFeatureLabels.featureLabels;
import static com.lineplus.featurestore.statistics.domain.QFeatureRanges.featureRanges;

@Repository
@RequiredArgsConstructor
public class StatisticsCustomRepositoryImpl implements StatisticsCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<FeatureStatisticsDao> findByBaseFeatureGroupAndTargetFeatureSet(FeatureStatisticsRequestDto featureStatisticsRequestDTO, Pageable pageable) {
        List<FeatureStatisticsDao> featureStatisticsDaoList = jpaQueryFactory.select(
                Projections.fields(FeatureStatisticsDao.class,
                        baseFeatureGroup.id,
                        baseFeatureGroup.name,
                        baseFeatureGroup.description,
                        baseFeatureGroup.totalCount,
                        baseFeatureGroup.createdAt,
                        baseFeatureGroup.createdBy,
                        baseFeatureGroup.updateAt,
                        baseFeatureGroup.updatedBy,
                        baseFeatureGroup.updatedInterval,
                        Projections.fields(FeaturesDao.class,
                                baseFeature.id.as("featureID"),
                                baseFeature.baseFeatureGroup.as("featureGroupID"),
                                baseFeature.name.as("featureName"),
                                baseFeature.type.as("featureType"),
                                baseFeature.attributeClass,
                                baseFeature.description,
                                Projections.fields(LabelDao.class,
                                        featureLabels.id.as("statisticsID"),
                                        featureLabels.labels.id.as("labelID"),
                                        featureLabels.baseFeature.id.as("featureID"),
                                        featureLabels.baseFeatureGroup.id.as("featureGroupID"),
                                        featureLabels.targetFeatureSet.id.as("targetFeatureSetID"),
                                        featureLabels.targetFeature.id.as("targetFeatureID"),
                                        featureLabels.count,
                                        featureLabels.createdAt.as("creationDate"),
                                        featureLabels.labels.name.as("label"),
                                        featureLabels.labels.description,
                                        featureLabels.labels.labelCondition).as("labels"),
                                Projections.fields(FeatureRangesDao.class,
                                        featureRanges.id.as("statisticID"),
                                        featureRanges.baseFeature.id.as("featureID"),
                                        featureRanges.baseFeatureGroup.id.as("featureGroupID"),
                                        featureRanges.targetFeatureSet.id.as("targetFeatureSetID"),
                                        featureRanges.targetFeature.id.as("targetFeatureID"),
                                        featureRanges.count,
                                        featureRanges.createdAt.as("creationDate")),
                                Projections.fields(ContinuousAttributeDao.class,
                                        continuousAttribute.id.as("statisticsID"),
                                        continuousAttribute.baseFeature.id.as("featureID"),
                                        continuousAttribute.baseFeatureGroup.id.as("featureGroupID"),
                                        continuousAttribute.targetFeatureSet.id.as("targetFeatureSetID"),
                                        continuousAttribute.targetFeature.id.as("targetFeatureID"),
                                        continuousAttribute.min,
                                        continuousAttribute.max,
                                        continuousAttribute.average,
                                        continuousAttribute.numRanges,
                                        continuousAttribute.createdAt.as("creationDate")).as("features")
                        )
                )
        ).from(baseFeatureGroup)
                .leftJoin(baseFeature).on(baseFeatureGroup.id.eq(baseFeature.baseFeatureGroup.id))
                .leftJoin(featureLabels).on(featureLabels.baseFeature.id.eq(baseFeature.id))
                .leftJoin(featureRanges).on(featureRanges.baseFeature.id.eq(baseFeature.id))
                .leftJoin(continuousAttribute).on(continuousAttribute.baseFeature.id.eq(baseFeature.id))
                .where(baseFeatureGroup.id.eq(featureStatisticsRequestDTO.getFeatureGroupID()), targetFeatureSet.id.eq(featureStatisticsRequestDTO.getTargetFeatureSetID()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return featureStatisticsDaoList;
    }

    @Override
    public List<FeatureStatisticsDao> findByTargetFeatureSet(FeatureStatisticsRequestDto featureStatisticsRequestDTO, Pageable pageable) {
//        List<FeatureStatisticsDao>
        return null;
    }


}
