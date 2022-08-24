package com.lineplus.featurestore.feature.repository.custom;

import com.lineplus.featurestore.feature.domain.dao.BaseFeatureGroupDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseFeatureGroupCustomRepository {
    List<BaseFeatureGroupDao> findBaseFeatureGroup();
}
