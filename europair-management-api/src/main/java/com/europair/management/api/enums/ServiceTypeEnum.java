package com.europair.management.api.enums;

public enum ServiceTypeEnum {

    FLIGHT("VUE", "VUELO"),
    CARGO("CAR", "CARGA"),
    COMMISSION("COM", "COMISION"),
    TRANSPORT("TRA", "TRANSPORTE"),
    AIRPORT_TAX("ATX", "AIRPORT TAXES"),
    EXTRAS_ON_BOARD("EOB", "EXTRAS ON BOARD"),
    EXTRAS_ON_GROUND("EOG", "EXTRAS ON GROUND"),
    CATERING_ON_BOARD("COB", "CATERING ON BOARD"),
    CATERING_ON_GROUND("COG", "CATERING ON GROUND"),
    CANCEL_FEE("CNX", "GASTOS CANCELACION");

    private final String code;
    private final String description;

    ServiceTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String getAllValuesJSON() {

        String res = "[";
        int i = 0;
        for (final ServiceTypeEnum serviceTypeEnum : ServiceTypeEnum.values()) {
            res += toJSONString(serviceTypeEnum) + (ServiceTypeEnum.values().length - 1 == i ? "" : ",");
            i++;
        }
        res += "]";

        return res;
    }


    private static String toJSONString(ServiceTypeEnum serviceTypeEnum) {
        return "{" +
                "\"name\":\"" + serviceTypeEnum.name() + "\"" +
                ",\"code\":\"" + serviceTypeEnum.getCode() + "\"" +
                ",\"description\":\"" + serviceTypeEnum.getDescription()
                + "\"}";
    }

    /**
     * @return String of the enum
     */
    @Override
    public String toString() {
        return "ServiceTypeEnum{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
