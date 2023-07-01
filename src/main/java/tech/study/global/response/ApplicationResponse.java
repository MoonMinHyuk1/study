package tech.study.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tech.study.global.exception.ErrorCode;

@Data
public class ApplicationResponse<T> {

    private String code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApplicationResponse() {
        this.code = "SUCCESS";
        this.message = "성공";
    }

    public ApplicationResponse(T data) {
        this.code = "SUCCESS";
        this.message = "성공";
        this.data = data;
    }

    public ApplicationResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ApplicationResponse(ErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message;
    }
}
