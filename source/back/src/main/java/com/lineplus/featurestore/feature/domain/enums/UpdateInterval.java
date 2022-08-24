package com.lineplus.featurestore.feature.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UpdateInterval {
    H("HOURLY"),
    D("DAILY"),
    W("WEEKLY"),
    M("MONTHLY");
    @Getter
    private final String value;

    public String getCode() {
        return name();
    }
}
