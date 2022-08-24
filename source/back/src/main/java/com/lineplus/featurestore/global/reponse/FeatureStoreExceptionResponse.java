package com.lineplus.featurestore.global.reponse;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

/**
 * @author chano
 * 2021. 8. 13.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeatureStoreExceptionResponse<T> {


    public List<String> fields;
    public String message;
    FeatureStoreResponse<T> response;

    public FeatureStoreExceptionResponse(List<String> fields, String message) {
        this.message=message;
        this.fields = fields;
    }

    public FeatureStoreExceptionResponse(ResponseCodes code, List<String> fields, String message) {
        FeatureStoreResponse res = new FeatureStoreResponse(code, fields, message);
        this.response= res;
    }

    public FeatureStoreExceptionResponse(ResponseCodes code) {
        @SuppressWarnings("rawtypes")
        FeatureStoreResponse res = new FeatureStoreResponse(code);
        this.response= res;
    }
    public FeatureStoreExceptionResponse(ResponseCodes code, String message) {
        @SuppressWarnings("rawtypes")
        FeatureStoreResponse res = new FeatureStoreResponse(code, message);
        this.response= res;
    }

}