package com.lineplus.featurestore.global.exception;

import com.lineplus.featurestore.global.reponse.ResponseCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class FeatureStoreRuntimeException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String status;
    private String message;
    private ResponseCodes code;

    public FeatureStoreRuntimeException(String status, String message) {
        super(message);
        this.message=message;
        this.status=status;
    }
    public FeatureStoreRuntimeException(ResponseCodes code) {
        this(code.getStatus(),code.getMessage());
        this.code=code;
    }
    public FeatureStoreRuntimeException(ResponseCodes code, String Message) {
        this(code.getStatus(), Message);
        this.code = code;
    }
}
