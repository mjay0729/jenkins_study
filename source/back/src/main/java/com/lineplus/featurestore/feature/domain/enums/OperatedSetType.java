package com.lineplus.featurestore.feature.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OperatedSetType {
    A("A"),
    B("B");
    @Getter
    private final String value;

    public String getCode() {
        return name();
    }
}
