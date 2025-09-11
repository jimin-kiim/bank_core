package org.example.messages;

public enum ErrorMessage {
    INVALID_INPUT("유효하지 않은 입력입니다. 다시 입력해 주세요."),
    LIMIT_EXCEEDED("출금/이체 한도를 초과했습니다.");

    private String message;

    ErrorMessage(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}