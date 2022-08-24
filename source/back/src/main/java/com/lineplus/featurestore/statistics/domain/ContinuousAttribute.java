package com.lineplus.featurestore.statistics.domain;


import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name="continuous_attribute")
public class ContinuousAttribute extends AbstractStatistics {

    @Column(name = "min")
    private float min;

    @Column(name = "max")
    private float max;

    @Column(name = "average")
    private float average;

    @Column(name = "num_range")
    private Integer numRanges;

    public static Map<Long, ArrayList<ContinuousAttribute>> groupContinuousAttributeByBaseFeature(List<ContinuousAttribute> continuousAttributes) {
        Map<Long, ArrayList<ContinuousAttribute>> results = new HashMap<>();
        for(ContinuousAttribute target : continuousAttributes){
            if(results.containsKey(target.baseFeature.getId())){
                results.get(target.baseFeature.getId()).add(target);
            }else{
                ArrayList<ContinuousAttribute> continuousStatistics = new ArrayList<>();
                continuousStatistics.add(target);
                results.put(target.baseFeature.getId(), continuousStatistics);
            }
        }
        return results;
    }
}



