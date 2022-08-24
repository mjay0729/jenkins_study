package com.lineplus.featurestore.feature.repository.impl;

import com.lineplus.featurestore.feature.domain.TargetFeatureSet;
import com.lineplus.featurestore.feature.domain.TargetFeatureSetTag;
import com.lineplus.featurestore.feature.domain.dto.request.EditTargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.repository.TargetFeatureSetRepository;
import com.lineplus.featurestore.feature.repository.TargetFeatureSetTagRepository;
import com.lineplus.featurestore.feature.repository.custom.TargetFeatureSetTagCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.lineplus.featurestore.feature.domain.QTargetFeatureSetTag.targetFeatureSetTag;

@Repository
@RequiredArgsConstructor
public class TargetFeatureSetTagCustomRepositoryImpl implements TargetFeatureSetTagCustomRepository {

    private final JPAQueryFactory queryFactory;
    @Autowired
    TargetFeatureSetTagRepository targetFeatureSetTagRepository;
    @Autowired
    TargetFeatureSetRepository targetFeatureSetRepository;

    @Override
    public void saveTargetFeatureSetTag(TargetFeatureSet targetFeatureSet, TargetFeatureSetRequestDto requestDto) {

        List<TargetFeatureSetTag> targetFeatureSetTagList = new ArrayList<>();
        requestDto.getTagList().forEach(tag ->{
                targetFeatureSetTagList.add(TargetFeatureSetTag.builder()
                        .name(tag)
                        .targetFeatureSet(targetFeatureSet)
                        .build());
            }
        );
        targetFeatureSetTagRepository.saveAll(targetFeatureSetTagList);
    }

    @Override
    public void updateTargetFeatureSetTag(long targetFeatureSetId, EditTargetFeatureSetRequestDto editTargetFeatureSetRequestDto) {
        List<TargetFeatureSetTag> targetFeatureSetTagList = queryFactory
                .selectFrom(targetFeatureSetTag)
                .fetch();

        targetFeatureSetTagRepository.deleteAll(targetFeatureSetTagList);

        List<TargetFeatureSetTag> saveTargetFeatureSetTagList = new ArrayList<>();
        TargetFeatureSet targetFeatureSet = targetFeatureSetRepository.getById(targetFeatureSetId);
        editTargetFeatureSetRequestDto.getTagList().forEach(tag ->{
            saveTargetFeatureSetTagList.add(TargetFeatureSetTag.builder()
                            .name(tag)
                            .targetFeatureSet(targetFeatureSet)
                            .build());
                }
        );
        targetFeatureSetTagRepository.saveAll(targetFeatureSetTagList);
    }
}
