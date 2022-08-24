package com.lineplus.featurestore.global.reponse;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
@Getter
public class FeatureStoreResponse<T> {
    String result_code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer count;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    ErrorFieldMessage error;
    public FeatureStoreResponse() {
        this.result=(T) ResponseCodes.DEFAULT.getMessage();
        this.result_code=ResponseCodes.DEFAULT.getStatus();
    }

    public FeatureStoreResponse(ResponseCodes code, T result) {
        this.result=result;
        this.result_code=code.getStatus();
    }

    public FeatureStoreResponse(ResponseCodes code, List<?> result, Integer count) {
        this.count=count;
        this.result_code=code.getStatus();
        this.result=(T)result;
    }

    public FeatureStoreResponse(ResponseCodes code, List<String> fields, String message) {
        this.result_code = code.getStatus();
        this.result = (T) code.getMessage();
        ErrorFieldMessage error = new ErrorFieldMessage(fields, message);
        this.error = error;
    }
    public FeatureStoreResponse(ResponseCodes code, Result result) {
        System.out.println(result);
        if(result==null) {
            this.count=null;
            this.result=null;
        }else {
            this.count=result.getCount();
            this.result=(T)result.getMessage();

        }
        this.result_code=code.getStatus();
    }

    public FeatureStoreResponse(ResponseCodes code) {
        this.result=(T) code.getMessage();
        this.result_code=code.getStatus();
    }



    public Integer count() {
        return this.count;
    }

    public class ErrorFieldMessage {

        public List<String> fields;
        public String message;

        public ErrorFieldMessage(List<String> fields, String message){
            this.fields = fields;
            this.message = message;
        }
    }

}
