package com.lineplus.featurestore.feature.repository.impl;

import com.lineplus.featurestore.feature.domain.dao.BaseFeatureDao;
import com.lineplus.featurestore.feature.domain.dao.BaseFeatureGroupDao;
import com.lineplus.featurestore.feature.repository.custom.BaseFeatureGroupCustomRepository;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lineplus.featurestore.feature.domain.QBaseFeatureGroup.baseFeatureGroup;
import static com.lineplus.featurestore.feature.domain.QBaseFeature.baseFeature;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.dsl.Expressions.list;

@Repository
@RequiredArgsConstructor
public class BaseFeatureGroupCustomRepositoryImpl implements BaseFeatureGroupCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<BaseFeatureGroupDao> findBaseFeatureGroup(){
        List<BaseFeatureGroupDao> results = queryFactory
                .from(baseFeatureGroup)
                .leftJoin(baseFeature)
                .on(baseFeature.baseFeatureGroup.eq(baseFeatureGroup))
                .transform(
                        groupBy(baseFeatureGroup.id).list(
                                Projections.fields(
                                        BaseFeatureGroupDao.class
                                        , baseFeatureGroup.id.as("baseFeatureGroupId")
                                        , baseFeatureGroup.depthPath.as("depthPath")
                                        , baseFeatureGroup.name.as("baseFeatureGroupName")
                                        , baseFeatureGroup.description.as("description")
                                        , baseFeatureGroup.totalCount.as("totalCount")
                                        , GroupBy.list(
                                                Projections.fields(
                                                        BaseFeatureDao.class
                                                        , baseFeature.id.as("baseFeatureId")
                                                        , baseFeature.name.as("baseFeatureName")
                                                        , baseFeature.attributeClass.as("attributeClass")
                                                        , baseFeature.type.as("type")
                                                )
                                        ).as("baseFeature")
                                )
                        )
                );

        return results;
    }
}
