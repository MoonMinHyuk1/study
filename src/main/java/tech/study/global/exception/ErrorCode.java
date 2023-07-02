package tech.study.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //Common
    INTERNAL_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "C0000", "서버 내부 오류가 발생했습니다."),
    INVALID_VALUE_EXCEPTION(HttpStatus.BAD_REQUEST, "C0001", "올바르지 않은 요청 값입니다."),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "C0002", "권한이 없습니다."),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "C0003", "토큰 검증 실패"),

    //User
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "U0001", "이미 가입된 이메일입니다."),
    DONT_EQUAL_PASSWORD(HttpStatus.BAD_REQUEST, "U0002", "비밀번호가 일치하지 않습니다."),
    DONT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "U0003", "존재하지 않는 회원입니다."),
    FAIL_SIGNIN(HttpStatus.UNAUTHORIZED, "U0004", "로그인에 실패했습니다.");

    private HttpStatus status;

    private String code;

    private String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
