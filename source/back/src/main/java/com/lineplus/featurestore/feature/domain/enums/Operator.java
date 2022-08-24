package com.lineplus.featurestore.feature.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Operator {
    U("Union"),
    I("Interaction"),
    D("Difference");
    @Getter
    private final String value;

    public String getCode() {
        return name();
    }
}
