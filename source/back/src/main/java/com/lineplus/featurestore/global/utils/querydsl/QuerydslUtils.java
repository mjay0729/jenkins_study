package com.lineplus.featurestore.global.utils.querydsl;

import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class QuerydslUtils {
    public static <T> QueryResults<T> convertListToQueryResults(List<T> list, Pageable pageable){
        long offset = (pageable.getOffset() > list.size()) ? list.size() : pageable.getOffset();
        long limit = pageable.getPageSize() * (pageable.getPageNumber() +1);
        limit = (limit > list.size()) ? list.size() : limit;
        List<T> slicingList = list.subList((int)offset,(int)limit);
        return new QueryResults<T>(slicingList,limit,offset,slicingList.size());
    }
}
