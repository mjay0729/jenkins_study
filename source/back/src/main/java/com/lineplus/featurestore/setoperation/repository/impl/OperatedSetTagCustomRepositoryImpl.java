package com.lineplus.featurestore.setoperation.repository.impl;

import com.lineplus.featurestore.setoperation.repository.custom.OperatedSetTagCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OperatedSetTagCustomRepositoryImpl implements OperatedSetTagCustomRepository {
    private final JPAQueryFactory queryFactory;
}
