package com.lineplus.featurestore.feature.repository.impl;

import com.lineplus.featurestore.feature.domain.dao.BaseFeatureGroupAttributeDao;
import com.lineplus.featurestore.feature.domain.dao.BaseFeatureGroupDao;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import com.lineplus.featurestore.feature.repository.custom.BaseFeatureCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.lineplus.featurestore.feature.domain.QBaseFeature.baseFeature;
import static com.lineplus.featurestore.feature.domain.QBaseFeatureGroup.baseFeatureGroup;


@Repository
@RequiredArgsConstructor
public class BaseFeatureCustomRepositoryImpl implements BaseFeatureCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public BaseFeatureGroupAttributeDao findBaseFeaturesAttribute(long featureId) {
        BaseFeatureGroupAttributeDao result = queryFactory
                .select(
                        Projections.fields(
                                BaseFeatureGroupAttributeDao.class
                                , baseFeatureGroup.depthPath.as("baseFeatureGroupDepthPath")
                                , baseFeature.attributeClass.as("baseFeaturesAttribute")
                        )
                )
                .from(baseFeature)
                .join(baseFeature.baseFeatureGroup, baseFeatureGroup)
                .where(baseFeature.id.eq(featureId))
                .fetchOne();
        return result;
    }
}
