package com.europair.management.api.enums;

/**
 * This class shows all currencies accepted by the system
 */
public enum RevTypeEnum {

    ADD(0, "ADD"),

    UPDATE(1, "UPDATE"),

    DELETE(2, "DELETE");

    private final int code;
    private final String label;

    RevTypeEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "CurrencyEnum{" +
                "code='" + code + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
