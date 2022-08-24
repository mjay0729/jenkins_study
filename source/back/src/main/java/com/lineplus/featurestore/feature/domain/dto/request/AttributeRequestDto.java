package com.lineplus.featurestore.feature.domain.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AttributeRequestDto {
    private long labelId;
    private float min;
    private float max;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date startDate;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date endDate;
}
