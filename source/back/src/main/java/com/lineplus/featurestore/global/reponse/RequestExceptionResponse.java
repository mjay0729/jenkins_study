package com.lineplus.featurestore.global.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Getter
public class RequestExceptionResponse {
    private List<String> fields;
    private String message;

    private int status;

    public RequestExceptionResponse(List<String> fields, String message, int status) {
        this.status = status;
        this.message=message;
        this.fields = fields;
    }

}
