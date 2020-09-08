package com.europair.management.api.enums;

/**
 * This classs shows all currencies accepted by the system
 */
public enum CurrencyEnum {

    AUD("AUD", "Dolar australiano"),

    CHF("CHF", "Franco suizo"),

    DKK("DKK", "Corona danesa"),

    EUR("EUR", "Euro"),

    GBP("GBP", "Libra esterlina"),

    NOK("NOK", "Corona noruega"),

    PLN("PLN", "Zloty polaco"),

    RUR("RUR", "Rublo ruso"),

    SEK("SEK", "Corona sueca"),

    THB("THB", "Baht de Tailandia"),

    USD("USD", "Dólar de EE.UU."),

    ZAR("ZAR", "Rand sudáfricano");


    private String code;
    private String description;

    CurrencyEnum(String code, String description) {
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
        for(final CurrencyEnum currencyEnum : CurrencyEnum.values()){
            res += toJSONString(currencyEnum) + (CurrencyEnum.values().length -1 == i ? "" : ",");
            i++;
        }
        res += "]";

        return res;
    }


    private static String toJSONString(CurrencyEnum currencyEnum) {
        return "{" +
                "\"name\":\"" + currencyEnum.name() + "\"" +
                ",\"code\":\"" + currencyEnum.getCode() + "\"" +
                ",\"description\":\"" + currencyEnum.getDescription()
                + "\"}";
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "CurrencyEnum{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
