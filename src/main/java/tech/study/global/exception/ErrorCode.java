package tech.study.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //Common
    INTERNAL_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "C0000", "예기치 못한 오류가 발생했습니다."),
    INVALID_VALUE_EXCEPTION(HttpStatus.BAD_REQUEST, "C0001", "올바르지 않은 요청 값입니다."),

    //User
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "U0001", "이미 가입된 이메일입니다."),
    DONT_EQUAL_PASSWORD(HttpStatus.BAD_REQUEST, "U0002", "비밀번호가 일치하지 않습니다.");

    private HttpStatus status;

    private String code;

    private String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
