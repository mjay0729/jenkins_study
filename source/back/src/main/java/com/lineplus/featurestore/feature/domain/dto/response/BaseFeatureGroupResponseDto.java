package com.lineplus.featurestore.feature.domain.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lineplus.featurestore.feature.domain.dao.BaseFeatureGroupDao;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseFeatureGroupResponseDto {

    private List<BaseFeatureGroupDao> baseFeatureGroupList = new ArrayList<>();

}
