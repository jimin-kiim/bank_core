package org.example.constants;

public enum Type {
    KID("KID"),
    TEENAGER("TEENAGER"),
    ADULT("ADULT");

    private String value;

    Type (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}