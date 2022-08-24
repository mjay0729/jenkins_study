package com.lineplus.featurestore.feature.repository.custom;

import com.lineplus.featurestore.feature.domain.dao.BaseFeatureGroupAttributeDao;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseFeatureCustomRepository {
    BaseFeatureGroupAttributeDao findBaseFeaturesAttribute(long featureId);
}
