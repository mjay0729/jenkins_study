package com.lineplus.featurestore.global.reponse;

public enum
ResponseCodes {

    DEFAULT("0000","정상"),
    UPDATED("201", "Target Feature Set 정보가 업데이트 되었습니다."),
    INVALID_BODY("400","잘못된 데이터를 요청하였습니다."),
    SERVER_ERROR("500","서버에 이슈가있습니다."),
    INVALID_PARAMETER("400","잘못된 파라미터를 요청하였습니다."),
    NO_CONTENT("404","해당 정보가 없습니다.");
    private final String message;
    private final String status;

    public String getMessage() {
        return message;
    }
    public String getStatus() {
        return status;
    }
    ResponseCodes(final String status, final String message){
        this.status=status;
        this.message=message;
    }
}
