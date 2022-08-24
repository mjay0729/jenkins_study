package com.lineplus.featurestore.feature.service.impl;

import com.lineplus.featurestore.feature.domain.dao.BaseFeatureGroupAttributeDao;
import com.lineplus.featurestore.feature.domain.dao.BaseFeatureGroupDao;
import com.lineplus.featurestore.feature.domain.dto.response.BaseFeatureGroupResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.AttributeResponseDto;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import com.lineplus.featurestore.feature.repository.custom.BaseFeatureCustomRepository;
import com.lineplus.featurestore.feature.repository.custom.BaseFeatureGroupCustomRepository;
import com.lineplus.featurestore.feature.repository.custom.ContinuousAttributeCustomRepository;
import com.lineplus.featurestore.feature.repository.custom.LabelCustomRepository;
import com.lineplus.featurestore.feature.service.BaseFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class BaseFeatureServiceImpl implements BaseFeatureService {

    @Autowired
    BaseFeatureGroupCustomRepository baseFeatureGroupCustomRepository;
    @Autowired
    BaseFeatureCustomRepository baseFeatureCustomRepository;
    @Autowired
    ContinuousAttributeCustomRepository continuousAttributeCustomRepository;
    @Autowired
    LabelCustomRepository labelCustomRepository;

    @Override
    public BaseFeatureGroupResponseDto getBaseFeatureGroupDto() {
        BaseFeatureGroupResponseDto results = new BaseFeatureGroupResponseDto();

        List<BaseFeatureGroupDao> baseFeatureGroupDaoList = baseFeatureGroupCustomRepository.findBaseFeatureGroup();

        List<String> depthPathList = new ArrayList<>();

        IntStream.range(0, baseFeatureGroupDaoList.size()).forEach(idx -> {
            BaseFeatureGroupDao baseFeatureGroupDao = baseFeatureGroupDaoList.get(idx);
            Integer depthPathIdx = depthPathList.indexOf(baseFeatureGroupDao.getDepthPath().split("/")[0]);
            if(depthPathIdx == -1){
                if (baseFeatureGroupDao.getDepthPath().split("/").length == 1) {
                    depthPathList.add(baseFeatureGroupDao.getBaseFeatureGroupName());
                    results.getBaseFeatureGroupList().add(baseFeatureGroupDao);
                }
            }else{
                results.getBaseFeatureGroupList().get(depthPathIdx).getChildren().add(baseFeatureGroupDao);
            }

        });
        return results;
    }

    @Override
    public AttributeResponseDto getAttribute(long featuresId) {
        BaseFeatureGroupAttributeDao baseFeatureGroupAttributeDao = this.baseFeatureCustomRepository.findBaseFeaturesAttribute(featuresId);
        return new AttributeResponseDto(
                baseFeatureGroupAttributeDao.getBaseFeatureGroupDepthPath()
                , baseFeatureGroupAttributeDao.getBaseFeaturesAttribute()
                , baseFeatureGroupAttributeDao.getBaseFeaturesAttribute().equals(BaseFeaturesAttribute.label) ?
                        this.labelCustomRepository.findLabelByFeaturesId(featuresId)
                        : this.continuousAttributeCustomRepository.findContinuousAttributeByFeaturesId(featuresId)
        );
    }
}
