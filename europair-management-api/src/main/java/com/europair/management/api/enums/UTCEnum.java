package com.europair.management.api.enums;

/**
 * This Enum shows all UTC options for all countries arround the world
 */
public enum UTCEnum {

    MINUS_TWELVE(-12,0, "UTC -12:00"),

    MINUS_ELEVEN(-11,0, "UTC -11:00"),

    MINUS_TEN(-10,0, "UTC -10:00"),

    MINUS_HALF_PAST_NINE(-9,30, "UTC -09:30"),

    MINUS_NINE(-9,0, "UTC -09:00"),

    MINUS_EIGHT(-8,0, "UTC -08:00"),

    MINUS_SEVEN(-7,0, "UTC -07:00"),

    MINUS_SIX(-6,0, "UTC -06:00"),

    MINUS_FIVE(-5,0, "UTC -05:00"),

    MINUS_FOUR(-4,0, "UTC -04:00"),

    MINUS_THREE(-3,0, "UTC -03:00"),

    MINUS_HALF_PAST_TWO(-2,30, "UTC -02:30"),

    MINUS_ONE(-1,0, "UTC -01:00"),

    ZERO(0,0, "UTC 00:00"),

    ONE(1,0, "UTC 01:00"),

    TWO(2,0, "UTC 02:00"),

    THREE(3,0, "UTC 03:00"),

    FOUR(4,0, "UTC 04:00"),

    HALF_PAST_FOUR(4,30, "UTC 04:30"),

    FIVE(5,0, "UTC 05:00"),

    HALF_PAST_FIVE(5,30, "UTC 05:30"),

    QUARTER_TO_SIX(5,45, "UTC 05:45"),

    SIX(6,0, "UTC 06:00"),

    HALF_PAST_SIX(6,30, "UTC 06:30"),

    SEVEN(7,0, "UTC 07:00"),

    EIGHT(8,0, "UTC 08:00"),

    QUARTER_TO_NINE(8,45, "UTC 08:45"),

    NINE(9,0, "UTC 09:00"),

    HALF_PAST_NINE(9,30, "UTC 09:30"),

    TEN(10,0, "UTC 10:00"),

    HALF_PAST_TEN(10,30, "UTC 10:30"),

    ELEVEN(11,0, "UTC 11:00"),

    TWELVE(12,0, "UTC 12:00"),

    QUARTER_TO_THIRTEEN(12,45, "UTC 12:45"),

    THIRTEEN(13,0, "UTC 13:00"),

    FOURTEEN(14,0, "UTC 14:00");



    private Integer hours;
    private Integer minutes;
    private String description;

    UTCEnum(Integer hours, Integer minutes, String description) {
        this.hours = hours;
        this.minutes = minutes;
        this.description = description;
    }

    UTCEnum get(String name){
        UTCEnum res = null;

        if(null != name){
            for(final UTCEnum utcEnum : UTCEnum.values()){
                if (utcEnum.name().equals(name)) {
                    res = utcEnum;
                }
            }
        }

        return res;
    }


    public Integer getHours() {
        return hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getAllValues() {

        String res = "[";
        int i = 0;
        for(final UTCEnum utcEnum : UTCEnum.values()){
           res += toJSONString(utcEnum) + (UTCEnum.values().length -1 == i ? "" : ",");
           i++;
        }
        res += "]";

        return res;
    }


    private static String toJSONString(UTCEnum utcEnum) {
        return "{" +
                "\"name\":\"" + utcEnum.name() + "\"" +
                ",\"hours\":\"" + utcEnum.getHours() + "\"" +
                ",\"minutes\":\"" + utcEnum.getMinutes() +
                ",\"description\":" + utcEnum.getDescription() + "}";
    }

    @Override
    public String toString() {
        return "UTCEnum{" +
                "hours=" + hours +
                ", minutes=" + minutes +
                ", description='" + description + '\'' +
                '}';
    }
}
