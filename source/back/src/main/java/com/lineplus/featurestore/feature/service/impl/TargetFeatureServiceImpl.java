package com.lineplus.featurestore.feature.service.impl;

import com.lineplus.featurestore.feature.domain.BaseFeature;
import com.lineplus.featurestore.feature.domain.BaseFeatureGroup;
import com.lineplus.featurestore.feature.domain.TargetFeatureSet;
import com.lineplus.featurestore.feature.domain.dao.LabelDao;
import com.lineplus.featurestore.feature.domain.dto.request.*;
import com.lineplus.featurestore.feature.domain.dto.response.CreatedTargetFeatureSetResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetDetailResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetResponseDto;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import com.lineplus.featurestore.feature.repository.BaseFeatureGroupRepository;
import com.lineplus.featurestore.feature.repository.BaseFeatureRepository;
import com.lineplus.featurestore.feature.repository.custom.*;
import com.lineplus.featurestore.feature.service.TargetFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TargetFeatureServiceImpl implements TargetFeatureService {

    @Autowired
    TargetFeatureSetCustomRepository targetFeatureSetCustomRepository;
    @Autowired
    TargetFeatureCustomRepository targetFeatureCustomRepository;
    @Autowired
    BaseFeatureGroupRepository baseFeatureGroupRepository;
    @Autowired
    BaseFeatureRepository baseFeatureRepository;
    @Autowired
    LabelCustomRepository labelCustomRepository;
    @Autowired
    TargetFeatureSetTagCustomRepository targetFeatureSetTagCustomRepository;
    @Override
    @Transactional
    public CreatedTargetFeatureSetResponseDto saveTargetFeatureSet(TargetFeatureSetRequestDto requestDto) throws NoSuchAlgorithmException {
        TargetFeatureSet targetFeatureSet = this.targetFeatureSetCustomRepository.saveTargetFeatureSet(requestDto);
        requestDto.getTargetFeaturesRequestDtoList().forEach(targetFeaturesRequestDto -> {

            BaseFeatureGroup baseFeatureGroup = this.baseFeatureGroupRepository.getById(targetFeaturesRequestDto.getBaseFeatureGroupId());
            BaseFeature baseFeature = this.baseFeatureRepository.getById(targetFeaturesRequestDto.getBaseFeatureId());

            if(targetFeaturesRequestDto.getBaseFeaturesAttribute().equals(BaseFeaturesAttribute.range)){
                this.targetFeatureCustomRepository
                        .saveLabelTargetFeature(targetFeaturesRequestDto, targetFeatureSet,baseFeatureGroup,baseFeature);

            }else if(targetFeaturesRequestDto.getBaseFeaturesAttribute().equals(BaseFeaturesAttribute.label)){
                List<Long> labelIdList = targetFeaturesRequestDto.getAttributeList().stream()
                        .map(AttributeRequestDto::getLabelId)
                        .collect(Collectors.toList());

                List<LabelDao> labelDaoList = this.labelCustomRepository.findLabelByLabelIdList(labelIdList);
                this.targetFeatureCustomRepository
                        .saveRangeTargetFeature(targetFeaturesRequestDto, targetFeatureSet,baseFeatureGroup,baseFeature,labelDaoList);
            }else if(targetFeaturesRequestDto.getBaseFeaturesAttribute().equals(BaseFeaturesAttribute.date)){
                this.targetFeatureCustomRepository
                        .saveDateTargetFeature(targetFeaturesRequestDto, targetFeatureSet,baseFeatureGroup,baseFeature);
            }else{

            }
        });
        this.targetFeatureSetTagCustomRepository.saveTargetFeatureSetTag(targetFeatureSet,requestDto);

        return new CreatedTargetFeatureSetResponseDto(targetFeatureSet.getId());
    }

    @Override
    @Transactional
    public CreatedTargetFeatureSetResponseDto saveTargetFeatureSetOperation(TargetFeatureSetOperationRequestDto requestDto) throws NoSuchAlgorithmException {
        TargetFeatureSet targetFeatureSet = this.targetFeatureSetCustomRepository.saveTargetFeatureSet(requestDto);
        List<TargetFeatureSet> operatedTargetFeatureSet = new ArrayList<>();
        requestDto.getTargetFeatureSet().forEach(targetFeatureSetForOperationRequestDto -> {
            try {
                TargetFeatureSet tempTargetFeatureSet = this.targetFeatureSetCustomRepository.saveTargetFeatureSetForOperation(
                        targetFeatureSet
                        , targetFeatureSetForOperationRequestDto
                );
                targetFeatureSetForOperationRequestDto.getTargetFeaturesList().forEach(targetFeaturesRequestDto ->{
                    BaseFeatureGroup baseFeatureGroup = this.baseFeatureGroupRepository.getById(targetFeaturesRequestDto.getBaseFeatureGroupId());
                    BaseFeature baseFeature = this.baseFeatureRepository.getById(targetFeaturesRequestDto.getBaseFeatureId());

                    if(targetFeaturesRequestDto.getBaseFeaturesAttribute().equals(BaseFeaturesAttribute.range)){
                        this.targetFeatureCustomRepository
                                .saveLabelTargetFeature(targetFeaturesRequestDto, targetFeatureSet,baseFeatureGroup,baseFeature);

                    }else if(targetFeaturesRequestDto.getBaseFeaturesAttribute().equals(BaseFeaturesAttribute.label)){
                        List<Long> labelIdList = targetFeaturesRequestDto.getAttributeList().stream()
                                .map(AttributeRequestDto::getLabelId)
                                .collect(Collectors.toList());

                        List<LabelDao> labelDaoList = this.labelCustomRepository.findLabelByLabelIdList(labelIdList);
                        this.targetFeatureCustomRepository
                                .saveRangeTargetFeature(targetFeaturesRequestDto, targetFeatureSet,baseFeatureGroup,baseFeature,labelDaoList);
                    }else if(targetFeaturesRequestDto.getBaseFeaturesAttribute().equals(BaseFeaturesAttribute.date)){
                        this.targetFeatureCustomRepository
                                .saveDateTargetFeature(targetFeaturesRequestDto, targetFeatureSet,baseFeatureGroup,baseFeature);
                    }else{

                    }
                });

                operatedTargetFeatureSet.add(tempTargetFeatureSet);

                this.targetFeatureSetCustomRepository.updateOperatedTargetFeatureSet(targetFeatureSet, operatedTargetFeatureSet);

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        return new CreatedTargetFeatureSetResponseDto(targetFeatureSet.getId());
    }

    @Override
    public List<TargetFeatureSetResponseDto> getTargetFeatureSetList(String name, List<String> tagList, Pageable pageable) {
        return this.targetFeatureSetCustomRepository.findTargetFeatureSetList(name, tagList, pageable);
    }

    @Override
    public TargetFeatureSetDetailResponseDto getTargetFeatureSetDetailList(long targetFeatureSetId) {
        return this.targetFeatureSetCustomRepository.findTargetFeatureSetDetail(targetFeatureSetId);
    }

    @Override
    public void editTargetFeatureSetDetail(long targetFeatureSetId, EditTargetFeatureSetRequestDto editTargetFeatureSetRequestDto) {
        this.targetFeatureSetCustomRepository.updateTargetFeatureSet(targetFeatureSetId, editTargetFeatureSetRequestDto);
        this.targetFeatureSetTagCustomRepository.updateTargetFeatureSetTag(targetFeatureSetId, editTargetFeatureSetRequestDto);
    }

    @Override
    public void deleteTargetFeatureSetDetail(long targetFeatureSetId) {
        this.targetFeatureSetCustomRepository.deleteTargetFeatureSetDetail(targetFeatureSetId);
    }
}
