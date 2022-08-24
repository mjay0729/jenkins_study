package com.lineplus.featurestore.statistics.service.impl;
import com.lineplus.featurestore.statistics.domain.dao.FeatureStatisticsDao;
import com.lineplus.featurestore.statistics.domain.dto.request.FeatureStatisticsRequestDto;
import com.lineplus.featurestore.statistics.domain.dto.response.StatisticsResponseDto;
import com.lineplus.featurestore.statistics.repository.ContinuousAttributeRepository;
import com.lineplus.featurestore.statistics.repository.FeatureLabelsRepository;
import com.lineplus.featurestore.statistics.repository.FeatureRangesRepository;
import com.lineplus.featurestore.statistics.repository.custom.StatisticsCustomRepository;
import com.lineplus.featurestore.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ContinuousAttributeRepository continuousAttributeRepository;

    @Autowired
    private FeatureLabelsRepository featureLabelsRepository;

    @Autowired
    private FeatureRangesRepository featureRangesRepository;

    @Autowired
    private StatisticsCustomRepository statisticsCustomRepository;

    @Override
    public List<StatisticsResponseDto> getFeatureStatisticsList(FeatureStatisticsRequestDto featureStatisticsRequestDTO, Pageable pageable) {
//        List<StatisticsResponseDto> results = new ArrayList<>();
//        //featureGroupId로 baseFeature 갖고오는 로직 필요. 현재는 featureGroupId로 통계자로 직접 직접 접근
//        List<ContinuousAttribute> continuousAttributes = this.continuousAttributeRepository.findByBaseFeatureGroupAndTargetFeatureSet(featureStatisticsRequestDTO.getFeatureGroupID(), featureStatisticsRequestDTO.getTargetFeatureSetID());
//        List<FeatureRanges> featureRanges = this.featureRangesRepository.findByBaseFeatureGroupAndTargetFeatureSet(featureStatisticsRequestDTO.getFeatureGroupID(), featureStatisticsRequestDTO.getTargetFeatureSetID());
//        List<FeatureLabels> featureLabels = this.featureLabelsRepository.findByBaseFeatureGroupAndTargetFeatureSet(featureStatisticsRequestDTO.getFeatureGroupID(), featureStatisticsRequestDTO.getTargetFeatureSetID());
//
//        if(featureStatisticsRequestDTO.isNullTargetFeatureSetID()){
//            Set<Long> baseGroupFeatureSet = new HashSet<>();
//            baseGroupFeatureSet.addAll(AbstractStatistics.getBaseFeatureGroupIdSet(continuousAttributes));
//            baseGroupFeatureSet.addAll(AbstractStatistics.getBaseFeatureGroupIdSet(featureRanges));
//            baseGroupFeatureSet.addAll(AbstractStatistics.getBaseFeatureGroupIdSet(featureLabels));
//
//            Map<Long, ArrayList<FeatureLabels>> labelsMap = FeatureLabels.groupFeatureLabelsByBaseFeature(featureLabels);
//            Map<Long, ArrayList<ContinuousAttribute>> continuousAttributeMap = ContinuousAttribute.groupContinuousAttributeByBaseFeature(continuousAttributes);
//            Map<Long, ArrayList<FeatureRanges>> rangeMap = FeatureRanges.groupFeatureRangesByBaseFeature(featureRanges);
//
//        }
        List<FeatureStatisticsDao> featureStatisticsDaos = new ArrayList<>();
        if(featureStatisticsRequestDTO.isNullTargetFeatureSetID()){
            featureStatisticsDaos = this.statisticsCustomRepository.findByBaseFeatureGroupAndTargetFeatureSet(featureStatisticsRequestDTO, pageable);
        }else{
            featureStatisticsDaos = this.statisticsCustomRepository.findByTargetFeatureSet(featureStatisticsRequestDTO, pageable);
        }
        return null;
    }
}
