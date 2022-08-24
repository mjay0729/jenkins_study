package com.lineplus.featurestore.setoperation.repository.impl;

import com.lineplus.featurestore.setoperation.repository.custom.OperatedSetCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class OperatedSetCustomRepositoryImpl implements OperatedSetCustomRepository {

    private final JPAQueryFactory queryFactory;

}
