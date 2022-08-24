package com.lineplus.featurestore.setoperation.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OperatedSetType {

    EAR("EstimationAndRealData"),
    RAR("RealDataAndRealData");

    @Getter
    private final String value;

    public String getCode() {
        return name();
    }

}
