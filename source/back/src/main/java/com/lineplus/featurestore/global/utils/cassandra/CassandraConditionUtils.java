package com.lineplus.featurestore.global.utils.cassandra;

import com.lineplus.featurestore.global.utils.date.DateUtils;

import java.util.Date;
import java.util.List;

public class  CassandraConditionUtils {
    private static String isLessThanOrEquals = " <= ";
    private static String isGraterThanOrEquals = " >= ";

    private static String and = " and ";
    private static String in = " in ";
    private static String or = " or ";

    private static String getIsLessThanCondition(String featureName, float number){
        String result = featureName + isLessThanOrEquals + number;
        return result;
    }

    private static String getIsGraterThan(String featureName, float number){
        String result = number + isLessThanOrEquals + featureName;
        return result;
    }

    private static String getIsLessThanCondition(String featureName, String number){
        String result = featureName + isLessThanOrEquals + number;
        return result;
    }

    private static String getIsGraterThan(String featureName, String number){
        String result = number + isLessThanOrEquals + featureName;
        return result;
    }

    public static String getBetweenCondition(String featureName, float minNumber, float maxNumber){
        String result =
                getIsGraterThan(featureName,minNumber) + and +getIsLessThanCondition(featureName,maxNumber);
        return result;
    }

    public static String getBetweenCondition(String featureName, Date startDate, Date endDate){
        String result =
                getIsGraterThan(featureName, DateUtils.convertDateToStringFormat(startDate, "yyyy-MM-dd"))
                        + and
                        + getIsLessThanCondition(featureName, DateUtils.convertDateToStringFormat(endDate, "yyyy-MM-dd"));
        return result;
    }

    public static String getInCondition(String featureName, List<String> labelConditionList){
        String result =
                featureName + in + "(" +String.join(",", labelConditionList) +")";
        return result;
    }

    public static String getOrCondition(List<String> labelConditionList){
        String result = String.join(or, labelConditionList);
        return result;
    }


}
