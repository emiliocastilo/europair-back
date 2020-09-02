package com.europair.management.api.enums;

/**
 * This Enum shows all UTC options for all countries arround the world
 */
public enum UTCEnum {

    MINUS_TWELVE(-12,0),

    MINUS_ELEVEN(-11,0),

    MINUS_TEN(-10,0),

    MINUS_HALF_PAST_NINE(-9,30),

    MINUS_NINE(-9,0),

    MINUS_EIGHT(-8,0),

    MINUS_SEVEN(-7,0),

    MINUS_SIX(-6,0),

    MINUS_FIVE(-5,0),

    MINUS_FOUR(-4,0),

    MINUS_THREE(-3,0),

    MINUS_HALF_PAST_TWO(-2,30),

    MINUS_ONE(-1,0),

    ZERO(0,0),

    ONE(1,0),

    TWO(2,0),

    THREE(3,0),

    FOUR(4,0),

    HALF_PAST_FOUR(4,30),

    FIVE(5,0),

    HALF_PAST_FIVE(5,30),

    QUARTER_TO_SIX(5,45),

    SIX(6,0),

    HALF_PAST_SIX(6,30),

    SEVEN(7,0),

    EIGHT(8,0),

    QUARTER_TO_NINE(8,45),

    NINE(9,0),

    HALF_PAST_NINE(9,30),

    TEN(10,0),

    HALF_PAST_TEN(10,30),

    ELEVEN(11,0),

    TWELVE(12,0),

    QUARTER_TO_THIRTEEN(12,45),

    THIRTEEN(13,0),

    FOURTEEN(14,0);



    private Integer hours;
    private Integer minutes;

    UTCEnum(Integer hours, Integer minutes) {
        this.hours = hours;
        this.minutes = minutes;
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

    public static String getAllValues() {

        String res = "{'allUTCValues':[";
        for(final UTCEnum utcEnum : UTCEnum.values()){
           res += toJSONString(utcEnum) + ',';
        }
        res += "]}";

        return res;
    }


    private static String toJSONString(UTCEnum utcEnum) {
        return "{" +
                "'name':'" + utcEnum.name() + "'" +
                ",'hours':'" + utcEnum.getHours() + "'" +
                ",'minutes':'" + utcEnum.getMinutes() + "'" +
                '}';
    }

    @Override
    public String toString() {
        return "UTCEnum{" +
                "hours=" + hours +
                ", minutes=" + minutes +
                '}';
    }
}
