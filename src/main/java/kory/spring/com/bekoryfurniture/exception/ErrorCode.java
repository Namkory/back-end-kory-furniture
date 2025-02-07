package kory.spring.com.bekoryfurniture.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    KEY_INVALID(1001, "Invalid message key")

    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
