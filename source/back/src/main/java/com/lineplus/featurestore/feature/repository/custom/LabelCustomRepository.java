package com.lineplus.featurestore.feature.repository.custom;

import com.lineplus.featurestore.feature.domain.dao.LabelDao;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelCustomRepository{
    List<LabelDao> findLabelByFeaturesId(long featuresId);

    List<LabelDao> findLabelByLabelIdList(List<Long> labelIdList);
}
