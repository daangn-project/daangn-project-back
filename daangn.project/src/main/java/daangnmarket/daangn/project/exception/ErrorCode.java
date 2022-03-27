package daangnmarket.daangn.project.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INPUT_VALUE_INVALID("CM_001", "입력값이 올바르지 않습니다.", 400);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status){
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
