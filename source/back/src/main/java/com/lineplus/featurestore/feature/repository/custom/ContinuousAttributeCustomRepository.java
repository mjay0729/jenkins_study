package com.lineplus.featurestore.feature.repository.custom;

import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContinuousAttributeCustomRepository {

    List<?> findContinuousAttributeByFeaturesId(long featuresId);

}
