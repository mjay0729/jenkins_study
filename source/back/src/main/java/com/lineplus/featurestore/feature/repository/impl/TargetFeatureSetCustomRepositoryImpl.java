package com.lineplus.featurestore.feature.repository.impl;

import com.lineplus.featurestore.feature.domain.TargetFeatureSet;
import com.lineplus.featurestore.feature.domain.dao.SelectedBaseFeatureGroupDao;
import com.lineplus.featurestore.feature.domain.dao.SelectedTargetFeatureDao;
import com.lineplus.featurestore.feature.domain.dao.TargetFeatureSetTagDao;
import com.lineplus.featurestore.feature.domain.dto.request.EditTargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetForOperationRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetOperationRequestDto;
import com.lineplus.featurestore.feature.domain.dto.request.TargetFeatureSetRequestDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetDetailResponseDto;
import com.lineplus.featurestore.feature.domain.dto.response.TargetFeatureSetResponseDto;
import com.lineplus.featurestore.feature.domain.enums.BaseFeaturesAttribute;
import com.lineplus.featurestore.feature.domain.enums.OperatedSetType;
import com.lineplus.featurestore.feature.repository.TargetFeatureSetRepository;
import com.lineplus.featurestore.feature.repository.custom.TargetFeatureSetCustomRepository;
import com.lineplus.featurestore.global.exception.FeatureStoreRuntimeException;
import com.lineplus.featurestore.global.reponse.ResponseCodes;
import com.lineplus.featurestore.global.utils.cassandra.CassandraTableUtils;
import com.lineplus.featurestore.global.utils.querydsl.QuerydslUtils;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.lineplus.featurestore.feature.domain.QTargetFeature.targetFeature;
import static com.lineplus.featurestore.feature.domain.QTargetFeatureSet.targetFeatureSet;
import static com.lineplus.featurestore.feature.domain.QTargetFeatureSetTag.targetFeatureSetTag;
import static com.querydsl.core.group.GroupBy.groupBy;


