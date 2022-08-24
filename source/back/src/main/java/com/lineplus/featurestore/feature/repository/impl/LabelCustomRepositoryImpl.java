package com.lineplus.featurestore.feature.repository.impl;

import com.lineplus.featurestore.feature.domain.dao.LabelDao;
import com.lineplus.featurestore.feature.repository.custom.LabelCustomRepository;
import com.lineplus.featurestore.statistics.domain.dao.ContinuousAttributeDao;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lineplus.featurestore.feature.domain.QBaseFeature.baseFeature;
import static com.lineplus.featurestore.feature.domain.QLabel.label;


@Repository
@RequiredArgsConstructor
public class LabelCustomRepositoryImpl implements LabelCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<LabelDao> findLabelByFeaturesId(long featuresId) {
        List<LabelDao> results = queryFactory
                .select(
                        Projections.fields(
                                LabelDao.class
                                , label.baseFeatureGroupId.id.as("baseFeatureGroupId")
                                , label.baseFeatureId.id.as("baseFeatureId")
                                , label.id.as("labelId")
                                , label.name.as("labelName")
                                , label.description.as("description")
                                , label.labelCondition.as("labelCondition")
                        )
                )
                .from(label)
                .where(label.baseFeatureId.id.eq(featuresId))
                .fetch();
        return results;
    }

    @Override
    public List<LabelDao> findLabelByLabelIdList(List<Long> labelIdList) {
        List<LabelDao> results = queryFactory
                .select(
                        Projections.fields(
                                LabelDao.class
                                , label.baseFeatureGroupId.id.as("baseFeatureGroupId")
                                , label.baseFeatureId.id.as("baseFeatureId")
                                , label.id.as("labelId")
                                , label.name.as("labelName")
                                , label.description.as("description")
                                , label.labelCondition.as("labelCondition")
                        )
                )
                .from(label)
                .where(label.id.in(labelIdList))
                .fetch();
        return results;
    }


}
