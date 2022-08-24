package com.lineplus.featurestore.feature.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BaseFeaturesAttribute {
    range("Continuous/Range"),
    label("Labeled"),
    date("Date"),
    nan("Nan");
    @Getter
    private final String value;

    public String getCode() {
        return name();
    }
}