@Repository
@RequiredArgsConstructor
public class TargetFeatureSetCustomRepositoryImpl implements TargetFeatureSetCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    private final TargetFeatureSetRepository targetFeatureSetRepository;


    @Override
    public TargetFeatureSet saveTargetFeatureSet(TargetFeatureSetRequestDto targetFeatureSetRequestDto) throws NoSuchAlgorithmException {
        TargetFeatureSet targetFeatureSet = TargetFeatureSet
                .builder()
                .name(targetFeatureSetRequestDto.getFeatureSetName())
                .description(targetFeatureSetRequestDto.getDescription())
                .updateInterval(targetFeatureSetRequestDto.getUpdateInterval())
                .isBatch(targetFeatureSetRequestDto.isBatch())
                .estimationCount(targetFeatureSetRequestDto.getEstimationCount())
                .build();
        targetFeatureSet = targetFeatureSetRepository.save(targetFeatureSet);
        targetFeatureSet.setCassandraTableName(
                CassandraTableUtils.createCassandraTableName(String.valueOf(targetFeatureSet.getId()))
        );
        return targetFeatureSetRepository.save(targetFeatureSet);
    }

    @Override
    public TargetFeatureSet saveTargetFeatureSet(TargetFeatureSetOperationRequestDto targetFeatureSetOperationRequestDto) throws NoSuchAlgorithmException {
        TargetFeatureSet targetFeatureSet = TargetFeatureSet
                .builder()
                .name(targetFeatureSetOperationRequestDto.getFeatureSetName())
                .description(targetFeatureSetOperationRequestDto.getDescription())
                .updateInterval(targetFeatureSetOperationRequestDto.getUpdateInterval())
                .isBatch(targetFeatureSetOperationRequestDto.isBatch())
                .estimationCount(targetFeatureSetOperationRequestDto.getEstimationCount())
                .build();
        targetFeatureSet = targetFeatureSetRepository.save(targetFeatureSet);
        targetFeatureSet.setCassandraTableName(
                CassandraTableUtils.createCassandraTableName(String.valueOf(targetFeatureSet.getId()))
        );
        return targetFeatureSetRepository.save(targetFeatureSet);
    }

    @Override
    public TargetFeatureSet saveTargetFeatureSetForOperation(TargetFeatureSet parentTargetFeatureSet ,TargetFeatureSetForOperationRequestDto targetFeatureSetForOperationRequestDto) throws NoSuchAlgorithmException {
        TargetFeatureSet targetFeatureSet = TargetFeatureSet
                .builder()
                .name(parentTargetFeatureSet.getName()+"-"+targetFeatureSetForOperationRequestDto.getOperatedSetType())
                .build();
        return targetFeatureSetRepository.save(targetFeatureSet);
    }



    @Override
    public List<TargetFeatureSetResponseDto> findTargetFeatureSetList(String name, List<String> tagList, Pageable pageable) {
        List<TargetFeatureSetResponseDto> results = queryFactory
                .from(targetFeatureSet)
                .leftJoin(targetFeatureSetTag)
                .on(targetFeatureSetTag.targetFeatureSet.eq(targetFeatureSet))
                .where(
                        (name != "" ? targetFeatureSet.name.contains(name) : null)
                                .or(tagList.size() != 0 ? targetFeatureSetTag.name.in(tagList) : null)
                )
                .orderBy(targetFeatureSet.createdAt.desc())
                .transform(
                        groupBy(targetFeatureSet).list(
                                Projections.fields(
                                        TargetFeatureSetResponseDto.class
                                        , targetFeatureSet.id.as("targetFeatureSetId")
                                        , targetFeatureSet.name.as("targetFeatureSetName")
                                        , targetFeatureSet.description.as("targetFeatureSetDescription")
                                        , targetFeatureSet.createdAt.as("createdAt")
                                        , targetFeatureSet.updateAt.as("updatedAt")
                                        , GroupBy.list(
                                                Projections.fields(
                                                TargetFeatureSetTagDao.class
                                                , targetFeatureSetTag.id.as("targetFeatureSetTagId")
                                                , targetFeatureSetTag.name.as("targetFeatureSetTagName")
                                                )
                                        ).as("targetFeatureSetTagDaoList")
                                )
                        )
                );
        return QuerydslUtils.convertListToQueryResults(results,pageable).getResults();
    }

    @Override
    public TargetFeatureSetDetailResponseDto findTargetFeatureSetDetail(long targetFeatureSetId) {
        List<TargetFeatureSetDetailResponseDto> result = queryFactory
                .from(targetFeatureSet)
                .join(targetFeature)
                .on(targetFeature.targetFeatureSet.eq(targetFeatureSet))
                .leftJoin(targetFeatureSetTag)
                .on(targetFeatureSetTag.targetFeatureSet.eq(targetFeatureSet))
                .where(targetFeatureSet.id.eq(targetFeatureSetId))
                .transform(
                        groupBy(targetFeatureSet).list(
                            Projections.fields(TargetFeatureSetDetailResponseDto.class
                                    , targetFeatureSet.id.as("targetFeatureSetId")
                                    , targetFeatureSet.name.as("targetFeatureSetName")
                                    , targetFeatureSet.description.as("targetFeatureSetDescription")
                                    , targetFeatureSet.createdAt.as("createdAt")
                                    , targetFeatureSet.updateAt.as("updatedAt")
                                    , targetFeatureSet.cassandraTableName.as("cassandraTableName")
                                    , GroupBy.list(
                                            Projections.fields(
                                                    TargetFeatureSetTagDao.class
                                                    , targetFeatureSetTag.id.as("targetFeatureSetTagId")
                                                    , targetFeatureSetTag.name.as("targetFeatureSetTagName")
                                            )
                                    ).as("targetFeatureSetTagDaoList")
                                    , GroupBy.list(
                                            Projections.fields(
                                                    SelectedBaseFeatureGroupDao.class
                                                    , targetFeature.baseFeatureGroup.name.as("baseFeatureGroupName")
                                                    , GroupBy.list(
                                                            Projections.fields(
                                                                    SelectedTargetFeatureDao.class
                                                                    , targetFeature.baseFeature.name.as("targetFeatureName")
                                                                    , targetFeature.featureCondition.as("value")
                                                            )
                                                    ).as("selectedTargetFeatureDaoList")
                                            )
                                    ).as("selectedBaseFeatureGroupDaoList")
                            )
                        )
                );

        return result.get(0);
    }

    @Override
    public void updateTargetFeatureSet(long targetFeatureSetId, EditTargetFeatureSetRequestDto editTargetFeatureSetRequestDto) {
        TargetFeatureSet targetFeatureSetTemp = queryFactory
                .select(targetFeatureSet)
                .where(targetFeatureSet.id.eq(targetFeatureSetId))
                .fetchOne();
        if (targetFeatureSetTemp != null) {
            targetFeatureSetRepository.save(targetFeatureSetTemp);
        }else{
            throw new FeatureStoreRuntimeException(ResponseCodes.NO_CONTENT);
        }
    }

    @Override
    public void updateOperatedTargetFeatureSet(TargetFeatureSet targetFeatureSet, List<TargetFeatureSet> operatedTargetFeatureSetList) {
        operatedTargetFeatureSetList.forEach(operatedTargetFeatureSet ->{
                if(operatedTargetFeatureSet.getOperatedSetType().equals(OperatedSetType.A)){
                    targetFeatureSet.setTargetFeatureSetA(operatedTargetFeatureSet);
                }else{
                    targetFeatureSet.setTargetFeatureSetB(operatedTargetFeatureSet);
                }
            }
        );
        this.targetFeatureSetRepository.save(targetFeatureSet);
    }

    @Override
    public void deleteTargetFeatureSetDetail(long targetFeatureSetId) {

        TargetFeatureSet targetFeatureSet1 = queryFactory
                .selectFrom(targetFeatureSet)
                .where(targetFeatureSet.id.eq(targetFeatureSetId))
                .fetchOne();
        if(targetFeatureSet1 != null) {
            targetFeatureSetRepository.delete(targetFeatureSet1);
        }else{
            throw new FeatureStoreRuntimeException(ResponseCodes.NO_CONTENT);
        }
    }

}
