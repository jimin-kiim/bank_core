package org.example.messages;

public enum ErrorMessage {
    INVALID_INPUT("유효하지 않은 입력입니다. 다시 입력해 주세요.");

    private String message;

    ErrorMessage(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}