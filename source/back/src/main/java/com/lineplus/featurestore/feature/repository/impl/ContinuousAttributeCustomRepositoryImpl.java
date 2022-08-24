package com.lineplus.featurestore.feature.repository.impl;

import com.lineplus.featurestore.feature.domain.dao.ContinuousAttributeDao;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import com.lineplus.featurestore.feature.repository.custom.ContinuousAttributeCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lineplus.featurestore.feature.domain.QBaseFeature.baseFeature;
import static com.lineplus.featurestore.statistics.domain.QContinuousAttribute.continuousAttribute;

@Repository
@RequiredArgsConstructor
public class ContinuousAttributeCustomRepositoryImpl implements ContinuousAttributeCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<?> findContinuousAttributeByFeaturesId(long featuresId) {
        List<?> results = queryFactory
                .select(
                        Projections.fields(
                                ContinuousAttributeDao.class
                                , continuousAttribute.baseFeatureGroup.id.as("baseFeatureGroupId")
                                , continuousAttribute.baseFeature.id.as("baseFeatureId")
                                , continuousAttribute.id.as("ContinuousAttributeId")
                                , continuousAttribute.min.as("min")
                                , continuousAttribute.max.as("max")
                                , continuousAttribute.average.as("average")
                        )
                )
                .from(continuousAttribute)
                .where(continuousAttribute.baseFeature.id.eq(featuresId))
                .fetch();
        return results;
    }
}
