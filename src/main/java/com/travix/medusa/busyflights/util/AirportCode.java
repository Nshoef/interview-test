package com.travix.medusa.busyflights.util;


/**
 * this enum contains a mapping of airport codes to city names.
 * My assumption here is that in a real world application there would be some sort of standard mapping
 * or a supplier base mapping.
 */
public enum AirportCode {
    LHR("London Heathrow"),
    CPH("Copenhagen"),
    TLV("Tel-Aviv"),
    AMS("Amsterdam");



    private String name;

    AirportCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AirportCode forName(String name) {
        for(AirportCode code : AirportCode.values()) {
            if(code.name.equals(name)) return code;
        }
        return null;
    }
}
